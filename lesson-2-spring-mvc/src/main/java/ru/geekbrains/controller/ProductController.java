package ru.geekbrains.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persistance.Product;
import ru.geekbrains.persistance.ProductRepository;
import ru.geekbrains.persistance.User;
import ru.geekbrains.persistance.UserRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String allProducts(Model model) throws SQLException {
        List<Product> allProducts = productRepository.getAllProducts();
        model.addAttribute("allProducts", allProducts);
        return "products";
    }

    @GetMapping("/product")
    public String productNew(Model model) throws SQLException {
        Product product = new Product();
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) throws SQLException {
        Product product = productRepository.findById(id);
        model.addAttribute("product", product);
        return "product";
    }

    @PostMapping("/add")
    public String addProduct(Product product) throws SQLException {
        Product prod = productRepository.findById(Long.valueOf(product.getId()));
        if (prod != null) {
            productRepository.updateById(product.getTitle(), product.getCost(), product.getId());
        } else {
            productRepository.insert(product);
        }

        return "redirect:/products";
    }

    @GetMapping("/findProduct")
    public String findProductById(@RequestParam("id") Long id, Model model) throws SQLException {
        Product product = productRepository.findById(id);
        List<Product> allProduct = new ArrayList<>();
        if (product != null) {
            allProduct.add(product);

        }
        model.addAttribute("allProducts", allProduct);

        return "products";
    }

}
