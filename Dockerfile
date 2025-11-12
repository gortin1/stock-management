FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn -DskipTests clean install

# --- ESTÁGIO 2: Run (Execução) ---
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copia o .jar com o nome exato
COPY --from=builder /build/target/stock-management-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# --- CORREÇÃO AQUI ---
# Removemos a barra "/" antes de "app.jar"
ENTRYPOINT ["java", "-jar", "app.jar"]