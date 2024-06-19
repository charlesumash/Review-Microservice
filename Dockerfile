FROM openjdk:17-jdk
COPY target/review-ms.jar .
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "review-ms.jar"]