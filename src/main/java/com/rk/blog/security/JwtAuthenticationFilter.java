package com.rk.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtHelper jwtHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requesttoken = request.getHeader("Authorization");

		System.out.println(requesttoken);

		String username = null;
		String token = null;

		if (requesttoken != null && requesttoken.startsWith("Bearer")) {

			token = requesttoken.substring(7);

			try {

				username = jwtHelper.getUsername(token);

			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get jwt token");
			} catch (ExpiredJwtException e) {
				System.out.println("jwt token has Expired");
				// TODO: handle exception
			} catch (MalformedJwtException e) {
				System.out.println("Invalid jwt!");
			}

		} else {
			System.out.println("jwt token does not begin with Bearer");
		}
		
		
		
		
		if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			
			UserDetails userdetails = userDetailsService.loadUserByUsername(username);
			
			
			if(jwtHelper.validateToken(token, username)) {
				
				UsernamePasswordAuthenticationToken ut = new UsernamePasswordAuthenticationToken(userdetails,null,userdetails.getAuthorities());
				
				ut.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(ut);
				
				
				
			}else {
				
				System.out.println("invalid token !!");
			}
			
		}else {
			
			
			
			System.out.println("username is null or context is not null");
		}
		
		filterChain.doFilter(request, response);

	}

}
