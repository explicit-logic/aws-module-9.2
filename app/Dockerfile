FROM amazoncorretto:17-alpine-jdk

EXPOSE 3080

WORKDIR /usr/app
COPY ./target/app-*.jar ./app.jar
RUN test -f app.jar || (echo "ERROR: JAR file not found in target/" && exit 1)

ENTRYPOINT ["java", "-jar", "app.jar"]
