CREATE OR REPLACE TRIGGER trg_customers_bi
    BEFORE INSERT ON customers
    FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT seq_customers.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;
/
CREATE OR REPLACE TRIGGER trg_addresses_bi
    BEFORE INSERT ON addresses
    FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT seq_addresses.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;
/
CREATE OR REPLACE TRIGGER trg_accounts_bi
    BEFORE INSERT ON accounts
    FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT seq_accounts.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;
/
CREATE OR REPLACE TRIGGER trg_tokens_bi
    BEFORE INSERT ON tokens
    FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT seq_tokens.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;
/



CREATE OR REPLACE TRIGGER trg_brands_bi
    BEFORE INSERT ON brands
    FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT seq_brands.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;
/
CREATE OR REPLACE TRIGGER trg_categories_bi
    BEFORE INSERT ON categories
    FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT seq_categories.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;
/
CREATE OR REPLACE TRIGGER trg_products_bi
    BEFORE INSERT ON products
    FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT seq_products.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;
/
CREATE OR REPLACE TRIGGER trg_prices_bi
    BEFORE INSERT ON prices
    FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT seq_prices.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;
/
CREATE OR REPLACE TRIGGER trg_inventories_bi
    BEFORE INSERT ON inventories
    FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT seq_inventories.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;
/



CREATE OR REPLACE TRIGGER trg_orders_bi
    BEFORE INSERT ON orders
    FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT seq_orders.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;
/
CREATE OR REPLACE TRIGGER trg_order_items_bi
    BEFORE INSERT ON order_items
    FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT seq_order_items.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;
/
CREATE OR REPLACE TRIGGER trg_deliveries_bi
    BEFORE INSERT ON deliveries
    FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT seq_deliveries.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;
/