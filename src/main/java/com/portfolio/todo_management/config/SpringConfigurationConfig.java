package com.portfolio.todo_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.portfolio.todo_management.security.JwtAuthenticationEntryPoint;
import com.portfolio.todo_management.security.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SpringConfigurationConfig {
    @SuppressWarnings("unused")
    private UserDetailsService userDetailsService;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authorize-> {
//            authorize.requestMatchers(HttpMethod.POST,"/api/**").hasAnyRole("ADMIN");
//            authorize.requestMatchers(HttpMethod.DELETE,"/api/**").hasAnyRole("ADMIN");
   //          authorize.requestMatchers(HttpMethod.PUT,"/api/**").hasAnyRole("ADMIN");
//             authorize.requestMatchers(HttpMethod.GET,"/api/**").hasAnyRole("ADMIN", "USER");
//             authorize.requestMatchers(HttpMethod.PATCH,"/api/**").hasAnyRole("ADMIN", "USER");

                authorize.requestMatchers("/api/auth/**").permitAll();
                authorize.anyRequest().authenticated();
            })
        .httpBasic(Customizer.withDefaults());

        http.exceptionHandling((exception)->{ exception.authenticationEntryPoint(jwtAuthenticationEntryPoint);});
        
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{

        return configuration.getAuthenticationManager();
    }
    /*
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails leroy = User.builder().username("leroy").password(passwordEncoder().encode("password")).roles("USER").build();
        UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("password")).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(leroy,admin);
        
    }
         */
}
