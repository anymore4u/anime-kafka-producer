# Fase de construção (builder)
FROM maven:3.8.4-openjdk-17 AS builder

# Defina o diretório de trabalho
WORKDIR /app

# Copie o arquivo pom.xml e as dependências do projeto
COPY pom.xml .

# Baixe as dependências do projeto
RUN mvn dependency:go-offline -B

# Copie o código-fonte do projeto
COPY src ./src

# Execute o comando mvn clean package
RUN mvn clean package -DskipTests

# Fase final (runtime)
FROM openjdk:17-jdk-alpine

# Adicione um volume apontando para /tmp
VOLUME /tmp

# Torne a porta 8080 disponível para o mundo exterior
EXPOSE 8080

# Defina variáveis de ambiente
ENV SPRING_PROFILES_ACTIVE=prod

# Copie o JAR executável da fase de construção para a imagem final
COPY --from=builder /app/target/animeproducer-0.0.1-SNAPSHOT.jar animeproducer-app.jar

# Execute o JAR
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/animeproducer-app.jar"]
