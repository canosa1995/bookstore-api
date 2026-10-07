# Usa uma imagem oficial com Java 21 Runtime
FROM eclipse-temurin:21-jre-ubi10-minimal

# Define /app como diretório de trabalho dentro do container
WORKDIR /app

# Copia o JAR que já geramos para dentro da imagem
COPY target/bookstore-api-0.0.1-SNAPSHOT.jar app.jar

# Documenta que a aplicação utiliza a porta 8080.
EXPOSE 8080

# Comando executado quando o container inciar.
ENTRYPOINT ["java", "-jar", "app.jar"]
