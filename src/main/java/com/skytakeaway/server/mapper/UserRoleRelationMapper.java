package com.skytakeaway.server.mapper;

import com.skytakeaway.pojo.entity.UserRoleRelation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserRoleRelationMapper {
    @Select("SELECT * FROM user_role_relation WHERE role_id = #{roleId}")
    List<UserRoleRelation> getByRoleId(Long roleId);

    List<UserRoleRelation> getByUserIds(List<Long> userIds);

    @Select("SELECT * FROM user_role_relation WHERE user_id = #{userId}")
    List<UserRoleRelation> getByUserId(Long UserId);

    @Delete("DELETE FROM user_role_relation WHERE user_id = #{userId}")
    void deleteByUserId(Long userId);

    void batchInsert(List<UserRoleRelation> userRoleRelationList);

    void batchDelete(List<Long> userIds);
}
