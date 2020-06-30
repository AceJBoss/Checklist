package com.logistics.checklist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logistics.checklist.repositories.UserRepository;
import com.logistics.checklist.repositories.UserRoleRepository;

import com.logistics.checklist.models.User;
import com.logistics.checklist.models.UserRole;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class AdminRegController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

    // create role
    @PostMapping("/roles")
    public ResponseEntity<?> createRoles(@RequestBody UserRole body){
        return new ResponseEntity<>(userRoleRepository.save(body), HttpStatus.OK);
    }

	// register new admin
	@PostMapping("/register/admin")
	public ResponseEntity<?> create(@RequestBody User user) {
        UserRole userRole = userRoleRepository.findById(1L).orElse(null);
        if(userRole == null){
            userRole = new UserRole();
        }
        user.setUserRole(userRole);
        String username = user.getUsername();
       if (userRepository.existsByUsername(username)){

           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

       }
  
        String password = user.getPassword();
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        user.setPassword(encodedPassword);
	    return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }
    
    // register demo user
    @PostMapping("/register/demo/user")
    public ResponseEntity<?> createDemoUser(@RequestBody User user){
        UserRole userRole = userRoleRepository.findById(user.getUserRole().getId()).orElse(null);
        if(userRole == null){
            userRole = new UserRole();
        }
        user.setUserRole(userRole);
        String username = user.getUsername();
       if (userRepository.existsByUsername(username)){

           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

       }
  
        String password = user.getPassword();
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        user.setPassword(encodedPassword);
	    return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }

	@GetMapping("/users")
	public ResponseEntity<?> fetchUsers(){
		return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
	}
}
