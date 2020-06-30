FROM openjdk:11-slim-buster
WORKDIR /catalog-app
COPY build/libs/catalog-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java"]
CMD ["-jar", "catalog-0.0.1-SNAPSHOT.jar"]
