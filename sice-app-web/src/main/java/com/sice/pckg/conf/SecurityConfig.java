package com.sice.pckg.conf;

import com.sice.pckg.authentication.CustomAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    UserDetailsService userDetailsService;

    @Autowired
    ReflectionSaltSource saltSource;

    @Autowired
    MessageDigestPasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authentication) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        provider.setSaltSource(saltSource);
        authentication.authenticationProvider(provider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/Home/Login").permitAll()
                .antMatchers("/Home/BadCredentials").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/Usuario/*").hasAuthority("ROLE_ADMIN")
                .antMatchers("/*/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .and().formLogin().loginPage("/Home/Login").loginProcessingUrl("/login").defaultSuccessUrl("/Home/Principal", true).failureHandler(new CustomAuthenticationFailureHandler()).usernameParameter("j_username").passwordParameter("j_password").and()
                .logout().deleteCookies("JSESSIONID").invalidateHttpSession(true).logoutUrl("/Home/Logout").and()
                .sessionManagement().invalidSessionUrl("/Home/Login").maximumSessions(1).expiredUrl("/Home/Login?expiredSession=true").and()
                .and().csrf().disable();
    }

}
