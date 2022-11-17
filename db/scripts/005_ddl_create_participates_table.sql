CREATE TABLE if not exists participates(
   PRIMARY KEY (post_id, user_id),
   post_id INT REFERENCES auto_post(id),
   user_id INT REFERENCES auto_user(id)
);