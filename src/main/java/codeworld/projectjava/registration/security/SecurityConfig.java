// package codeworld.projectjava.registration.security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// // import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// // import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
// // import org.springframework.security.core.userdetails.User;


// @Configuration
// @EnableWebSecurity
// // @EnableMethodSecurity(prePostEnabled = true) // For @PreAuthorize
// public class SecurityConfig {

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder(12);
//     }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**")) // Disable for APIs
//             .headers(headers -> headers
//                 .contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self'"))
//             )
//             .authorizeHttpRequests(authz -> authz
//                 .requestMatchers("/", "/login", "/public/**").permitAll()
//                 .requestMatchers("/basic").hasRole("BASIC")
//                 .requestMatchers("/admin").hasRole("ADMIN")
//                 .anyRequest().authenticated()
//             )
//             .httpBasic(basic -> {})
//             .formLogin(form -> form
//                 .permitAll()
//             )
//             .logout(logout -> logout
//                 .logoutUrl("/logout")
//                 .logoutSuccessUrl("/login?logout")
//                 .permitAll()
//             )
//             .sessionManagement(session -> session
//                 .maximumSessions(1)
//                 .expiredUrl("/login?expired")
//             );

//         return http.build();
//     }
// }


package codeworld.projectjava.registration.security;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import codeworld.projectjava.registration.repository.UserRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Enables @PreAuthorize annotations
public class SecurityConfig {

    // Strong password hashing (adjust strength as needed)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    
    @Bean
public UserDetailsService userDetailsService(UserRepository userRepository) {
    return email -> {
        return userRepository.findUserByEmail(email)
            .map(user -> org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName()) // or user.getUsername() depending on your schema
                .password(user.getPassWord())
                .build())
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }; //the method email(String) is undefined for the type User.UserBuilder
}


    //Main security configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Security headers
            .headers(headers -> headers
                .xssProtection(xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
                .contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self'; script-src 'self' 'unsafe-inline'"))
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
            )
            
            // Authorization rules
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/", "/home", "/public/**").permitAll()
                .requestMatchers("/basic").hasAnyRole("BASIC", "ADMIN")
                .requestMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            
            // Form login configuration
            .httpBasic(basic -> {})
            .formLogin(form -> form
            .permitAll()
            )
            
            // Logout configuration
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            
            // Session management
            .sessionManagement(session -> session
                .sessionFixation().changeSessionId()
                .maximumSessions(1)
                .expiredUrl("/login?expired")
            )
            
            // CSRF configuration (enabled by default in Spring Security 6)
            .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**")); // Disable for APIs if needed
        
        return http.build();
    }
}
  

// Topic: Spring Framework – Bean configuration and dependency injection
// Topics: Spring Dependency Injection, Java Interfaces
// Topics: Java Lambdas, Functional Interfaces
// Topics: Spring Data JPA, Java Optional, Repository pattern
// Topics: Java Streams & Optional, Builder Pattern, Spring Security UserDetails
