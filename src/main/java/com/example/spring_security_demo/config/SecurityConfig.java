package com.example.spring_security_demo.config;

import com.example.spring_security_demo.entity.Permissions;
import com.example.spring_security_demo.entity.Role;
import com.example.spring_security_demo.filters.JwtAuthFilter;
import com.example.spring_security_demo.service.CustomUserDetailsService;
import com.example.spring_security_demo.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

// https://docs.spring.io/spring-security/reference/servlet/architecture.html
// https://docs.spring.io/spring-security/reference/servlet/authorization/architecture.html
// https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/basic.html

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

//    @Autowired
//    JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/authenticate","/greet", "/").permitAll()
                                .requestMatchers("/api/users/register").permitAll()

//                                .requestMatchers("/health").hasRole(Role.USER.name()) // Role Based

//                                .requestMatchers(HttpMethod.GET, "/greet/**").hasAuthority(Permissions.DEMO_READ.name()) // Permission Based
//                                .requestMatchers(HttpMethod.POST, "/greet/**").hasAuthority(Permissions.DEMO_WRITE.name())
//                                .requestMatchers(HttpMethod.DELETE, "/greet/**").hasAuthority(Permissions.DEMO_DELETE.name())
                                .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);

//        provider.setUserDetailsService(userDetailsService); // this one is deprecated
        provider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(provider);
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter(JWTUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        return new JwtAuthFilter(jwtUtil, customUserDetailsService);
    }

}
