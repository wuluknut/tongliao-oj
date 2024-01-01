FROM eclipse-temurin:21.0.1_12-jre

ENV DB_HOST=pgsql

EXPOSE 8000

VOLUME /tmp
WORKDIR /data

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar ${0} ${@}"]
