package ru.geekbrains.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.geekbrains.persist.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    List<Product> findByTitleLike(String s);

    List<Product> findByTitleLikeAndCostBetween(String title, Integer minCost, Integer maxCost);

    List<Product> findByTitleLikeAndCostGreaterThanEqual(String title, Integer minCost);

    List<Product> findByTitleLikeAndCostLessThanEqual(String title, Integer maxCost);

    List<Product> findByCostBetween(Integer minCost, Integer maxCost);

    List<Product> findByCostLessThanEqual(Integer maxCost);

    List<Product> findByCostGreaterThanEqual(Integer minCost);

}
