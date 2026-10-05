#!/bin/bash
set -euo pipefail

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" \
  -v user_pw="$USER_DB_PASSWORD" \
  -v merchant_pw="$MERCHANT_DB_PASSWORD" \
  -v admin_pw="$ADMIN_DB_PASSWORD" \
  -v keycloak_pw="$KEYCLOAK_DB_PASSWORD" <<'EOSQL'

CREATE ROLE userlocal     LOGIN PASSWORD :'user_pw';
CREATE ROLE merchantlocal LOGIN PASSWORD :'merchant_pw';
CREATE ROLE adminlocal    LOGIN PASSWORD :'admin_pw';
CREATE ROLE keycloak      LOGIN PASSWORD :'keycloak_pw';

CREATE DATABASE user_mode_db     OWNER userlocal;
CREATE DATABASE merchant_mode_db OWNER merchantlocal;
CREATE DATABASE admin_mode_db    OWNER adminlocal;
CREATE DATABASE keycloak_db      OWNER keycloak;

REVOKE CONNECT ON DATABASE user_mode_db, merchant_mode_db, admin_mode_db, keycloak_db FROM PUBLIC;
EOSQL