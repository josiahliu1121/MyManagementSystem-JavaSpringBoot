package com.skytakeaway.server.service.implement;

import com.skytakeaway.pojo.dto.PropertiesDTO;
import com.skytakeaway.pojo.entity.Properties;
import com.skytakeaway.pojo.entity.PropertiesValue;
import com.skytakeaway.pojo.vo.PropertiesVO;
import com.skytakeaway.server.mapper.PropertiesMapper;
import com.skytakeaway.server.mapper.PropertiesValueMapper;
import com.skytakeaway.server.service.PropertiesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class PropertiesServiceImpl implements PropertiesService {
    @Autowired
    private PropertiesMapper propertiesMapper;
    @Autowired
    private PropertiesValueMapper propertiesValueMapper;

    @Override
    public List<PropertiesDTO> getPropertiesList(Long category1Id, Long category2Id, Long category3Id) {
        List<PropertiesDTO> result = new ArrayList<>();

        // Search for category1
        List<Properties> propertiesList = propertiesMapper.getByCategoryIdAndLevel(category1Id, 1);

        if (propertiesList != null) {
            propertiesList.forEach(property -> {
                // Fetch property values
                List<PropertiesValue> propertiesValueList = propertiesValueMapper.getByPropId(property.getId());

                // Create and populate PropertiesDTO
                PropertiesDTO propertiesDTO = new PropertiesDTO();
                BeanUtils.copyProperties(property, propertiesDTO);
                propertiesDTO.setPropValueList(propertiesValueList);

                // Add to the result list
                result.add(propertiesDTO);
            });
        }

        //search for category2
        propertiesList = propertiesMapper.getByCategoryIdAndLevel(category2Id, 2);

        if (propertiesList != null) {
            propertiesList.forEach(property -> {
                // Fetch property values
                List<PropertiesValue> propertiesValueList = propertiesValueMapper.getByPropId(property.getId());

                // Create and populate PropertiesDTO
                PropertiesDTO propertiesDTO = new PropertiesDTO();
                BeanUtils.copyProperties(property, propertiesDTO);
                propertiesDTO.setPropValueList(propertiesValueList);

                // Add to the result list
                result.add(propertiesDTO);
            });
        }

        //search for category3
        propertiesList = propertiesMapper.getByCategoryIdAndLevel(category3Id, 3);

        if (propertiesList != null) {
            propertiesList.forEach(property -> {
                // Fetch property values
                List<PropertiesValue> propertiesValueList = propertiesValueMapper.getByPropId(property.getId());

                // Create and populate PropertiesDTO
                PropertiesDTO propertiesDTO = new PropertiesDTO();
                BeanUtils.copyProperties(property, propertiesDTO);
                propertiesDTO.setPropValueList(propertiesValueList);

                // Add to the result list
                result.add(propertiesDTO);
            });
        }

        return result;
    }

    @Override
    @Transactional
    public void saveOrUpdateProperties(PropertiesVO propertiesVO) {
        if(propertiesVO.getId() != null){
            //update

            //update data in properties table
            Properties properties = new Properties();
            BeanUtils.copyProperties(propertiesVO, properties);
            propertiesMapper.updateProperties(properties);

            //update properties value
            propertiesValueMapper.deleteByPropId(propertiesVO.getId());

            //insert prop id for value
            propertiesVO.getPropValueList().forEach(propertiesValue -> {
                propertiesValue.setPropId(properties.getId());
            });

            propertiesValueMapper.batchInsert(propertiesVO.getPropValueList());
        } else {
            //save

            //insert data in properties table
            Properties properties = new Properties();
            BeanUtils.copyProperties(propertiesVO, properties);
            propertiesMapper.insert(properties);

            //insert prop id for value
            propertiesVO.getPropValueList().forEach(propertiesValue -> {
                propertiesValue.setPropId(properties.getId());
            });

            //insert properties value
            propertiesValueMapper.batchInsert(propertiesVO.getPropValueList());
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {


        //delete properties value
        propertiesValueMapper.deleteByPropId(id);

        //delete properties
        propertiesMapper.deleteById(id);
    }
}
