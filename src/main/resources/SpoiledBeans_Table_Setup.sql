--SpoiledBeans table setup 

--CREATE TABLE users (
--
--	id			serial,
--	username	varchar(25) UNIQUE NOT NULL,
--	password	varchar(256) NOT NULL,
--	email		varchar(256) UNIQUE NOT NULL,
--	firstname 	varchar(25),
--	lastname	varchar(25),
--	bio			varchar(256),
--	
--	CONSTRAINT user_id
--	PRIMARY KEY (id)
--	
--
--);


create table user_roles(
	id 		serial,
	name	varchar(25),
	
	constraint id
	primary key (id)
);



CREATE TABLE users (

	id			serial,
	username	varchar(25) UNIQUE NOT NULL,
	password	varchar(256) NOT NULL,
	email		varchar(256) UNIQUE NOT NULL,
	firstname 	varchar(25),
	lastname	varchar(25),
	bio			varchar(256),
	user_role 	int,
	
	CONSTRAINT user_id
	PRIMARY KEY (id),
	
	FOREIGN KEY (user_role) REFERENCES user_roles (id)

);



--CREATE TABLE movies (
--
--	id 			serial,
--	name		varchar(180),
--	director	varchar(51),
--	genre		varchar(25),
--	synopsis	varchar(25),
--	
--	CONSTRAINT movie_id
--	PRIMARY KEY (id)
--
--);

CREATE TABLE movies (

	id 			serial,
	name		varchar(180),
	director	varchar(51),
	genre		varchar(25),
	synopsis	varchar(25),
	release_date	int not null,
	
	CONSTRAINT movie_id
	PRIMARY KEY (id)

);



--CREATE TABLE review (
--
--	id 			serial,
--	rating		numeric(2,1) NOT NULL,
--	review		varchar(256),
--	
--	CONSTRAINT review_id
--	PRIMARY KEY (id)
--	
--);

CREATE TABLE review (

	id 			serial,
	rating		numeric(2,1) NOT NULL,
	review		varchar(256),
	review_time timestamp not null,
	
	CONSTRAINT review_id
	PRIMARY KEY (id)
	
);


CREATE TABLE user_reviews (

	review_id		int,
	user_id			int,
	
	PRIMARY KEY (review_id, user_id),
	FOREIGN KEY (review_id) REFERENCES review (id),
	FOREIGN KEY (user_id) REFERENCES users (id)

);


CREATE TABLE movie_review (

	review_id		int,
	movie_id		int,
	
	
	PRIMARY KEY (review_id, movie_id),
	FOREIGN KEY (review_id) REFERENCES review (id),
	FOREIGN KEY (movie_id) REFERENCES movies (id)

);

CREATE TABLE favorites (

	user_id 		int,
	movie_id		int,
	
	PRIMARY KEY (user_id, movie_id),
	FOREIGN KEY (user_id) REFERENCES users (id),
	FOREIGN KEY (movie_id) REFERENCES movies (id)

);

--INSERT INTO user_roles (name) 
--		values('Premium User');

--INSERT INTO users(username,"password",email,firstname,lastname,bio,user_role)
--		values('testingUser', 'bad password', 'dummy@dumdum.com','test','user','test value to test out connection',1);


--insert into movies("name",release_date)
--	values('testMovie1',2000);
--
--insert into movies("name",release_date)
--	values('testMovie2',2001);
--
--insert into movies("name",release_date)
--	values('testMovie3',2002);
--
--insert into movies("name",release_date)
--	values('dummyMovie',2003);


--select * from user_roles;
--	select * from users;
--select * from movies;

-- these drops should be in order to delete everything for recreation

--drop table user_reviews;
--drop table favorites;
--drop table users;
--drop table user_roles;
--drop table movie_review;
--drop table movies;
--drop table review;
