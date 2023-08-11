package com.facebook.facebook.services.servicesInterfaces;

import com.facebook.facebook.entities.FacebookUser;

import java.util.List;
import java.util.Optional;

public interface IUserServices {
    FacebookUser registerUser(FacebookUser newUser);
    List<FacebookUser> getAllUsers();
    Optional<FacebookUser> getUserById(Long id);
    FacebookUser updateUser(FacebookUser user);
    Optional<FacebookUser> loginUser(String email, String password);
    void deleteUser(Long id);
    Long getUserIdByUsername(String name);

    //FacebookUser getUserByUsername(String username);
}
