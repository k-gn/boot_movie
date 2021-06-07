package org.zerock.club.config;


import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.zerock.club.security.handler.ClubLoginSuccessHandler;

@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.inMemoryAuthentication().withUser("user1")
//                .password("$2a$10$8gxBT7N83BMDHCnHWDnYhuDbT8fo3shYnSQSQQ39kTjttRzsuKWOa")
//                .roles("USER");
//
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/sample/all").permitAll()
                .antMatchers("/sample/member").hasRole("USER");

        http.formLogin();
        http.csrf().disable();
//        http.logout();

        // oauth2 소셜 로그인 등록 (기본적으로 ROLE_USER)
        http.oauth2Login()
            .successHandler(successHandler());
    }

    @Bean
    public ClubLoginSuccessHandler successHandler() {
        return new ClubLoginSuccessHandler(passwordEncoder());
    }
}
