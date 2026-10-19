#!/bin/bash
set -euo pipefail
[ -e .env ] && { echo ".env already exists, refusing to overwrite"; exit 1; }
cp .env.example .env
for v in POSTGRES_PASSWORD USER_DB_PASSWORD MERCHANT_DB_PASSWORD ADMIN_DB_PASSWORD \
         AUTH_DB_PASSWORD REDIS_PASSWORD; do
  sed -i '' "s|^$v=.*|$v=$(openssl rand -base64 32 | tr -d '=+/')|" .env
done
sed -i '' "s|^JWT_SECRET=.*|JWT_SECRET=$(openssl rand -base64 64 | tr -d '=+/\n')|" .env
chmod 600 .env
echo "Generated .env (permissions 600)"