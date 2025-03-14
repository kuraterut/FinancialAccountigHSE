FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY target/FinancialAccountingHSE-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]