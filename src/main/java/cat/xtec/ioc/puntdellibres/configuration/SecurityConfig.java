package cat.xtec.ioc.puntdellibres.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import cat.xtec.ioc.puntdellibres.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private UserService userService;

  @Autowired
  BCryptPasswordEncoder encoder;

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return  new BCryptPasswordEncoder();
  }

  @Override
  protected void configure (AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(encoder);
  }

  @Override
  protected void configure (HttpSecurity http) throws Exception {
    // http.authorizeRequests()
    //   .anyRequest()
    //   .permitAll();

    http.authorizeRequests()
      .antMatchers("/api/**").hasRole("USER")
      .antMatchers("/afegir-llibre").hasRole("USER")
      .and().formLogin().loginPage("/login").permitAll()
        .and().authorizeRequests().anyRequest().permitAll();

    // http.authorizeRequests()
    //   .anyRequest().authenticated()
    //   .and().formLogin()
    //   .loginPage("/login").permitAll()
    //   .and()
    //   .logout()
    //   .logoutSuccessUrl("/");
      // .antMatchers("/").access("hasRole('ROLE_USER')")
      // .and()
      // .authorizeRequests()
      // .anyRequest()
      // .permitAll();
  }

}
