package com.skytakeaway.server.service.implement;

import com.skytakeaway.pojo.dto.MenuDTO;
import com.skytakeaway.pojo.entity.Menu;
import com.skytakeaway.pojo.entity.RoleMenuRelation;
import com.skytakeaway.pojo.vo.MenuVO;
import com.skytakeaway.server.mapper.MenuMapper;
import com.skytakeaway.server.mapper.RoleMenuRelationMapper;
import com.skytakeaway.server.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMenuRelationMapper roleMenuRelationMapper;

    @Override
    public List<MenuVO> getAllMenu() {
        // Get all menus from the database
        List<Menu> menuList = menuMapper.getAll();

        // Find the root menu (Level 1)
        List<Menu> mainMenu = menuList.stream()
                .filter(menu -> menu.getLevel() == 1)
                .collect(Collectors.toList());

        // Convert to MenuVO
        List<MenuVO> mainMenuVO = new ArrayList<>();
        mainMenu.forEach(menu -> {
            MenuVO menuVO = new MenuVO();
            BeanUtils.copyProperties(menu, menuVO);

            // Recursively set child menus
            menuVO.setMenuChildren(menuVO.getId(), menuList);

            mainMenuVO.add(menuVO);
        });

        return mainMenuVO;
    }

    @Override
    public List<MenuVO> getByRole(Long roleId) {
        List<MenuVO> result = getAllMenu();

        //check the selected menu of the role
        List<RoleMenuRelation> roleMenuRelationList = roleMenuRelationMapper.getByRoleId(roleId);

        //get menu id list
        List<Long> menuIdList = roleMenuRelationList.stream()
                .map(RoleMenuRelation::getMenuId)  // Assuming there's a `getMenuId()` method
                .collect(Collectors.toList());

        // Set selected for each menu in the result list
        result.forEach(menuVO -> {
            menuVO.setSelected(menuIdList);
        });

        return result;
    }

    @Override
    public void addMenu(MenuDTO menuDTO) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDTO,menu);

        menuMapper.addMenu(menu);
    }

    @Override
    public void updateMenu(MenuDTO menuDTO) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDTO,menu);

        menuMapper.updateMenuById(menu);
    }

    @Override
    @Transactional
    public void deleteMenu(Long id) {
        //delete menu in role_menu_relation table
        roleMenuRelationMapper.deleteByMenuId(id);

        //delete menu in menu table
        menuMapper.deleteById(id);
    }
}
