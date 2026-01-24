CREATE TABLE dim_time (
    time_key     NUMBER(19) PRIMARY KEY,
    date_value   DATE,
    day          NUMBER(2),
    month        NUMBER(2),
    year         NUMBER(4),
    week_day     VARCHAR2(10),
    month_name   VARCHAR2(10)
);
CREATE TABLE dim_location (
    location_key   NUMBER(19) PRIMARY KEY,
    address_id     NUMBER(19),
    city           VARCHAR2(100),
    region         VARCHAR2(100),
    country        VARCHAR2(100)
);
CREATE TABLE dim_customer (
    customer_key   NUMBER(19) PRIMARY KEY,
    customer_id    NUMBER(19),
    username       VARCHAR2(100),
    email          VARCHAR2(255),
    full_name      VARCHAR2(200),
    date_of_birth  DATE,
    created_at     TIMESTAMP WITH TIME ZONE
);
CREATE TABLE dim_product (
    product_key    NUMBER(19) PRIMARY KEY,
    product_id     NUMBER(19),
    sku            VARCHAR2(100),
    product_name   VARCHAR2(255),
    brand_name     VARCHAR2(255),
    category_tree  VARCHAR2(500),
    status         VARCHAR2(50)
);
CREATE TABLE dim_payment_method (
    payment_method_key   NUMBER(19) PRIMARY KEY,
    payment_method       VARCHAR2(50)
);
CREATE TABLE fact_sales (
    sales_key            NUMBER(19) PRIMARY KEY,
    time_key             NUMBER(19),
    location_key         NUMBER(19),
    customer_key         NUMBER(19),
    product_key          NUMBER(19),
    payment_method_key   NUMBER(19),
    order_id             NUMBER(19),
    order_item_id        NUMBER(19),
    unit_price           NUMBER(10,2),
    quantity             NUMBER(10),
    status               VARCHAR2(50),
    shipped_by           VARCHAR2(100),

    CONSTRAINT fk_fs_time
        FOREIGN KEY (time_key)
            REFERENCES dim_time (time_key),

    CONSTRAINT fk_fs_location
        FOREIGN KEY (location_key)
            REFERENCES dim_location (location_key),

    CONSTRAINT fk_fs_customer
        FOREIGN KEY (customer_key)
            REFERENCES dim_customer (customer_key),

    CONSTRAINT fk_fs_product
        FOREIGN KEY (product_key)
            REFERENCES dim_product (product_key),

    CONSTRAINT fk_fs_payment_method
        FOREIGN KEY (payment_method_key)
            REFERENCES dim_payment_method (payment_method_key)
);