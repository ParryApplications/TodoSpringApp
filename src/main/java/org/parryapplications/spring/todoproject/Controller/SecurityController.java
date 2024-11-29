package org.parryapplications.spring.todoproject.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.parryapplications.spring.todoproject.dto.AuthenticationDto;
import org.parryapplications.spring.todoproject.dto.JwtTokenResponse;
import org.parryapplications.spring.todoproject.security.JwtSecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @Autowired
    private JwtSecurityConfiguration jwtSecurityConfiguration;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtEncoder jwtEncoder;



//    @GetMapping("/csrf-token")
//    public CsrfToken getCsrfToken(HttpServletRequest httpServletRequest) {
//        return (CsrfToken) httpServletRequest.getAttribute("_csrf");
//    }

    /**
     * This method will authenticate using Basic Authentication only
     * @param authentication
     * @return
     */
    @PostMapping("/authentication")
    public JwtTokenResponse authenticate(Authentication authentication) {
        String encode = jwtEncoder.encode(JwtEncoderParameters.from(jwtSecurityConfiguration.createToken(authentication))).getTokenValue();
        return new JwtTokenResponse(encode);
    }

    /**
     * This method will authenticate via request body (DISABLED)
     * @param AuthenticationDto
     * @return JwtTokenResponse
     */
//    @PostMapping("/authentication")
//    public JwtTokenResponse authenticate(@RequestBody AuthenticationDto authenticationDto){
//
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticationDto.getUsername(), authenticationDto.getPassword());
//        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//
//        String encode = jwtEncoder.encode(JwtEncoderParameters.from(jwtSecurityConfiguration.createToken(authentication))).getTokenValue();
//        return new JwtTokenResponse(encode);
//    }

}
