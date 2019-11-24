package ru.rosbank.javaschool.web.repository;


import ru.rosbank.javaschool.util.SQLLib;
import ru.rosbank.javaschool.util.RowMapper;
import ru.rosbank.javaschool.web.constant.Constants;
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
            rs.getInt(Constants.PRODUCTS_COLUMN_ID),
            rs.getString(Constants.PRODUCTS_COLUMN_NAME),
            rs.getInt(Constants.PRODUCTS_COLUMN_PRICE),
            rs.getInt(Constants.PRODUCTS_COLUMN_QUANTITY),
            rs.getString(Constants.PRODUCTS_COLUMN_IMAGE),
            rs.getString(Constants.PRODUCTS_COLUMN_DESCRIPTION),
            rs.getString(Constants.PRODUCTS_COLUMN_CATEGORY)
    );


    public ProductRepositoryJdbcImpl(DataSource ds, SQLLib template) {
        this.ds = ds;
        this.template = template;

        try {
//            template.update(ds, "DROP TABLE products;");
            template.update(ds, "CREATE TABLE IF NOT EXISTS products\n" +
                    "(\n" +
                    "id          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "name        TEXT    NOT NULL UNIQUE,\n" +
                    "price       INTEGER NOT NULL CHECK (price >= 0)      DEFAULT 0,\n" +
                    "quantity    INTEGER NOT NULL CHECK ( quantity >= 0 ) DEFAULT 0,\n" +
                    "image       TEXT                                     DEFAULT 'https://sun9-51.userapi.com/c853528/v853528760/17c8c7/s6AWQdYJC04.jpg',\n" +
                    "description TEXT    NOT NULL                         DEFAULT 'No description yet...',\n" +
                    "category TEXT NOT NULL DEFAULT 'burgers'\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS burgers\n" +
                    "(\n" +
                    "product_id   INTEGER,\n" +
                    "cutlet_meat  TEXT    NOT NULL                             default 'Beef',\n" +
                    "cutlet_count INTEGER NOT NULL CHECK ( cutlet_count >= 0 ) default 1\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS potatoes\n" +
                    "(\n" +
                    "product_id  INTEGER,\n" +
                    "weight_in_g INTEGER NOT NULL CHECK ( weight_in_g >= 0 ) default 50\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS drinks\n" +
                    "(\n" +
                    "product_id   INTEGER,\n" +
                    "volume_in_ml INTEGER NOT NULL CHECK ( volume_in_ml >= 0 ) default 400\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS desserts\n" +
                    "(\n" +
                    "product_id INTEGER,\n" +
                    "syrup      TEXT NOT NULL default 'Chocolate'\n" +
                    ");");
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public List<ProductModel> getAll() {
        try {
            return template.queryForList(ds, "SELECT id, name, price, quantity, image, description, category FROM products;", mapper);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Optional<ProductModel> getById(int id) {
        try {
            return template.queryForObject(ds, "SELECT id, name, quantity, price, image, description, category FROM products WHERE id = ?;", stmt -> {
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
                int id = template.<Integer>updateForId(ds, "INSERT INTO products(name, price, quantity, image, description, category) VALUES (?, ?, ?, ?, ?, ?);", stmt -> {
                    int nextIndex = 1;
                    stmt.setString(nextIndex++, model.getName());
                    stmt.setInt(nextIndex++, model.getPrice());
                    stmt.setInt(nextIndex++, model.getQuantity());
                    stmt.setString(nextIndex++, model.getImageUrl());
                    stmt.setString(nextIndex++, model.getDescription());
                    stmt.setString(nextIndex++, model.getCategory());
                    return stmt;
                });
                model.setId(id);
            } else {
                template.update(ds, "UPDATE products SET name = ?, price = ?, quantity = ?, image = ?, description = ?, category = ? WHERE id = ?;", stmt -> {
                    int nextIndex = 1;
                    stmt.setString(nextIndex++, model.getName());
                    stmt.setInt(nextIndex++, model.getPrice());
                    stmt.setInt(nextIndex++, model.getQuantity());
                    stmt.setString(nextIndex++, model.getImageUrl());
                    stmt.setString(nextIndex++, model.getDescription());
                    stmt.setString(nextIndex++, model.getCategory());
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
        try {
            template.update(ds, "DELETE FROM burgers WHERE id = ?;", stmt -> {
                stmt.setInt(1, id);
                return stmt;
            });
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        try {
            template.update(ds, "DELETE FROM potatoes WHERE id = ?;", stmt -> {
                stmt.setInt(1, id);
                return stmt;
            });
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        try {
            template.update(ds, "DELETE FROM drinks WHERE id = ?;", stmt -> {
                stmt.setInt(1, id);
                return stmt;
            });
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        try {
            template.update(ds, "DELETE FROM desserts WHERE id = ?;", stmt -> {
                stmt.setInt(1, id);
                return stmt;
            });
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}
