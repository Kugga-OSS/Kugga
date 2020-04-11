FROM maven:3-jdk-8-alpine

# 工作目录
ENV HOME=/app

# 用于构建缓存层的临时目录
ENV LIB=/app/lib

# 替换maven配置
COPY mvn_setting.xml /usr/share/maven/conf/settings.xml

# 构建镜像缓存依赖层
RUN mkdir -p $LIB \
    && mkdir -p $LIB/kugga-netty \
    && mkdir -p $LIB/kugga-services \
    && mkdir -p $LIB/kugga-starter \
    && mkdir -p $LIB/kugga-utils

COPY pom.xml $LIB/pom.xml
COPY kugga-netty/pom.xml $LIB/kugga-netty/pom.xml
COPY kugga-services/pom.xml $LIB/kugga-services/pom.xml
COPY kugga-starter/pom.xml $LIB/kugga-starter/pom.xml
COPY kugga-utils/pom.xml $LIB/kugga-utils/pom.xml

WORKDIR $LIB

RUN mvn clean install

COPY /. $HOME

WORKDIR $HOME

RUN mvn clean install

RUN pwd

RUN ls -al

# 解决时区错误问题
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/${TZ} /etc/localtime && echo ${TZ} > /etc/timezone

ARG JAR_FILE=kugga-starter/target/kugga-starter-1.0.0.jar

RUN pwd

RUN ls -al

COPY $JAR_FILE app.jar

EXPOSE 5555
EXPOSE 10086
# 这里需要在启动容器时将必备环境变量通过-e参数传进去
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]