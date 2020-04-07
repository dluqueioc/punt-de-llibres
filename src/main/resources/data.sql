CREATE EXTENSION IF NOT EXISTS pgcrypto;

TRUNCATE TABLE genres RESTART IDENTITY CASCADE;
TRUNCATE TABLE languages RESTART IDENTITY CASCADE;
TRUNCATE TABLE publishers RESTART IDENTITY CASCADE;
TRUNCATE TABLE themes RESTART IDENTITY CASCADE;
TRUNCATE TABLE books RESTART IDENTITY CASCADE;
TRUNCATE TABLE users RESTART IDENTITY CASCADE;
TRUNCATE TABLE authors RESTART IDENTITY CASCADE;

INSERT INTO users (id, username, password, name, last_name, location, geo_location, email, created_date, profile, rating) VALUES (1, 'Jaume_A', crypt(crypt('12345678', gen_salt('bf')), gen_salt('bf')), 'Jaume', 'Alepuz Bria', '08004', NULL, 'jaualbr@yahoo.es', '2020-01-01 10:00:00', NULL, 0);
INSERT INTO users (id, username, password, name, last_name, location, geo_location, email, created_date, profile, rating) VALUES (2, 'PepElLector', crypt('12345678', gen_salt('bf')), 'Josep', 'Garcia', '08024', NULL, 'test@test.com', '2020-02-02 12:05:00', NULL, 0);
INSERT INTO users (id, username, password, name, last_name, location, geo_location, email, created_date, profile, rating) VALUES (3, 'Llibrefila83', crypt('12345678', gen_salt('bf')), 'Montse', 'Martinez Rovira', '08004', NULL, 'mmrov@test.cat', '2020-03-02 20:15:30', NULL, 0);
INSERT INTO users (id, username, password, name, last_name, location, geo_location, email, created_date, profile, rating) VALUES (4, 'maria_ga', crypt('12345678', gen_salt('bf')), 'Maria', 'Garcia Alvarez', '08001', NULL, 'mariaga@test.cat', '2020-03-03 12:00:00', NULL, 0);
INSERT INTO users (id, username, password, name, last_name, location, geo_location, email, created_date, profile, rating) VALUES (5, 'montse_mt', crypt('12345678', gen_salt('bf')), 'Montserrat', 'Martinez Torres', '08001', NULL, 'montsemt@test.cat', '2020-03-04 12:00:00', NULL, 0);
INSERT INTO users (id, username, password, name, last_name, location, geo_location, email, created_date, profile, rating) VALUES (6, 'jordi_ln', crypt('12345678', gen_salt('bf')), 'Jordi', 'Lopez Navarro', '08002', NULL, 'jordiln@test.cat', '2020-03-05 12:00:00', NULL, 0);
INSERT INTO users (id, username, password, name, last_name, location, geo_location, email, created_date, profile, rating) VALUES (7, 'manel_sn', crypt('12345678', gen_salt('bf')), 'Manel', 'Sanchez Romero', '08002', NULL, 'manelsr@test.cat', '2020-03-06 12:00:00', NULL, 0);
INSERT INTO users (id, username, password, name, last_name, location, geo_location, email, created_date, profile, rating) VALUES (8, 'david_rd', crypt('12345678', gen_salt('bf')), 'David', 'Rodriguez Diaz', '08003', NULL, 'davidrd@test.cat', '2020-03-07 12:00:00', NULL, 0);
INSERT INTO users (id, username, password, name, last_name, location, geo_location, email, created_date, profile, rating) VALUES (9, 'joan_fm', crypt('12345678', gen_salt('bf')), 'Joan', 'Fernandez Muñoz', '08003', NULL, 'joanfm@test.cat', '2020-03-08 12:00:00', NULL, 0);
INSERT INTO users (id, username, password, name, last_name, location, geo_location, email, created_date, profile, rating) VALUES (10, 'carme_ph', crypt('12345678', gen_salt('bf')), 'Carme', 'Perez Hernandez', '08002', NULL, 'carmeph@test.cat', '2020-03-09 12:00:00', NULL, 0);
INSERT INTO users (id, username, password, name, last_name, location, geo_location, email, created_date, profile, rating) VALUES (11, 'marc_gm', crypt('12345678', gen_salt('bf')), 'Marc', 'Gonzalez Moreno', '08003', NULL, 'marcgm@test.cat', '2020-03-10 12:00:00', NULL, 0);


INSERT INTO authors (name) VALUES ('Frank Herbert');
INSERT INTO authors (name) VALUES ('Joseph Campbell');
INSERT INTO authors (name) VALUES ('Dashiell Hammet');
INSERT INTO authors (name) VALUES ('Patricia Highsmith');
INSERT INTO authors (name) VALUES ('Richard Ford');
INSERT INTO authors (name) VALUES ('Eduardo Mendoza');
INSERT INTO authors (name) VALUES ('Matilde Asensi');
INSERT INTO authors (name) VALUES ('Enric V. Alepuz Llopis');


