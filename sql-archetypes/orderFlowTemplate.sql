-- CREATE EMPTY ORDER, DEFAULT STATUS IS "UNPAID"
INSERT INTO orders (date, time) VALUES
(date('now'), time('now'));

-- CREATE NEW ORDER POSITION IN NEW ORDER
INSERT INTO orderPositions (order_id, product_id, product_name, price, quantity) VALUES
( (SELECT last_insert_rowid() FROM orders), 7, (SELECT name FROM products WHERE id = 7), (SELECT price FROM products WHERE id = 7), 1);

-- CHANGE QUANTITY, PRICE WILL BE CHANGED THE SAME WAY
UPDATE orderPositions SET quantity = quantity + 1 WHERE order_id = 1;

-- DELETE ORDER POSITION FROM NEW ORDER
DELETE FROM orderPositions WHERE id = 1;

-- QUERY TO CALCULATE TOTAL ORDER PRICE
SELECT SUM(orderPositions.price) AS total FROM orderPositions WHERE order_id = 1;

-- STATUS TEMPLATES TO IMPROVE INTEGRITY
UPDATE orders SET status = 'Paid' WHERE id = 1;
UPDATE orders SET status = 'Cancelled' WHERE id = 1;