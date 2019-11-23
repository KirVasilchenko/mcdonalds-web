package ru.rosbank.javaschool.web.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {
    private int id;
    private String name;
    private int price;
    private int quantity;
    private String imageUrl;
    private String description;
    private String category;

//    public ProductModel (String name, int price, int quantity, String imageUrl, String description) {
//        this.name = name;
//        this.price = price;
//        this.quantity = quantity;
//        this.imageUrl = imageUrl;
//        this.description = description;
//    }
}



