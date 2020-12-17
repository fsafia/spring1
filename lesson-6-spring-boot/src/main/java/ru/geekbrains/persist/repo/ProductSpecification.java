package ru.geekbrains.persist.repo;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.persist.entity.Product;

public class ProductSpecification {

    public static Specification<Product> trueLiteral() {
        return (root, query, builder) -> builder.isTrue(builder.literal(true));
    }

    public static Specification<Product> titleLike(String title) {
        return (root, query, builder) -> builder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Product> minCostLike(Integer minCost) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("cost"), minCost);
    }

    public static Specification<Product> maxCostLike(Integer maxCost) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("cost"), maxCost);
    }

    public static Specification<Product> idEquals(Integer id) {
        return (root, query, builder) -> builder.equal(root.get("id"), id);
    }
//    public static Specification<Product> idSort(Integer id) {
//        return (root, query, builder) -> builder.asc(root.get("id"), id);
//    }
}
