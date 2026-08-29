package me.mano.SpringBootECommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Autowired
  private DataSource dataSource;

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
    

    JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
    if(!userDetailsManager.userExists("user1")) {
      userDetailsManager.createUser(
        User.withUsername("user1")
            .password("{noop}password1")
            .roles("USER")
            .build()
      );
    }

    if(!userDetailsManager.userExists("admin")) {
      userDetailsManager.createUser(
        User.withUsername("admin")
            .password("{noop}adminPass")
            .roles("ADMIN")
            .build()
      );
    }

    return userDetailsManager;
    // return new InMemoryUserDetailsManager(user1, admin);
  }
  
}
