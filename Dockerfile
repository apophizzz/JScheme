FROM openjdk:8-jre
COPY /target/JScheme-1.0-jar-with-dependencies.jar jscheme.jar
ENTRYPOINT ["java", "-jar", "jscheme.jar"]

