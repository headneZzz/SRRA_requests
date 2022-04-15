FROM gradle:7.4.2-jdk17-alpine as gradle
COPY . /home/gradle/project/srra
WORKDIR /home/gradle/project/srra
RUN gradle build

FROM openjdk:17-jdk
COPY --from=gradle /home/gradle/project/srra/build/libs/srra*.jar /srra.jar
EXPOSE 8080
CMD ["java", "-jar", "/srra.jar"]
