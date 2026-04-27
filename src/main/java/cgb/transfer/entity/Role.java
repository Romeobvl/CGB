package cgb.transfer.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Role {

	@Id
	private Long Id;
	
	private String name;
	

	@OneToMany(mappedBy = "role")
	private List<UserCGB> listUsers = new ArrayList<UserCGB>();

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

	public List<UserCGB> getListUsers() {
		return listUsers;
	}

	public void setListUsers(List<UserCGB> listUsers) {
		this.listUsers = listUsers;
	}
	
	
	
	
}
