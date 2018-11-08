package com.codegym.myveryfirstmavenproject;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    DataSource securityDataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("tomcat").password("tomcat").roles("USER")
//        .and().withUser("vietnam").password("vietnam").roles("ADMIN");
        auth.jdbcAuthentication().dataSource(securityDataSource());
    }

    //    @Bean
//    @ConfigurationProperties("spring.datasource")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }
    @Bean
    public DataSource securityDataSource() {
        BasicDataSource securityDataSource = new BasicDataSource();
        securityDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        securityDataSource.setUrl("jdbc:mysql://localhost:3306/cms");
        securityDataSource.setUsername("rob");
        securityDataSource.setPassword("123456");
        return securityDataSource;
    }


    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SuccessHandleImpl();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll()
                .and().formLogin().loginPage("/").loginProcessingUrl("/loginProcess").successHandler(authenticationSuccessHandler());
    }
}
