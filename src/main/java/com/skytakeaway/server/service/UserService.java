package com.skytakeaway.server.service;

import com.skytakeaway.common.result.PageResult;
import com.skytakeaway.pojo.dto.AddUserDTO;
import com.skytakeaway.pojo.dto.AssignRoleDTO;
import com.skytakeaway.pojo.dto.LoginDTO;
import com.skytakeaway.pojo.dto.UpdateUserDTO;
import com.skytakeaway.pojo.entity.User;
import com.skytakeaway.pojo.vo.AssignedRoleVO;
import com.skytakeaway.pojo.vo.UserInfoVO;

import java.util.List;

public interface UserService {
    User login(LoginDTO loginDTO);

    UserInfoVO getUserInfo();

    PageResult<UserInfoVO> pageQuery(int pageNumber, int pageSize, String userName);

    void addUser(AddUserDTO addUserDTO);

    void updateUser(UpdateUserDTO updateUserDTO);

    AssignedRoleVO getAssignedRole(Long id);

    void assignRole(AssignRoleDTO assignRoleDTO);

    void batchDelete(List<Long> ids);
}
