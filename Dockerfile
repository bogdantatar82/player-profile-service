FROM alpine:3.18.3

# Upgrade all packages
RUN apk --update --no-cache upgrade

ENV JAVA_VERSION=17.0.8
ENV BINARY_URL="https://cdn.azul.com/zulu/bin/zulu17.44.15-ca-jre${JAVA_VERSION}-linux_musl_x64.tar.gz"
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH $JAVA_HOME/bin:$PATH

RUN set -eux; \
	  wget -O /tmp/openjdk.tar.gz ${BINARY_URL}; \
	  mkdir -p /opt/java/openjdk; \
	  tar --extract \
	      --file /tmp/openjdk.tar.gz \
	      --directory /opt/java/openjdk \
	      --strip-components 1 \
	      --no-same-owner \
	  ; \
    rm -rf /tmp/openjdk.tar.gz;

RUN echo Verifying install ... \
    && echo java --version \
    && echo Complete. \
RUN apk update && apk add --no-cache curl # used for container healthcheck
ARG JAR_FILE
ADD ${JAR_FILE} /opt/player-profile-service.jar

RUN mkdir -p /etc/hunus /etc/hunus/player-profile-service /var/log/hunus /var/log/hunus/player-profile-service
VOLUME [ \
    "/etc/hunus/player-profile-service", \
    "/var/log/hunus/player-profile-service" \
]
EXPOSE 8888 8787
HEALTHCHECK --interval=5s --timeout=3s CMD curl -f http://localhost:8888/rest/actuator/health

ENTRYPOINT exec java $JAVA_OPTS -jar /opt/player-profile-service.jar
