# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar arquivos de configuração do Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Baixar dependências (cache layer)
RUN mvn dependency:go-offline -B

# Copiar código fonte
COPY src ./src

# Build da aplicação
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Criar usuário não-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiar JAR do build stage
COPY --from=build /app/target/demo-*.jar app.jar

# Expor porta
EXPOSE 8080

# Instalar curl para healthcheck
USER root
RUN apk add --no-cache curl
USER spring:spring

# Variáveis de ambiente padrão
ENV SPRING_PROFILES_ACTIVE=production
ENV SERVER_PORT=8080
ENV SPRING_DATASOURCE_URL=jdbc:h2:file:/app/data/banco
ENV SPRING_DATASOURCE_USERNAME=sa
ENV SPRING_DATASOURCE_PASSWORD=
ENV SPRING_H2_CONSOLE_ENABLED=false
ENV SPRING_JPA_SHOW_SQL=false
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8080/api/status || exit 1

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

