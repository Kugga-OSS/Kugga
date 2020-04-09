FROM maven:3-jdk-8-alpine

COPY ./ /app

COPY ./mvn_setting.xml /usr/share/maven/conf/settings.xml

WORKDIR /app

RUN mvn package

# 解决时区错误问题
RUN ln -snf /usr/share/zoneinfo/${TZ} /etc/localtime && echo ${TZ} > /etc/timezone

ARG JAR_FILE=kugga-starter/target/*.jar

COPY ${JAR_FILE} app.jar
# 这里需要在启动容器时将必备环境变量通过-e参数传进去
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]