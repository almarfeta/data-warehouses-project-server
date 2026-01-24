ALTER TABLE dim_time
    ADD CONSTRAINT unique_date_value UNIQUE (date_value)
        RELY DISABLE NOVALIDATE;
ALTER TABLE dim_time
    ADD CONSTRAINT check_week_day CHECK (week_day IN ('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'))
        RELY DISABLE NOVALIDATE;
ALTER TABLE dim_time
    ADD CONSTRAINT check_month_name CHECK (month_name IN ('January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'))
        RELY DISABLE NOVALIDATE;

ALTER TABLE dim_location
    ADD CONSTRAINT unique_address_id UNIQUE (address_id)
        RELY DISABLE NOVALIDATE;

ALTER TABLE dim_customer
    ADD CONSTRAINT unique_customer_id UNIQUE (customer_id)
        RELY DISABLE NOVALIDATE;
ALTER TABLE dim_customer
    ADD CONSTRAINT unique_username UNIQUE (username)
        RELY DISABLE NOVALIDATE;
ALTER TABLE dim_customer
    ADD CONSTRAINT unique_email UNIQUE (email)
        RELY DISABLE NOVALIDATE;

ALTER TABLE dim_product
    ADD CONSTRAINT unique_product_id UNIQUE (product_id)
        RELY DISABLE NOVALIDATE;
ALTER TABLE dim_product
    ADD CONSTRAINT unique_sku UNIQUE (sku)
        RELY DISABLE NOVALIDATE;
ALTER TABLE dim_product
    ADD CONSTRAINT check_status CHECK (status IN ('ACTIVE', 'INACTIVE'))
        RELY DISABLE NOVALIDATE;

ALTER TABLE dim_payment_method
    ADD CONSTRAINT unique_payment_method UNIQUE (payment_method)
        RELY DISABLE NOVALIDATE;

ALTER TABLE fact_sales
    ADD CONSTRAINT unique_order_id UNIQUE (order_id)
        RELY DISABLE NOVALIDATE;
ALTER TABLE fact_sales
    ADD CONSTRAINT unique_order_item_id UNIQUE (order_item_id)
        RELY DISABLE NOVALIDATE;
ALTER TABLE fact_sales
    ADD CONSTRAINT check_order_status CHECK (status IN ('CREATED', 'ACCEPTED', 'CANCELLED'))
        RELY DISABLE NOVALIDATE;