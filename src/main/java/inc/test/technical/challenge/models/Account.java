package inc.test.technical.challenge.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;


@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "accounts")
public class Account {
	@Id
	@Column(name = "account_id")
	private int accountId;

	private String email;

	private java.sql.Date birthdate;

	private Date last_payment_date;

	private Date created_on;

	public Account() {
	}

	public Account(int accountId, String email, java.sql.Date birthdate, Date last_payment_date, Date created_on) {
		this.accountId = accountId;
		this.email = email;
		this.birthdate = birthdate;
		this.last_payment_date = last_payment_date;
		this.created_on = created_on;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public java.sql.Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(java.sql.Date birthdate) {
		this.birthdate = birthdate;
	}

	public Date getLast_payment_date() {
		return last_payment_date;
	}

	public void setLast_payment_date(Date last_payment_date) {
		this.last_payment_date = last_payment_date;
	}

	public Date getCreated_on() {
		return created_on;
	}

	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}


}
