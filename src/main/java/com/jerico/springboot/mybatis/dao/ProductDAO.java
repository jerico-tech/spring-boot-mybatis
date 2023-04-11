package com.jerico.springboot.mybatis.dao;

import com.jerico.springboot.mybatis.entity.ProductEntity;

import java.util.List;

public interface ProductDAO {
    int insertProduct(ProductEntity productEntity);

    int updateProduct(ProductEntity productEntity);

    int deleteProduct(String id);

    ProductEntity getProduct(String id);

    List<ProductEntity> listProducts();

    int batchInsertProduct(List<ProductEntity> productEntityList);

    int batchUpdateProduct(List<ProductEntity> productEntityList);

    int batchDeleteProduct(List<ProductEntity> productEntityList);
}
