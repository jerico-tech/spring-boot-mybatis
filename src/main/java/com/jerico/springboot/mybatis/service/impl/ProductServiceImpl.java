package com.jerico.springboot.mybatis.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jerico.springboot.mybatis.dao.ProductDAO;
import com.jerico.springboot.mybatis.entity.ProductEntity;
import com.jerico.springboot.mybatis.service.ProductService;
import com.jerico.springboot.mybatis.util.SnowflakeUtil;
import org.apache.tomcat.jni.Proc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDAO productDAO;


    @Override
    public ProductEntity saveProduct(ProductEntity productEntity) {
        Long id = SnowflakeUtil.snowflakeId();
        productEntity.setId(id.toString());
        productDAO.insertProduct(productEntity);
        return productEntity;
    }

    @Override
    public ProductEntity getProduct(String id) {
        return productDAO.getProduct(id);
    }

    @Override
    public ProductEntity updateProduct(ProductEntity productEntity) {
        productDAO.updateProduct(productEntity);
        return productEntity;
    }

    @Override
    public void removeProduct(String id) {
        productDAO.deleteProduct(id);
    }

    @Override
    public PageInfo<ProductEntity> listProductByPage(int pageNum, int pageSize, String orderBy) {
        // 这一句是核心,下面紧跟列表查询语句，中间不能有其他代码。PageHelper中startPage开启分页方法只对后面的sql查询起作用.
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<ProductEntity> productEntityList = productDAO.listProducts();
        PageInfo<ProductEntity> pageInfo = new PageInfo<>(productEntityList);
        return pageInfo;
    }


}
