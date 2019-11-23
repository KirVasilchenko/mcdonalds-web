SELECT id, name
FROM burgers
         INNER JOIN products
WHERE burgers.product_id == products.id;