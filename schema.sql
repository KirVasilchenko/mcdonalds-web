CREATE TABLE products
(
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    name     TEXT    NOT NULL UNIQUE,
    price    INTEGER NOT NULL CHECK (price >= 0)      default 0,
    quantity INTEGER NOT NULL CHECK ( quantity >= 0 ) default 0
);

CREATE TABLE burgers
(
    product_id FOREIGN KEY REFERENCES products,
    cutlet_meat  TEXT    NOT NULL                             default 'Beef',
    cutlet_count INTEGER NOT NULL CHECK ( cutlet_count >= 0 ) default 1
);

CREATE TABLE potatoes
(
    product_id FOREIGN KEY REFERENCES products,
    weight_in_g INTEGER NOT NULL CHECK ( weight_in_g >= 0 ) default 50
);

CREATE TABLE drinks
(
    product_id FOREIGN KEY REFERENCES products,
    volume_in_ml INTEGER NOT NULL CHECK ( volume_in_ml >= 0 ) default 400
);

CREATE TABLE desserts
(
    product_id FOREIGN KEY REFERENCES products,
    syrup TEXT NOT NULL default 'Chocolate'
);
