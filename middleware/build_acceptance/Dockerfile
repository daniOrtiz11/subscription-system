FROM maven:3.6.3-openjdk-16 AS build

COPY ./src /usr/app/src
COPY ./pom.xml /usr/app/pom.xml

EXPOSE 9092

ENTRYPOINT ["mvn","clean","test", "-f", "/usr/app/pom.xml"]
