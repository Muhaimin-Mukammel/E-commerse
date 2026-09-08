CREATE DATABASE user_mode_db;
CREATE DATABASE merchant_mode_db;
CREATE DATABASE admin_mode_db;
CREATE DATABASE shared_db;
CREATE DATABASE keycloak_db;

CREATE USER userlocal WITH PASSWORD "userlocal1234";
CREATE USER merchantlocal WITH PASSWORD "merchantlocal1234";
CREATE USER adminlocal WITH PASSWORD "adminlocal1234";
CREATE USER shared WITH PASSWORD "shared1234";
CREATE USER keycloak WITH PASSWORD "keycloak1234";

GRANT ALL PRIVILEGES ON DATABASE user_mode_db TO userlocal;
GRANT ALL PRIVILEGES ON DATABASE merchant_mode_db TO merchantlocal;
GRANT ALL PRIVILEGES ON DATABASE admin_mode_db TO adminlocal;
GRANT ALL PRIVILEGES ON DATABASE shared_db TO shared;
GRANT ALL PRIVILEGES ON DATABASE keycloak_db TO keycloak;

ALTER DATABASE user_mode_db OWNER TO userlocal;
ALTER DATABASE merchant_mode_db OWNER TO merchantlocal;
ALTER DATABASE admin_mode_db OWNER TO adminlocal;
ALTER DATABASE shared_db OWNER TO shared;
ALTER DATABASE keycloak_db OWNER TO keycloak;