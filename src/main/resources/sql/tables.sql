CREATE DATABASE test;

drop database test;

use test;

create table user
(
    id  bigint auto_increment
        primary key,
    email    varchar(255) null,
    password varchar(255) null,
    phone    varchar(255) null,
    username varchar(255) null

);

create table role
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
);

create table user_roles
(
    user_id bigint  null,
    role_id bigint  null,
    constraint FK55itppkw3i07do3h7qoclqd4k
        foreign key (user_id) references user (id)
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    constraint FKrhfovtciq1l558cw6udg0h0d3
        foreign key (role_id) references role (id)
            ON UPDATE CASCADE
            ON DELETE CASCADE
);

insert into role(name) value ("ADMIN");
insert into role(name) value ("USER");

insert into user(username, email, phone, password) value ("Dmitry Kravchuk", "krava@krava.ua", "0666219061", "test");
insert into user(username, email, phone, password) value ("test", "test@test.com", "0666666", "test");
insert into user(username, email, phone, password) value ("test1", "test1@test1.com", "0222222", "test");

insert into user_role(user_id, role_id) value (1,1);
insert into user_role(user_id, role_id) value (2,2);

delete from user where id = 3;




