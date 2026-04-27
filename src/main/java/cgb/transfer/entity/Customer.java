package cgb.transfer.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class Customer {
	
	@Id
	private Long Id;
	
	private String name;
	
	private String address;
	
	private String LEI;

	@OneToMany(mappedBy = "customer")
	private List<UserCGB> listUsers = new ArrayList<UserCGB>();

	@OneToMany(mappedBy = "customer")
	private List<Account> listRecipientAccounts  = new ArrayList<Account>();
	
	
	@ManyToMany(mappedBy = "recipientAccounts", cascade =
		{CascadeType.PERSIST, CascadeType.MERGE})
	@JsonBackReference
	private List<Account> list = new ArrayList<Account>();

	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLEI() {
		return LEI;
	}

	public void setLEI(String lEI) {
		LEI = lEI;
	}

	public List<UserCGB> getListUsers() {
		return listUsers;
	}

	public void setListUsers(List<UserCGB> listUsers) {
		this.listUsers = listUsers;
	}
	
	public void addUsers(UserCGB userCGB) {
		this.listUsers.add(userCGB);
	}


	public List<Account> getListAccounts() {
		return listAccounts;
	}

	public void setListAccounts(List<Account> listAccounts) {
		this.listAccounts = listAccounts;
	}
	
	public void addAccount(Account account) {
		this.list.add(account);
	}

	public List<Account> getList() {
		return list;
	}

	public void setList(List<Account> list) {
		this.list = list;
	}
	
	public void add(Account account) {
		this.list.add(account);
	}

}
