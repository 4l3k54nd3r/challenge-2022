# Challenge

## :computer: How to execute
mvn clean package
java -jar target/challenge-0.0.2.jar
## :memo: Notes

Note: This solution uses java 17.

It's fairly simple, the @KafkaListener in the Consumer component receives payments and sends for processing via PaymentService.

If the payment type is online the server will use a WebClient to check if the payment is valid, then check if there's a matching account_id. If the payment is offline it skips the WebClient check.

## :pushpin: Things to improve

Figure out how to convert payments from Kafka to Payment objects without the separate POJO.
