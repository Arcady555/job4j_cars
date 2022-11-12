create table history_owner(
    PRIMARY KEY (driver_id, car_id),
    driver_id int not null references driver(id),
    car_id int not null references car(id),
    startAt TIMESTAMP,
    endAt TIMESTAMP
);