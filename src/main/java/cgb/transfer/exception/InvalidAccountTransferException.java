package cgb.transfer.exception;

public class InvalidAccountTransferException extends TransferException{
	
	public InvalidAccountTransferException(String account) {
		super("Invalid transfer: " + account + " account doesn't exist");
	}


}

