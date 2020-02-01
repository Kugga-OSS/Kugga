# Kugga

[![CircleCI](https://circleci.com/gh/Kugga-OSS/Kugga/tree/dev.svg?style=svg)](https://circleci.com/gh/Kugga-OSS/Kugga/tree/dev)

[README-EN](README.md)

## 如何开始
把这个项目拉到你的本地
```bash
git clone git@github.com:Kugga-OSS/Kugga.git

cd Kugga
```
使用intellij-idea或者eclipse打开这个项目

修改位于项目根目录的```pom.xml```里的一些配置，包括以下字段的内容
```bash
<host-name>localhost</host-name>
<database-name>kugga</database-name>
<mysql-port>3306</mysql-port>
<username>root</username>
<password>123</password>
<database-url>jdbc:mysql://${host-name}:${mysql-port}/${database-name}</database-url>
<redis-port>6379</redis-port>
```

## 需要安装的依赖
你需要
1. JDK1.8 environment 
2. MySQL database(里面需要创建一个叫做 ```kugga```的数据库)
3. Redis server

我推荐你使用[docker](https://www.docker.com)来安装MySQL和redis 或者直接使用你的远程Linux主机

## 运行
在做完上述的的工作后，运行以下脚本来初始化数据库并运行此服务 ！！！
```bash
cd kugga-services

mvn flyway:migrate // 执行数据库自动化脚本

cd kugga // 回根目录

mvn clean install // 使用maven打包

cd kugga-starter/target

java -jar kugga-starter-1.0.0.jar // 运行打出来的可执行jar包
```
如果你不想使用[kugga.app](https://github.com/Kugga-OSS/Kugga.App)来交互。在浏览器中打开```example/client.html```, 你可以使用这个html demo来和服务端做交互，虽然很简陋就是了~

## 协议
MIT 协议

更多的开发计划，你可以在[这里](https://github.com/Kugga-OSS/Prepare-Kugga)看到。