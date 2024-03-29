package ru.rosbank.javaschool.web.repository;


import ru.rosbank.javaschool.util.RowMapper;
import ru.rosbank.javaschool.util.SQLLib;
import ru.rosbank.javaschool.web.constant.Constants;
import ru.rosbank.javaschool.web.exception.DataAccessException;
import ru.rosbank.javaschool.web.model.OrderModel;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryJdbcImpl implements OrderRepository {
    private final DataSource ds;
    private final SQLLib template;
    private final RowMapper<OrderModel> mapper = rs -> new OrderModel(
            rs.getInt(Constants.ORDERS_COLUMN_ID),
            rs.getString(Constants.ORDERS_COLUMN_DATE),
            rs.getString(Constants.ORDERS_COLUMN_TIME),
            rs.getString(Constants.ORDERS_COLUMN_STATUS)
    );

    public OrderRepositoryJdbcImpl(DataSource ds, SQLLib template) {
        this.ds = ds;
        this.template = template;

        try {
            template.update(ds, "CREATE TABLE IF NOT EXISTS orders\n" +
                    "(\n" +
                    "id     INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "date   TEXT NOT NULL,\n" +
                    "time   TEXT NOT NULL,\n" +
                    "status TEXT NOT NULL DEFAULT 'UNPAID'\n" +
                    ");"
            );
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public List<OrderModel> getAll() {
        try {
            return template.queryForList(ds, "SELECT id, date, time, status FROM orders;", mapper);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Optional<OrderModel> getById(int id) {
        try {
            return template.queryForObject(ds, "SELECT id, date, time, status FROM orders WHERE id = ?;", stmt -> {
                stmt.setInt(1, id);
                return stmt;
            }, mapper);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void save(OrderModel model) {
        try {
            if (model.getId() == 0) {
                int id = template.<Integer>updateForId(ds, "INSERT INTO orders (date, time) VALUES\n" +
                        "(date('now'), time('now'));");
                model.setId(id);
                return;
            }
            throw new DataAccessException("Can't update model");
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void removeById(int id) {
        try {
            template.update(ds, "DELETE FROM orders WHERE id = ?;", stmt -> {
                stmt.setInt(1, id);
                return stmt;
            });
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}
