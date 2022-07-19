package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.security.UdAuthenticationFailureHandler;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configurable
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   // @Autowired
    private AuthenticationService  authenticationService;
    public SecurityConfig(AuthenticationService  authenticationService){
        this.authenticationService = authenticationService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(  this.authenticationService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers( "/h2-console/**", "/signup", "/css/**", "/js/**", "/postLogin", "/login*").permitAll()
                .anyRequest().authenticated();


        http.formLogin().permitAll()
                .loginPage("/login").loginProcessingUrl("/postLogin")
                .defaultSuccessUrl("/home", true)
                .failureHandler(getAuthenticationFailureHandler());
//        super.configure(http);
        http.logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login?logout=true");
    }

    @Bean
    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return new UdAuthenticationFailureHandler();
    }
}
