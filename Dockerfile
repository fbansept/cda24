FROM maven:3.9.6-eclipse-temurin-17 AS build

RUN mkdir -p /root/.m2 \
    && mkdir /root/.m2/repository
# Copy maven settings, containing repository configurations
COPY ../.m2/settings.xml /root/.m2

RUN mkdir /project

COPY . /project

WORKDIR /project

RUN mvn clean package -P production,docker

FROM eclipse-temurin:17-jdk-alpine

RUN mkdir /app

RUN addgroup -g 1001 -S mnsgroup

RUN adduser -S mns -u 1001

COPY --from=build /project/target/cda24-0.0.1-SNAPSHOT.jar /app/cda24.jar

WORKDIR /app

RUN chown -R mns:mnsgroup /app

CMD java $JAVA_OPTS -jar cda24.jar