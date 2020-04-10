FROM maven:3-jdk-8-alpine

# 工作目录
ENV HOME=/app

# 用于构建缓存层的临时目录
ENV LIB=/app/lib

COPY . $HOME
# 替换maven配置
COPY ./mvn_setting.xml /usr/share/maven/conf/settings.xml

WORKDIR $LIB

# 构建镜像缓存依赖层
RUN mkdir -p $LIB
    && copy pom.xml $LIB/
    && copy kugga-netty/pom.xml $LIB/kugga-netty/
    && copy kugga-services/pom.xml $LIB/kugga-services/
    && copy kugga-starter/pom.xml $LIB/kugga-stater/
    && copy kugga-utils/pom.xml $LIB/kugga-utils/
    && mvn dependency:resolve

WORKDIR $HOME

RUN mvn package

# 解决时区错误问题
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/${TZ} /etc/localtime && echo ${TZ} > /etc/timezone

ARG JAR_FILE=kugga-starter/target/*.jar

COPY ${JAR_FILE} app.jar
# 这里需要在启动容器时将必备环境变量通过-e参数传进去
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]