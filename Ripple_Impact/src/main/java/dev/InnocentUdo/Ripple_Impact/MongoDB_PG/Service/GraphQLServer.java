package dev.InnocentUdo.Ripple_Impact.MongoDB_PG.Service;

import dev.InnocentUdo.Ripple_Impact.MongoDB_PG.Model.LoginRequest;
import dev.InnocentUdo.Ripple_Impact.MongoDB_PG.Model.User;
import dev.InnocentUdo.Ripple_Impact.MongoDB_PG.Repository.UserRepository;
import dev.InnocentUdo.Ripple_Impact.MongoDB_PG.Utils.JwtUtility;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;


public class GraphQLServer {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtUtility jwtUtility;

    public GraphQLServer(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtility jwtUtility){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtility = jwtUtility;
    }

    public GraphQLSchema getGraphQLSchema(){
        File schemaFile = getResource("schema.graphqls");
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring runtimeWiring = null;
        buildRuntimeWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildRuntimeWiring(){
        return RuntimeWiring.newRuntimeWiring()
                .type("Mutation", typeWiring -> typeWiring
                        .dataFetcher("login", environment -> {
                            LoginRequest loginRequest = environment.getArgument("loginRequest");
                            return userRepository.findByUsername(loginRequest.getUsername())
                            bCryptPasswordEncoder.encode(loginRequest.getPassword());
                        })
                        .dataFetcher("register", environment -> {
                            String username = environment.getArgument("username");
                            String password = environment.getArgument("password");
                            User user = userRepository.findByUsername(username);

                            if (username != null && bCryptPasswordEncoder.matches(password, user.getPassword()))
                                return jwtUtility.generateToken(username);
                            else return null;
                        }));
    }
    private File getResource(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }
}
