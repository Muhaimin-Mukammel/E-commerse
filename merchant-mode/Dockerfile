FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY merchant-mode merchant-mode
RUN mvn -Pdocker -pl merchant-mode -am -q -B -Dmaven.test.skip=true package

FROM eclipse-temurin:21-jre
WORKDIR /app

RUN useradd --system --uid 10001 app
USER app

COPY --from=build /app/merchant-mode/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]