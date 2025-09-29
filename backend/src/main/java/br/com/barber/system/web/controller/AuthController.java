package br.com.barber.system.web.controller;

import br.com.barber.system.web.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${app.security.user}")
    private String username;

    @Value("${app.security.password}")
    private String password;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String user = body.get("username");
        String pass = body.get("password");

        if (username.equals(user) && password.equals(pass)) {
            String token = JwtUtil.generateToken(user);
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(401).body("Usuário ou senha inválidos");
    }
}