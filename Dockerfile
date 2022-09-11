From openjdk:11
copy ./target/company-service-1.0.jar company-service-1.0.jar
CMD ["java","-jar","company-service-1.0.jar"]