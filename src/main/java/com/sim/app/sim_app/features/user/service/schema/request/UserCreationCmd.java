package com.sim.app.sim_app.features.user.service.schema.request;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreationCmd {

    private String email;

    private String password;

    private String username;
    
    private List<String> roleKeys;
}
