package com.hau.kute.springbootelasticsearch.services;

import com.hau.kute.springbootelasticsearch.model.Product;
import com.hau.kute.springbootelasticsearch.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSearchServiceWithRepo {

    private ProductRepository productRepository;

    @Autowired
    public ProductSearchServiceWithRepo(final ProductRepository productRepository) {
        super();
        this.productRepository = productRepository;
    }

    public void createProductIndexBulk(final List<Product> products) {
        productRepository.saveAll(products);
    }

    public void createProductIndex(final Product product) {
        productRepository.save(product);
    }
}
