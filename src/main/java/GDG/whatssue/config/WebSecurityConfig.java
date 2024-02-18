package GDG.whatssue.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            // swagger
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            "/signUp",
            "/"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(AUTH_WHITELIST).permitAll()
//                        .requestMatchers(HttpMethod.POST,"/board/get/list").hasRole("USER") // prefixed with ROLE_
                        .requestMatchers(HttpMethod.GET,"/manager/**").hasAnyRole("MANAGER")
//                        .requestMatchers(HttpMethod.DELETE,"/board/delete/{id}").hasRole("MANAGER")
//                        .requestMatchers(HttpMethod.POST,"/board/update/list").hasRole("ADMIN")
                        .anyRequest().authenticated())

                .formLogin((form) -> form
//                        .loginPage("/loginForm")
//                        .loginProcessingUrl("/login") // 로그인 요청시, 스프링 시큐리티가 낚아체 로그인 처리하는 URL
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/")
                )
                .logout((logout) -> logout.permitAll());
        return http.build();
    }

    @Bean // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
