package codeworld.projectjava.registration.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import codeworld.projectjava.registration.model.User;
import codeworld.projectjava.registration.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository userRepo;

    public UserController(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    @PostMapping 
    public ResponseEntity<Map<String, Object>> storeUser(@RequestBody User user, @RequestParam Long residence_id){
        Map<String, Object> response = new HashMap<>();
        response.put("respose", userRepo.save(user, residence_id)) ;
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public List<User> getUsers(){
        return userRepo.getUsers();
    }


    @GetMapping
    public ResponseEntity<Page<User>> getUserss(Pageable pageable) {
    Page<User> page = userRepo.getUsers(
        PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            pageable.getSortOr(Sort.by(Sort.Direction.DESC, "amount"))));
    return ResponseEntity.ok(page);
}


    @GetMapping("/{id}")
    public ResponseEntity<User> getSingleUser(@PathVariable Long id){
        Optional<User> user = userRepo.getUserById(id);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userRepo.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
