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

	@Column(name = "last_payment_date")
	private Date lastPaymentDate;

	@Column(name = "created_on")
	private Date createdOn;

	public Account() {
	}

	public Account(int accountId, String email, java.sql.Date birthdate, Date lastPaymentDate, Date createdOn) {
		this.accountId = accountId;
		this.email = email;
		this.birthdate = birthdate;
		this.lastPaymentDate = lastPaymentDate;
		this.createdOn = createdOn;
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

	public Date getLastPaymentDate() {
		return lastPaymentDate;
	}

	public void setLastPaymentDate(Date lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
