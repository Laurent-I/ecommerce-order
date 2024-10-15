package com.laurent.interview.Service;

import com.laurent.interview.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    List<Product> products = new ArrayList<>(Arrays.asList(
            new Product(1, "product1", 10.0),
            new Product(2, "product2", 20.0),
            new Product(3, "product3", 30.0)
    ));
    public List<Product> getProducts(){
        return products;
    }

    public Product getProductById(int id) {
        return products.stream().filter(product -> product.getId() == id).findFirst().orElse(null);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public Product updateProduct(int id, Product product) {
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.getId() == id) {
                products.set(i, product);
                return product;
            }
        }
        return null;
    }

    public void deleteProduct(int id) {
        products.removeIf(product -> product.getId() == id);
    }

}
