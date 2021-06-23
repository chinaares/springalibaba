# 环境
FROM  williamyeh/java8:latest

# 作者信息
MAINTAINER Igor<"123456@qq.com">

# 设置环境变量
#ENV <key> <value>  #<key>之后的所有内容均会被视为其<value>的组成部分，因此，一次只能设置一个变量
#ENV <key>=<value> ...  #可以设置多个变量，每个变量为一个"<key>=<value>"的键值对，如果<key>中包含空格，可以使用\来进行转义，也可以通过""来进行标示；另外，反斜线也可以用于续行
ENV TZ=Asia/Shanghai LANG=en_US.UTF-8 LANGUAGE=en_US.UTF-8 LC_ALL=en_US.UTF-8

#ADD：将本地文件添加到容器中，tar类型文件会自动解压(网络压缩资源不会被解压)，可以访问网络资源，类似wget
#格式：
#    ADD <src>... <dest>
#    ADD ["<src>",... "<dest>"] 用于支持包含空格的路径
#示例：
#    ADD hom* /mydir/          # 添加所有以"hom"开头的文件
#    ADD hom?.txt /mydir/      # ? 替代一个单字符,例如："home.txt"
#    ADD test relativeDir/     # 添加 "test" 到 `WORKDIR`/relativeDir/
#    ADD test /absoluteDir/    # 添加 "test" 到 /absoluteDir/

#COPY：功能类似ADD，但是是不会自动解压文件，也不能访问网络资源
COPY target/nacos-consumer-0.0.1-SNAPSHOT.jar /work/app.jar
COPY src/main/resources/ /work/config/nacos-consumer/
COPY lib/* /work/lib

# RUN：构建镜像时执行的命令
#
# RUN用于在镜像容器中执行命令，其有以下两种命令执行方式：
# 格式：
#    RUN <command>
# exec执行
# 格式：
#    RUN ["executable", "param1", "param2"]
# 示例：
#    RUN ["executable", "param1", "param2"]
#    RUN apk update
#    RUN ["/etc/execfile", "arg1", "arg1"]
#注：
#　　RUN指令创建的中间镜像会被缓存，并会在下次构建中使用。如果不想使用这些缓存镜像，可以在构建时指定--no-cache参数，如：docker build --no-cache
RUN mkdir -p /work/conf && echo "configPath=/work/config/nacos-consumer" > /work/conf/configPath.conf
# WORKDIR：工作目录，类似于cd命令
#
# 格式：
#    WORKDIR /path/to/workdir
# 示例：
#    WORKDIR /a  (这时工作目录为/a)
#    WORKDIR b  (这时工作目录为/a/b)
#    WORKDIR c  (这时工作目录为/a/b/c)
# 注：
#　　通过WORKDIR设置工作目录后，Dockerfile中其后的命令RUN、CMD、ENTRYPOINT、ADD、COPY等命令都会在该目录下执行。在使用docker run运行容器时，可以通过-w参数覆盖构建时所设置的工作目录。
WORKDIR /work

#EXPOSE：指定于外界交互的端口
#
# 格式：
#    EXPOSE <port> [<port>...]
# 示例：
#    EXPOSE 80 443
#    EXPOSE 8080
#    EXPOSE 11211/tcp 11211/udp
# 注：
#　　EXPOSE并不会让容器的端口访问到主机。要使其可访问，需要在docker run运行容器时通过-p来发布这些端口，或通过-P参数来发布EXPOSE导出的所有端口
EXPOSE 8077

#ENTRYPOINT：配置容器，使其可执行化。配合CMD可省去"application"，只使用参数。
#
# 格式：
#    ENTRYPOINT ["executable", "param1", "param2"] (可执行文件, 优先)
#    ENTRYPOINT command param1 param2 (shell内部命令)
# 示例：
#    FROM ubuntu
#    ENTRYPOINT ["top", "-b"]
#    CMD ["-c"]
# 注：
#　　　ENTRYPOINT与CMD非常类似，不同的是通过docker run执行的命令不会覆盖ENTRYPOINT，而docker run命令中指定的任何参数，都会被当做参数再次传递给ENTRYPOINT。Dockerfile中只允许有一个ENTRYPOINT命令，多指定时会覆盖前面的设置，而只执行最后的ENTRYPOINT指令。
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-classpath","lib/*.jar","-jar","app.jar"]

#CMD：构建容器后调用，也就是在容器启动时才进行调用。
#
# 格式：
#    CMD ["executable","param1","param2"] (执行可执行文件，优先)
#    CMD ["param1","param2"] (设置了ENTRYPOINT，则直接调用ENTRYPOINT添加参数)
#    CMD command param1 param2 (执行shell内部命令)
# 示例：
#    CMD echo "This is a test." | wc -
#    CMD ["/usr/bin/wc","--help"]
# 注：
# 　　CMD不同于RUN，CMD用于指定在容器启动时所要执行的命令，而RUN用于指定镜像构建时所要执行的命令。
CMD ["-Xmx512M","-Xms256M","-Xmn192M","-XX:MaxMetaspaceSize=192M","-XX:MetaspaceSize=192M"]

#VOLUME：用于指定持久化目录
#
# 格式：
#    VOLUME ["/path/to/dir"]
# 示例：
#    VOLUME ["/data"]
#    VOLUME ["/var/www", "/var/log/apache2", "/etc/apache2"
# 注：
#　　一个卷可以存在于一个或多个容器的指定目录，该目录可以绕过联合文件系统，并具有以下功能：
# 1 卷可以容器间共享和重用
# 2 容器并不一定要和其它容器共享卷
# 3 修改卷后会立即生效
# 4 对卷的修改不会对镜像产生影响
# 5 卷会一直存在，直到没有任何容器在使用它

