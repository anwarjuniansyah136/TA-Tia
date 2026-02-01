package com.rental.Inventory.config;

import com.rental.Inventory.exception.CustomAccessDeniedException;
import com.rental.Inventory.exception.CustomUnAuthorizeException;
import com.rental.Inventory.repository.RolesRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.rental.Inventory.security.JwtFilter;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of("*")); // support origin: null
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        String roles = rolesRepository.findByRoleName("cashier")
                http
                        .cors(cors -> corsConfigurationSource())
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                 .exceptionHandling(ex -> ex
                                                 .authenticationEntryPoint(new CustomUnAuthorizeException())
                                                 .accessDeniedHandler(new CustomAccessDeniedException()))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(
                                                        "/auth/**",
                                                        "/v3/api-docs/**",
                                                        "/swagger-ui/**",
                                                        "/customer/register",
                                                        "/customer/upload-customer-photo",
                                                        "/product/get",
                                                        "/category/get",
                                                        "/api/v1/signup",
                                                        "/api/v1/find-all",
                                                        "/api/v1/signin",
                                                        "/api/v1/categories/**",
                                                        "/api/v1/daily-reports/**",
                                                        "/api/v1/products/**",
                                                        "/api/v1/rental-details/**",
                                                        "/api/v1/stock-movements/**",
                                                        "/api/v1/history/**"
                                                )
                                                .permitAll()
//                                                 .requestMatchers(
//                                                                 "/api/v1/rentals"
//                                                 )
//                                                 .hasAnyAuthority(String.valueOf(rolesRepository.findByRoleName("cashier").orElseThrow()))
                                                // .requestMatchers(
                                                //                 "/product/**",
                                                //                 "/category/**")
                                                // .hasAnyAuthority(RolesConstant.WAREHOUSE_ROLE)
                                                // .requestMatchers("/transaction/{id}","/transaction/view")
                                                // .hasAuthority(RolesConstant.ACCOUNTANT_ROLE)
                                                 .anyRequest().authenticated()
                                                )
                                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }
}
