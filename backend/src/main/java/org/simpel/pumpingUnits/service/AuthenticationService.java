package org.simpel.pumpingUnits.service;

import org.simpel.pumpingUnits.model.UsersBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.simpel.pumpingUnits.controller.AuthRegisterModel.AuthenticationRequest;
import org.simpel.pumpingUnits.controller.AuthRegisterModel.AuthenticationResponse;
import org.simpel.pumpingUnits.controller.AuthRegisterModel.RegisterRequest;
import org.simpel.pumpingUnits.model.Users;
import org.simpel.pumpingUnits.model.enums.Role;
import org.simpel.pumpingUnits.repository.UserRepository;
import org.simpel.pumpingUnits.service.jwtUtils.JwtService;

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
        var user = new UsersBuilder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .name(request.getName())
                .surname(request.getSurname())
                .patronymic(request.getPatronymic())
                .phoneNumber(request.getPhoneNumber())
                .jobTitle(request.getJobTitle())
                .company(request.getCompany())
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
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
        return new AuthenticationResponse(jwtToken);
    }
}
