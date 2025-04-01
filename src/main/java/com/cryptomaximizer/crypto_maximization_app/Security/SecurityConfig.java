package com.cryptomaximizer.crypto_maximization_app.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cryptomaximizer.crypto_maximization_app.Service.UserService;

@Configuration
public class SecurityConfig {

    private final UserService userService;

    @Autowired
    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    // Allows Spring Security to retrieve user credentials
    public UserDetailsService userDetailsService() {
        return userService;
    }

    // Fetches user details and checks passwords with passwordEncoder() to authenticate the user
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // Encode the user's password
    @Bean public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(httpForm ->{
                    httpForm.loginPage("/login.html").permitAll();
                    httpForm.loginProcessingUrl("/login");
                    httpForm.defaultSuccessUrl("/index.html", true);
                })
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/register.html", "/login.html", "/passwordrecovery.html", "usernamerecovery.html", "/css/**", "/js/**", "/images/**").permitAll();
                    registry.requestMatchers("/api/register", "/login", "/api/user", "/api/expenses", "/api/expenses/add", "/api/expenses/delete/{id}", "/api/preferences",
                            "/api/crypto/**", "/api/recommendations").permitAll();
                    registry.anyRequest().authenticated();
                })
                .build();
    }

}
