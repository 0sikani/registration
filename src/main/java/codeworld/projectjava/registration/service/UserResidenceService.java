package codeworld.projectjava.registration.service;

import org.springframework.stereotype.Service;
import codeworld.projectjava.registration.model.UserResidence;
import codeworld.projectjava.registration.repository.UserResidenceRepository;
import codeworld.projectjava.registration.repository.UserRepository;
import codeworld.projectjava.registration.repository.ResidenceRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserResidenceService {
    private final UserResidenceRepository userResidenceRepository;
    private final UserRepository userRepository;
    private final ResidenceRepository residenceRepository;

    public UserResidenceService(UserResidenceRepository userResidenceRepository,
                              UserRepository userRepository,
                              ResidenceRepository residenceRepository) {
        this.userResidenceRepository = userResidenceRepository;
        this.userRepository = userRepository;
        this.residenceRepository = residenceRepository;
    }

    public UserResidence createAssociation(UserResidence userResidence) {
        // Validate user exists
        if (!userRepository.findById(userResidence.getUserId()).isPresent()) {
            throw new RuntimeException("User not found");
        }

        // Validate residence exists
        if (!residenceRepository.findById(userResidence.getResidenceId()).isPresent()) {
            throw new RuntimeException("Residence not found");
        }

        // UserResidence userResidence = new UserResidence();
        // userResidence.setUserId(userResidence.getUserId());
        // userResidence.setResidenceId(userResidence.getResidenceId());
        
        userResidenceRepository.save(userResidence);
        
        return userResidence;
    }

    public List<UserResidence> getAssociationsByUser(Long userId) {
        return userResidenceRepository.findByUserId(userId).stream().collect(Collectors.toList());
    }

    public List<UserResidence> getAssociationsByResidence(Long residenceId) {
        return userResidenceRepository.findByResidenceId(residenceId).stream().collect(Collectors.toList());
    }

    public void deleteAssociation(Long userId, Long residenceId) {
        userResidenceRepository.deleteAssociation(userId, residenceId);
    }
}
