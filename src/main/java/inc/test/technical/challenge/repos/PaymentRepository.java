package inc.test.technical.challenge.repos;

import org.springframework.data.repository.CrudRepository;

import inc.test.technical.challenge.models.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Long>{
	Boolean existsById(String id);

}
