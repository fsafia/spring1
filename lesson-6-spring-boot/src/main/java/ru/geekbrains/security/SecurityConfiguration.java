package ru.geekbrains.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.geekbrains.persist.repo.UserRepository;

import javax.servlet.http.HttpServletResponse;
import java.net.Authenticator;

@EnableWebSecurity  //содержит в себе аннотацию @Configurition
public class SecurityConfiguration {

    // процесс авторизации
    //для создания нового пользователя
    @Autowired
    public void authConfigure(AuthenticationManagerBuilder auth,
                              UserDetailsService userDetailService,
                              PasswordEncoder passwordEncoder) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder);
        auth.authenticationProvider(provider);
        auth.inMemoryAuthentication()
                .withUser("mem_user")
                .password(passwordEncoder.encode("password")) //password
//                .password("{bcrypt}$2y$12$A/A8ZYZ1m.7iTjm1Y8dwDOdYrQURN/Zt7Lz9CjM0xKpurI7UJalJG") //password
                .roles("ADMIN");
    }


    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserAuthService(userRepository);
    }

    //обеспечение доступа к страницам
    @Configuration
    @Order(2)
    public static class UiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception{
            http
                    .authorizeRequests()
                    .antMatchers("/").anonymous()//даже не авторизованный пользователь имеет доступ к этой странице
                    .antMatchers("/users/**").hasRole("ADMIN")
                    .and()
                    .formLogin();
        }
    }

    //для использования Basic авторизации
//    @Configuration
//    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception{
            http
                    .authorizeRequests()
                    .antMatchers("/api/**").hasRole("ADMIN")
                    .and()
                    .httpBasic()
                    .authenticationEntryPoint((req,resp,exception) -> {
                        resp.setContentType("application/json");
                        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        resp.setCharacterEncoding("UTF-8");
                        resp.getWriter().println("{ \"error\": \"" + exception.getMessage() + "\" }");
                    })
                    .and()
                    .csrf().disable() //отключили проверку csrf, для рест сервисов она не нужна, т.к.сессия не создается в рест-сеовисах
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS); //сессия создаваться не будет
        }
    }


}
