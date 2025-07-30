package codeworld.projectjava.registration.service;

import org.springframework.stereotype.Service;
import codeworld.projectjava.registration.model.User;
import codeworld.projectjava.registration.repository.UserRepository;
import codeworld.projectjava.registration.repository.UserResidenceRepository;
import java.util.*;
// import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserResidenceRepository userResidenceRepository;

    public UserService(UserRepository userRepository, 
                     UserResidenceRepository userResidenceRepository) {
        this.userRepository = userRepository;
        this.userResidenceRepository = userResidenceRepository;
    }

    public User createUser(User user) {
        User savedUser = userRepository.save(user);
         
        return savedUser;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Stream<User> getAllUsers() {
        return userRepository.findAll().stream();
               
    }

    public void deleteUser(Long id) {
        userResidenceRepository.deleteByUserId(id);
        userRepository.delete(id);
    }       
}
