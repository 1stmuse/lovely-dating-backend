package com.muse.lovely.WebsecurityConfig;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtFilter jwtFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception{

        http.cors( corss -> corss.disable())
                .csrf(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(re -> re.requestMatchers(
                        "/h2-console/**",
                        "/api/v1/auth"
                ).permitAll().anyRequest().authenticated())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
    }
}
