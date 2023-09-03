package com.trucks.adapter.rest.security;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import es.ull.utils.rest.pagination.UllPagination;

// @EnableWebSecurity(debug = true)
@Configuration
public class WebSecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
    public static final String[] ALLOWED_PATHS = {
            "/**",
            /*"/actuator/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/webjars/swagger-ui/**",
            "/",
            "/trucks/",
            "/trucks/**",
            "/ports/",
            "/ports/**",
            "/vessels/",
            "/vessels/**",
            "/access/",
            "/access/**",
            "/access/",
            "/accesses/**",
            "/scales/",
            "/scales/**",
            "/operations/",
            "/operations/**",
            "/berths/",
            "/berths/**",
            "localhost:8080",
            "localhost:8080/**",
            "localhost:8081",
            "localhost:8081/**",*/
    };

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // final ApiKeyFilter filter = new ApiKeyFilter(new ApiKeyAuthenticationManager());
        http.cors().and()
                .csrf((csrf) -> csrf.disable()).sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http//.addFilter(filter)
                .authorizeHttpRequests().requestMatchers(ALLOWED_PATHS).permitAll()
        // .anyRequest().authenticated()
        ;
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(CorsConfiguration.ALL));
        configuration.setAllowedMethods(Arrays.asList(CorsConfiguration.ALL));
        configuration.setAllowedHeaders(Arrays.asList(CorsConfiguration.ALL));
        configuration.setExposedHeaders(UllPagination.getExposedHeaders());
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        logger.info("CORS configured correctly");
        return source;
    }
}