package ru.geekbrains.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {
    private final Connection conn;

    @Autowired
    public ProductRepository(DataSource dataSource) throws SQLException {
        this(dataSource.getConnection());
    }

    public ProductRepository(Connection conn) throws SQLException {
        this.conn = conn;
        createTableIfNotExists(conn);
    }

    private void createTableIfNotExists(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("create table if not exists products (\n" +
                    "\tid int not null auto_increment primary key,\n" +
                    "    title varchar(25),\n" +
                    "    cost int null\n" +
                    ");");
        }
    }

    public Product findById(Long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "select id, title, cost from products where id = ?")) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Product(rs.getInt(1), rs.getString(2), rs.getInt(3));
            }
        }
        return null;
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> res = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select id, title, cost from products");

            while (rs.next()) {
                res.add(new Product(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }
        }
        return res;
    }

    public void update(Product product) {
        try {
            String sql = String.format("update products set title = '%s', cost = '%s' where id = '%s'", product.getTitle(), product.getCost(), product.getId());
            PreparedStatement stmt = conn.prepareStatement(sql
                    /*"update users set login = ? where id = ?"*/);
            int a = stmt.executeUpdate();
            System.out.println(stmt.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Product product) {
        try (PreparedStatement stmt = conn.prepareStatement(
                "insert into products (title, cost) values (?, ?);")) {
            stmt.setString(1, product.getTitle());
            stmt.setInt(2, product.getCost());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Integer id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "delete from products where id = ?;")) {
            stmt.setLong(1, id);
            stmt.execute();
        }
    }
}
