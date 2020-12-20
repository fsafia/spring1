package ru.geekbrains.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.repo.ProductRepository;
import ru.geekbrains.persist.repo.ProductSpecification;

import java.util.Optional;

@RestController
@RequestMapping("/rest/products")
public class RestProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public Page<Product> allProducts(@RequestParam(value = "title", required = false) String title,
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
        Page<Product> products = productRepository.findAll(specProduct, pageRequest);
        return products;
    }



}
