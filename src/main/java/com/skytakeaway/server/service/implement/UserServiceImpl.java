package com.skytakeaway.server.service.implement;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.skytakeaway.common.constant.MessageConstant;
import com.skytakeaway.common.context.BaseContext;
import com.skytakeaway.common.exception.AccountNotFoundException;
import com.skytakeaway.common.exception.DeleteUserException;
import com.skytakeaway.common.exception.PasswordErrorException;
import com.skytakeaway.common.result.PageResult;
import com.skytakeaway.pojo.dto.AddUserDTO;
import com.skytakeaway.pojo.dto.AssignRoleDTO;
import com.skytakeaway.pojo.dto.LoginDTO;
import com.skytakeaway.pojo.dto.UpdateUserDTO;
import com.skytakeaway.pojo.entity.*;
import com.skytakeaway.pojo.vo.AssignedRoleVO;
import com.skytakeaway.pojo.vo.UserInfoVO;
import com.skytakeaway.server.mapper.*;
import com.skytakeaway.server.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;
    @Autowired
    private RoleMenuRelationMapper roleMenuRelationMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public User login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        User user = userMapper.getByUsername(username);

        //if employee is not found in the employee_table
        if(user == null){
            throw new AccountNotFoundException(MessageConstant.ACOCUNT_NOT_FOUND);
        }

        //compare password
        //convert password with md5
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        if(!password.equals(user.getPassword())){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        return user;
    }

    @Override
    public UserInfoVO getUserInfo() {
        User user =  userMapper.getById(BaseContext.getCurrentId());
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfoVO);

        //get the role of the user
        // Get user role relations
        List<UserRoleRelation> userRoleRelationList = userRoleRelationMapper.getByUserId(BaseContext.getCurrentId());

        // Extract assigned role IDs
        List<Long> assignedRoleIds = userRoleRelationList.stream()
                .map(UserRoleRelation::getRoleId)
                .toList();

        if(assignedRoleIds.isEmpty()){
            return userInfoVO;
        }
        List<RoleMenuRelation> roleMenuRelationList = roleMenuRelationMapper.getByRoleIds(assignedRoleIds);

        //extract menu ids
        List<Long> menuIds = roleMenuRelationList.stream()
                .map(RoleMenuRelation::getMenuId)
                .distinct()
                .toList();

        //get menu
        if(menuIds.isEmpty()){
            return userInfoVO;
        }
        List<Menu> menuList = menuMapper.getByIds(menuIds);

        //get the route of the roles
        userInfoVO.setRoute(menuList.stream()
                .filter(menu -> menu.getLevel() > 1 && menu.getLevel() < 4)
                .map(Menu::getCode)
                .toList()
        );

        //get the button of the roles
        userInfoVO.setButton(menuList.stream()
                .filter(menu -> menu.getLevel() > 3)
                .map(Menu::getCode)
                .toList()
        );
        return userInfoVO;
    }

    @Override
    public PageResult<UserInfoVO> pageQuery(int pageNumber, int pageSize, String userName) {
        // Start pagination
        PageHelper.startPage(pageNumber, pageSize);

        // Fetch paginated users
        List<User> users = userMapper.pageQuery(userName);

        // Wrap the result using PageInfo
        PageInfo<User> pageInfo = new PageInfo<>(users);

        // Get user IDs from the current page
        List<Long> userIds = users.stream().map(User::getId).toList();

        // Get user-role relationships only for the current page
        List<UserRoleRelation> userRoleRelations = userRoleRelationMapper.getByUserIds(userIds);

        // Get unique role IDs
        List<Long> roleIds = userRoleRelations.stream().map(UserRoleRelation::getRoleId).distinct().toList();

        // Fetch only relevant roles
        List<Role> roleList = roleMapper.getByIds(roleIds);

        // Map roles for quick lookup
        Map<Long, String> roleMap = roleList.stream().collect(Collectors.toMap(Role::getId, Role::getRoleName));

        // Convert users to UserInfoVO
        List<UserInfoVO> result = new ArrayList<>();
        for (User user : users) {
            UserInfoVO userInfoVO = new UserInfoVO();
            BeanUtils.copyProperties(user, userInfoVO);

            // Get assigned roles
            List<String> assignedRoleNames = userRoleRelations.stream()
                    .filter(rel -> rel.getUserId().equals(user.getId()))
                    .map(rel -> roleMap.get(rel.getRoleId()))
                    .filter(Objects::nonNull)
                    .toList();

            // Format as "role1, role2"
            userInfoVO.setRoles(String.join(", ", assignedRoleNames));

            result.add(userInfoVO);
        }

        // Build and return the result
        return new PageResult<>(result, pageInfo.getTotal());
    }

    @Override
    public void addUser(AddUserDTO addUserDTO) {
        //copy properties get
        User user = new User();
        BeanUtils.copyProperties(addUserDTO, user);

        //set default avatar
        user.setAvatar("http://localhost:9000/management-system/44cb4864-44fe-4478-874e-dc3e8b8a6384.png");

        //convert encrypted password for account
        user.setPassword(DigestUtils.md5DigestAsHex(addUserDTO.getPassword().getBytes()));

        //send sql
        userMapper.addUser(user);
    }

    @Override
    public void updateUser(UpdateUserDTO updateUserDTO) {
        User user = new User();
        BeanUtils.copyProperties(updateUserDTO, user);
        userMapper.updateUser(user);
    }

    @Override
    public AssignedRoleVO getAssignedRole(Long id) {
        AssignedRoleVO assignedRoleVO = new AssignedRoleVO();

        // Get all roles
        List<Role> allRoleList = roleMapper.getAll();
        assignedRoleVO.setAllRoleList(allRoleList);

        // Get user role relations
        List<UserRoleRelation> userRoleRelationList = userRoleRelationMapper.getByUserId(id);

        // Extract assigned role IDs
        List<Long> assignedRoleIds = userRoleRelationList.stream()
                .map(UserRoleRelation::getRoleId)
                .toList();

        // Fetch assigned roles directly from the database
        List<Role> assignedRoleList = assignedRoleIds.isEmpty() ?
                Collections.emptyList() :
                roleMapper.getByIds(assignedRoleIds);

        assignedRoleVO.setAssignedRoleList(assignedRoleList);

        return assignedRoleVO;
    }

    @Override
    @Transactional
    public void assignRole(AssignRoleDTO assignRoleDTO) {
        Long userId = assignRoleDTO.getUserId();

        // Delete old data in user-role relation table
        userRoleRelationMapper.deleteByUserId(userId);

        // Insert new data only if there are roles assigned
        if (!assignRoleDTO.getRoleIds().isEmpty()) {
            List<UserRoleRelation> userRoleRelationList = assignRoleDTO.getRoleIds().stream()
                    .map(roleId -> new UserRoleRelation(null,userId, roleId))
                    .toList();

            userRoleRelationMapper.batchInsert(userRoleRelationList);
        }
    }

    @Override
    @Transactional
    public void batchDelete(List<Long> ids) {
        //avoid user to delete own account
        if(ids.contains(BaseContext.getCurrentId())){
            throw new DeleteUserException(MessageConstant.USER_DELETE);
        }

        if(!ids.isEmpty()){
            //delete user role relation
            userRoleRelationMapper.batchDelete(ids);

            //delete user
            userMapper.batchDelete(ids);
        }
    }
}
