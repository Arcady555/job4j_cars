CREATE TABLE participates(
   id SERIAL PRIMARY KEY,
   post_id INT REFERENCES auto_post(id),
   user_id INT REFERENCES auto_user(id)
);