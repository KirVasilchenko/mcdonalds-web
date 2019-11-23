-- PRAGMA foreign_keys = on;

CREATE TABLE products
(
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    name        TEXT    NOT NULL UNIQUE,
    price       INTEGER NOT NULL CHECK (price >= 0)      default 0,
    quantity    INTEGER NOT NULL CHECK ( quantity >= 0 ) default 0,
    image       TEXT                                     DEFAULT 'https://sun9-51.userapi.com/c853528/v853528760/17c8c7/s6AWQdYJC04.jpg',
    description TEXT    NOT NULL                         DEFAULT 'No description yet...'
);

CREATE TABLE burgers
(
    product_id   INTEGER,
--         FOREIGN KEY REFERENCES products(id),
    cutlet_meat  TEXT    NOT NULL                             default 'Beef',
    cutlet_count INTEGER NOT NULL CHECK ( cutlet_count >= 0 ) default 1
);

CREATE TABLE potatoes
(
    product_id  INTEGER,
    weight_in_g INTEGER NOT NULL CHECK ( weight_in_g >= 0 ) default 50
);

CREATE TABLE drinks
(
    product_id   INTEGER,
    volume_in_ml INTEGER NOT NULL CHECK ( volume_in_ml >= 0 ) default 400
);

CREATE TABLE desserts
(
    product_id INTEGER,
    syrup      TEXT NOT NULL default 'Chocolate'
);

ALTER TABLE products
    ADD COLUMN hidden INTEGER DEFAULT 0;

CREATE TABLE orders
(
    id     INTEGER PRIMARY KEY AUTOINCREMENT,
    date   TEXT NOT NULL,
    time   TEXT NOT NULL,
    status TEXT NOT NULL DEFAULT 'UNPAID'
);

CREATE TABLE orderPositions
(
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    order_id   INTEGER REFERENCES orders,
    product_id INTEGER REFERENCES products,
    product_name TEXT NOT NULL,
    price      INTEGER NOT NULL CHECK ( price >= 0 ),
    quantity   INTEGER NOT NULL CHECK ( quantity > 0 )
);

