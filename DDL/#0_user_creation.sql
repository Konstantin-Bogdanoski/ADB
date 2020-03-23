create role adb_admin login superuser password 'admin';

create role adb_user login nosuperuser password 'user';
grant connect on database adb to adb_user;
grant usage on schema public to adb_user;
grant select on all tables in schema public to adb_user;