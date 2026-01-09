CREATE TABLE customers (
    id            NUMBER(19) PRIMARY KEY,
    first_name    VARCHAR2(100) NOT NULL,
    last_name     VARCHAR2(100) NOT NULL,
    date_of_birth DATE,
    phone_number  VARCHAR2(50),
    created_at    TIMESTAMP WITH TIME ZONE DEFAULT SYSTIMESTAMP NOT NULL
);
CREATE TABLE addresses (
    id          NUMBER(19) PRIMARY KEY,
    customer_id NUMBER(19) NOT NULL,
    street      VARCHAR2(255) NOT NULL,
    city        VARCHAR2(100) NOT NULL,
    region      VARCHAR2(100) NOT NULL,
    country     VARCHAR2(100) NOT NULL,
    postal_code VARCHAR2(20) NOT NULL,

    CONSTRAINT fk_addresses_customer
        FOREIGN KEY (customer_id)
            REFERENCES customers (id)
);
CREATE TABLE accounts (
    id          NUMBER(19) PRIMARY KEY,
    customer_id NUMBER(19) UNIQUE,
    username    VARCHAR2(100) NOT NULL UNIQUE,
    email       VARCHAR2(255) NOT NULL UNIQUE,
    password    VARCHAR2(255) NOT NULL,
    role        VARCHAR2(50) DEFAULT 'USER' NOT NULL,

    CONSTRAINT chk_account_customer_role
        CHECK ((role = 'USER' AND customer_id IS NOT NULL) OR (role = 'ADMIN' AND customer_id IS NULL)),

    CONSTRAINT fk_account_customer
        FOREIGN KEY (customer_id)
            REFERENCES customers (id)
);
CREATE TABLE tokens (
    id         NUMBER(19) PRIMARY KEY,
    account_id NUMBER(19) NOT NULL,
    token      VARCHAR2(512) NOT NULL,
    revoked    NUMBER(1) DEFAULT 0 NOT NULL,
    expired    NUMBER(1) DEFAULT 0 NOT NULL,

    CONSTRAINT fk_tokens_account
        FOREIGN KEY (account_id)
            REFERENCES accounts (id)
);



CREATE TABLE brands (
    id         NUMBER(19) PRIMARY KEY,
    brand_name VARCHAR2(255) NOT NULL UNIQUE
);
CREATE TABLE categories (
    id                 NUMBER(19) PRIMARY KEY,
    parent_category_id NUMBER(19),
    category_name      VARCHAR2(255) NOT NULL,

    CONSTRAINT fk_category_parent
        FOREIGN KEY (parent_category_id)
            REFERENCES categories (id)
);
CREATE TABLE products (
    id           NUMBER(19) PRIMARY KEY,
    brand_id     NUMBER(19) NOT NULL,
    category_id  NUMBER(19) NOT NULL,
    sku          VARCHAR2(100) NOT NULL UNIQUE,
    product_name VARCHAR2(255) NOT NULL,
    description  CLOB,
    status       VARCHAR2(50) DEFAULT 'INACTIVE' NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE')),

    CONSTRAINT fk_products_brand
        FOREIGN KEY (brand_id)
            REFERENCES brands (id),

    CONSTRAINT fk_products_category
        FOREIGN KEY (category_id)
            REFERENCES categories (id)
);
CREATE TABLE prices (
    id         NUMBER(19) PRIMARY KEY,
    product_id NUMBER(19) NOT NULL,
    value      NUMBER(10, 2) NOT NULL,
    currency   VARCHAR2(10) NOT NULL CHECK (currency IN ('EUR', 'USD', 'RON')),
    status     VARCHAR2(50) DEFAULT 'DEFAULT' NOT NULL CHECK (status IN ('DEFAULT', 'ACTIVE', 'INACTIVE')),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT SYSTIMESTAMP NOT NULL,

    CONSTRAINT fk_prices_product
        FOREIGN KEY (product_id)
            REFERENCES products (id)
);
CREATE TABLE inventories (
    id              NUMBER(19) PRIMARY KEY,
    product_id      NUMBER(19) NOT NULL UNIQUE,
    stock_available NUMBER(10) DEFAULT 0 NOT NULL,
    updated_at      TIMESTAMP WITH TIME ZONE DEFAULT SYSTIMESTAMP NOT NULL,

    CONSTRAINT fk_inventory_product
        FOREIGN KEY (product_id)
            REFERENCES products (id)
);



CREATE TABLE orders (
    id             NUMBER(19) PRIMARY KEY,
    customer_id    NUMBER(19) NOT NULL,
    created_at     TIMESTAMP WITH TIME ZONE DEFAULT SYSTIMESTAMP NOT NULL,
    status         VARCHAR2(50) DEFAULT 'CREATED' NOT NULL CHECK (status IN ('CREATED', 'ACCEPTED', 'CANCELLED')),
    payment_method VARCHAR2(50) NOT NULL CHECK (payment_method IN ('CARD', 'CASH', 'TRANSFER')),

    CONSTRAINT fk_orders_customer
        FOREIGN KEY (customer_id)
            REFERENCES customers (id)
);
CREATE TABLE order_items (
    id         NUMBER(19) PRIMARY KEY,
    order_id   NUMBER(19) NOT NULL,
    product_id NUMBER(19) NOT NULL,
    price_id   NUMBER(19) NOT NULL,
    quantity   NUMBER(10) NOT NULL,

    CONSTRAINT fk_orders_item_order
        FOREIGN KEY (order_id)
            REFERENCES orders (id),

    CONSTRAINT fk_orders_item_product
        FOREIGN KEY (product_id)
            REFERENCES products (id),

    CONSTRAINT fk_orders_item_price
        FOREIGN KEY (price_id)
            REFERENCES prices (id)
);
CREATE TABLE deliveries (
    id         NUMBER(19) PRIMARY KEY,
    order_id   NUMBER(19) NOT NULL UNIQUE,
    address_id NUMBER(19) NOT NULL,
    shipped_by VARCHAR2(100),
    awb        VARCHAR2(100),

    CONSTRAINT fk_delivery_order
        FOREIGN KEY (order_id)
            REFERENCES orders (id),

    CONSTRAINT fk_deliveries_address
        FOREIGN KEY (address_id)
            REFERENCES addresses (id)
);