    package org.simpel.pumpingUnits.controller;

    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.authentication.BadCredentialsException;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.web.bind.annotation.*;
    import org.simpel.pumpingUnits.controller.AuthRegisterModel.AuthenticationRequest;
    import org.simpel.pumpingUnits.controller.AuthRegisterModel.RegisterRequest;
    import org.simpel.pumpingUnits.controller.AuthRegisterModel.errorMessage;
    import org.simpel.pumpingUnits.service.AuthenticationService;
    import org.simpel.pumpingUnits.service.UserService;
    import org.springframework.web.bind.annotation.CrossOrigin;
    import org.springframework.web.bind.annotation.RequestMethod;

    @RestController
    @RequestMapping("/api/simple/auth/")
    @CrossOrigin(origins = {"http://localhost:3000", "http://51.250.25.148:3000","https://web.telegram.org","https://t.me","https://telegram.me","https://telegram.org"},
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
    public class AuthenticationController {
        private final UserService userService;
        private final AuthenticationService authenticationService;
        private final PasswordEncoder passwordEncoder;

        public AuthenticationController(UserService userService, AuthenticationService authenticationService, PasswordEncoder passwordEncoder) {
            this.userService = userService;
            this.authenticationService = authenticationService;
            this.passwordEncoder = passwordEncoder;
        }

        @PostMapping("/register")
        public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
            try {
                if (userService.isExist(request.getEmail())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(new errorMessage("username \'" + request.getEmail() + "\' is already taken"));
                } else {
                    return ResponseEntity.ok(authenticationService.register(request));
                }
            }
            catch (NullPointerException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        @PostMapping("/authenticate")
        public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
            try {
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+ passwordEncoder.encode("admin"));
                return ResponseEntity.ok(authenticationService.authenticate(request));
            } catch (UsernameNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new errorMessage("User \'" + request.getEmail() + "\' is not found"));
            }
            catch(BadCredentialsException e) {
                return ResponseEntity.status(HttpStatus.resolve(400)).body(new errorMessage("Incorrect password"));
            }
            catch (NullPointerException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }

        }

    }
