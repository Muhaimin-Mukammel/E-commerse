#!/bin/bash
set -euo pipefail

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" \
  -v user_pw="$USER_DB_PASSWORD" \
  -v merchant_pw="$MERCHANT_DB_PASSWORD" \
  -v admin_pw="$ADMIN_DB_PASSWORD" \
  -v auth_pw="$AUTH_DB_PASSWORD" <<'EOSQL'

CREATE ROLE user      LOGIN PASSWORD :'user_pw';
CREATE ROLE merchant  LOGIN PASSWORD :'merchant_pw';
CREATE ROLE admin     LOGIN PASSWORD :'admin_pw';
CREATE ROLE auth      LOGIN PASSWORD :'auth_pw';

CREATE DATABASE user_mode_db     OWNER user;
CREATE DATABASE merchant_mode_db OWNER merchant;
CREATE DATABASE admin_mode_db    OWNER admin;
CREATE DATABASE auth_db      OWNER auth;

REVOKE CONNECT ON DATABASE user_mode_db, merchant_mode_db, admin_mode_db, auth_db FROM PUBLIC;
EOSQL