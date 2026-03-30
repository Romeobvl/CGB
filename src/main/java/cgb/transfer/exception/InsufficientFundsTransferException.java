package cgb.transfer.exception;

public class InsufficientFundsTransferException extends TransferException{
	
	public InsufficientFundsTransferException() {
		super("Invalid transfer: Insufficient funds in the source account");
	}


}

