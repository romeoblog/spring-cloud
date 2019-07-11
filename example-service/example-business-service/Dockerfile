FROM java:8
MAINTAINER benji "zheng_weilu@163.com"
VOLUME /tmp
#ENV LOG_HOME=/var/log/example
ADD ./target/example-business-service-*.jar /usr/share/myservice/myservice.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /usr/share/myservice/myservice.jar" ]
EXPOSE 8650