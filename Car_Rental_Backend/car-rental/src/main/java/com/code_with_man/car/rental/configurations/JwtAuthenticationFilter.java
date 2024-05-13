package com.code_with_man.car.rental.configurations;

import com.code_with_man.car.rental.services.jwt.UserService;
import com.code_with_man.car.rental.services.jwt.UserServiceImpl;
import com.code_with_man.car.rental.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private   UserService userService;
	@Autowired
	private  JwtUtils jwtUtils;

	@Override
	protected void doFilterInternal(
			@NotNull HttpServletRequest request,@NotNull HttpServletResponse response,@NotNull FilterChain filterChain)
			throws ServletException, IOException {

		final String authHeader=request.getHeader("Authorization");
		final String jwt;
		final String userEmail;

		if(StringUtils.isEmpty(authHeader)|| !StringUtils.startsWith(authHeader,"Bearer ")){
			filterChain.doFilter(request,response);
			return;
		}
		jwt=authHeader.substring(7);
		userEmail=jwtUtils.extractUsername(jwt);
		if(StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication()==null){
			UserDetails userDetails=userService.userDetailsService().loadUserByUsername(userEmail);
			if(jwtUtils.isTokenValid(jwt,userDetails)){
				SecurityContext context= SecurityContextHolder.createEmptyContext();
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userDetails,null,userDetails.getAuthorities()
				);
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				context.setAuthentication(authToken);
				SecurityContextHolder.setContext(context);
			}

		}

  filterChain.doFilter(request,response);

	}
}
