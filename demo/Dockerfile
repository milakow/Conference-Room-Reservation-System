#1. zaciągnięcie obrazu bazowego
FROM openjdk:17-alpine

#2. wkopiowanie własnego pliku jar
COPY target/demo-0.0.1-SNAPSHOT.jar demo-0.0.1.jar

EXPOSE 8080/tcp

#3. wskazanie punktu startu aplikacji
ENTRYPOINT ["java","-jar", "demo-0.0.1.jar"]

