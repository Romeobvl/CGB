package cgb.transfer.entity;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.*;

@Entity
public class AccountHistorical {
	
	@Id
	@GeneratedValue
	private Long id;

	private String accountNumber;

	private LocalDate date;
	
	private String action;
	
	private String status;

	
	public String getAccountNumber() {
		return accountNumber;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getAction() {
		return action;
	}

	public String getStatus() {
		return status;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setDate(LocalDate localDate) {
		this.date = localDate;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
