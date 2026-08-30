package me.mano.SpringBootECommerce.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUtils jwtUtils; 

  @Autowired
  private UserDetailsService userDetailsService;

  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
    logger.debug("AuthTokenFilter called for URI: {}", request.getRequestURI());

    try {
      String jwt = parseJwt(request);
      if(jwt != null && jwtUtils.validateToken(jwt)) {
        String username = jwtUtils.getUsernameFromJwtToken(jwt);
        UserDetails userdetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // sets all deatils(metadata) of request to authenticationToken. Its not necessary, but good to do. 

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        logger.debug("Roles from jwt: {}", userdetails.getAuthorities());
      }
    } catch (Exception e){
      logger.error("Cannot set user authentication: {}", e);
    }

    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String jwt = jwtUtils.getJwtFromHeader(request);
    logger.debug("AuthTokenFilter.java: {}", jwt);
    return jwt;
  }
  
}
