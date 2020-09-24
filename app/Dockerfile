FROM openjdk:8-jre-slim

EXPOSE 8080 8008
EXPOSE 5000

ENV JAVA_TOOL_OPTIONS "-Dcom.sun.management.jmxremote.ssl=false \
 -Dcom.sun.management.jmxremote.authenticate=false \
 -Dcom.sun.management.jmxremote.port=5000 \
 -Dcom.sun.management.jmxremote.rmi.port=5000 \
 -Dcom.sun.management.jmxremote.host=0.0.0.0 \
 -Djava.rmi.server.hostname=0.0.0.0"

COPY scripts/docker-entrypoint.sh .

RUN chmod a+x docker-entrypoint.sh

COPY build/libs/app-*.jar boilerplate.jar

ENTRYPOINT ["./docker-entrypoint.sh"]

HEALTHCHECK --interval=3s --timeout=2s --retries=100 CMD curl -f http://localhost:8080/actuator/health || exit 1
