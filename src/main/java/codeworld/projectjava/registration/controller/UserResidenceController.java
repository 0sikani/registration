package codeworld.projectjava.registration.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import codeworld.projectjava.registration.model.UserResidence;
import codeworld.projectjava.registration.repository.UserResidenceRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/userresidence")
public class UserResidenceController {
    private final UserResidenceRepository urRepo; 

    public UserResidenceController(UserResidenceRepository urRepo){
        this.urRepo = urRepo;
    }

    @GetMapping
    public List<UserResidence> getUserResidence() {
        return urRepo.getUserResidence();
    }
    

    @PostMapping
    public ResponseEntity<UserResidence> storeUserResidence(@RequestBody UserResidence userResidence){
        UserResidence storedResidence = urRepo.storeUserResidence(userResidence);
        return ResponseEntity.ok(storedResidence);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserResidence(@PathVariable Long id){
        urRepo.deleteUserResidence(id);
        return ResponseEntity.noContent().build();
    }

    
    
}
