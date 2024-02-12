create table websites (
    id serial primary key,
    site varchar(255) not null unique,
    login varchar(255) not null unique,
    password varchar(255) not null
);