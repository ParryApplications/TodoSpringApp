package org.parryapplications.spring.todoproject.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.parryapplications.spring.todoproject.dto.JwtTokenResponse;
import org.parryapplications.spring.todoproject.security.JwtSecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @Autowired
    public JwtSecurityConfiguration jwtSecurityConfiguration;

    @Autowired
    private JwtEncoder jwtEncoder;

//    @Autowired
//    private AuthenticationManager authenticationManager;

    //CSRF:
    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest httpServletRequest) {
        return (CsrfToken) httpServletRequest.getAttribute("_csrf");
    }

    //JWT:
    @PostMapping("/authentication")
    public JwtTokenResponse authenticate(Authentication authentication) {
        String encode = jwtEncoder.encode(JwtEncoderParameters.from(jwtSecurityConfiguration.createToken(authentication))).getTokenValue();
        return new JwtTokenResponse(encode);
    }

    //TODO: If goes with below impl, need to create bean of authenticationManager
//    @PostMapping("/v2.0/authentication")
//    public JwtTokenResponse authenticateV2(AuthenticationDto authenticationDto){
//
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticationDto.getUsername(), authenticationDto.getPassword());
//        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//
//        String encode = jwtEncoder.encode(JwtEncoderParameters.from(jwtSecurityConfiguration.createToken(authentication))).getTokenValue();
//        return new JwtTokenResponse(encode);
//    }

}
