package com.laurent.interview.Controller;

import com.laurent.interview.Service.ProductService;
import com.laurent.interview.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }

    @PostMapping("/products")
    public void addProduct(@RequestBody Product product) {
        productService.addProduct(product);
    }

    @PutMapping("/product/{id}")
    public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
    }

}
