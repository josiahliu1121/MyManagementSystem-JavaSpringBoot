package com.skytakeaway.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MenuDTO implements Serializable {
    private Long id;
    private String menuName;
    private String code;
    private Long pid;
    private int level;
}
