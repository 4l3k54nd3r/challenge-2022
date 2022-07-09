# Challenge

## :computer: How to execute
``./mvnw package``

``java -jar target/challenge-0.0.2.jar``
## :memo: Notes

Requires Java 17 and for the provided docker-compose to be up.

Thw @KafkaListener in the Consumer component receives payments and processes them via PaymentService.

If the validation API takes longer than 1 second to reply then the payment is considered invalid.

## :pushpin: Things to improve upon

- Unit testing

- Adding it to the docker-compose. I tried using mvn spring-boot:build-image, but when testing it with

``docker run --network docker_kafka-net -e POSTGRES_HOST='172.19.0.2' -e KAFKA-HOST='172.19.0.4' -e RESTAPI_HOST='172.19.0.5' docker.io/library/challenge:0.0.2``

It's my guess that the api-producer doesn't accept connections from non-localhost. It connects to both the Postgres and Kafka hosts but fails once it tries to check validity or submit a new log entry.
