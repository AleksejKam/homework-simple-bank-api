insert into user (id, username, password) values (101, 'user1', '$2a$10$XPG5egwmLrAecukyNm7O/O.Oe98.jMDJ0lhCMAFz3bY7.a35pbw9y');
insert into user (id, username, password) values (102, 'user2', '$2a$10$XPG5egwmLrAecukyNm7O/O.Oe98.jMDJ0lhCMAFz3bY7.a35pbw9y');
insert into user (id, username, password) values (103, 'user3', '$2a$10$XPG5egwmLrAecukyNm7O/O.Oe98.jMDJ0lhCMAFz3bY7.a35pbw9y');

insert into account (id, user_id, amount) values (101, 101, 0);
insert into account (id, user_id, amount) values (102, 102, 0);
insert into account (id, user_id, amount) values (103, 103, 0);