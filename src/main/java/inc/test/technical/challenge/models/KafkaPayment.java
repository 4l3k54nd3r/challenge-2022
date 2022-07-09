package inc.test.technical.challenge.models;

import java.math.BigInteger;
import java.util.Date;
import inc.test.technical.challenge.models.Payment.PaymentType;

/*
 * Separate POJO for Kafka.
 * */
public class KafkaPayment{

	String payment_id;

	private int account_id;

	private PaymentType payment_type;

	private String credit_card;

	private BigInteger amount;

	private Date created_on;

	public KafkaPayment(String payment_id, int account_id, PaymentType payment_type, String credit_card,
			BigInteger amount, Date created_on) {
		this.payment_id = payment_id;
		this.account_id = account_id;
		this.payment_type = payment_type;
		this.credit_card = credit_card;
		this.amount = amount;
		this.created_on = created_on;
	}

	public KafkaPayment() {
	}

	public String getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	public PaymentType getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(PaymentType payment_type) {
		this.payment_type = payment_type;
	}

	public String getCredit_card() {
		return credit_card;
	}

	public void setCredit_card(String credit_card) {
		this.credit_card = credit_card;
	}

	public BigInteger getAmount() {
		return amount;
	}

	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}

	public Date getCreated_on() {
		return created_on;
	}

	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}

}
