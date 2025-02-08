package com.skytakeaway.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skytakeaway.pojo.entity.Menu;
import com.skytakeaway.pojo.entity.RoleMenuRelation;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MenuVO {
    private Long id;
    private String menuName;
    private String code;
    private Long pid; // Parent ID
    private int level;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private List<MenuVO> children = new ArrayList<>(); // Ensure it's always initialized
    private boolean selected; // Used to check role access

    public void setMenuChildren(Long parentId, List<Menu> source) {
        children = source.stream()
                .filter(menu -> menu.getPid() != null && menu.getPid().equals(parentId)) // Use .equals()
                .map(menu -> {
                    MenuVO menuVO = new MenuVO();
                    BeanUtils.copyProperties(menu, menuVO);
                    menuVO.setMenuChildren(menuVO.getId(), source); // Recursive call
                    return menuVO;
                })
                .collect(Collectors.toList());
    }

    public void setSelected(List<Long> menuIdList) {
        // Ensure selected is initialized to false first
        this.selected = menuIdList.contains(id);

        // Recursively set selected for children
        if (children != null) {
            for (MenuVO child : children) {
                child.setSelected(menuIdList);  // Recursively process children
            }
        }
    }
}
