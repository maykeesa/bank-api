FROM maven as build

WORKDIR /bank
COPY . .
RUN mvn clean package -Dspring.profiles.active=dev

FROM ghcr.io/graalvm/jdk-community:17

WORKDIR /sporthub
COPY --from=build /bank/target/bank-api-0.0.1-SNAPSHOT.jar ./app.jar

CMD java -jar app.jar
