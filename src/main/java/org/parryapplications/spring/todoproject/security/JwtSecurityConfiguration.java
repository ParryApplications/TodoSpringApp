package org.parryapplications.spring.todoproject.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
@EnableMethodSecurity
public class JwtSecurityConfiguration {

    @Value("${spring.datasource.password}")
    private String mySqlPassword;

    @Bean
    public SecurityFilterChain customSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(req -> req.anyRequest().authenticated());
//        httpSecurity.authorizeHttpRequests(req -> req.requestMatchers("admins/todos/**").hasRole("ADMIN"));
        httpSecurity.httpBasic(Customizer.withDefaults());
//        httpSecurity.httpBasic(AbstractHttpConfigurer::disable);
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        if(!jdbcUserDetailsManager.userExists("parry")) {
            UserDetails user = User.withUsername("parry")
                    .password("user")
                    .passwordEncoder(bCryptPasswordEncoder()::encode)
                    .roles("USER")
                    .build();
            jdbcUserDetailsManager.createUser(user);
        }

        if(!jdbcUserDetailsManager.userExists("roots")) {
            UserDetails admin = User.withUsername("roots")
                    .password("admin")
                    .passwordEncoder(bCryptPasswordEncoder()::encode)
                    .roles("ADMIN")
                    .build();
            jdbcUserDetailsManager.createUser(admin);
        }

        return jdbcUserDetailsManager;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/todo_db");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword(mySqlPassword);
        return driverManagerDataSource;
    }


    //Creating keypair using RSA Algo.:
    @Bean
    public KeyPair keyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    //Creating RSA key object using keypair (contains public, private keys):
    @Bean
    public RSAKey rsaKey(KeyPair keyPair) {
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    //Validating JWT using RSA public key:
    @Bean
    public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
       return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }

    //Creating JWK(Json web keys) Source, used to validate the signature of JWT:
    @Bean
    public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey){
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }


    //Creation of JWT, using Private RSA(Road safety Audit) key:
    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> securityContextJWKSource){
        return new NimbusJwtEncoder(securityContextJWKSource);
    }

    //creating Token:
    public JwtClaimsSet createToken(Authentication authentication){
        return JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60))
                .subject(authentication.getName())
                .claim("scope", createScope_Authorities(authentication))
                .build();
    }

    private String createScope_Authorities(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
    }




}
