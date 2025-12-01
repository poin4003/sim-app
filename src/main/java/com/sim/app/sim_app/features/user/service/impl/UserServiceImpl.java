package com.sim.app.sim_app.features.user.service.impl;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sim.app.sim_app.core.exception.ExceptionFactory;
import com.sim.app.sim_app.features.user.entity.UserBaseEntity;
import com.sim.app.sim_app.features.user.enums.UserStatusEnum;
import com.sim.app.sim_app.features.user.repository.UserBaseRepository;
import com.sim.app.sim_app.features.user.service.UserService;
import com.sim.app.sim_app.features.user.service.schema.UserCoreMapStruct;
import com.sim.app.sim_app.features.user.service.schema.command.UserCreationCmd;
import com.sim.app.sim_app.features.user.service.schema.result.UserResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserBaseRepository userBaseRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserCoreMapStruct userCoreMapStruct;

    @Override
    public void checkEmailUnique(String email) {
        LambdaQueryWrapper<UserBaseEntity> checkEmail = new LambdaQueryWrapper<>();
        checkEmail.eq(UserBaseEntity::getUserEmail, email);
        if (userBaseRepository.exists(checkEmail)) {
            throw ExceptionFactory.dataAlreadyExists("Email " + email + "already exists.");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResult createUser(UserCreationCmd cmd) {
        checkEmailUnique(cmd.getEmail()); 
        
        UUID userId = UUID.randomUUID();
        
        UserBaseEntity userBase = new UserBaseEntity();
        userBase.setUserId(userId);
        userBase.setUserEmail(cmd.getEmail());
        userBase.setUserPassword(passwordEncoder.encode(cmd.getPassword()));
        userBase.setUserStatus(UserStatusEnum.ACTIVE);
        userBase.setDelFlag("0");

        userBaseRepository.insert(userBase);

        return userCoreMapStruct.toUserResult(userBase);
    }
}
