package com.skytakeaway.server.mapper;

import com.skytakeaway.common.constant.AutoFillConstant;
import com.skytakeaway.common.enumeration.OperationType;
import com.skytakeaway.pojo.dto.UpdateUserDTO;
import com.skytakeaway.pojo.entity.Brand;
import com.skytakeaway.pojo.entity.User;
import com.skytakeaway.server.annotation.AutoFill;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user where username=#{username}")
    User getByUsername(String username);

    @Select("select * from user where id=#{id}")
    User getById(Long id);

    List<User> pageQuery(String userName);

    @Insert("INSERT INTO user (username, password, avatar, name, create_time, update_time) " +
            "VALUES (#{username}, #{password}, #{avatar}, #{name}, #{createTime}, #{updateTime})")
    @AutoFill(value = OperationType.INSERT)
    void addUser(User user);

    @Update("UPDATE user SET username = #{username}, name = #{name}, update_time= #{updateTime} where id = #{id}")
    @AutoFill(value = OperationType.UPDATE)
    void updateUser(User user);

    void batchDelete(List<Long> ids);
}
