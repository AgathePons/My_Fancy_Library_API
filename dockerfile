FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
#ARG JAR_FILE
#COPY scripts/run.sh .
#COPY data/data_ddl.sql data_ddl.sql
#COPY data/data_dml.sql data_dml.sql
COPY target/*.jar app.jar
#RUN chmod +x run.sh
#ENTRYPOINT ["./run.sh"]
ENTRYPOINT ["java","-jar","/app.jar"]