package com.amirscode.payment.service;

import com.amirscode.payment.entity.RoleEnum;
import com.amirscode.payment.entity.User;
import com.amirscode.payment.model.UserDTO;
import com.amirscode.payment.model.UserInDTO;
import com.amirscode.payment.repository.RoleRepository;
import com.amirscode.payment.repository.UserRepository;
import com.amirscode.payment.security.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(value -> (UserDetails) value).orElseGet(() -> (UserDetails) ResponseEntity.status(HttpStatus.CONFLICT).body(username + "is not found"));
    }

    @Value("${app.jwtExpirationInMs}")
    private long accessTokenDate;

    public ResponseEntity<?> signUp(UserDTO reqSignUp) {

        Optional<User> byUsername = userRepository.findByUsername(reqSignUp.getUsername());
        if (byUsername.isEmpty()) {
            if (!reqSignUp.getUsername().isEmpty() && !reqSignUp.getFullName().isEmpty() && !reqSignUp.getPassword().isEmpty()) {
                User user = new User();
                user.setFullName(reqSignUp.getFullName());
                user.setUsername(reqSignUp.getUsername());
                user.setRole(roleRepository.findByName(RoleEnum.ROLE_USER));
                user.setPassword(passwordEncoder.encode(reqSignUp.getPassword()));
                user.setPhoneNumber(reqSignUp.getPhoneNumber());
                userRepository.save(user);
                return ResponseEntity.ok("User success");
            }else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User malumot toliq berilmadi!");
            }

        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username mavjud");
        }
    }

    public ResponseEntity<?> signIn(UserInDTO reqSignIn) {
        ObjectNode data = objectMapper.createObjectNode();
        userRepository.findByUsername(reqSignIn.getUsername()).orElseThrow(() -> new IllegalStateException("Username mavjud emas"));


        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(reqSignIn.getUsername(), reqSignIn.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);
        String refreshToken = jwtTokenProvider.refreshToken(authentication);
        data.put("accessToken", jwt);
        data.put("refreshToken", refreshToken);
        data.put("tokenType", "Bearer ");
        data.put("expiryDate", accessTokenDate);

        return ResponseEntity.ok(data);
    }

    public UserDetails loadUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User id not found: " + userId));
    }
}
