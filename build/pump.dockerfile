FROM ubuntu:latest
LABEL authors="https://t.me/Magicheskaya_Pechenka"
FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /backend

COPY PumpsSimple/pom.xml .
RUN mvn dependency:go-offline

COPY PumpsSimple/src ./src

RUN mvn package -DskipTests

FROM openjdk:17-jdk-alpine

RUN apk add --no-cache fontconfig freetype ttf-dejavu

COPY --from=build /backend/target/PumpsSimple-0.0.1-SNAPSHOT.jar /backend/backend.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/backend/backend.jar"]