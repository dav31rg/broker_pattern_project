FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -B dependency:go-offline
COPY src ./src
RUN mvn -q -B package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/aip-notification-consumer-*.jar app.jar
EXPOSE 8083
HEALTHCHECK --interval=5s --timeout=3s --start-period=25s --retries=12 \
  CMD bash -c 'exec 3<>/dev/tcp/127.0.0.1/8083 && exit 0 || exit 1'
ENTRYPOINT ["java", "-jar", "app.jar"]
