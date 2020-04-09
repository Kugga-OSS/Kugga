# Kugga

[![CircleCI](https://circleci.com/gh/Kugga-OSS/Kugga/tree/dev.svg?style=svg)](https://circleci.com/gh/Kugga-OSS/Kugga/tree/dev)

[README-EN](README.md)

## 样例
这个网站就是基于这个项目搭建的 ———— [kugga-app](https://kugga.ayang818.top)。

## 如何开始
把这个项目拉到你的本地
```bash
git clone git@github.com:Kugga-OSS/Kugga.git

cd Kugga
```
使用intellij-idea或者eclipse打开这个项目

如果你只是想在你的windows机器上测试这个项目，你需要先设置好下面这些环境变量。
```bash
DATABASE_HOST="localhost"        // mysql server host
DATABASE_PORT="3306"             // mysql server port
DATABASE_USERNAME="root"         // mysql server user 
DATABASE_PASSWORD="123"          // mysql server password
REDIS_HOST="localhost"           // redis server id
REDIS_PORT="6379"                // redis server post
JWT_SECRET_KEY="random_string"   // json web token`s secret key
OSS_ENDPOINT=""                  // 阿里云OSS 对应Bucket所在的地域域名
ALI_KEY_ID=""                    // 阿里云RAM Key Id
ALI_KEY_SECRET=""                // 阿里云RAM Key Secret
BUCKET_NAME=""                   // OSS Bucket桶的名称
AVATAR_FOLDER_URL=""             // 头像文件存储路径，参考 "avatar/"
```

如果你想要在linux机器上运行```kugga```，你可以修改位于根目录的```init_variable.sh```文件里的配置，然后将这个文件传输到你的linux机器，
并运行以下命令
```bash
chmod u+x init_variable.sh

./init_variable.sh
```

## 需要安装的依赖
你需要
1. JDK1.8 environment 
2. Maven 3+
3. MySQL database(里面需要创建一个叫做 ```kugga```的数据库)
4. Redis server

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