# Etapa 1: Usando a imagem base do Maven para construir o projeto
FROM maven:3.8.5-openjdk-17 AS build

# Definindo o diretório de trabalho no contêiner para o build
WORKDIR /app

# Copiando o arquivo pom.xml e baixando as dependências
COPY pom.xml .

# Baixando as dependências sem construir
RUN mvn dependency:go-offline

# Copiando o restante do código para o contêiner
COPY src ./src

# Executando o build do projeto
RUN mvn clean package -DskipTests

# Etapa 2: Usando uma imagem base do Java para rodar a aplicação
FROM openjdk:17-jdk-alpine

# Definindo o diretório de trabalho no contêiner para a execução
WORKDIR /demo_copia

# Copiando o arquivo JAR gerado na etapa anterior para o contêiner
COPY --from=build /app/target/CP5_JAVA-P2-0.0.1-SNAPSHOT.jar demo_copia.jar

# Expondo a porta 8080 (ou outra porta que seu Spring Boot usa)
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "demo_copia.jar"]
