package com.jerico.springboot.mybatis.service;

import com.github.pagehelper.PageInfo;
import com.jerico.springboot.mybatis.entity.ProductEntity;

public interface ProductService {

    ProductEntity saveProduct(ProductEntity productEntity);

    ProductEntity getProduct(String id);

    ProductEntity updateProduct(ProductEntity productEntity);

    void removeProduct(String id);

    PageInfo<ProductEntity> listProductByPage(int pageNum, int pageSize, String orderBy);
}
