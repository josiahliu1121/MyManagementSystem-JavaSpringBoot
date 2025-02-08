package com.skytakeaway.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddUserDTO implements Serializable {
    private String username;
    private String name;
    private String password;
}
