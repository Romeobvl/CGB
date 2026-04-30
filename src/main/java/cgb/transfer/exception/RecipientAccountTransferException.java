package cgb.transfer.exception;

public class RecipientAccountTransferException extends TransferException {

	public RecipientAccountTransferException(String sourceAccountNumber, String destinationAccountNumber) {
		super("Transfer failed: Account " + destinationAccountNumber + " is not a registered beneficiary of account " + sourceAccountNumber);
	}


}
