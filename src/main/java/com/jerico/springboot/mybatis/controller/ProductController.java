package com.jerico.springboot.mybatis.controller;


import com.github.pagehelper.PageInfo;
import com.jerico.springboot.mybatis.entity.ProductEntity;
import com.jerico.springboot.mybatis.service.ProductService;
import com.jerico.springboot.mybatis.util.SnowflakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 产品controller，演示ResponseEntity的另一种使用方式。
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ProductEntity savaProduct(@RequestBody ProductEntity productEntity) {
        return productService.saveProduct(productEntity);
    }

    @GetMapping("/{id}")
    public ProductEntity getProduct(@PathVariable String id) {
        return productService.getProduct(id);
    }

    @PutMapping("/{id}")
    public ProductEntity updateProduct(@PathVariable String id, @RequestBody ProductEntity productEntity) {
        productEntity.setId(id);
        return productService.updateProduct(productEntity);
    }

    @DeleteMapping("/{id}")
    public void removeProduct(@PathVariable String id) {
        productService.removeProduct(id);
    }

    @GetMapping
    public PageInfo<ProductEntity> listProductsByPage(int pageNum, int pageSize, String orderBy) {
        PageInfo<ProductEntity> result = productService.listProductByPage(pageNum, pageSize, orderBy);
        return result;
    }

    @PostMapping("/batch")
    public List<ProductEntity> batchSaveProduct(@RequestBody List<ProductEntity> productEntityList) {
        return  productService.batchSaveProduct(productEntityList);
    }

    @PutMapping
    public List<ProductEntity> batchUpdateProduct(@RequestBody List<ProductEntity> productEntityList) {
        productService.batchUpdateProduct(productEntityList);
        return productEntityList;
    }

    @DeleteMapping
    public void batchRemoveProduct(@RequestBody List<ProductEntity> productEntityList) {
        productService.batchRemoveProduct(productEntityList);
    }
}
