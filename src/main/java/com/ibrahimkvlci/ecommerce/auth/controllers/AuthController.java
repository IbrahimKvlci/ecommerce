package com.ibrahimkvlci.ecommerce.auth.controllers;

import com.ibrahimkvlci.ecommerce.auth.dto.AuthRequest;
import com.ibrahimkvlci.ecommerce.auth.dto.AuthResponse;
import com.ibrahimkvlci.ecommerce.auth.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.auth.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @Value("${jwt.expiration-time.access-token:1800000}")
    private long jwtExpirationTime;

    @PostMapping("/login")
    public ResponseEntity<DataResult<AuthResponse>> login(@Valid @RequestBody AuthRequest request,
            HttpServletResponse httpResponse) {
        log.info("Login attempt for {}", request.getEmail());
        DataResult<AuthResponse> result = authService.login(request);

        // Set JWT in HttpOnly cookie
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", Objects.requireNonNull(result.getData().getAccessToken()))
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge((int) (jwtExpirationTime / 1000))
                .sameSite("None")
                .build();
        httpResponse.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        return ResponseEntity.ok(result);
    }

}
