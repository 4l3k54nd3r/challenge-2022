package inc.test.technical.challenge.models;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentError {

	public enum ErrorType {
		DATABASE,
		NETWORK,
		OTHER
	}

	String payment_id;

	@Enumerated(EnumType.STRING)
	private ErrorType error;

	String error_description;

	public PaymentError(String payment_id, ErrorType error, String error_description) {
		this.payment_id = payment_id;
		this.error = error;
		this.error_description = error_description;
	}

	public PaymentError() {
	}

	public String getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}

	public ErrorType getError() {
		return error;
	}

	public void setError(ErrorType error) {
		this.error = error;
	}

	public String getError_description() {
		return error_description;
	}

	public void setError_description(String error_description) {
		this.error_description = error_description;
	}

}
