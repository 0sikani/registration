package codeworld.projectjava.registration.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import codeworld.projectjava.registration.model.AuthRequest;
import codeworld.projectjava.registration.model.AuthResponse;
import codeworld.projectjava.registration.model.User;
import codeworld.projectjava.registration.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<AuthResponse> register(
        @RequestParam("userName") String userName,
        @RequestParam("email") String email,
        @RequestParam("phone") String phone,
        @RequestParam("passWord") String passWord
      ){
            User user = new User();
            user.setUserName(userName);
            user.setEmail(email);
            user.setPhone(phone);
            user.setPassWord(passWord);

            return ResponseEntity.ok(authService.register(user));
       }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.authenticate(authRequest));
    }
}