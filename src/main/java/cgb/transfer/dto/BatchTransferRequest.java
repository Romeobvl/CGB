package cgb.transfer.dto;

import java.util.List;

import cgb.transfer.entity.Transfer;


/**
 * La classe de DTO d'un transfert.
 */
public class BatchTransferRequest {
	
    private String sourceAccountNumber;
  
    private String descriptionLot;
    
    private List<TransferRequest> listTransfer;

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

	public List<TransferRequest> getListTransfer() {
		return listTransfer;
	}

	public void setListTransfer(List<TransferRequest> listTransfer) {
		this.listTransfer = listTransfer;
	}



    
}
