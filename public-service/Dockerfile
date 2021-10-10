FROM maven:3.6.3-openjdk-16 AS build

COPY ./target/public-service-1.0.0-SNAPSHOT-fat.jar /usr/app/public-service-1.0.0-SNAPSHOT.jar

EXPOSE 9091

ENTRYPOINT ["java","-jar","/usr/app/public-service-1.0.0-SNAPSHOT.jar"]
