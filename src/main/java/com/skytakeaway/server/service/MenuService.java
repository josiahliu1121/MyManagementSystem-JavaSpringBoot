package com.skytakeaway.server.service;

import com.skytakeaway.pojo.dto.MenuDTO;
import com.skytakeaway.pojo.vo.MenuVO;

import java.util.List;

public interface MenuService {
    List<MenuVO> getAllMenu();

    List<MenuVO> getByRole(Long roleId);

    void addMenu(MenuDTO menuDTO);

    void updateMenu(MenuDTO menuDTO);

    void deleteMenu(Long id);
}
