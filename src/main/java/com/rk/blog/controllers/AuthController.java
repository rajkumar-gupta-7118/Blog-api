package com.rk.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rk.blog.payloads.JwtAuthRequest;
import com.rk.blog.payloads.JwtAuthResponse;
import com.rk.blog.payloads.UserDto;
import com.rk.blog.security.JwtHelper;
import com.rk.blog.service.UserService;

@RestController
@RequestMapping("/apis/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtHelper jwtHelper;
	@Autowired
	private UserService userService;

	@Autowired
	private UserDetailsService userDetailsService;

	@PostMapping("/register")
	public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {

		UserDto register = userService.register(userDto);

		return new ResponseEntity<UserDto>(register, HttpStatus.CREATED);

	}

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {

		authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

		String token = jwtHelper.generateToken(userDetails.getUsername());

		JwtAuthResponse jt = new JwtAuthResponse();
		jt.setToken(token);

		return ResponseEntity.ok(jt);

	}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(username, password);
		try {
			authenticationManager.authenticate(upat);
		} catch (Exception e) {

			throw new Exception("InValid Username and Password");
		}

	}

}
