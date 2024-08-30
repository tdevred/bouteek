package com.tdevred.bouteek.configuration;

import com.tdevred.bouteek.authentication.JwtAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    private final AuthenticationProvider authenticationProvider;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categories/").permitAll()
                        .requestMatchers("/orders/**").authenticated()
                        .requestMatchers("/orders/").authenticated()
                        //.requestMatchers(HttpMethod.POST, "/products/**", "/categories/**", "/products/", "/categories/").hasAuthority("admin")
                        //.requestMatchers(HttpMethod.PATCH, "/products/**", "/categories/**", "/products/", "/categories/").hasAuthority("admin")
                        //.requestMatchers(HttpMethod.DELETE, "/products/**", "/categories/**", "/products/", "/categories/").hasAuthority("admin")
                        .requestMatchers(HttpMethod.POST, "/products/**", "/categories/**", "/products/", "/categories/").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/products/**", "/categories/**", "/products/", "/categories/").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/products/**", "/categories/**", "/products/", "/categories/").authenticated()
                )
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("**")
                        .permitAll())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
     */

    /*
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        logger.info("CALLED !!!!!!");
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);

        return source;
    }*/
}
