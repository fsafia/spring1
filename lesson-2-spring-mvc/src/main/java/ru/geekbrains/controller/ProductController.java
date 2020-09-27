package ru.geekbrains.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.repo.ProductRepository;
import ru.geekbrains.persist.repo.ProductRepository1;
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

        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(2));

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
//        model.addAttribute("allProducts", productRepository.findAll(specProduct));
//        List<Product> allProducts = new ArrayList<>();
//        if ((title == null || title.isEmpty()) && (minCost == null) && (maxCost == null)) {// - - -
//            allProducts = productRepository.findAll();
//        }
//
//        if (title != null && minCost != null && maxCost != null) {  //если все фильтры заполнены + + +
//            allProducts = productRepository.findByTitleLikeAndCostBetween("%" + title + "%", minCost, maxCost);
//        }
//
//        if (!(title == null || title.isEmpty()) && (minCost == null) && (maxCost == null) ) { // + - -
//            allProducts = productRepository.findByTitleLike("%" + title + "%");
//        }
//
//        if (!(title == null || title.isEmpty()) && (minCost != null) && (maxCost == null)) { // + + -
//            allProducts = productRepository.findByTitleLikeAndCostGreaterThanEqual("%" + title + "%", minCost);
//        }
//
//        if (!(title == null || title.isEmpty()) && (minCost == null) && (maxCost != null)) { // + - +
//            allProducts = productRepository.findByTitleLikeAndCostLessThanEqual("%" + title + "%", maxCost);
//        }
//
//        if ((title == null || title.isEmpty()) && (minCost != null) && (maxCost != null)) { //- + +
//            allProducts = productRepository.findByCostBetween(minCost, maxCost);
//        }
//
//        if ((title == null || title.isEmpty()) && (minCost == null) && (maxCost != null)) {//- - +
//            allProducts = productRepository.findByCostLessThanEqual(maxCost);
//        }
//
//        if ((title == null || title.isEmpty()) && (minCost != null) && (maxCost == null)) { // - + -
//            allProducts = productRepository.findByCostGreaterThanEqual(minCost);
//        }
//
//        model.addAttribute("allProducts", allProducts);
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
        Product product = productRepository.findById(id).get();
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
        Product product = productRepository.findById(id).get();
        List<Product> allProduct = new ArrayList<>();
        if (product != null) {
            allProduct.add(product);
        }
        model.addAttribute("allProducts", allProduct);

        return "products";
    }

}
