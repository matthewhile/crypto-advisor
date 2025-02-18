package com.cryptomaximizer.crypto_maximization_app;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Login")


public class Login{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String Username;
	private String Password;
}


