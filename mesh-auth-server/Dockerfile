FROM java:8
MAINTAINER willlu.zheng "zheng_weilu@163.com"
ARG LOG_HOME
ARG JAR_FILE
ENV JAVA_OPTS="${JAVA_OPTS} -DLOG_HOME=${LOG_HOME}"
ADD target/${JAR_FILE} /usr/share/myservice/myservice.jar
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /usr/share/myservice/myservice.jar" ]
VOLUME ${LOG_HOME}
EXPOSE 8010