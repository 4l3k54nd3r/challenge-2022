package inc.test.technical.challenge.repos;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import inc.test.technical.challenge.models.Account;

public interface AccountRepository extends CrudRepository<Account, Long>{
	Optional<Account> findByAccountId(int accountId);
}
