package inc.test.technical.challenge.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
//import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	@Id
	@Getter
	@Setter
	private int account_id;

	private String email;
	@Getter
	@Setter
	private java.sql.Date birthdate;

	@Getter
	@Setter
	private Date last_payment_date;

	@Getter
	@Setter
	private Date created_on;
}
