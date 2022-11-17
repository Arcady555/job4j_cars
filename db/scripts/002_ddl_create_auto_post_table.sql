CREATE TABLE if not exists auto_post (
  id SERIAL PRIMARY KEY,
  text TEXT,
  created TIMESTAMP,
  auto_user_id int references auto_user(id)
);