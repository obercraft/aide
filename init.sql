drop table if exists `comment`;
drop table if exists `authority`;
drop table if exists `user`;

create table `user` (
	id int(11) primary key auto_increment,
	name varchar(255) not null,
	email varchar(255) not null,
	password varchar(255) not null,
	created datetime not null,
	last_login datetime,
	changed datetime,
	unique (email),
	unique (name)
);
create table `authority` (
	id int(11) primary key auto_increment,
	user_id int(11) not null,
	name varchar(255) not null,
	unique (user_id,name),
	foreign key (user_id) references `user` (id)
);
create table comment (
	id int(11) primary key auto_increment,
	user_id int(11) not null,
	subject varchar(255) not null,
	created datetime not null,
	text text,
	foreign key (user_id) references `user` (id)
);

