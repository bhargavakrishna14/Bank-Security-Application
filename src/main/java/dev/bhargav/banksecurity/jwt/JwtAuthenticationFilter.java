package dev.bhargav.banksecurity.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private final JwtAuthenticationHelper jwtHelper;

	private final UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {

		String requestHeader = request.getHeader("Authorization");

		String username =null;
		String token =null;

		if(requestHeader!=null && requestHeader.startsWith("Bearer")) {
			token = requestHeader.substring(7);
			
			username= jwtHelper.getUsernameFromToken(token);
			
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				
				if(!jwtHelper.isTokenExpired(token)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(token, null,userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		}
		filterChain.doFilter(request, response);
	}
	
}
