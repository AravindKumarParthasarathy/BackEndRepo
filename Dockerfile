#define base docker image
FROM openjdk:11
LABEL maintainer="msciq"
ADD target/storage-0.0.1-SNAPSHOT.jar storage.jar
ENTRYPOINT ["java", "-jar", "storage.jar"]
