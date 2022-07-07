package inc.test.technical.challenge.models;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

	String payment_id;

	String error;

	String error_description;

	public Error() {
	}

	public Error(String payment_id, String error, String error_description) {
		this.payment_id = payment_id;
		this.error = error;
		this.error_description = error_description;
	}

}
