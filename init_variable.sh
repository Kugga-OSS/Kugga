echo '
export DATABASE_HOST="localhost"
export DATABASE_PORT="3306"
export DATABASE_USERNAME="root"
export DATABASE_PASSWORD="123"
export REDIS_HOST="ip"
export REDIS_PORT="6379"
export JWT_SECRET_KEY="random_string"
' >> /etc/profile

source /etc/profile