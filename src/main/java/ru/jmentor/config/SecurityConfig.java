package ru.jmentor.config;

import ru.jmentor.security.jwt.JwtConfigurer;
import ru.jmentor.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/rest/login", "/rest/register", "/error").permitAll()
                .antMatchers("/rest/**", "/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessUrl("/").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/error")
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
