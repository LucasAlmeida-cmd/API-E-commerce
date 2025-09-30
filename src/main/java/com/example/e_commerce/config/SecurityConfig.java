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
                 .authorizeHttpRequests(auth -> {

                     // --- 1. ENDPOINTS PÚBLICOS ---
                     auth.requestMatchers(HttpMethod.POST, "/login").permitAll();
                     auth.requestMatchers(HttpMethod.POST, "/usuarios").permitAll();
                     auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();
                     auth.requestMatchers(HttpMethod.GET, "/produtos").permitAll();
                     auth.requestMatchers(HttpMethod.GET, "/produtos/**").permitAll();

                     // --- 2. ENDPOINTS DE ADMIN ---
                     auth.requestMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN");
                     auth.requestMatchers(HttpMethod.PUT, "/produtos/**").hasRole("ADMIN");
                     auth.requestMatchers(HttpMethod.DELETE, "/produtos/**").hasRole("ADMIN");
                     auth.requestMatchers(HttpMethod.GET, "/pedidos/**").hasRole("ADMIN"); // CUIDADO: Esta regra bloqueia a visualização de pedidos para usuários normais. Podemos ajustar depois.

                     // --- 3. ENDPOINTS DE USUÁRIO AUTENTICADO ---
                     auth.requestMatchers(HttpMethod.POST, "/pedidos").authenticated();
                     auth.requestMatchers(HttpMethod.GET, "/pedidos/meus").authenticated();

                     // --- 4. REGRA FINAL ---
                     auth.anyRequest().authenticated();
                 })
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
