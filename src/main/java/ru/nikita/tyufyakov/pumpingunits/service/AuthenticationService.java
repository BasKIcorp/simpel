package ru.nikita.tyufyakov.pumpingunits.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nikita.tyufyakov.pumpingunits.controller.AuthRegisterModel.AuthenticationRequest;
import ru.nikita.tyufyakov.pumpingunits.controller.AuthRegisterModel.AuthenticationResponse;
import ru.nikita.tyufyakov.pumpingunits.controller.AuthRegisterModel.RegisterRequest;
import ru.nikita.tyufyakov.pumpingunits.model.Users;
import ru.nikita.tyufyakov.pumpingunits.model.enums.Role;
import ru.nikita.tyufyakov.pumpingunits.repository.UserRepository;
import ru.nikita.tyufyakov.pumpingunits.service.jwtUtils.JwtService;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request){
        var user = Users.builder()
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws UsernameNotFoundException, BadCredentialsException {
        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->
                        new UsernameNotFoundException("ну не нашел, что поделать ;("));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
