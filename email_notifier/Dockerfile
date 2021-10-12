FROM maven:3.6.3-openjdk-16 AS build

COPY ./target/email_notifier-1.0.0-SNAPSHOT-fat.jar /usr/app/email_notifier-1.0.0-SNAPSHOT.jar

EXPOSE 9092

ENTRYPOINT ["java","-jar","/usr/app/email_notifier-1.0.0-SNAPSHOT.jar"]
