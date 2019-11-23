package ru.rosbank.javaschool.web.repository;


import ru.rosbank.javaschool.util.SQLLib;
import ru.rosbank.javaschool.util.RowMapper;
import ru.rosbank.javaschool.web.exception.DataAccessException;
import ru.rosbank.javaschool.web.model.ProductModel;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryJdbcImpl implements ProductRepository {
    private final DataSource ds;
    private final SQLLib template;
    private final RowMapper<ProductModel> mapper = rs -> new ProductModel(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getInt("price"),
            rs.getInt("quantity"),
            rs.getString("image"),
            rs.getString("description"),
            rs.getInt("hidden")
    );


    public ProductRepositoryJdbcImpl(DataSource ds, SQLLib template) {
        this.ds = ds;
        this.template = template;

        try {
//            template.update(ds, "DROP TABLE products;");
            template.update(ds, "CREATE TABLE IF NOT EXISTS products" +
                    "(" +
                    "id          INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name        TEXT    NOT NULL UNIQUE," +
                    "price       INTEGER NOT NULL CHECK (price >= 0)      DEFAULT 0," +
                    "quantity    INTEGER NOT NULL CHECK ( quantity >= 0 ) DEFAULT 0," +
                    "image       TEXT                                     DEFAULT 'https://sun9-51.userapi.com/c853528/v853528760/17c8c7/s6AWQdYJC04.jpg'," +
                    "description TEXT    NOT NULL                         DEFAULT 'No description yet...'" +
                    "hidden      INTEGER                                  DEFAULT 0" +
                    ");" +
                    "CREATE TABLE IF NOT EXISTS burgers" +
                    "(" +
                    "product_id   INTEGER," +
                    "cutlet_meat  TEXT    NOT NULL                             default 'Beef'," +
                    "cutlet_count INTEGER NOT NULL CHECK ( cutlet_count >= 0 ) default 1" +
                    ");" +
                    "CREATE TABLE IF NOT EXISTS potatoes" +
                    "(" +
                    "product_id  INTEGER," +
                    "weight_in_g INTEGER NOT NULL CHECK ( weight_in_g >= 0 ) default 50" +
                    ");" +
                    "CREATE TABLE IF NOT EXISTS drinks" +
                    "(" +
                    "product_id   INTEGER," +
                    "volume_in_ml INTEGER NOT NULL CHECK ( volume_in_ml >= 0 ) default 400" +
                    ");" +
                    "CREATE TABLE IF NOT EXISTS desserts" +
                    "(" +
                    "product_id INTEGER," +
                    "syrup      TEXT NOT NULL default 'Chocolate'" +
                    ");");
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public List<ProductModel> getAll() {
        try {
            return template.queryForList(ds, "SELECT id, name, price, quantity, image, description, hidden FROM products;", mapper);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Optional<ProductModel> getById(int id) {
        try {
            return template.queryForObject(ds, "SELECT id, name, quantity, price, image, description, hidden FROM products WHERE id = ?;", stmt -> {
                stmt.setInt(1, id);
                return stmt;
            }, mapper);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void save(ProductModel model) {
        try {
            if (model.getId() == 0) {
                int id = template.<Integer>updateForId(ds, "INSERT INTO products(name, price, quantity, image, description, hidden) VALUES (?, ?, ?, ?, ?, ?);", stmt -> {
                    int nextIndex = 1;
                    stmt.setString(nextIndex++, model.getName());
                    stmt.setInt(nextIndex++, model.getPrice());
                    stmt.setInt(nextIndex++, model.getQuantity());
                    stmt.setString(nextIndex++, model.getImageUrl());
                    stmt.setString(nextIndex++, model.getDescription());
                    stmt.setInt(nextIndex++, model.getHidden());
                    return stmt;
                });
                model.setId(id);
            } else {
                template.update(ds, "UPDATE products SET name = ?, price = ?, quantity = ?, image = ?, description = ?, hidden = ? WHERE id = ?;", stmt -> {
                    int nextIndex = 1;
                    stmt.setString(nextIndex++, model.getName());
                    stmt.setInt(nextIndex++, model.getPrice());
                    stmt.setInt(nextIndex++, model.getQuantity());
                    stmt.setString(nextIndex++, model.getImageUrl());
                    stmt.setString(nextIndex++, model.getDescription());
                    stmt.setInt(nextIndex++, model.getHidden());
                    stmt.setInt(nextIndex++, model.getId());
                    return stmt;
                });
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void removeById(int id) {
        try {
            template.update(ds, "DELETE FROM products WHERE id = ?;", stmt -> {
                stmt.setInt(1, id);
                return stmt;
            });
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}
