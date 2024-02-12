create table shortcuts (
    id serial primary key,
    link varchar(255) not null unique,
    code varchar(255) not null unique,
    total int default 0,
    website_id int references websites(id)
);