package com.skytakeaway.server.mapper;

import com.skytakeaway.pojo.entity.RoleMenuRelation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMenuRelationMapper {
    @Select("SELECT * FROM role_menu_relation WHERE role_id = #{roleId}")
    List<RoleMenuRelation> getByRoleId(Long roleId);

    @Delete("DELETE FROM role_menu_relation where menu_id = #{menuId}")
    void deleteByMenuId(Long menuId);

    void batchInsert(List<RoleMenuRelation> roleMenuRelationList);

    @Delete("DELETE FROM role_menu_relation where role_id = #{roleId}")
    void deleteByRoleId(Long roleId);

    List<RoleMenuRelation> getByRoleIds(List<Long> roleIds);
}
