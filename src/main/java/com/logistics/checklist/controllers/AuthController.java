package com.logistics.checklist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logistics.checklist.security.JwtToken;

import java.util.Optional;

import com.logistics.checklist.models.User;
import com.logistics.checklist.payloads.JwtRequest;
import com.logistics.checklist.payloads.JwtResponse;
import com.logistics.checklist.repositories.UserRepository;
import com.logistics.checklist.response.ResponseMessage;
import com.logistics.checklist.services.JwtUserDetailsService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private UserRepository userRepository;

    // login new user
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody final JwtRequest authenticationRequest)
            throws Exception {
            try{
                authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

                final UserDetails userDetails = jwtUserDetailsService

                        .loadUserByUsername(authenticationRequest.getUsername());

                Optional<User> userData = userRepository.findByUsername(authenticationRequest.getUsername());

                final String token = jwtToken.generateToken(userDetails);

                return ResponseEntity.ok(new JwtResponse(token, userData.get().getFullname(), userData.get().getUsername(), userData.get().getEmail(), userData.get().getPhone(), userData.get().getAddress()));
            }catch(Exception ex){
                return new ResponseEntity<>(new ResponseMessage(true, ex.getMessage()), HttpStatus.OK);
            }

        

    }

    private void authenticate(final String username, final String password) throws Exception {

        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        } catch (final DisabledException e) {

            throw new Exception("USER_DISABLED", e);

        } catch (final BadCredentialsException e) {

            throw new Exception("INVALID_CREDENTIALS", e);

        }

    }

}

