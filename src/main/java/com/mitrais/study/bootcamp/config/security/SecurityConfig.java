package com.mitrais.study.bootcamp.config.security;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import javax.inject.Inject;

/**
 * @author Rudi_W144
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Inject
    private UserDetailsService userDetailsService;
    @Inject
    private PasswordEncoder passwordEncoder;
    @Inject
    private DaoAuthenticationProvider daoAuthProvider;
    @Inject
    private AuthenticationManager authMgr;
    @Autowired
    private BasicAuthenticationEntryPoint basicAuthEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthProvider() {
        final DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
        daoAuthProvider.setPasswordEncoder(this.passwordEncoder);
        daoAuthProvider.setUserDetailsService(userDetailsService);
        return daoAuthProvider;
    }

    @Bean
    public AuthenticationManager authMgr() {
        final ProviderManager providerMgr = new ProviderManager(ImmutableList.of(daoAuthProvider()));
        return providerMgr;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.daoAuthProvider);
    }


    @Bean
    public BasicAuthenticationFilter basicAuthFilter() {
        return new BasicAuthenticationFilter(authMgr, basicAuthEntryPoint);
    }

    @Bean
    public BasicAuthenticationEntryPoint basicAuthEntryPoint() {
        final BasicAuthenticationEntryPoint bauth = new BasicAuthenticationEntryPoint();
        bauth.setRealmName("MITRAIS_BASIC_AUTH");
        return bauth;
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilter(basicAuthFilter())
                .authorizeRequests()
                .antMatchers("/").permitAll()
//                .antMatchers("/cucumber/**").permitAll()
                .antMatchers("/secure/**").authenticated()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .and().addFilter(new SecurityContextPersistenceFilter()).securityContext()
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
                .and().formLogin().loginPage("/signin");
    }
}
