-- Margherita pizza
insert into pizza(id, date_created, date_updated, name, price, spicy, veggie)
        VALUES (nextval('main_sequence'), now(), now(), 'Margherita', 150, false, true);
-- Capricciosa pizza
insert into pizza(id, date_created, date_updated, name, price, spicy, veggie)
        VALUES (nextval('main_sequence'), now(), now(), 'Capricciosa', 180, false, false);
-- Carbonara pizza
insert into pizza(id, date_created, date_updated, name, price, spicy, veggie)
        VALUES (nextval('main_sequence'), now(), now(), 'Carbonara', 200, false, false);
-- Vegetariana pizza
insert into pizza(id, date_created, date_updated, name, price, spicy, veggie)
        VALUES (nextval('main_sequence'), now(), now(), 'Vegetariana', 160, false, true);
-- Cheddar pizza
insert into pizza(id, date_created, date_updated, name, price, spicy, veggie)
        VALUES (nextval('main_sequence'), now(), now(), 'Cheddar', 160, false, true);
-- Burger Classic pizza
insert into pizza(id, date_created, date_updated, name, price, spicy, veggie)
        VALUES (nextval('main_sequence'), now(), now(), 'Burger Classic', 200, true, false);
-- Boston Barbecue pizza
insert into pizza(id, date_created, date_updated, name, price, spicy, veggie)
        VALUES (nextval('main_sequence'), now(), now(), 'Boston Barbecue', 210, true, false);
-- Pepperoni pizza
insert into pizza(id, date_created, date_updated, name, price, spicy, veggie)
        VALUES (nextval('main_sequence'), now(), now(), 'Pepperoni', 190, true, false);
-- Quatro Formaggi pizza
insert into pizza(id, date_created, date_updated, name, price, spicy, veggie)
        VALUES (nextval('main_sequence'), now(), now(), 'Quatro Formaggi', 240, false, true);
-- Calzone pizza
insert into pizza(id, date_created, date_updated, name, price, spicy, veggie)
        VALUES (nextval('main_sequence'), now(), now(), 'Calzone', 200, false, true);
