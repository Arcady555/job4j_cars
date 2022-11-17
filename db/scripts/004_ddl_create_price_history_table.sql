CREATE TABLE if not exists PRICE_HISTORY(
   id SERIAL PRIMARY KEY,
   before BIGINT not null,
   after BIGINT not null,
   created TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
   post_id INT NOT NULL UNIQUE REFERENCES auto_post(id)
);