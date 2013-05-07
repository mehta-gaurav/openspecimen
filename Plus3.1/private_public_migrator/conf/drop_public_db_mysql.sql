Drop DATABASE if exists @@PUBLIC_DB_NAME@@;    

CREATE DATABASE @@PUBLIC_DB_NAME@@;

use @@PUBLIC_DB_NAME@@;

GRANT ALL PRIVILEGES ON @@PUBLIC_DB_NAME@@.* TO '@@PUBLIC_DB_USER_NAME@@'@'localhost' IDENTIFIED BY '@@PUBLIC_DB_PASSWORD@@' WITH GRANT OPTION;

GRANT ALL PRIVILEGES ON @@PUBLIC_DB_NAME@@.* TO '@@PUBLIC_DB_USER_NAME@@'@'%' IDENTIFIED BY '@@PUBLIC_DB_PASSWORD@@' WITH GRANT OPTION;

commit;