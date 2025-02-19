package com.cryptomaximizer.crypto_maximization_app;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity saveUser(String name) {
        userRepository.deleteAll(); // Remove the previous name from the table
        return userRepository.save(new UserEntity(name)); // Add the new name to the table
    }

    public Optional<UserEntity> getLatestName() {
        return userRepository.findAll().stream().findFirst(); // Return the name saved in the table
    }
}
