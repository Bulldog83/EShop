CREATE TABLE IF NOT EXISTS "CATEGORIES" (
    "RAW_ID" BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    "TITLE" VARCHAR(50) NOT NULL,
    "CREATED" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "UPDATED" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT UNIQUE_TITLE UNIQUE("TITLE"));
INSERT INTO "CATEGORIES" (TITLE) VALUES('Other');
INSERT INTO "CATEGORIES" (TITLE) VALUES('Painting');
INSERT INTO "CATEGORIES" (TITLE) VALUES('Writing');

CREATE TABLE IF NOT EXISTS "PRODUCTS" (
    "RAW_ID" BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    "TITLE" VARCHAR(50) NOT NULL,
    "PRICE" DECIMAL DEFAULT 0.0,
    "CATEGORY" BIGINT DEFAULT 1,
    "CREATED" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "UPDATED" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_CATEGORY_ID FOREIGN KEY ("CATEGORY") REFERENCES CATEGORIES("RAW_ID"));
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Blue Pen', 5.45, 3);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Red Pen', 5.45, 3);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Green Pen', 5.45, 3);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Pencils Box', 20.0, 2);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Paper Block', 10.25, 2);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Notebook', 3.15, 3);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Notebook', 3.15, 3);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Notebook', 2.95, 3);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Notebook', 2.85, 3);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Notebook', 4.0, 3);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Scratchpad', 2.15, 3);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Scratchpad', 2.15, 3);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Scratchpad', 2.2, 3);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Scotch tape', 1.25, 1);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Scotch tape', 1.75, 1);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Scotch tape', 2.25, 1);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Scotch tape', 4.5, 1);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Paints', 3.0, 2);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Paints', 3.55, 2);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Paints', 4.5, 2);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Paints', 5.25, 2);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Paints', 6.15, 2);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Set of pens', 12.45, 3);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Set of pens', 13.15, 3);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Set of pens', 13.5, 3);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Set of pens', 13.8, 3);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Ruler', 1.45, 1);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Ruler', 1.5, 1);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Ruler', 2.0, 1);
INSERT INTO "PRODUCTS" (TITLE, PRICE, CATEGORY) VALUES('Ruler', 2.45, 1);

CREATE TABLE IF NOT EXISTS "ORDERS" (
    "RAW_ID" BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    "SESSION" UUID NOT NULL,
    "SUM" DECIMAL DEFAULT 0.0,
    "CREATED" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "UPDATED" TIMESTAMP DEFAULT CURRENT_TIMESTAMP);
CREATE TABLE IF NOT EXISTS "ORDER_ITEMS" (
    "RAW_ID" BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    "ORDER_ID" BIGINT NOT NULL,
    "TITLE" VARCHAR(50) NOT NULL,
    "COUNT" INT DEFAULT 1,
    "PRICE" DECIMAL DEFAULT 0.0,
    "SUM" DECIMAL DEFAULT 0.0,
    "CREATED" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "UPDATED" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_ORDER_ID FOREIGN KEY ("ORDER_ID") REFERENCES ORDERS("RAW_ID"));
