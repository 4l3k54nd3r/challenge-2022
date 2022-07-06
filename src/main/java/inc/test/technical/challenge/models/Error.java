package inc.test.technical.challenge.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

	@Getter
	@Setter
	String payment_id;

	@Getter
	@Setter
	String error;

	@Getter
	@Setter
	String error_description;

}
