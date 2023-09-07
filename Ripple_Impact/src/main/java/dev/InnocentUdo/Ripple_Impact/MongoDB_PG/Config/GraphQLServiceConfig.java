package dev.InnocentUdo.Ripple_Impact.MongoDB_PG.Config;

import dev.InnocentUdo.Ripple_Impact.MongoDB_PG.Repository.UserRepository;
import dev.InnocentUdo.Ripple_Impact.MongoDB_PG.Service.GraphQLServer;
import dev.InnocentUdo.Ripple_Impact.MongoDB_PG.Utils.JwtUtility;
import jakarta.servlet.Servlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GraphQLServiceConfig {

    @Bean
    public GraphQLServer graphQLServer(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtility jwtUtility){
        return new GraphQLServer(userRepository, bCryptPasswordEncoder, jwtUtility);
    }


    @Bean
    public ServletRegistrationBean graphQLServletRegistrationBean(GraphQLServer graphQLServer){
        return new ServletRegistrationBean((Servlet) graphQLServer, "/graphql");
    }
}
