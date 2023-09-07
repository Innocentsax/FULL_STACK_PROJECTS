package dev.InnocentUdo.Ripple_Impact.MongoDB_PG.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String username;
    private String password;

    public User(String username, String encode) {
    }
}
