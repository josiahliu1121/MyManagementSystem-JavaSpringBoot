package com.skytakeaway.server.service.implement;

import com.skytakeaway.pojo.entity.StaticRandomData;
import com.skytakeaway.pojo.vo.DataVO;
import com.skytakeaway.server.service.DataService;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl implements DataService {
    @Override
    public DataVO initialData() {
        return StaticRandomData.generateRandomData();
    }
}
