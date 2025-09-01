FROM openjdk:8
COPY taxone-mapper-api.jar taxone.mv.db /app/
WORKDIR /app/
CMD ["java", "-jar", "taxone-mapper-api.jar"]

