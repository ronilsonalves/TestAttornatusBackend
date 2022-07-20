FROM amazoncorretto:11.0.16-alpine3.15
WORKDIR /apiPessoas
EXPOSE 80
COPY target/apiPessoas.jar /apiPessoas/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]