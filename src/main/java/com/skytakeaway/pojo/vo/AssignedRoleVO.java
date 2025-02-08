package com.skytakeaway.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skytakeaway.pojo.entity.Menu;
import com.skytakeaway.pojo.entity.Role;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AssignedRoleVO {
    private List<Role> allRoleList;
    private List<Role> assignedRoleList;
}
