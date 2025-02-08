package com.skytakeaway.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateUserDTO implements Serializable {
    private Long id;
    private String username;
    private String name;
}
