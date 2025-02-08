package com.skytakeaway.server.service.implement;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.skytakeaway.common.constant.MessageConstant;
import com.skytakeaway.common.exception.RoleUsedException;
import com.skytakeaway.common.result.PageResult;
import com.skytakeaway.pojo.dto.RoleDTO;
import com.skytakeaway.pojo.entity.Role;
import com.skytakeaway.pojo.entity.RoleMenuRelation;
import com.skytakeaway.pojo.entity.UserRoleRelation;
import com.skytakeaway.server.mapper.RoleMapper;
import com.skytakeaway.server.mapper.RoleMenuRelationMapper;
import com.skytakeaway.server.mapper.UserRoleRelationMapper;
import com.skytakeaway.server.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuRelationMapper roleMenuRelationMapper;
    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;

    @Override
    public PageResult<Role> pageQuery(int pageNumber, int pageSize, String roleName) {
        // Start pagination
        PageHelper.startPage(pageNumber, pageSize);

        // Fetch the list of brands
        List<Role> roles = roleMapper.pageQuery(pageNumber, pageSize, roleName);

        // Wrap the result using PageInfo
        PageInfo<Role> pageInfo = new PageInfo<>(roles);

        // Build the PageResult (custom object to encapsulate data and metadata)
        return new PageResult<>(
                pageInfo.getList(),          // Data
                pageInfo.getTotal()         // Total records
        );
    }

    @Override
    public void addRole(RoleDTO roleDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        roleMapper.addRole(role);
    }

    @Override
    public void updateRole(RoleDTO roleDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);

        roleMapper.updateRole(role);
    }

    @Override
    @Transactional
    public void assignMenu(Long roleId, List<Long> menuIdList) {
        //delete the exists data in role menu relation table
        roleMenuRelationMapper.deleteByRoleId(roleId);

        if (menuIdList == null || menuIdList.isEmpty()) {
            return; // Avoid unnecessary DB operations
        }

        //create a list of role menu relation item
        List<RoleMenuRelation> roleMenuRelationList = menuIdList.stream().map(menuId -> {
            RoleMenuRelation roleMenuRelation = new RoleMenuRelation();
            roleMenuRelation.setRoleId(roleId);
            roleMenuRelation.setMenuId(menuId);
            return roleMenuRelation;
        }).collect(Collectors.toList());

        roleMenuRelationMapper.batchInsert(roleMenuRelationList);
    }

    @Override
    public void deleteRole(Long id) {
        //check if role is used by user
        List<UserRoleRelation> userRoleRelationList = userRoleRelationMapper.getByRoleId(id);

        if(!userRoleRelationList.isEmpty()){
            throw new RoleUsedException(MessageConstant.ROLE_USED);
        }

        //delete role
        roleMapper.deleteById(id);
    }
}
