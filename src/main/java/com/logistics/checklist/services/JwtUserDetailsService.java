package com.logistics.checklist.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.logistics.checklist.models.User;
import com.logistics.checklist.repositories.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService{
	String ROLE_PREFIX = "ROLE_";
	 @Autowired
	 private UserRepository userRepository;


	 @Override
	 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	     Optional<User> user = userRepository.findByUsername(username);
	     if (user == null) {
	          throw new UsernameNotFoundException("User not found with username: " + username);
	     }
	     return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(),
	              mapRolesToAuthorities(user.get().getUserRole().getId()));
	}
	 
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Long role) {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

		list.add(new SimpleGrantedAuthority(ROLE_PREFIX +role));

		return list;
	}
}
