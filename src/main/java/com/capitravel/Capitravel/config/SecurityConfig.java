package com.capitravel.Capitravel.config;

import com.capitravel.Capitravel.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import com.capitravel.Capitravel.service.impl.CustomUserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas accesibles para todos
                        .requestMatchers("/users/register", "/auth/login", "/loginError", "/logout").permitAll()

                        // Rutas GET accesibles para ADMIN y USER (ver usuarios)
                        .requestMatchers(HttpMethod.GET, "/users/**").hasAnyRole(ADMIN, USER)

                        // Rutas GET accesibles para todos (por ejemplo, categorías, experiencias, propiedades)
                        .requestMatchers(HttpMethod.GET, "/categories/**", "/experiences/**", "/properties/**").permitAll()

                        // Rutas POST, PUT, DELETE solo accesibles para ADMIN
                        .requestMatchers(HttpMethod.POST, "/categories/**", "/experiences/**", "/properties/**").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/categories/**", "/experiences/**", "/properties/**").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/categories/**", "/experiences/**", "/properties/**").hasRole(ADMIN)

                        // Resto de las rutas requieren al menos el rol USER
                        .anyRequest().hasRole(USER)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }
}
