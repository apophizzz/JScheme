FROM maven:latest
COPY . /jscheme
WORKDIR /jscheme
RUN mvn clean package -Dmaven.test.skip=true && \
    mv ./target/JScheme-*-jar-with-dependencies.jar /jscheme.jar
WORKDIR /
RUN rm -rf /jscheme
ENTRYPOINT ["java", "-jar", "jscheme.jar"]

