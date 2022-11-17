create table if not exists car(
    id serial primary key,
    name VARCHAR,
    engine_id int not null unique references engine(id)
);