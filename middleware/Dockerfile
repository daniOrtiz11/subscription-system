FROM maven:3.6.3-openjdk-16 AS build

COPY ./target/middleware-1.0.0-SNAPSHOT-fat.jar /usr/app/middleware-1.0.0-SNAPSHOT.jar

EXPOSE 9092

ENTRYPOINT ["java","-jar","/usr/app/middleware-1.0.0-SNAPSHOT.jar"]
