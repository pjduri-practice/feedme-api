package FeedMe.Auth.Authorization.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Handles various security configurations, including the main
 * application route filter chain that guards all desired routes
 * behind a basic JWT authentication implementation.
 */
@Configuration
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true) // @todo replace deprecated decorator with newer implementation
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * The main application route filter chain that guards all desired
     * routes behind a basic JWT authentication implementation.
     //* @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Disable cors and csrf for ease-of-use.
        // These should be properly configured and enabled in a production server.
        http.cors().and().csrf().disable()
                // Sets the authentication provider for the application.
                .authenticationProvider(authenticationProvider())
                // Adds the JWT authentication filter to the chain.
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                // Adds an exception handler to the chain.
                .exceptionHandling()
                // Sets the authentication entry point to the unauthorizedHandler,
                // aka <b>AuthEntryPointJwt</b>.
                .authenticationEntryPoint(unauthorizedHandler).and()
                // Sets the session management policy to stateless.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // Authorizes HTTP requests and ensures that routes matching
                // <b>/api/auth/**</b> are always accepted and do not require
                // an authentication token.
                .authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/api/auth/**")).permitAll()
                // Ensures that routes matching <b>/api/public/**</b> do not
                // require an authentication token.
                .requestMatchers(new AntPathRequestMatcher("/api/public/**")).permitAll()
                // Ensures that any and all other routes in the application
                // require a valid JWT authentication token to be provided.
                .anyRequest().authenticated();

        // Builds the actual filter so that the application can utilize it.
        return http.build();
    }
}