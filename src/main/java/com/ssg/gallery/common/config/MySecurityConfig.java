package com.ssg.gallery.common.config;

import com.ssg.gallery.common.security.CustomAuthenticationProvider;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class MySecurityConfig {

    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            // localhost:5173에서 localhost:8080으로 요청을 보내기 위해 필요한 설정
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/v1/api/**", "/index.html", "/").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                    .loginProcessingUrl("/v1/api/account/login")
                    .usernameParameter("loginId")
                    .passwordParameter("loginPw")
                    .successHandler((request, response, authentication) -> {
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.setContentType("application/json;charset=UTF-8");
                    })
                    .failureHandler((request, response, exception) -> {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json;charset=UTF-8");
                    })
            )
            .logout(logout -> logout
                    .logoutUrl("/v1/api/account/logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessHandler((request, response, authentication) -> {
                        response.setStatus(HttpServletResponse.SC_OK);
                    })
            )
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint((request, response, authException) -> {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    })
            )
            .authenticationProvider(customAuthenticationProvider);
        SecurityFilterChain chain = http.build();
        chain.getFilters().forEach(System.out::println);
        return chain;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Vue URL
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
