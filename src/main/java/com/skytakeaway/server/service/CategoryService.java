package com.skytakeaway.server.service;

import com.skytakeaway.pojo.entity.Category1;
import com.skytakeaway.pojo.entity.Category2;
import com.skytakeaway.pojo.entity.Category3;

import java.util.List;

public interface CategoryService {

    List<Category1> getCategory1();

    List<Category2> getCategory2(Long category1Id);

    List<Category3> getCategory3(Long category2Id);
}
