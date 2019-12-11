package ru.rosbank.javaschool.web.constant;

public class Parameters {

    public static final String PRODUCTS_COLUMN_ID = "id";
    public static final String PRODUCTS_COLUMN_NAME = "name";
    public static final String PRODUCTS_COLUMN_PRICE = "price";
    public static final String PRODUCTS_COLUMN_QUANTITY = "quantity";
    public static final String PRODUCTS_COLUMN_IMAGE = "image";
    public static final String PRODUCTS_COLUMN_DESCRIPTION = "description";
    public static final String PRODUCTS_COLUMN_CATEGORY = "category";

    public static final String ORDERPOSITIONS_COLUMN_ID = "id";
    public static final String ORDERPOSITIONS_COLUMN_ORDERID = "order_id";
    public static final String ORDERPOSITIONS_COLUMN_PRODUCTID = "product_id";
    public static final String ORDERPOSITIONS_COLUMN_PRODUCTNAME = "product_name";
    public static final String ORDERPOSITIONS_COLUMN_PRICE = "price";
    public static final String ORDERPOSITIONS_COLUMN_QUANTITY = "quantity";

    public static final String BURGERSERVICE_ATTRIBUTE_ORDERPOSITIONID = "id";
    public static final String BURGERSERVICE_ATTRIBUTE_QUANTITY = "quantity";

    private Parameters() {
    }
}
