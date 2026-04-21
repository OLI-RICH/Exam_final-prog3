
CREATE USER federation_user WITH PASSWORD 'votre_mot_de_passe';

CREATE DATABASE federation_db OWNER federation_user;

GRANT ALL PRIVILEGES ON DATABASE federation_db TO federation_user;