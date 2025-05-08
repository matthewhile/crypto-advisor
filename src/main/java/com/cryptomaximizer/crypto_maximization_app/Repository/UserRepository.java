package com.cryptomaximizer.crypto_maximization_app.Repository;

import java.util.Optional;
import com.cryptomaximizer.crypto_maximization_app.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Find a user by username
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
