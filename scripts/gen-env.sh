#!/bin/bash
set -euo pipefail
[ -e .env ] && { echo ".env already exists, refusing to overwrite"; exit 1; }
cp .env.example .env
for v in POSTGRES_PASSWORD USER_DB_PASSWORD MERCHANT_DB_PASSWORD ADMIN_DB_PASSWORD \
         KEYCLOAK_DB_PASSWORD KC_ADMIN_PASSWORD KC_CLIENT_SECRET \
         KC_TEST_USER_PASSWORD REDIS_PASSWORD; do
  sed -i "s|^$v=.*|$v=$(openssl rand -base64 32 | tr -d '=+/')|" .env
done
chmod 600 .env
echo "Generated .env (permissions 600)"