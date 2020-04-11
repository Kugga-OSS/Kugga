# Kugga

[![Build Status](https://dev.azure.com/ayang818/Kugga/_apis/build/status/Kugga-OSS.Kugga?branchName=master)](https://dev.azure.com/ayang818/Kugga/_build/latest?definitionId=2&branchName=master)

[中文简介](README-ZH.md)

## Example
This site is based on this project ———— [kugga-app](https://kugga.ayang818.top).

## How to start
Clone this repository to your machine.
```bash
git clone git@github.com:Kugga-OSS/Kugga.git

cd Kugga
```

if you just want to test this project in your windows machine, set these environment variables firstly,
```bash
DATABASE_HOST="localhost"        // mysql server host
DATABASE_PORT="3306"             // mysql server port
DATABASE_USERNAME="root"         // mysql server user 
DATABASE_PASSWORD="123"          // mysql server password
REDIS_HOST="localhost"           // redis server id
REDIS_PORT="6379"                // redis server post
JWT_SECRET_KEY="random_string"   // json web token`s secret key
OSS_ENDPOINT=""                  // aliyun OSS Bucket` endpoint
ALI_KEY_ID=""                    // aliyun RAM Key Id
ALI_KEY_SECRET=""                // aliyun RAM Key Secret
BUCKET_NAME=""                   // OSS Bucket name
AVATAR_FOLDER_URL=""          // avatar storage location on ${BUCKET_NAME}，for example
 —— "avatar/"
```

if you want to deploy it to a linux machine, you can modify the ```init_variable.sh```(on the root of the project) to your own setting, and transfer this file to your 
linux machine. Then run the following commands.
```
chmod u+x init_variable.sh

./init_variable.sh
```

## What dependencies you need 
You need 
1. JDK1.8 environment 
2. Maven 3+
3. MySQL database(and create a database named ```kugga```)
4. Redis server

I recommend you could install MySQL and Redis with [docker](https://www.docker.com/) or just use remote linux machine.

## Run it
After finishing all the prepare works, run the following command to init your mysql database and start server !!!
```bash
cd kugga-services

mvn flyway:migrate

cd kugga

mvn clean install

cd kugga-starter/target

java -jar kugga-starter-1.0.0.jar
```
If you don`t want to interactive with [kugga.app](https://github.com/Kugga-OSS/Kugga.App). open ```examlpe/client.html``` in your browser, you could use this demo to interactive with this server.


More development plan, you could see in [here](https://github.com/Kugga-OSS/Prepare-Kugga). 

## License
MIT License.