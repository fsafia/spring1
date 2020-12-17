package ru.geekbrains.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.repo.ProductRepository;
import ru.geekbrains.persist.repo.ProductSpecification;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String allProducts(Model model,
                              @RequestParam(value = "title", required = false) String title,
                              @RequestParam(value = "min_cost", required = false) Integer minCost,
                              @RequestParam(value = "max_cost", required = false) Integer maxCost,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size) {


        Specification<Product> specProduct = ProductSpecification.trueLiteral();

        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(4), Sort.by(Sort.Direction.ASC, "title"));

        if (title != null && !title.isEmpty()) {
            specProduct = specProduct.and(ProductSpecification.titleLike(title)); //к спецификации добавл в конец критерий API
        }

        if (minCost != null) {
            specProduct = specProduct.and(ProductSpecification.minCostLike(minCost));
        }

        if (maxCost != null) {
            specProduct = specProduct.and(ProductSpecification.maxCostLike(maxCost));
        }

        model.addAttribute("productsPage", productRepository.findAll(specProduct, pageRequest));
        return "products";
    }

    @GetMapping("/product")
    public String productNew(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model) {
        Product product = productRepository.findById(id).orElseThrow(()-> new NotFoundException("product with id = " + id + " not found"));
        model.addAttribute("product", product);
        return "product";
    }

    @PostMapping("/add")
    public String addProduct(Product product) throws SQLException {
        productRepository.save(product);
        return "redirect:/products";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteProduct(@PathVariable("id") Integer id, Model model) throws SQLException {
        productRepository.deleteById(id);
        return "redirect:/products";
    }

    @GetMapping("/findProduct")
    public String findProductById(@RequestParam("id") Integer id, Model model) throws SQLException {
//        Page page;
//        productRepository.findById(id).get();
//        productRepository.findById(id).
//
//
//
//        model.addAttribute("productsPage", allProduct);
        Specification<Product> specProduct = ProductSpecification.trueLiteral();

        PageRequest pageRequest = PageRequest.of( 0, 1);

        if (id != null) {
            specProduct = specProduct.and(ProductSpecification.idEquals(id)); //к спецификации добавл в конец критерий API
        }

        model.addAttribute("productsPage", productRepository.findAll(specProduct, pageRequest));
        return "products";

    }

}
