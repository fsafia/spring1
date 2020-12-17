package ru.geekbrains.persist.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.persist.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository1 {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void insert(Product product) {
        em.persist(product);
    }

    @Transactional
    public void update(Product product) {
        em.merge(product);
    }

    @Transactional
    public void delete(Integer id) {
        Product product = em.find(Product.class, id);
        if (product != product) {
            em.remove(product);
        }
    }

    public Product findById(Integer id) {
        return em.find(Product.class, id);
    }

    public Product findByTitle(String title) {
        return em.createQuery("from Product where title = :title", Product.class)
                .setParameter("title", title)
                .getSingleResult();
    }

    public List<Product> getAllProducts() {
        return em.createQuery("from Product", Product.class).getResultList();
    }

    public List<Product> findByTitleBetweenMinAndMaxCost(String title, Integer minCost, Integer maxCost) {
        return em.createQuery("from Product where title = :title", Product.class)
                .setParameter("title", "%" + title + "%")
                .getResultList();
    }
}

