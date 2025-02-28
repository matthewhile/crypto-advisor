package com.cryptomaximizer.crypto_maximization_app.Repository;

import java.util.Optional;
import com.cryptomaximizer.crypto_maximization_app.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
}
