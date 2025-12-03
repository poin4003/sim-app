package com.sim.app.sim_app.features.auth.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sim.app.sim_app.core.controller.BaseController;
import com.sim.app.sim_app.core.vo.ResultMessage;
import com.sim.app.sim_app.features.auth.api.dto.request.LoginRequest;
import com.sim.app.sim_app.features.auth.api.dto.response.LoginResponse;
import com.sim.app.sim_app.features.auth.service.AuthService;
import com.sim.app.sim_app.features.auth.service.schema.command.LoginCmd;
import com.sim.app.sim_app.features.auth.service.schema.result.LoginResult;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication V1", description = "Auth docs")
public class AuthController extends BaseController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResultMessage<LoginResponse>> login(
        @RequestBody LoginRequest request,
        HttpServletRequest servletRequest
    ) {
        String ipAddress = getClientIp(servletRequest);

        LoginCmd cmd = LoginCmd.builder()
                            .userEmail(request.getUserEmail())
                            .userPassword(request.getUserPassword())
                            .ipAddress(ipAddress)
                            .build();

        LoginResult result = authService.login(cmd);

        return OK("Login success", LoginResponse.builder()
                                                    .accessToken(result.getAccessToken())                                 
                                                    .refreshToken(result.getRefreshToken())
                                                    .userEmail(result.getUserEmail())
                                                    .userId(result.getUserId())
                                                    .build()); 
    }

    private String getClientIp(HttpServletRequest request) {
        if (request == null) return "UNKNOWN";
        String remoteAddr = request.getHeader("X-FORWARDED-FOR");
        if (remoteAddr == null || "".equals(remoteAddr)) {
            remoteAddr = request.getRemoteAddr();
        }
        if (remoteAddr != null && remoteAddr.contains(",")) {
            return remoteAddr.split(",")[0].trim();
        }
        return remoteAddr;
    }
}
