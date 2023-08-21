BEGIN TRANSACTION;

DROP TABLE IF EXISTS game_list;
DROP TABLE IF EXISTS games;
DROP TABLE IF EXISTS lists;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE lists (
    list_id serial,
    user_id int,
    list_name varchar(50) NOT NULL,
    CONSTRAINT PK_list PRIMARY KEY (list_id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE games (
    game_id int NOT NULL,
    CONSTRAINT PK_game PRIMARY KEY (game_id)
);

CREATE TABLE game_list (
    list_id int,
    game_id int,
    CONSTRAINT FK_game_id FOREIGN KEY (game_id) REFERENCES games(game_id),
    CONSTRAINT FK_list_id FOREIGN KEY (list_id) REFERENCES lists(list_id)
);

COMMIT TRANSACTION;
