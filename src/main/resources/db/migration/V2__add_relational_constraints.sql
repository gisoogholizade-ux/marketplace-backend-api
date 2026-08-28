ALTER TABLE products
    ADD CONSTRAINT fk_products_seller
    FOREIGN KEY (seller_id) REFERENCES users(id);

ALTER TABLE orders
    ADD CONSTRAINT fk_orders_customer
    FOREIGN KEY (customer_id) REFERENCES users(id);

ALTER TABLE payments
    ADD CONSTRAINT fk_payments_order
    FOREIGN KEY (order_id) REFERENCES orders(id);

ALTER TABLE payments
    ADD CONSTRAINT uk_payments_transaction_reference UNIQUE (transaction_reference);
