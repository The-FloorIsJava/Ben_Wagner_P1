create table users (
	id serial primary key,
	email varchar(255) not null unique,
	password text not null,
	is_manager bool default false
);
create table tickets (
	id serial primary key,
	amount numeric not null,
	description text not null,
	status bool default null,
	submitter_id integer references users(id),
	processer_id integer references users(id)
);