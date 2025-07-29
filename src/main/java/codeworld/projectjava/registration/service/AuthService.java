package codeworld.projectjava.registration.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import codeworld.projectjava.registration.model.AuthRequest;
import codeworld.projectjava.registration.model.AuthResponse;
import codeworld.projectjava.registration.model.User;
import codeworld.projectjava.registration.repository.UserRepository;
import codeworld.projectjava.registration.security.JwtTokenProvider;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthService(UserRepository userRepository, 
                     PasswordEncoder passwordEncoder,
                     JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public AuthResponse register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User usr = new User();
        usr.setUserName(user.getUserName());
        usr.setPhone(user.getPhone());
        usr.setEmail(user.getEmail());
        usr.setRole(user.getRole());
        usr.setPassWord(passwordEncoder.encode(user.getPassWord()));
        
        User savedUser = userRepository.save(usr);
        
        String token = tokenProvider.generateToken(savedUser.getEmail());
        
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUser(convertToAuthuser(savedUser));
        
        return response;
    }

    public AuthResponse authenticate(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassWord())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = tokenProvider.generateToken(user.getEmail());
        
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUser(convertToAuthuser(user));
        
        return response;
    }

    private User convertToAuthuser(User user) {
        User usr = new User();
        usr.setId(user.getId());
        usr.setUserName(user.getUserName());
        usr.setEmail(user.getEmail());
        usr.setRole(user.getRole());
        usr.setPhone(user.getPhone());
        return usr;
    }
}