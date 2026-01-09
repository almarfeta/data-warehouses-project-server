CREATE OR REPLACE TRIGGER trg_dim_time_bi
    BEFORE INSERT ON dim_time
    FOR EACH ROW
    WHEN (NEW.time_key IS NULL)
BEGIN
    SELECT seq_dim_time.NEXTVAL
    INTO :NEW.time_key
    FROM dual;
END;
/
CREATE OR REPLACE TRIGGER trg_dim_location_bi
    BEFORE INSERT ON dim_location
    FOR EACH ROW
    WHEN (NEW.location_key IS NULL)
BEGIN
    SELECT seq_dim_location.NEXTVAL
    INTO :NEW.location_key
    FROM dual;
END;
/
CREATE OR REPLACE TRIGGER trg_dim_customer_bi
    BEFORE INSERT ON dim_customer
    FOR EACH ROW
    WHEN (NEW.customer_key IS NULL)
BEGIN
    SELECT seq_dim_customer.NEXTVAL
    INTO :NEW.customer_key
    FROM dual;
END;
/
CREATE OR REPLACE TRIGGER trg_dim_product_bi
    BEFORE INSERT ON dim_product
    FOR EACH ROW
    WHEN (NEW.product_key IS NULL)
BEGIN
    SELECT seq_dim_product.NEXTVAL
    INTO :NEW.product_key
    FROM dual;
END;
/
CREATE OR REPLACE TRIGGER trg_dim_payment_method_bi
    BEFORE INSERT ON dim_payment_method
    FOR EACH ROW
    WHEN (NEW.payment_method_key IS NULL)
BEGIN
    SELECT seq_dim_payment_method.NEXTVAL
    INTO :NEW.payment_method_key
    FROM dual;
END;
/
CREATE OR REPLACE TRIGGER trg_fact_sales_bi
    BEFORE INSERT ON fact_sales
    FOR EACH ROW
    WHEN (NEW.sales_key IS NULL)
BEGIN
    SELECT seq_fact_sales.NEXTVAL
    INTO :NEW.sales_key
    FROM dual;
END;
/