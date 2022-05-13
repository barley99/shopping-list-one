DROP TABLE IF EXISTS ShoppingItems;
CREATE TABLE ShoppingItems (
    id BIGSERIAL PRIMARY KEY,
    title text,
    done boolean NOT NULL DEFAULT FALSE
);

INSERT INTO ShoppingItems (title, done)
VALUES  ('Milk 2 packs', FALSE),
        ('Baguette', TRUE);