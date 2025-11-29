package com.sim.app.sim_app.features.user.service.schema.result;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.sim.app.sim_app.features.user.enums.UserStatusEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResult {

    private UUID userId;

    private String email;

    private String username;

    private String phoneNumber;

    private UserStatusEnum status;

    private List<String> roles;

    private LocalDateTime createdAt;
}
