package com.sazim.io.onboarding.controllers;

import com.sazim.io.onboarding.models.Product;
import com.sazim.io.onboarding.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityNotFoundException;

@Controller
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryMapping
    public Iterable<Product> products() {
        return productRepository.findAll();
    }
    @MutationMapping
    public Product createProduct(@Argument ProductCreateReq productCreateReq) {
        Product p = new Product();
        BeanUtils.copyProperties(productCreateReq,p);
        return productRepository.save(p);
    }
    @MutationMapping
    public Product updateProduct(@Argument Product product) {
        Product p = productRepository.findById(product.getId()).orElseThrow();
        BeanUtils.copyProperties(product,p);
        productRepository.save(p);
        return p;
    }
    @MutationMapping
    public boolean deleteProduct(@Argument Long id){
        Product p = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product ID:"+id+" not found"));
        productRepository.delete(p);
        return true;
    }
}
record ProductCreateReq(String name,String description, String imageUrl){}
