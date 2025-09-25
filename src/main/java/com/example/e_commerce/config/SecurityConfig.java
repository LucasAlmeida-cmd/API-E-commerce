 package com.example.e_commerce.config;

 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.security.config.annotation.web.builders.HttpSecurity;
 import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
 import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
 import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(httpForm -> {
//                    httpForm.loginPage("/login")
//                            .usernameParameter("email")
//                            .passwordParameter("password")
//                            .successHandler(loginSuccessHandler)
//                            .failureUrl("/login?error")
//                            .permitAll();
//                })
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/usuarios/**").permitAll();
                    registry.requestMatchers("/css/**", "/login","/swagger-ui/**", "/v3/api-docs/**").permitAll();
                    registry.anyRequest().authenticated();
                })
                .build();
    }
}