INSERT INTO genres (name) VALUES ('Novel·la');
INSERT INTO genres (name) VALUES ('Assaig');
INSERT INTO genres (name) VALUES ('Poesia');


INSERT INTO languages (name) VALUES ('Català');
INSERT INTO languages (name) VALUES ('Castellà');
INSERT INTO languages (name) VALUES ('Anglès');


INSERT INTO publishers (name) VALUES ('Anagrama');
INSERT INTO publishers (name) VALUES ('Edicions 62');
INSERT INTO publishers (name) VALUES ('Fondo de Cultura Económica');
INSERT INTO publishers (name) VALUES ('Hodder');
INSERT INTO publishers (name) VALUES ('Planeta');
INSERT INTO publishers (name) VALUES ('Círculo de lectores');
INSERT INTO publishers (name) VALUES ('Alféizar');


INSERT INTO themes (name) VALUES ('Ciència-ficció');
INSERT INTO themes (name) VALUES ('Antropologia');
INSERT INTO themes (name) VALUES ('Novel·la negra');
INSERT INTO themes (name) VALUES ('Suspens');
INSERT INTO themes (name) VALUES ('Realisme social');
INSERT INTO themes (name) VALUES ('Guerra civil i postguerra');
INSERT INTO themes (name) VALUES ('Aventures');

INSERT INTO book_statuses (name) VALUES ('disponible');
INSERT INTO book_statuses (name) VALUES ('no disponible');
INSERT INTO book_statuses (name) VALUES ('reservat');


INSERT INTO books (isbn, title, author_id, publisher_id, genre_id, theme_id, language_id, edition, created_date, preservation, book_status_id, user_id) VALUES ('84-339-2055-3', 'Mar de fondo', 4, 1, 1, 4, 2, '1ª Edició', '2020-03-10 12:00:00', 'Coberta desencolada', 1, 1);
INSERT INTO books (isbn, title, author_id, publisher_id, genre_id, theme_id, language_id, edition, created_date, preservation, book_status_id, user_id) VALUES ('84-339-3022-2', 'El juego del escodite', 4, 1, 1, 1, 2, '3ª Edicio', '2020-03-11 12:00:00', 'Fulles envellides', 1, 1);
INSERT INTO books (isbn, title, author_id, publisher_id, genre_id, theme_id, language_id, edition, created_date, preservation, book_status_id, user_id) VALUES ('84-297-1701-3', 'La clau de vidre', 3, 2, 1, 3, 1, '1ª Edició', '2020-03-12 12:00:00', 'Bon estat', 1, 2);
INSERT INTO books (isbn, title, author_id, publisher_id, genre_id, theme_id, language_id, edition, created_date, preservation, book_status_id, user_id) VALUES ('978-607-16-2013-2', 'El héroe de las mil caras', 2, 3, 2, 2, 2, '2ª edició - 2ª reimpressió', '2020-03-13 12:00:00', 'Bon estat', 1, 3);
INSERT INTO books (isbn, title, author_id, publisher_id, genre_id, theme_id, language_id, edition, created_date, preservation, book_status_id, user_id) VALUES ('978 0 340 96019 69', 'Dune', 1, 4, 1, 1, 3, '50th anniversary edition', '2020-03-13 14:00:00', 'Capa plàstica de la coberta deteriorada', 1, 2);
INSERT INTO books (isbn, title, author_id, publisher_id, genre_id, theme_id, language_id, edition, created_date, preservation, book_status_id, user_id) VALUES ('978-84-339-7938-4', 'Francamente, Frank', 5, 1, 1, 5, 2, '1ª Edició', '2020-03-14 08:00:00', 'Bon estat', 1, 6);
INSERT INTO books (isbn, title, author_id, publisher_id, genre_id, theme_id, language_id, edition, created_date, preservation, book_status_id, user_id) VALUES ('978-84-08-09725-9', 'Riña de gatos', 6, 5, 1, 6, 2, '1ª Edició', '2020-03-14 10:00:00', 'Petita ruptura en la sobrecoberta', 1, 5);
INSERT INTO books (isbn, title, author_id, publisher_id, genre_id, theme_id, language_id, edition, created_date, preservation, book_status_id, user_id) VALUES ('978-84-672-2978-3', 'Tierra Firme', 7, 6, 1, 7, 2, '1ª Edició', '2020-03-14 14:00:00', 'Molt bon estat', 1, 5);
INSERT INTO books (isbn, title, author_id, publisher_id, genre_id, theme_id, language_id, edition, created_date, preservation, book_status_id, user_id) VALUES ('13:978-84-948248-9-0', 'Yo pongo el muerto', 8, 7, 1, 6, 2, '1ª Edició', '2020-03-14 16:00:00', 'Molt bon estat. Amb dedicatòria personal', 1, 7);
INSERT INTO books (isbn, title, author_id, publisher_id, genre_id, theme_id, language_id, edition, created_date, preservation, book_status_id, user_id) VALUES ('13:978-84-947805-3-0', 'Huidos', 8, 7, 1, 6, 2, '1ª Edició', '2020-03-15 08:00:00', 'Molt bon estat. Amb dedicatòria personal', 1, 9);
