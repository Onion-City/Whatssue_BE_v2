package GDG.whatssue.global.config;

import GDG.whatssue.domain.user.service.CustomOauth2Service;
import GDG.whatssue.domain.user.service.OauthSuccessHandler;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true) //secured 활성화, preAuthorize, postAuthorize 활성화
public class WebSecurityConfig {
    private final CustomOauth2Service customOauth2Service;
    private final OauthSuccessHandler oauthSuccessHandler;

    @Value("${server.url}")
    private String serverUrl;
    private static final String[] AUTH_WHITELIST = {
            // swagger
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            "/signUp"
        //,"/api/**"
    };

    @Bean
    public FilterRegistrationBean<SameSiteFilter> sameSiteFilter() {
        FilterRegistrationBean<SameSiteFilter> filterRegBean = new FilterRegistrationBean<>();
        filterRegBean.setFilter(new SameSiteFilter());
        filterRegBean.addUrlPatterns("/*");
        return filterRegBean;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth2->oauth2
                        .successHandler(oauthSuccessHandler)
                        .userInfoEndpoint(userInfo->userInfo
                                .userService(customOauth2Service))
                                .permitAll()
                )
                .formLogin((form) -> form
//                        .loginPage("/loginForm")
//                        .loginProcessingUrl("/login") // 로그인 요청시, 스프링 시큐리티가 낚아체 로그인 처리하는 URL
//                        .usernameParameter("username")
//                        .passwordParameter("password")
//                        .defaultSuccessUrl("/")
                                .disable()
                )
                .logout((logout) -> logout.permitAll())
                .exceptionHandling(exception -> exception
                    .accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.setStatus(HttpStatus.FORBIDDEN.value()); // 403
                    })
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.FORBIDDEN)) // 403
                ); // 권한 없음
        return http.build();
    }


    @Bean // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.xg
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    void init() {
        log.info("Security Config init");
    }
}
