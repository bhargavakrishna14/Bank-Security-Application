package dev.bhargav.banksecurity.auth;

import dev.bhargav.banksecurity.entity.Role;
import dev.bhargav.banksecurity.entity.User;
import dev.bhargav.banksecurity.config.JwtAuthenticationHelper;
import dev.bhargav.banksecurity.exceptions.UserNameAlreadyExistsException;
import dev.bhargav.banksecurity.repository.RoleRepository;
import dev.bhargav.banksecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JwtAuthenticationHelper jwtAuthenticationHelper;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UserNameAlreadyExistsException("Username already exists");
        }

        User newUser = new User();
        newUser.setName(registerRequest.getName());
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setIdentityProof(registerRequest.getIdentityProof());
        newUser.setNumber(registerRequest.getNumber());
        newUser.setAddress(registerRequest.getAddress());

        // Dynamically assign role
//        Role role = new Role();
//        if ("ADMIN".equalsIgnoreCase(registerRequest.getRole())) {
//            role.setRoleName("ADMIN");
//        } else {
//            role.setRoleName("CUSTOMER"); // default
//        }
//        newUser.setRoles(role);

        // Fetch role from DB
        String roleName = "ADMIN".equalsIgnoreCase(registerRequest.getRole()) ? "ADMIN" : "CUSTOMER";
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        newUser.setRole(role);

        userRepository.save(newUser);

        final String token = jwtAuthenticationHelper.generateToken(newUser);
        return AuthenticationResponse.builder()
                .jwtToken(token)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = (User) auth.getPrincipal();

//        User user = userRepository.findByUsername(request.getUsername())
//                .orElseThrow();

        final String token = jwtAuthenticationHelper.generateToken(user);

        return AuthenticationResponse.builder()
                .jwtToken(token)
                .build();
    }
}
