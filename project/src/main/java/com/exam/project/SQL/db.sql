
CREATE USER federation_user WITH PASSWORD '1234';

CREATE DATABASE federation_db OWNER federation_user;

GRANT ALL PRIVILEGES ON DATABASE federation_db TO federation_user;