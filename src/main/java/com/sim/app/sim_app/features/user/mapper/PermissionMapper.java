// package com.sim.app.sim_app.features.user.mapper;

// import java.util.List;
// import java.util.UUID;

// import org.apache.ibatis.annotations.Mapper;
// import org.apache.ibatis.annotations.Param;
// import org.apache.ibatis.annotations.Select;

// import com.baomidou.mybatisplus.core.mapper.BaseMapper;
// import com.sim.app.sim_app.features.user.entity.Permission;

// @Mapper
// public interface PermissionMapper extends BaseMapper<Permission>{
//     @Select("SELECT DISTINCT p.* FROM permissions p " +
//         "JOIN role_permissions rp ON p.permission_id = rp.permission_id " +
//         "JOIN user_roles ur ON rp.role_id = ur.role_id " +
//         "WHERE ur.user_id = #{userId}")
//     List<Permission> selectByUserId(@Param("userId") UUID userId);
// }
