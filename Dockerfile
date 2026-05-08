# Estágio 1: Baixar as dependências e compilar o código
FROM maven:3.9.6-eclipse-temurin-25 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

# Estágio 2: Rodar a aplicação
FROM eclipse-temurin:25-jre-alpine
COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]