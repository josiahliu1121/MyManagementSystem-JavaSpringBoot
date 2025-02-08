package com.skytakeaway.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    private Long id;
    private String menuName;
    private String code;
    private Long pid; //parent id
    private int level;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
