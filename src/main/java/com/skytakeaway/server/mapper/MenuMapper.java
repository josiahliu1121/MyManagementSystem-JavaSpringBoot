package com.skytakeaway.server.mapper;

import com.skytakeaway.common.enumeration.OperationType;
import com.skytakeaway.pojo.entity.Menu;
import com.skytakeaway.server.annotation.AutoFill;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenuMapper {
    @Select("SELECT * FROM menu")
    List<Menu> getAll();

    @Insert("INSERT INTO menu (menuName, code, pid, level, create_time, update_time) " +
            "VALUES (#{menuName}, #{code}, #{pid}, #{level}, #{createTime}, #{updateTime})")
    @AutoFill(OperationType.INSERT)
    void addMenu(Menu menu);

    @Update("UPDATE menu SET menuName = #{menuName}, code = #{code}, pid = #{pid}, level = #{level}, " +
            "create_time = #{createTime}, update_time = #{updateTime} " +
            "WHERE id = #{id}")
    @AutoFill(OperationType.UPDATE)
    void updateMenuById(Menu menu);

    @Delete("DELETE FROM menu where id = #{id}")
    void deleteById(Long id);

    List<Menu> getByIds(List<Long> menuIds);
}
