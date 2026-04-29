package com.shubham.online.food.ordering.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Fix 5: /auth/** explicitly public — anyRequest pe depend mat karo
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/api/admin/**")
                        .hasAnyAuthority("ROLE_RESTAURANT_OWNER", "ROLE_ADMIN")
                        .requestMatchers("/api/food/search").permitAll()
                        .requestMatchers("/api/restaurants/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/api/payment/**").authenticated()
                        .anyRequest().permitAll()
                )

                .addFilterBefore(new JwtTokenValidator(jwtSecret), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        return (HttpServletRequest request) -> {
            CorsConfiguration cfg = new CorsConfiguration();


            cfg.setAllowedOrigins(Arrays.asList(
                    "https://zosh-food.vercel.app",
                    "https://shubhamraj01.in",
                    "https://admin.shubhamraj01.in",
                    "http://localhost:3000",
                    "http://localhost:3001",
                    "http://localhost:3002",
                    "http://localhost:3003",
                    "http://127.0.0.1:5500",
                    "http://localhost:5500"
            ));


            cfg.setAllowedMethods(List.of(
                    "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"
            ));

            cfg.setAllowCredentials(true);
            cfg.setAllowedHeaders(List.of("*"));
            cfg.setExposedHeaders(List.of("Authorization"));
            cfg.setMaxAge(3600L);
            return cfg;
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}