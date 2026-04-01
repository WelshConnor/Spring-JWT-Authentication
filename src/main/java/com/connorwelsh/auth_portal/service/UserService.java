package com.connorwelsh.auth_portal.service;

import com.connorwelsh.auth_portal.dto.AuthResponse;
import com.connorwelsh.auth_portal.dto.LoginRequest;
import com.connorwelsh.auth_portal.dto.RegistrationRequest;
import com.connorwelsh.auth_portal.exception.RegistrationException;
import com.connorwelsh.auth_portal.model.Role;
import com.connorwelsh.auth_portal.model.User;
import com.connorwelsh.auth_portal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email.toLowerCase())
				.orElseThrow(() -> new UsernameNotFoundException("User not found with this email: " + email));

		return new org.springframework.security.core.userdetails.User(
			    user.getEmail(),
				user.getPassword(),
				List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))	
		);
	}

	public AuthResponse registerUser(RegistrationRequest request) {
		if (!request.getPassword().equals(request.getConfirmPassword())) {
			throw new RegistrationException("Passwords do not match");
		}
		if (userRepository.existsByEmail(request.getEmail().toLowerCase())) {
			throw new RegistrationException("Email is already in use");
		}

		User user = User.builder()
				.email(request.getEmail().toLowerCase())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.USER)
				.build();

		userRepository.save(user);

		UserDetails userDetails = loadUserByUsername(user.getEmail());
		String token = jwtService.generateToken(userDetails);

		return AuthResponse.builder()
				.token(token)
				.email(user.getEmail())
				.role(user.getRole().name())
				.build();
	}

	public AuthResponse loginUser(LoginRequest request) {
		User user = userRepository.findByEmail(request.getEmail().toLowerCase())
				.orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));

		UserDetails userDetails = loadUserByUsername(user.getEmail());
		String token = jwtService.generateToken(userDetails);

		return AuthResponse.builder()
				.token(token)
				.email(user.getEmail())
				.role(user.getRole().name())
				.build();
	}
    
}
