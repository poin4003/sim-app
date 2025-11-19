package com.sim.app.sim_app.features.user.api.v1.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String email; 

    private String password;
}
