package com.reapal.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by jack-cooper on 2017/1/15.
 */
@Configurable
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)//允许进入页面方法前检验
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                .antMatchers("/**","/login").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                    .formLogin().loginPage("/login").defaultSuccessUrl("/index")
                    .failureUrl("/login?error").permitAll()
                .and()
                    .logout().logoutSuccessUrl("/").permitAll()
                    .invalidateHttpSession(true)
                    .deleteCookies()
                .and()
                    .csrf().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
            .withUser("admin").password("admin").roles("USER");
    }
}
