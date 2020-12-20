package ru.geekbrains.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.geekbrains.persist.repo.UserRepository;


@EnableWebSecurity  //содержит в себе аннотацию @Configurition
public class SecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserAuthService(userRepository);
    }

    //обеспечение доступа к страницам
    @Configuration
    @Order(2)
    public static class UiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Override  //c такой настройкой может любой пользователь на любую страницу попасть
        protected void configure(HttpSecurity http) throws Exception{
            http
                    .authorizeRequests()
                    .antMatchers("/").hasRole("ADMIN")//даже не авторизованный пользователь имеет доступ к этой странице
                    .antMatchers("/users/**").hasRole("ADMIN")//**любой URL , //* только 1 сегмент в URL
                    .antMatchers("/products/**").hasAnyRole("ADMIN", "MANAGER")
                    .and()
                    .formLogin();
        }
    }
}
