# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table music_count (
  code                      varchar(255) not null,
  size                      integer,
  constraint pk_music_count primary key (code))
;

create table play_list (
  id                        bigint auto_increment not null,
  keycode                   varchar(255),
  title                     varchar(255),
  description               varchar(255),
  recommend                 bigint,
  thumbnail                 varchar(255),
  constraint pk_play_list primary key (id))
;

create table play_list_group (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  password                  varchar(255),
  description               varchar(255),
  recommend                 bigint,
  thumbnail                 varchar(255),
  constraint pk_play_list_group primary key (id))
;

create table play_list_group_r (
  id                        bigint auto_increment not null,
  keycode                   varchar(255),
  password                  varchar(255),
  title                     varchar(255),
  description               varchar(255),
  recommend                 bigint,
  thumbnail                 varchar(255),
  group_id                  bigint,
  constraint pk_play_list_group_r primary key (id))
;

create table user (
  email                     varchar(255),
  password                  varchar(255),
  fullname                  varchar(255),
  is_admin                  tinyint(1) default 0)
;

alter table play_list_group_r add constraint fk_play_list_group_r_group_1 foreign key (group_id) references play_list_group (id) on delete restrict on update restrict;
create index ix_play_list_group_r_group_1 on play_list_group_r (group_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table music_count;

drop table play_list;

drop table play_list_group;

drop table play_list_group_r;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

