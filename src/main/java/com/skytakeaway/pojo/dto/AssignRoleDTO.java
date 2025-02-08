package com.skytakeaway.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AssignRoleDTO implements Serializable {
    private Long userId;
    private List<Long> roleIds;
}
