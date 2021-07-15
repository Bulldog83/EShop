CREATE TABLE IF NOT EXISTS "PRODUCTS" (
    "RAW_ID" BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    "TITLE" VARCHAR(50) NOT NULL,
    "PRICE" DOUBLE DEFAULT 0.0);