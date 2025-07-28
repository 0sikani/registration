package codeworld.projectjava.registration.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import codeworld.projectjava.registration.model.Residence;
import codeworld.projectjava.registration.service.ResidenceService;
// import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/residences")
public class ResidenceController {
    private final ResidenceService residenceService;

    public ResidenceController(ResidenceService residenceService) {
        this.residenceService = residenceService;
    }

    @PostMapping
    public ResponseEntity<Residence> createResidence(@RequestBody Residence residence) {
        return ResponseEntity.ok(residenceService.createResidence(residence));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Residence>> getResidence(@PathVariable Long id) {
        Optional<Residence> residence = residenceService.getResidenceById(id);
        return residence != null ? ResponseEntity.ok(residence) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Stream<Residence>> getAllResidences() {
        return ResponseEntity.ok(residenceService.getAllResidences());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResidence(@PathVariable Long id) {
        residenceService.deleteResidence(id);
        return ResponseEntity.noContent().build();
    }
}