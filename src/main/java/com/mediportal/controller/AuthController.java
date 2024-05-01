package com.mediportal.controller;

import com.mediportal.entity.Role;
import com.mediportal.entity.User;
import com.mediportal.payloads.LoginDto;
import com.mediportal.payloads.SignUpDto;
import com.mediportal.repository.RoleRepository;
import com.mediportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    public AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username Already taken!!", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is Already Exits", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName(signUpDto.getRoleType()).get();

        Set<Role> convertRoleToSet = new HashSet<>();
        convertRoleToSet.add(roles);

        user.setRoles(convertRoleToSet);

        userRepository.save(user);

        return new ResponseEntity<>("user registration successful" , HttpStatus.OK);

    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody LoginDto loginDto){

//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                new UsernamePasswordAuthenticationToken(loginDto.getPassword(),loginDto.getUsername());

//        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

//        SecurityContextHolder.getContext().setAuthentication(authenticate);

        authenticationManager.authenticate(
                new  UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword())
        );

        return new ResponseEntity<>("User signIn Successful" , HttpStatus.OK);
    }
}
