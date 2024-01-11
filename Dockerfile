FROM openjdk:17

WORKDIR /app

COPY build/libs/grocery-service-0.0.1-SNAPSHOT.jar /app/grocery-service-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "grocery-service-0.0.1-SNAPSHOT.jar"]