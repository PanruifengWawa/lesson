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
token_str varchar(200) unique not null,
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

alter table t_course add column state int not null default 0;
create index course_is_free on t_course(is_free);
create index course_type on t_course(type);
create index course_is_free_type on t_course(is_free,type);
create index course_is_free_type_state on t_course(is_free,type,state);

drop table if exists t_course_arrangement;
create table t_course_arrangement(
course_arrangement_id serial primary key,
img_src varchar(256) not null,
name varchar(256) not null,
course_id bigint(20) unsigned NOT NULL,
foreign key(course_id) references t_course(course_id) on delete cascade
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
 alter table t_course_arrangement add column state int not null default 0;

drop table if exists t_course_content;
create table t_course_content(
course_content_id serial primary key,
name varchar(256) not null,
sub_name varchar(256) not null,
content text not null,
book_name varchar(256) not null,
book_img_src varchar(256) not null,
course_id bigint(20) unsigned NOT NULL,
course_arrangement_id bigint(20) unsigned NOT NULL,
foreign key(course_id) references t_course(course_id) on delete cascade,
foreign key(course_arrangement_id) references t_course_arrangement(course_arrangement_id) on delete cascade
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table t_course_content add column state int not null default 0;


drop table if exists t_course_code;
create table t_course_code(
course_code_id serial primary key,
course_code_str char(32) not null unique,
course_id bigint(20) unsigned NOT NULL,
user_id bigint(20) unsigned NOT NULL,
is_used tinyint not null default 0,
use_date datetime,
foreign key(course_id) references t_course(course_id) on delete cascade,
foreign key(user_id) references t_user(user_id) on delete cascade
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create index course_code_str_index on t_course_code(course_code_str);
create index course_code_user_course_id on t_course_code(user_id, course_id);

drop table if exists t_book;
create table t_book(
book_id serial primary key,
book_name varchar(256) not null,
book_img_src varchar(256) not null,
read_date datetime not null,
user_id bigint(20) unsigned NOT NULL,
is_course_book tinyint not null,
course_content_id bigint(20) unsigned,
foreign key(user_id) references t_user(user_id) on delete cascade
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create index book_user_course_content_index on t_book(user_id,course_content_id);

drop table if exists t_link;
create table t_link(
link_id serial primary key,
link_src varchar(512) not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into t_link(link_id,link_src) values(1,'http://mp.weixin.qq.com/mp/homepage?__biz=MzIzNzYzMTc4Ng==&hid=5&sn=ceb19fbcab735c8a9fee0d7193effe82#wechat_redirect');

 alter table t_course_content modify column content longtext;

INSERT INTO `t_user` (`phone`,`password`,`nick_name`,`gender`,`birthday`,`type`,`city`,`head_src`,`note`,`parent`,`register_date`) VALUES ('admin','8b6f59508eab3af66a2b3bbd8bd2846f','管理员','M','1993-10-22',0,'上海',NULL,NULL,'','2017-6-28');

INSERT INTO `t_user` (`phone`,`password`,`nick_name`,`gender`,`birthday`,`type`,`city`,`head_src`,`note`,`parent`,`register_date`) VALUES ('admin1','8b6f59508eab3af66a2b3bbd8bd2846f','管理员','M','1993-10-22',0,'上海',NULL,NULL,'','2017-6-28');


INSERT INTO `t_user` (`phone`,`password`,`nick_name`,`gender`,`birthday`,`type`,`city`,`head_src`,`note`,`parent`,`register_date`) VALUES ('admin2','8b6f59508eab3af66a2b3bbd8bd2846f','管理员','M','1993-10-22',0,'上海',NULL,NULL,'','2017-6-28');

INSERT INTO `t_user` (`phone`,`password`,`nick_name`,`gender`,`birthday`,`type`,`city`,`head_src`,`note`,`parent`,`register_date`) VALUES ('admin3','8b6f59508eab3af66a2b3bbd8bd2846f','管理员','M','1993-10-22',0,'上海',NULL,NULL,'','2017-6-28');

INSERT INTO `t_user` (`phone`,`password`,`nick_name`,`gender`,`birthday`,`type`,`city`,`head_src`,`note`,`parent`,`register_date`) VALUES ('admin4','8b6f59508eab3af66a2b3bbd8bd2846f','管理员','M','1993-10-22',0,'上海',NULL,NULL,'','2017-6-28');

INSERT INTO `t_user` (`phone`,`password`,`nick_name`,`gender`,`birthday`,`type`,`city`,`head_src`,`note`,`parent`,`register_date`) VALUES ('admin5','8b6f59508eab3af66a2b3bbd8bd2846f','管理员','M','1993-10-22',0,'上海',NULL,NULL,'','2017-6-28');

INSERT INTO `t_user` (`phone`,`password`,`nick_name`,`gender`,`birthday`,`type`,`city`,`head_src`,`note`,`parent`,`register_date`) VALUES ('prf','8b6f59508eab3af66a2b3bbd8bd2846f','管理员','M','1993-10-22',0,'上海',NULL,NULL,'','2017-6-28');

INSERT INTO `t_user` (`phone`,`password`,`nick_name`,`gender`,`birthday`,`type`,`city`,`head_src`,`note`,`parent`,`register_date`) VALUES ('admin6','8b6f59508eab3af66a2b3bbd8bd2846f','管理员','M','1993-10-22',0,'上海',NULL,NULL,'','2017-6-28');
INSERT INTO `t_user` (`phone`,`password`,`nick_name`,`gender`,`birthday`,`type`,`city`,`head_src`,`note`,`parent`,`register_date`) VALUES ('admin7','8b6f59508eab3af66a2b3bbd8bd2846f','管理员','M','1993-10-22',0,'上海',NULL,NULL,'','2017-6-28');
INSERT INTO `t_user` (`phone`,`password`,`nick_name`,`gender`,`birthday`,`type`,`city`,`head_src`,`note`,`parent`,`register_date`) VALUES ('admin8','8b6f59508eab3af66a2b3bbd8bd2846f','管理员','M','1993-10-22',0,'上海',NULL,NULL,'','2017-6-28');
INSERT INTO `t_user` (`phone`,`password`,`nick_name`,`gender`,`birthday`,`type`,`city`,`head_src`,`note`,`parent`,`register_date`) VALUES ('admin9','8b6f59508eab3af66a2b3bbd8bd2846f','管理员','M','1993-10-22',0,'上海',NULL,NULL,'','2017-6-28');
INSERT INTO `t_user` (`phone`,`password`,`nick_name`,`gender`,`birthday`,`type`,`city`,`head_src`,`note`,`parent`,`register_date`) VALUES ('admin10','8b6f59508eab3af66a2b3bbd8bd2846f','管理员','M','1993-10-22',0,'上海',NULL,NULL,'','2017-6-28');

-- version 2

 alter table t_course_arrangement add column arrangement_order int not null default 0;
 alter table t_course_content add column content_order int not null default 0;
