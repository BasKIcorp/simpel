FROM ubuntu:latest
LABEL authors="https://t.me/Magicheskaya_Pechenka"
FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /backend

COPY backend/pom.xml .
RUN mvn dependency:go-offline

COPY backend/src ./src

RUN mvn package -DskipTests

FROM openjdk:17-jdk-alpine

RUN apk add --no-cache fontconfig freetype ttf-dejavu

COPY --from=build /backend/target/pumpingUnits-0.0.1-SNAPSHOT.jar /backend/backend.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/backend/backend.jar"]