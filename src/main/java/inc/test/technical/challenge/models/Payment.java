package inc.test.technical.challenge.models;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "payments")
@ToString(includeFieldNames = true)
public class Payment{
	@Id
	String payment_id;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

	private String payment_type;

	private String credit_card;

	private BigInteger amount;

	@JsonIgnore
	private Date created_on;


	public String getCredit_card() {
		return credit_card;
	}

	public void setCredit_card(String credit_card) {
		this.credit_card = credit_card;
	}

}
