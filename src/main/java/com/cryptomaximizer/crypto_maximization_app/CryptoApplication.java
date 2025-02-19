package com.cryptomaximizer.crypto_maximization_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.cryptomaximizer.crypto_maximization_app")
@RestController
public class CryptoApplication {

	public static void main(String[] args) {

		SpringApplication.run(CryptoApplication.class, args);
	}

	@GetMapping
	public String start() {
		return "Back end is running";
	}

}
