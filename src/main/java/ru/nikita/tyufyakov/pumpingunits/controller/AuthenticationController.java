package ru.nikita.tyufyakov.pumpingunits.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.nikita.tyufyakov.pumpingunits.controller.AuthRegisterModel.AuthenticationRequest;
import ru.nikita.tyufyakov.pumpingunits.controller.AuthRegisterModel.RegisterRequest;
import ru.nikita.tyufyakov.pumpingunits.controller.AuthRegisterModel.errorMessage;
import ru.nikita.tyufyakov.pumpingunits.service.AuthenticationService;
import ru.nikita.tyufyakov.pumpingunits.service.UserService;


@RestController
@RequestMapping("/api/simple/auth/")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userService.isExist(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new errorMessage("username \'" + request.getEmail() + "\' is already taken"));
        } else {
            return ResponseEntity.ok(authenticationService.register(request));
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.ok(authenticationService.authenticate(request));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new errorMessage("User \'" + request.getEmail() + "\' is not found"));
        }
        catch(BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.resolve(400)).body(new errorMessage("Incorrect password"));
        }

    }

}
