# Kugga

[![CircleCI](https://circleci.com/gh/Kugga-OSS/Kugga/tree/dev.svg?style=svg)](https://circleci.com/gh/Kugga-OSS/Kugga/tree/dev)

[README-ZH](README-ZH.md)

## How to start
Clone this repository to your machine.
```bash
git clone git@github.com:Kugga-OSS/Kugga.git

cd Kugga
```
Open this project in intellij-idea or eclipse.

Modify some database configurations in ```pom.xml```  located on project root, including these fields, maybe like this
```bash
<host-name>localhost</host-name>
<database-name>kugga</database-name>
<mysql-port>3306</mysql-port>
<username>root</username>
<password>123</password>
<database-url>jdbc:mysql://${host-name}:${mysql-port}/${database-name}</database-url>
<redis-port>6379</redis-port>
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