// package com.sim.app.sim_app.features.user.mapper;

// import java.util.List;
// import java.util.UUID;

// import org.apache.ibatis.annotations.Many;
// import org.apache.ibatis.annotations.Mapper;
// import org.apache.ibatis.annotations.Param;
// import org.apache.ibatis.annotations.Result;
// import org.apache.ibatis.annotations.Results;
// import org.apache.ibatis.annotations.Select;

// import com.baomidou.mybatisplus.core.mapper.BaseMapper;
// import com.sim.app.sim_app.features.user.entity.Role;
// import com.sim.app.sim_app.features.user.entity.User;

// @Mapper
// public interface UserMapper extends BaseMapper<User> {
//     @Select("SELECT r.* FROM roles r " +
//         "JOIN user_roles ur ON r.role_id = ur.role_id " +
//         "WHERE ur.user_id = #{userId}")
//     @Results({
//         @Result(property = "roleId", column = "role_id"),
//         @Result(property = "permission", column = "role_id",
//             many = @Many(select = "com.sim.app.sim_app.features.user.mapper.RoleMapper.selectPermissionByRoleId"))
//     })
//     List<Role> selectRolesByUserId(@Param("userId") UUID userId);
// }
