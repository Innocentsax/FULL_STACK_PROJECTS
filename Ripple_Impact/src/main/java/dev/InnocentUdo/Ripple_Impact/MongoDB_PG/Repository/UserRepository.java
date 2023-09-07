package dev.InnocentUdo.Ripple_Impact.MongoDB_PG.Repository;

import dev.InnocentUdo.Ripple_Impact.MongoDB_PG.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);
}
