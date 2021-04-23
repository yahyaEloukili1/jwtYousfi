package org.ensas.jwtBrains.security;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ensas.jwtBrains.constants.SecurityConstants;
import org.ensas.jwtBrains.entities.AppUser;
import org.ensas.jwtBrains.entities.MyUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	private AuthenticationManager authenticationManager;
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	

	 @Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		AppUser appUser = null;
		try {
			appUser = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
			
		}
		System.out.println("username : "+ appUser.getUsername());
		System.out.println("password : "+ appUser.getPassword());
	 
		
		return authenticationManager.authenticate(new
				UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword()));
	}
	 @Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		 System.out.println("now the user is authenticated we have to calculate jwt");
		 MyUserDetails user = (MyUserDetails) authResult.getPrincipal();
		Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.SECRET);
		String jwt = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+ SecurityConstants.EXPIRATION_TIME))
				.withIssuer(request.getRequestURI().toString())
				.withClaim("roles",  user.getAuthorities().stream()
						.map(ga->ga.getAuthority()).collect(Collectors.toList()))
				.sign(algorithm);
			
		 
		 
		 response.addHeader(SecurityConstants.HEADER_STRING,SecurityConstants.TOKEN_REFIX+jwt);
		 
	}
}
