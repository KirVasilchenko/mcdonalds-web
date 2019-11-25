package ru.rosbank.javaschool.web.service;

import lombok.RequiredArgsConstructor;
import ru.rosbank.javaschool.web.exception.NotFoundException;
import ru.rosbank.javaschool.web.model.*;
import ru.rosbank.javaschool.web.repository.ProductRepository;

import java.util.List;

@RequiredArgsConstructor
public class BurgerAdminService {
    private final ProductRepository productRepository;

    public void save(ProductModel model) {
        productRepository.save(model);
    }

    public List<ProductModel> getAll() {
        return productRepository.getAll();
    }

    public ProductModel getById(int id) {
        return productRepository.getById(id).orElseThrow(NotFoundException::new);
    }

    public void saveBurger(int id, String meat, int count) {
        productRepository.saveBurger(new BurgerModel(id, meat, count));
    }
    public void savePotato(int id, int weight) {
        productRepository.savePotato(new PotatoModel(id, weight));
    }
    public void saveDrink(int id, int volume) {
        productRepository.saveDrink(new DrinkModel(id, volume));
    }
    public void saveDessert(int id, String syrup) {
        productRepository.saveDessert(new DessertModel(id, syrup));
    }

    public void removeById(int id) {
        productRepository.removeById(id);
    }
}