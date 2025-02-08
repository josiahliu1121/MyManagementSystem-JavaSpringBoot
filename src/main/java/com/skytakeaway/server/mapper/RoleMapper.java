package com.skytakeaway.server.mapper;

import com.skytakeaway.common.enumeration.OperationType;
import com.skytakeaway.pojo.entity.Role;
import com.skytakeaway.server.annotation.AutoFill;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<Role> pageQuery(int pageNumber, int pageSize, String roleName);

    @Insert("insert into role (role_name, create_time, update_time) VALUES (#{roleName},#{createTime},#{updateTime})")
    @AutoFill(OperationType.INSERT)
    void addRole(Role role);

    @Update("update role set role_name=#{roleName}, update_time= #{updateTime} where id = #{id}")
    @AutoFill(OperationType.UPDATE)
    void updateRole(Role role);

    @Delete("delete from role where id = #{id}")
    void deleteById(Long id);

    @Select("SELECT * FROM role")
    List<Role> getAll();

    List<Role> getByIds(List<Long> roleIds);
}
