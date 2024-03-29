package ru.rosbank.javaschool.web.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderModel {
    private int id;
    private String date;
    private String time;
    private String status;
}
