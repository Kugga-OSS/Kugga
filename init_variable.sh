echo '
export DATABASE_HOST="localhost"
export DATABASE_PORT="3306"
export DATABASE_USERNAME="root"
export DATABASE_PASSWORD="123"
export REDIS_HOST="ip"
export REDIS_PORT="6379"
export JWT_SECRET_KEY="random_string"
export OSS_ENDPOINT="阿里云OSS 对应Bucket所在的地域域名"
export ALI_KEY_ID="阿里云RAM Key Id"
export ALI_KEY_SECRET="阿里云RAM Key Secret"
export BUCKET_NAME="<OSS Bucket桶的名称>"
export AVATAR_FOLDER_URL="<头像文件存储路径，参考 "avatar/" >"
' >> /etc/profile

source /etc/profile