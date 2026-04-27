package cgb.transfer.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;


/**
 * Classe permettant le mapping d'un compte entre la DB et l'API.
 */
@Entity
public class Account {

	/**
	 * L'identifiant unique d'un compte.
	 */
	@Id
	private String accountNumber;

	/**
	 * Le solde du compte.
	 */
	private Double solde;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name = "recipient_accounts",
			joinColumns = @JoinColumn(name = "accountNumber"),
			inverseJoinColumns = @JoinColumn(name = "account_id")
			)
	private List<Customer> recipientAccounts = new ArrayList<Customer>();


	// Getters and Setters obtenus grace à Data

	public Double getSolde() {
		return solde;
	}

	public void setSolde(Double solde) {
		this.solde = solde;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Customer> getRecipientAccounts() {
		return recipientAccounts;
	}

	public void setRecipientAccounts(List<Customer> recipientAccounts) {
		this.recipientAccounts = recipientAccounts;
	}
	
	public void addCustomer(Customer customer) {
		this.recipientAccounts.add(customer);
	}

	
}