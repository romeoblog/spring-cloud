FROM java:8
MAINTAINER benji "zheng_weilu@163.com"
VOLUME /tmp
ADD ./target/example-boot-admin-*.jar /usr/share/myservice/myservice.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /usr/share/myservice/myservice.jar" ]
EXPOSE 8020