create database controle_de_gastos_de_desempregado;

use controle_de_gastos_de_desempregado;

create table items(
    id int not null auto_increment primary key,
    nome varchar(255) unique not null,
    genero varchar(255) not null,
    created_at datetime not null,
    updated_at datetime not null
);

create table contas(
    id int not null auto_increment primary key,
    saldo decimal(10, 2) not null,
    created_at datetime not null,
    updated_at datetime not null
);

create table gastos(
    id int not null auto_increment primary key,
    data date not null,
    valor decimal(10, 2) not null,
    item_id int not null,
    conta_id int not null,
    created_at datetime not null,
    updated_at datetime not null,
    foreign key (item_id) references items (id),
    foreign key (conta_id) references contas (id)
);