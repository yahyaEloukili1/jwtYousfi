package org.ensas.jwtBrains.security;

import java.io.IOException;
import java.security.AlgorithmConstraints;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ensas.jwtBrains.constants.SecurityConstants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authorizationToken = request.getHeader(SecurityConstants.HEADER_STRING);
		if(authorizationToken == null || !authorizationToken.startsWith(SecurityConstants.TOKEN_REFIX)) {
			System.out.println("Error$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			filterChain.doFilter(request, response);
			return;
		}else {
			try {
				String jwt = authorizationToken.substring(7);
			Algorithm algorighm = Algorithm.HMAC256(SecurityConstants.SECRET);
			JWTVerifier jwtVerifier = JWT.require(algorighm).build();
			DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
			String username = decodedJWT.getSubject();
			String[] roles= decodedJWT.getClaim("roles").asArray(String.class);
		
		
			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			for(String r : roles) {
				authorities.add(new SimpleGrantedAuthority(r));
			}
			UsernamePasswordAuthenticationToken authenticatedUser =
					new UsernamePasswordAuthenticationToken(username,null, authorities);
			SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
			System.out.println("success **********************************************");
			filterChain.doFilter(request, response);
			}
			catch(Exception e)
			{
				response.setHeader("error-message", e.getMessage());
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		}
		
	}

}
