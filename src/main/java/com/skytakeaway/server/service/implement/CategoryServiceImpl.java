package com.skytakeaway.server.service.implement;

import com.skytakeaway.pojo.entity.Category1;
import com.skytakeaway.pojo.entity.Category2;
import com.skytakeaway.pojo.entity.Category3;
import com.skytakeaway.server.mapper.Category1Mapper;
import com.skytakeaway.server.mapper.Category2Mapper;
import com.skytakeaway.server.mapper.Category3Mapper;
import com.skytakeaway.server.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private Category1Mapper category1Mapper;
    @Autowired
    private Category2Mapper category2Mapper;
    @Autowired
    private Category3Mapper category3Mapper;


    @Override
    public List<Category1> getCategory1() {
        return category1Mapper.getAllCategory();
    }

    @Override
    public List<Category2> getCategory2(Long category1Id) {
        return category2Mapper.getByCategory1Id(category1Id);
    }

    @Override
    public List<Category3> getCategory3(Long category2Id) {
        return category3Mapper.getByCategory2Id(category2Id);
    }
}
