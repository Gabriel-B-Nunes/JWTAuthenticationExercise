create table usuarios(

    id serial,
    login varchar(100) unique not null,
    senha varchar(255) not null,

    primary key(id)
);