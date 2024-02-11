FROM maven:3.8.5-openjdk-17-slim AS builder

WORKDIR /app

COPY pom.xml .

ENV PG_USER=$PG_USER
ENV PG_PASS=$PS_PASS

RUN mvn dependency:go-offline

COPY src src

RUN mvn clean package

FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/pandatribe-0.0.1-SNAPSHOT.jar panda.jar

EXPOSE 8080

ENV PG_USER=$PG_USER
ENV PG_PASS=$PG_PASS

CMD ["java", "-jar", "panda.jar"]