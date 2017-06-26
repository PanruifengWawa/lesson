drop database lesson;
create database lesson;

use lesson;

drop table if exists t_user;
create table t_user (
user_id serial primary key,
phone char(11) not null unique,
password varchar(256) not null,
parent varchar(64) not null,
nick_name varchar(64) not null,
gender char(1) not null default 'F',
birthday date not null,
type tinyint not null default 1,
city varchar(64) not null,
head_src varchar(200),
register_date datetime not null,
note text
)engine=innodb default charset=utf8;

create index user_phone_index on t_user(phone);

drop table if exists t_token;
create table t_token(
id serial primary key,
token_str varchar(200) unique,
user_id bigint(20) unsigned not null unique,
user_type tinyint not null,
login_date datetime,
foreign key(user_id) references t_user(user_id) on delete cascade
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create index token_str_index on t_token(token_str);
create index token_user_id_index on t_token(user_id);

drop table if exists t_course;
create table t_course(
course_id serial primary key,
name varchar(256) not null,
img_src varchar(200) not null,
is_free tinyint not null,
type tinyint not null,
link varchar(200),
create_date datetime not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
create index course_is_free on t_course(is_free);
create index course_type on t_course(type);
create index course_is_free_type on t_course(is_free,type);



INSERT INTO `t_user` (`phone`,`password`,`nick_name`,`gender`,`birthday`,`type`,`city`,`head_src`,`note`,`parent`,`register_date`) VALUES ('admin','8b6f59508eab3af66a2b3bbd8bd2846f','管理员','M','1993-10-22',0,'上海',NULL,NULL,'','2017-6-28');






