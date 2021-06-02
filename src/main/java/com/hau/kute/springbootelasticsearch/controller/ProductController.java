package com.hau.kute.springbootelasticsearch.controller;

import com.hau.kute.springbootelasticsearch.model.Product;
import com.hau.kute.springbootelasticsearch.services.ProductSearchServiceWithRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileReader;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@RestController("/")
public class ProductController {

    @Autowired
    ProductSearchServiceWithRepo productSearchServiceWithRepo;

    @GetMapping("/product/add/dummny-data")
    public String addProduct() {

        JSONParser jsonParser = new JSONParser();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("dummydata.json").getFile());
            FileReader reader = new FileReader(file);

            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray products = (JSONArray) obj;
            System.out.println(products);

            products.forEach(jsonProduct -> {
                Product product = parseProduct((JSONObject)jsonProduct);
                productSearchServiceWithRepo.createProductIndex(product);
            });
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "ok";
    }

    private Product parseProduct(JSONObject jsonProduct) {
        String name = (String) jsonProduct.get("name");

        long price = (long) jsonProduct.get("price");

        Integer quantity = Integer.valueOf(String.valueOf((long) jsonProduct.get("quantity")));

        String category = (String) jsonProduct.get("category");

        String description = (String) jsonProduct.get("description");

        String manufacturer = (String) jsonProduct.get("manufacturer");

        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setCategory(category);
        product.setDescription(description);
        product.setManufacturer(manufacturer);
        return product;
    }
}
