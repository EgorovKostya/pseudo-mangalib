drop table account;
create table account
(
    id       bigserial primary key,
    username varchar(255),
    password varchar(255),
    deleted  boolean default false,
    role varchar(244) default 'user'
);

drop table manga;
create table manga
(
    id          bigserial primary key,
    name        varchar(255),
    description varchar(255),
    page_count  int,
    url varchar(255)
);
-- -- alter table  manga add column    extension varchar(20);
-- alter table manga add column type varchar(50);
-- alter table manga add column start_date varchar(20);
-- alter table manga add column status varchar(50);
-- alter table manga add column author varchar(50);
-- alter table manga add column alternative_name varchar(250);
-- alter table manga add column full_description text;
-- alter table manga add column link text;
alter table manga add column deleted boolean default false;


insert into manga (name, description, page_count)
values ('solo level up', 'not', 100),
       ('Ранкер, который всё время спит', 'cool', 47),
       ('Я хочу зарабатывать деньги, используя навыки данные богом, и флиртовать с девушками разных рас',
        'not bad', 33),
       ('Наномашины', 'amazing', 55)
;


drop table account_manga;
create table account_manga
(
    account_id bigint references account (id) on delete cascade on update cascade,
    manga_id   bigint references manga (id) on delete cascade on update cascade
);

create table faq
(
    id     bigserial primary key,
    label  varchar(255),
    answer text
);

drop table chat;
create table chat
(
    id       bigserial primary key,
    user_id  bigint references account (id) on update cascade,
    username varchar(255),
    message  text
);


