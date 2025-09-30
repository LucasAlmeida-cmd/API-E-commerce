 package com.example.e_commerce.config;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.http.HttpMethod;
 import org.springframework.security.config.annotation.web.builders.HttpSecurity;
 import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
 import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
 import org.springframework.security.web.SecurityFilterChain;


 import org.springframework.security.authentication.AuthenticationManager;
 import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
 import org.springframework.security.config.http.SessionCreationPolicy;
 import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 import org.springframework.security.crypto.password.PasswordEncoder;
 import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

 @Configuration
 @EnableWebSecurity
 public class SecurityConfig {

     @Autowired
     private SecurityFilter securityFilter;

     @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         return http
                 .csrf(AbstractHttpConfigurer::disable)
                 .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .authorizeHttpRequests(auth -> auth

                         .requestMatchers(HttpMethod.POST, "/login").permitAll()
                         .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                         .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                         .requestMatchers(HttpMethod.GET, "/pedidos/meus").authenticated()

                         .requestMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN")
                         .requestMatchers(HttpMethod.PUT, "/produtos/**").hasRole("ADMIN")
                         .requestMatchers(HttpMethod.DELETE, "/produtos/**").hasRole("ADMIN")
                         .requestMatchers(HttpMethod.GET, "/pedidos/**").hasRole("ADMIN")


                         .requestMatchers(HttpMethod.GET, "/produtos").permitAll()
                         .requestMatchers(HttpMethod.GET, "/produtos/**").permitAll()


                         .requestMatchers(HttpMethod.POST, "/pedidos").authenticated()

                         .anyRequest().authenticated()
                 )
                 .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                 .build();
     }

     @Bean
     public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
         return configuration.getAuthenticationManager();
     }

     @Bean
     public PasswordEncoder passwordEncoder() {
         return new BCryptPasswordEncoder();
     }
 }
