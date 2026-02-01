FROM maven:3.9-eclipse-temurin-11 AS build
WORKDIR /app

COPY pom.xml ./
COPY .mvn/ .mvn/
COPY mvnw mvnw
COPY mvnw.cmd mvnw.cmd
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw
ENV MAVEN_CONFIG=
RUN ./mvnw -q -DskipTests dependency:go-offline

COPY src/ src/
RUN ./mvnw -q -DskipTests package

FROM eclipse-temurin:11-jre
WORKDIR /app

COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
