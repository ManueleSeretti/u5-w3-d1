package ManueleSeretti.u5w3d1.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JWTAuthorFilter jwtAuthFilter;

    @Bean
// SERVE PER CONFIGURARE LE OPZIONI SU CUI VOGLIAMO ANDARE A FARE LE AUTENTIFICAZIONI
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //DISABILITà quelle di default di spring web security
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        http.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable());

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests(request -> request.requestMatchers("/**").permitAll());
        return http.build();
    }
}
