package cgb.transfer.exception;

public class AmountTransferException extends TransferException{
	
	public AmountTransferException() {
		super("Invalid transfer: Negative or null amount");
	}


}

