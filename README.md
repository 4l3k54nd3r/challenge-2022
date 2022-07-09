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
