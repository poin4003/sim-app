// package com.sim.app.sim_app.features.user.mapper;

// import java.util.List;
// import java.util.UUID;

// import org.apache.ibatis.annotations.Mapper;
// import org.apache.ibatis.annotations.Param;
// import org.apache.ibatis.annotations.Select;

// import com.baomidou.mybatisplus.core.mapper.BaseMapper;
// import com.sim.app.sim_app.features.user.entity.Permission;
// import com.sim.app.sim_app.features.user.entity.Role;

// @Mapper
// public interface RoleMapper extends BaseMapper<Role> {
//     @Select("SELECT m.* FROM permissions m " +
//             "JOIN role_permissions rm ON m.permission_id = rm.permission_id " +
//             "WHERE rm.role_id = #{roleId}")    
//     List<Permission> selectPermissionByRoleId(@Param("roleId") UUID roleId);
// }
