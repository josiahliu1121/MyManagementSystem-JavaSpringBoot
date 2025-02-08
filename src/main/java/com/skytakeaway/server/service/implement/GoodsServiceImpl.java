package com.skytakeaway.server.service.implement;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.skytakeaway.common.constant.MessageConstant;
import com.skytakeaway.common.exception.BaseException;
import com.skytakeaway.common.exception.BrandUsedException;
import com.skytakeaway.common.result.PageResult;
import com.skytakeaway.pojo.entity.Brand;
import com.skytakeaway.pojo.entity.SPU;
import com.skytakeaway.server.mapper.GoodsMapper;
import com.skytakeaway.server.mapper.SPUMapper;
import com.skytakeaway.server.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private SPUMapper spuMapper;

    @Override
    public PageResult<Brand> pageQuery(int pageNumber, int pageSize) {
        // Start pagination
        PageHelper.startPage(pageNumber, pageSize);

        // Fetch the list of brands
        List<Brand> brands = goodsMapper.pageQuery();

        // Wrap the result using PageInfo
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);

        // Build the PageResult (custom object to encapsulate data and metadata)
        return new PageResult<>(
                pageInfo.getList(),          // Data
                pageInfo.getTotal()         // Total records
        );
    }

    @Override
    public void addBrand(Brand brand) {
        if (brand == null || brand.getName() == null || brand.getName().isEmpty()) {
            throw new BaseException(MessageConstant.INVALID_INPUT);
        }

        try {
            goodsMapper.addBrand(brand);
        } catch (DuplicateKeyException e) {
            // Handle unique constraint violation
            throw new BaseException(MessageConstant.UNIQUE_CONSTRAINT_ERROR);
        } catch (DataAccessException e) {
            // Handle other database-related exceptions
            throw new BaseException(MessageConstant.DATABASE_ERROR);
        } catch (Exception e) {
            // Generic fallback for unexpected exceptions
            throw new BaseException(MessageConstant.UNKNOWN_ERROR);
        }
    }

    @Override
    public void updateBrand(Brand brand) {
        if (brand == null || brand.getName() == null || brand.getName().isEmpty()) {
            throw new BaseException(MessageConstant.INVALID_INPUT);
        }

        try {
            goodsMapper.updateBrand(brand);
        } catch (DuplicateKeyException e) {
            // Handle unique constraint violation
            throw new BaseException(MessageConstant.UNIQUE_CONSTRAINT_ERROR);
        } catch (DataAccessException e) {
            // Handle other database-related exceptions
            throw new BaseException(MessageConstant.DATABASE_ERROR);
        } catch (Exception e) {
            // Generic fallback for unexpected exceptions
            throw new BaseException(MessageConstant.UNKNOWN_ERROR);
        }
    }

    @Override
    public void deleteBrand(Long id) {
        //if spu is using the brand, cannot delete brand
        SPU spu = spuMapper.selectByBrandId(id);

        if(spu != null){
            throw new BrandUsedException(MessageConstant.BRAND_USED);
        }

        goodsMapper.deleteById(id);
    }

    @Override
    public List<Brand> getBrandList() {
        return goodsMapper.pageQuery();
    }
}
