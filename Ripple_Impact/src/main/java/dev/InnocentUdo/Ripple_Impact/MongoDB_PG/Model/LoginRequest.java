package dev.InnocentUdo.Ripple_Impact.MongoDB_PG.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String username;
    private String password;
}
