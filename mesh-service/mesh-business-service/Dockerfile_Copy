FROM java:8
MAINTAINER willlu.zheng "willlu.zheng@163.com"
VOLUME /tmp
ADD ./target/mesh-business-service-*.jar /usr/share/myservice/myservice.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /usr/share/myservice/myservice.jar" ]
EXPOSE 8610