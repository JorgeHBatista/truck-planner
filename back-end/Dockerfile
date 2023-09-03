FROM openjdk:17-jdk-alpine

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

COPY src/main/java/com/trucks/adapter/db_conector/database_scripts/*.sql /database_scripts/

ENTRYPOINT ["java","-jar","/app.jar", "--spring.datasource.init-script=classpath:/database_scripts/"]