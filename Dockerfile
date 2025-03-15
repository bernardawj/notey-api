FROM maven:3.9.9-eclipse-temurin-11 AS build
COPY . /build
WORKDIR /build
RUN cp src/main/resources/application.release.properties src/main/resources/application.properties
RUN mvn clean install -DskipTests

FROM eclipse-temurin:11-alpine AS run
COPY --from=build /build/target/notey-*.jar /app/app.jar
ENV JWT_SECRET=VERY_SECURE_TOKEN
EXPOSE 4444
ENTRYPOINT ["java", "-jar", "/app/app.jar"]