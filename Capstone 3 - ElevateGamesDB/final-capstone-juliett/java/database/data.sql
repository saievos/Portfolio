BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');
INSERT INTO users (username,password_hash,role) VALUES ('zack','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO lists (user_id, list_name) VALUES (3, 'Wishlist');
INSERT INTO lists (user_id, list_name) VALUES (3, 'Currently Playing');
INSERT INTO lists (user_id, list_name) VALUES (3, 'Completed');
INSERT INTO games (game_id) VALUES (3498);
INSERT INTO games (game_id) VALUES (4200);
INSERT INTO games (game_id) VALUES (5286);
INSERT INTO games (game_id) VALUES (5679);
INSERT INTO games (game_id) VALUES (28);
INSERT INTO games (game_id) VALUES (4286);
INSERT INTO games (game_id) VALUES (1030);
INSERT INTO games (game_id) VALUES (32);
INSERT INTO game_list (list_id, game_id) VALUES ((SELECT lists.list_id FROM lists WHERE (list_name LIKE 'Wishlist') AND (user_id = 3)), 3498);
INSERT INTO game_list (list_id, game_id) VALUES ((SELECT lists.list_id FROM lists WHERE list_name LIKE 'Wishlist' AND user_id = 3), 4200);
INSERT INTO game_list (list_id, game_id) VALUES ((SELECT lists.list_id FROM lists WHERE list_name LIKE 'Wishlist' AND user_id = 3), 5286);
INSERT INTO game_list (list_id, game_id) VALUES ((SELECT lists.list_id FROM lists WHERE list_name LIKE 'Currently Playing' AND user_id = 3), 5679);
INSERT INTO game_list (list_id, game_id) VALUES ((SELECT lists.list_id FROM lists WHERE list_name LIKE 'Completed' AND user_id = 3), 28);
INSERT INTO game_list (list_id, game_id) VALUES ((SELECT lists.list_id FROM lists WHERE list_name LIKE 'Completed' AND user_id = 3), 4286);
INSERT INTO game_list (list_id, game_id) VALUES ((SELECT lists.list_id FROM lists WHERE list_name LIKE 'Completed' AND user_id = 3), 1030);
INSERT INTO game_list (list_id, game_id) VALUES ((SELECT lists.list_id FROM lists WHERE list_name LIKE 'Completed' AND user_id = 3), 32);

COMMIT TRANSACTION;
