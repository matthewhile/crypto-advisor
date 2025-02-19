package com.cryptomaximizer.crypto_maximization_app;

import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserEntity  createUser(@RequestBody UserEntity user) {
        return userService.saveUser(user.getName());
    }

    @GetMapping
    public UserEntity getLatestUser() {
        return userService.getLatestName().orElse(new UserEntity("No name found"));
    }
}
