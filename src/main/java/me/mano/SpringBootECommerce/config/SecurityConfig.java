package me.mano.SpringBootECommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests((requests) ->
                                requests.requestMatchers("/test/noAuth/**").permitAll()
                                        .anyRequest().authenticated());
    http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    // http.formLogin(withDefaults());
    http.httpBasic(withDefaults());
    http.csrf(csrf -> csrf.disable());
    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user1 = User.withUsername("user1")
                            .password("{noop}password1")   // {noop} is a prefix which tells the Spring boot to save the password in plain text;
                            .roles("USER").build();

    UserDetails admin = User.withUsername("admin").password("{noop}adminPass").roles("ADMIN").build();

    return new InMemoryUserDetailsManager(user1, admin);
  }
  
}
