create table if not exists driver(
    id serial primary key,
    name VARCHAR,
    auto_user_id int references auto_user(id)
);