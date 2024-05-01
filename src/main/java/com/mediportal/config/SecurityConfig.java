package com.mediportal.config;

import com.mediportal.security.CustomeUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private CustomeUserDetailsService customeUserDetailsService;
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
              .antMatchers(HttpMethod.POST, "/api/post").hasRole("ADMIN") //this work as @PreAuthorize("hasRole('ADMIN')")
                .antMatchers(HttpMethod.POST, "/api/auth/**").permitAll()                                                               //in controller layer
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

//    @Override   //InMemoryAuthentication
//    @Bean // This method responsible for to create object  in which username and password store
//    protected UserDetailsService userDetailsService() {
//        UserDetails user1 = User.builder().username("satyam").password(passwordEncoder().
//                encode("password")).roles("USER").build();
//
//        UserDetails user2 = User.builder().username("admin").password(passwordEncoder().
//                encode("test")).roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(user1,user2);
//    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customeUserDetailsService).passwordEncoder(passwordEncoder());
    }
}
