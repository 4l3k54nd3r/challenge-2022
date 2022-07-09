package inc.test.technical.challenge.models;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "payments")
public class Payment {
	public enum PaymentType {
		online,
		offline
	}

	@Id
	@Column(name = "payment_id")
	String id;

	@JoinColumn(name = "account_id")
	private int accountId;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_type")
	private PaymentType paymentType;


	@Column(name = "credit_card")
	private String creditCard;

	private BigInteger amount;

	@JsonIgnore
	@Column(name = "created_on")
	private Date createdAt;

	public Payment() {
	}

	public Payment(String id, int accountId, PaymentType paymentType, String creditCard, BigInteger amount,
			Date createdAt) {
		this.id = id;
		this.accountId = accountId;
		this.paymentType = paymentType;
		this.creditCard = creditCard;
		this.amount = amount;
		this.createdAt = createdAt;
	}

	public Payment(String id, int accountId, PaymentType paymentType, String creditCard, BigInteger amount) {
		this.id = id;
		this.accountId = accountId;
		this.paymentType = paymentType;
		this.creditCard = creditCard;
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public BigInteger getAmount() {
		return amount;
	}

	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
