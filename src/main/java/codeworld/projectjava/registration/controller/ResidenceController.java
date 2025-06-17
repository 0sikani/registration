package codeworld.projectjava.registration.controller;

// import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import codeworld.projectjava.registration.model.Residence;
import codeworld.projectjava.registration.repository.ResidenceRepository;;

@RestController
@RequestMapping("/api/residence")
public class ResidenceController {

    private final ResidenceRepository resRepo;

    public ResidenceController(ResidenceRepository resRepo){
        this.resRepo = resRepo;
    }

    @PostMapping
    public Residence storeResidence(@RequestBody Residence residence){
        return resRepo.save(residence);
    }

    @GetMapping 
    public List<Residence> getResidence(){
        return resRepo.getResidence();
    }

    @GetMapping("/{id}")
     public ResponseEntity<Residence> getSingleResidence(@PathVariable Long id){
        Optional<Residence> residence =  resRepo.getResidenceById(id);
        return residence.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResidence(@PathVariable Long id){
        resRepo.deleteResidence(id);
        return ResponseEntity.noContent().build();
    }
}
