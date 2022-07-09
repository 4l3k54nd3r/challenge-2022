package inc.test.technical.challenge.models;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentError {

	public enum ErrorType {
		DATABASE,
		NETWORK,
		OTHER
	}

	@JsonProperty("payment_id")
	String paymentId;

	@Enumerated(EnumType.STRING)
	private ErrorType error;

	@JsonProperty("error_description")
	String error_description;

	public PaymentError(String paymentId, ErrorType error, String error_description) {
		this.paymentId = paymentId;
		this.error = error;
		this.error_description = error_description;
	}

	public PaymentError() {
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
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
