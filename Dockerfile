FROM eclipse-temurin:8u392-b08-jre

ENV TZ=Asia/Shanghai DB_HOST=pgsql REDIS_HOST=redis JUDGE_HOST=judge0_server

EXPOSE 7000
EXPOSE 8000

VOLUME /tmp
WORKDIR /data

RUN echo '#!/bin/sh' > erupt-entrypoint.sh && echo 'set -ex' >> erupt-entrypoint.sh && \
    echo 'exec /opt/java/openjdk/bin/java $JAVA_OPTS -jar /data/erupt_app.jar "${@}"' >> erupt-entrypoint.sh && \
    chmod +x erupt-entrypoint.sh
RUN echo '#!/bin/sh' > entrypoint.sh && echo 'set -ex' >> entrypoint.sh && \
    echo 'exec /opt/java/openjdk/bin/java $JAVA_OPTS -jar /data/app.jar "$@"' >> entrypoint.sh && \
    chmod +x entrypoint.sh

ARG ERUPT_JAR_FILE=*-erupt/target/*.jar
ARG JAR_FILE=*-system/target/*.jar

COPY ${ERUPT_JAR_FILE} erupt_app.jar
COPY ${JAR_FILE} app.jar

CMD ["./entrypoint.sh"]
