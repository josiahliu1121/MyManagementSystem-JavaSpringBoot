package com.skytakeaway.server.service;

import com.skytakeaway.pojo.dto.PropertiesDTO;
import com.skytakeaway.pojo.vo.PropertiesVO;

import java.util.List;

public interface PropertiesService {
    List<PropertiesDTO> getPropertiesList(Long category1Id, Long category2Id, Long category3Id);

    void saveOrUpdateProperties(PropertiesVO propertiesVO);

    void deleteById(Long id);
}
