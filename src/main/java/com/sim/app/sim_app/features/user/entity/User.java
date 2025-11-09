package com.sim.app.sim_app.features.user.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("users")
public class User implements UserDetails {

    @TableId(value = "user_id", type = IdType.ASSIGN_UUID)
    private UUID userId;

    @TableField(value = "email")
    private String email;

    @TableField(value = "password")
    private String password;

    @TableField(value = "status")
    private String status;

    @TableField(value = "del_flag")
    private String delFlag;

    @TableField(exist = false)
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        authorities.addAll(roles.stream()
           .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleKey()))
           .collect(Collectors.toList()));

        authorities.addAll(roles.stream()
            .flatMap(role -> role.getPermissions().stream())
            .map(permissionKey -> new SimpleGrantedAuthority(permissionKey.getPermissionKey()))
            .collect(Collectors.toList()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;    
    }

    @Override
    public boolean isAccountNonLocked() { 
        return "0".equals(this.status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return "0".equals(this.delFlag);
    }
}
