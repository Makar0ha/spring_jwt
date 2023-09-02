package com.spring_security.jwt.controller;

import com.spring_security.jwt.security.JWTService;
import com.spring_security.jwt.service.UserService;
import com.spring_security.jwt.user.LoginRequest;
import com.spring_security.jwt.user.RegisterRequest;
import com.spring_security.jwt.user.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${jwtPrefix}")
    private String jwtPrefix;
    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest, HttpServletResponse response) {
        User user = userService.register(registerRequest);
        String jwt = jwtService.generateToken(user);
        Cookie jwtCookie = new Cookie("token", jwt);
        jwtCookie.setHttpOnly(true);  // This makes the cookie inaccessible to JavaScript on the frontend for security reasons
        jwtCookie.setMaxAge(10 * 24 * 60 * 60);  // Set cookie expiration as needed (e.g., 7 days here)
        // If you're on HTTPS, ensure the cookie is secure
        // jwtCookie.setSecure(true);

        response.addCookie(jwtCookie);

        return ResponseEntity.ok().body(user);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword())
        );
        User user = userService.login(loginRequest);
        String jwt = jwtService.generateToken(user);
        Cookie jwtCookie = new Cookie("token", jwt);
        jwtCookie.setHttpOnly(true);  // This makes the cookie inaccessible to JavaScript on the frontend for security reasons
        jwtCookie.setMaxAge(10 * 24 * 60 * 60);  // Set cookie expiration as needed (e.g., 7 days here)
        // If you're on HTTPS, ensure the cookie is secure
        // jwtCookie.setSecure(true);

        response.addCookie(jwtCookie);
        return ResponseEntity.ok().body(user);

    }
}
