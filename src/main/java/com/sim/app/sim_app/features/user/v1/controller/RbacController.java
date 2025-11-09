package com.sim.app.sim_app.features.user.v1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.Authentication;

import com.sim.app.sim_app.core.vo.ResultMessage;
import com.sim.app.sim_app.features.user.entity.User;
import com.sim.app.sim_app.core.controller.BaseController;

import lombok.RequiredArgsConstructor;

@RestController
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class RbacController extends BaseController {
    @GetMapping("/user/info") 
    public ResponseEntity<ResultMessage<User>> getUserInfo(Authentication authentication) {
        System.out.println(authentication); 
        User principal = (User)authentication.getPrincipal();
        return OK(principal);
    }
}
