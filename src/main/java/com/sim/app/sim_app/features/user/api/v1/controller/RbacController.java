package com.sim.app.sim_app.features.user.api.v1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.Authentication;

import com.sim.app.sim_app.core.vo.ResultMessage;
import com.sim.app.sim_app.features.user.entity.User;

import io.swagger.v3.oas.annotations.tags.Tag;

import com.sim.app.sim_app.core.controller.BaseController;

import lombok.RequiredArgsConstructor;

@RestController
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "RBAC Management V1", description = "RBAC docs")
public class RbacController extends BaseController {
    @GetMapping("/info") 
    public ResponseEntity<ResultMessage<User>> getUserInfo(Authentication authentication) {
        User principal = (User)authentication.getPrincipal();
        return OK(principal);
    }
}
