package iuh.fit.bookstorebackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain webFilterChain(HttpSecurity http)
            throws Exception {

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/login",
                        "/error",
                        "/css/**",
                        "/js/**",
                        "/images/**"
                ).permitAll()
                .requestMatchers(
                        HttpMethod.GET,
                        "/api/auth/csrf"
                ).permitAll()
                .requestMatchers(
                        HttpMethod.POST,
                        "/api/auth/register"
                ).permitAll()
                .requestMatchers(
                        HttpMethod.GET,
                        "/api/books",
                        "/api/books/*",
                        "/api/categories",
                        "/api/categories/*"
                ).permitAll()
                .requestMatchers(
                        HttpMethod.GET,
                        "/api/accounts/me"
                ).authenticated()
                .requestMatchers(
                        HttpMethod.PUT,
                        "/api/accounts/me"
                ).authenticated()
                .requestMatchers(
                        HttpMethod.DELETE,
                        "/api/accounts/me"
                ).authenticated()
                .requestMatchers(
                        "/api/accounts/**"
                ).hasRole("ADMIN")
                .requestMatchers(
                        "/api/books/**",
                        "/api/categories/**",
                        "/api/authors/**",
                        "/api/publishers/**"
                ).hasRole("ADMIN")
                .anyRequest().authenticated()
        );

        http.formLogin(Customizer.withDefaults());

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
        );

        return http.build();
    }
}