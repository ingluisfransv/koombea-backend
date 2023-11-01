package com.koombea.scrapping.controller;

 import com.koombea.scrapping.service.TokenService;
 import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 import java.util.HashMap;
 import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public Map<String, String> token(Authentication authentication) {
        String generatedToken = tokenService.generateToken(authentication);
        Map<String, String> response = new HashMap<>();
        response.put("token", generatedToken);
        return response;
    }

}
