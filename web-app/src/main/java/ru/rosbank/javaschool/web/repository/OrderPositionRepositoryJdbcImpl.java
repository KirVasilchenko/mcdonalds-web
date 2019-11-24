package ru.rosbank.javaschool.web.repository;


import ru.rosbank.javaschool.util.RowMapper;
import ru.rosbank.javaschool.util.SQLLib;
import ru.rosbank.javaschool.web.constant.Constants;
import ru.rosbank.javaschool.web.exception.DataAccessException;
import ru.rosbank.javaschool.web.model.OrderPositionModel;
import ru.rosbank.javaschool.web.model.ProductModel;

import javax.sql.DataSource;

import ru.rosbank.javaschool.util.RowMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OrderPositionRepositoryJdbcImpl implements OrderPositionRepository {
    private final DataSource ds;
    private final SQLLib template;
    private final RowMapper<OrderPositionModel> mapper = rs -> new OrderPositionModel(
            rs.getInt(Constants.ORDERPOSITIONS_COLUMN_ID),
            rs.getInt(Constants.ORDERPOSITIONS_COLUMN_ORDERID),
            rs.getInt(Constants.ORDERPOSITIONS_COLUMN_PRODUCTID),
            rs.getString(Constants.ORDERPOSITIONS_COLUMN_PRODUCTNAME),
            rs.getInt(Constants.ORDERPOSITIONS_COLUMN_PRICE),
            rs.getInt(Constants.ORDERPOSITIONS_COLUMN_QUANTITY)
    );

    public OrderPositionRepositoryJdbcImpl(DataSource ds, SQLLib template) {
        this.ds = ds;
        this.template = template;

        try {
            template.update(ds, "CREATE TABLE IF NOT EXISTS orderPositions\n" +
                    "(\n" +
                    "id           INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "order_id     INTEGER REFERENCES orders,\n" +
                    "product_id   INTEGER REFERENCES products,\n" +
                    "product_name TEXT    NOT NULL,\n" +
                    "price        INTEGER NOT NULL CHECK ( price >= 0 ),\n" +
                    "quantity     INTEGER NOT NULL CHECK ( quantity > 0 )\n" +
                    ");"
            );
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public List<OrderPositionModel> getAll() {
        try {
            return template.queryForList(ds, "SELECT id, order_id, product_id, product_name, price, quantity FROM orderPositions;", mapper);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Optional<OrderPositionModel> getById(int id) {
        try {
            return template.queryForObject(ds, "SELECT id, order_id, product_id, product_name, price, quantity FROM orderPositions WHERE id = ?;", stmt -> {
                stmt.setInt(1, id);
                return stmt;
            }, mapper);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void save(OrderPositionModel model) {
        try {
            if (model.getId() == 0) {
                int id = template.<Integer>updateForId(ds, "INSERT INTO orderPositions(order_id, product_id, product_name, price, quantity) VALUES (?, ?, ?, ?, ?);", stmt -> {
                    int nextIndex = 1;
                    stmt.setInt(nextIndex++, model.getOrderId());
                    stmt.setInt(nextIndex++, model.getProductId());
                    stmt.setString(nextIndex++, model.getProductName());
                    stmt.setInt(nextIndex++, model.getProductPrice());
                    stmt.setInt(nextIndex++, model.getProductQuantity());
                    return stmt;
                });
                model.setId(id);
            } else {
                template.update(ds, "UPDATE orderPositions SET order_id = ? product_id = ?, product_name = ?, price = ?, quantity = ? WHERE id = ?;", stmt -> {
                    int nextIndex = 1;
                    stmt.setInt(nextIndex++, model.getOrderId());
                    stmt.setInt(nextIndex++, model.getProductId());
                    stmt.setString(nextIndex++, model.getProductName());
                    stmt.setInt(nextIndex++, model.getProductPrice());
                    stmt.setInt(nextIndex++, model.getProductQuantity());
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
            template.update(ds, "DELETE FROM orderPositions WHERE id = ?;", stmt -> {
                stmt.setInt(1, id);
                return stmt;
            });
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public List<OrderPositionModel> getAllByOrderId(int orderId) {
        try {
            return template.queryForList(ds, "SELECT id, order_id, product_id, product_name, price, quantity FROM orderPositions WHERE order_id = ?;", mapper, stmt -> {
                stmt.setInt(1, orderId);
                return stmt;
            });
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}
