package com.mpesocial.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;
    private static final String USERS_SQL_QUERY = "SELECT username, password, enabled FROM users WHERE username = ?";
    private static final String AUTHORITIES_SQL_QUERY = "SELECT users.username, authorities.authority FROM users INNER JOIN users_authorities ON (users.id = users_authorities.user_id) INNER JOIN authorities ON (users_authorities.authority_id = authorities.id) WHERE users.username = ?;";

    @Autowired
    public WebSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public BCryptPasswordEncoder bcryptEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable()
                .and().authorizeRequests().antMatchers("/createNewPost/**", "/editPost/**", "/comment/**").hasRole("USER")
                .antMatchers("/deletePost/**").hasRole("USER").antMatchers("/deletePost/**").hasRole("ADMIN").antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").loginProcessingUrl("/login").usernameParameter("username").passwordParameter("password")
                .defaultSuccessUrl("/").failureUrl("/login?error").permitAll()
                .and().rememberMe().rememberMeParameter("remember-me")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll()
                .and().sessionManagement().maximumSessions(1);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.jdbcAuthentication().usersByUsernameQuery(USERS_SQL_QUERY).authoritiesByUsernameQuery(AUTHORITIES_SQL_QUERY).dataSource(dataSource).passwordEncoder(bcryptEncoder());
    }
    
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
    }

}
