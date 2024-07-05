package org.parryapplications.spring.todoproject.security;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * This class is responsible for configuring the security settings for the OAuth2 client application (GSignIn).
 * It extends the Spring Security configuration and sets up OAuth2 login as the primary authentication mechanism.
 */
//@Configuration
public class OauthClientSecurityConfiguration {

    /**
     * This method creates a custom security filter chain for the OAuth2 client application.
     * It configures the security settings, including authentication, form login, HTTP basic, and OAuth2 login.
     *
     * @param httpSecurity The HttpSecurity object used to configure the security settings.
     * @return A SecurityFilterChain object representing the custom security filter chain.
     * @throws Exception If any error occurs during the security configuration.
     */
    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain customSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(req -> req.anyRequest().authenticated());
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);
        httpSecurity.httpBasic(AbstractHttpConfigurer::disable);

        httpSecurity.oauth2Login(Customizer.withDefaults());
        return httpSecurity.build();
    }
}
