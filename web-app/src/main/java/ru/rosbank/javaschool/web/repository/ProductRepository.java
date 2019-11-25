package ru.rosbank.javaschool.web.repository;

import ru.rosbank.javaschool.web.exception.DataAccessException;
import ru.rosbank.javaschool.web.model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<ProductModel> getAll();
    Optional<ProductModel> getById(int id);
    void save(ProductModel model);
    void removeById(int id);
    void saveBurger(BurgerModel model);
    void saveDessert(DessertModel model);
    void saveDrink(DrinkModel model);
    void savePotato(PotatoModel model);
}
