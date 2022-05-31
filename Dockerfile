FROM openjdk:8
EXPOSE 8080
ADD target/order-service.jar test-app.jar 
ENTRYPOINT ["java","-jar","/test-app.jar"]
