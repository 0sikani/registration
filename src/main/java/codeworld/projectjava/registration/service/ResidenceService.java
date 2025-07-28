package codeworld.projectjava.registration.service;

import org.springframework.stereotype.Service;
import codeworld.projectjava.registration.model.Residence;
import codeworld.projectjava.registration.repository.ResidenceRepository;
import codeworld.projectjava.registration.repository.UserResidenceRepository;
import java.util.*;
// import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ResidenceService {
    private final ResidenceRepository residenceRepository;
    private final UserResidenceRepository userResidenceRepository;
    // private final UserService userService;

    public ResidenceService(ResidenceRepository residenceRepository,
                          UserResidenceRepository userResidenceRepository,
                          UserService userService) {
        this.residenceRepository = residenceRepository;
        this.userResidenceRepository = userResidenceRepository;
        // this.userService = userService;
    }

    public Residence createResidence(Residence residence) {
        Residence resdence = new Residence();
        resdence.setPhysicalAddress(residence.getPhysicalAddress());
        resdence.setDigitalAddress(residence.getDigitalAddress());
        resdence.setCity(residence.getCity());
        Residence savedResidence = residenceRepository.save(resdence);
        
        if(savedResidence.getId() != null){
            resdence.setId(savedResidence.getId());
        }
        
        return savedResidence;
    }

    public Optional<Residence> getResidenceById(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }

        return residenceRepository.findById(id);
}

    public Stream<Residence> getAllResidences() {
        return residenceRepository.findAll().stream();
    }

    public void deleteResidence(Long id) {
        userResidenceRepository.deleteByResidenceId(id);
        residenceRepository.delete(id);
    }

}