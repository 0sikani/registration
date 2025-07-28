package codeworld.projectjava.registration.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import codeworld.projectjava.registration.model.UserResidence;
import codeworld.projectjava.registration.service.UserResidenceService;
import java.util.List;

@RestController
@RequestMapping("/api/user-residences")
public class UserResidenceController {
    private final UserResidenceService userResidenceService;

    public UserResidenceController(UserResidenceService userResidenceService) {
        this.userResidenceService = userResidenceService;
    }

    @PostMapping
    public ResponseEntity<UserResidence> createAssociation(@RequestBody UserResidence userResidence) {
        return ResponseEntity.ok(userResidenceService.createAssociation(userResidence));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserResidence>> getAssociationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userResidenceService.getAssociationsByUser(userId));
    }

    @GetMapping("/residence/{residenceId}")
    public ResponseEntity<List<UserResidence>> getAssociationsByResidence(@PathVariable Long residenceId) {
        return ResponseEntity.ok(userResidenceService.getAssociationsByResidence(residenceId));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAssociation(
            @RequestParam Long userId,
            @RequestParam Long residenceId) {
        userResidenceService.deleteAssociation(userId, residenceId);
        return ResponseEntity.noContent().build();
    }
}