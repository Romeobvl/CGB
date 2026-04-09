package cgb.transfer.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import cgb.transfer.dto.TransferRequest;

@Entity
public class BatchTransfer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String refLot;
	
	private String sourceAccountNumber;
	
	private String descriptionLot;
	
	private LocalDate date;
	
	@OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Transfer> listTransfer = new ArrayList<>();

    /**
     * Etat du transfer
     */
    private State state;
	
	//Getters & Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRefLot() {
		return refLot;
	}

	public void setRefLot(String refLot) {
		this.refLot = refLot;
	}

	public String getSourceAccountNumber() {
		return sourceAccountNumber;
	}

	public void setSourceAccountNumber(String sourceAccountNumber) {
		this.sourceAccountNumber = sourceAccountNumber;
	}

	public String getDescriptionLot() {
		return descriptionLot;
	}

	public void setDescriptionLot(String descriptionLot) {
		this.descriptionLot = descriptionLot;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<Transfer> getListTransfer() {
		return listTransfer;
	}

	public void setListTransfer(List<Transfer> listTransfer) {
		this.listTransfer = listTransfer;
	}
	
	public void addTransfer(Transfer transfer) {
		//transfer.setBatch(this);
		this.listTransfer.add(transfer);
	}
	
	
}
