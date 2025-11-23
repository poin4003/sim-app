package com.sim.app.sim_app.features.auth.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.sim.app.sim_app.config.jwt.JwtTokenProvider;
import com.sim.app.sim_app.core.controller.BaseController;
import com.sim.app.sim_app.core.vo.ResultMessage;
import com.sim.app.sim_app.features.auth.api.dto.request.LoginRequest;
import com.sim.app.sim_app.features.auth.api.dto.response.LoginResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication V1", description = "Auth docs")
public class AuthController extends BaseController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<ResultMessage<LoginResponse>> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        LoginResponse loginRes = new LoginResponse();
        loginRes.setAccessToken(jwtTokenProvider.generateToken(authentication));

        return OK("Login success", loginRes); 
    }
}
