package com.skytakeaway.server.service;

import com.skytakeaway.common.result.PageResult;
import com.skytakeaway.pojo.dto.RoleDTO;
import com.skytakeaway.pojo.entity.Role;

import java.util.List;

public interface RoleService {
    PageResult<Role> pageQuery(int pageNumber, int pageSize, String roleName);

    void addRole(RoleDTO roleDTO);

    void updateRole(RoleDTO roleDTO);

    void assignMenu(Long roleId, List<Long> menuIdList);

    void deleteRole(Long id);
}
