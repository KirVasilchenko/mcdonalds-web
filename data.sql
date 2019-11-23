INSERT INTO products (name, price, quantity, image, description) VALUES
('Hamburger', 50, 10, 'https://sun9-51.userapi.com/c853528/v853528760/17c8c7/s6AWQdYJC04.jpg', 'Classic burger');
INSERT INTO burgers (product_id, cutlet_meat, cutlet_count) VALUES
( (SELECT id FROM products WHERE name='Hamburger'), 'Beef', 1);

INSERT INTO products (name, price, quantity, image, description) VALUES
('Cheeseburger', 52, 20, 'https://sun9-51.userapi.com/c853528/v853528760/17c8c7/s6AWQdYJC04.jpg', 'Classic burger with tasty cheese');
INSERT INTO burgers (product_id, cutlet_meat, cutlet_count) VALUES
( (SELECT id FROM products WHERE name='Cheeseburger'), 'Beef', 1);

UPDATE products SET price = 50 WHERE id = 1;
UPDATE products SET price = 52 WHERE id = 2;

INSERT INTO products (name, price, quantity, image, description) VALUES
('French Fries Small', 49, 20, 'https://sun9-51.userapi.com/c853528/v853528760/17c8c7/s6AWQdYJC04.jpg', 'Crispy french fries with salt');
INSERT INTO potatoes (product_id, weight_in_g) VALUES
( (SELECT id FROM products WHERE name='French Fries Small'), 50);

INSERT INTO products (name, price, quantity, image, description) VALUES
('French Fries Medium', 55, 10, 'https://sun9-51.userapi.com/c853528/v853528760/17c8c7/s6AWQdYJC04.jpg', 'Crispy french fries with salt');
INSERT INTO potatoes (product_id, weight_in_g) VALUES
( (SELECT id FROM products WHERE name='French Fries Small'), 70);

INSERT INTO products (name, price, quantity, image, description) VALUES
('Fanta 0,4', 59, 20, 'https://sun9-51.userapi.com/c853528/v853528760/17c8c7/s6AWQdYJC04.jpg', 'Orange soda with cold ice.');
INSERT INTO drinks (product_id, volume_in_ml) VALUES
( (SELECT id FROM products WHERE name='Fanta 0,4'), 400);

INSERT INTO products (name, price, quantity, image, description) VALUES
('Strawberry Cheesecake', 149, 20, 'https://sun9-51.userapi.com/c853528/v853528760/17c8c7/s6AWQdYJC04.jpg', 'The famous New York cheesecake with strawberry syrup.');
INSERT INTO desserts (product_id, syrup) VALUES
( (SELECT id FROM products WHERE name='Strawberry Cheesecake'), 'Strawberry');

INSERT INTO orders (date, time) VALUES
(date('now'), time('now'));

INSERT INTO orderPositions (order_id, product_id, price, quantity) VALUES
( (SELECT last_insert_rowid() FROM orders), 7, (SELECT price FROM products WHERE id = 7), 1);

INSERT INTO orderPositions (order_id, product_id, price, quantity) VALUES
( (SELECT last_insert_rowid() FROM orders), 8, (SELECT price FROM products WHERE id = 8), 2);

SELECT SUM(orderPositions.price) AS total FROM orderPositions WHERE order_id = 1;

UPDATE orders SET status = 'Paid' WHERE id = 1;
UPDATE orders SET status = 'Cancelled' WHERE id = 1;