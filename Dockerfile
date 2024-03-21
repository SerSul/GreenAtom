FROM maven:3.8.4 as cache
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline


FROM maven:3.8.4 as builder
WORKDIR /app
COPY --from=cache /root/.m2 /root/.m2
COPY . .
RUN mvn package -DskipTests


FROM openjdk:21
WORKDIR /app
COPY --from=builder /app/target/GreenAtom-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]
