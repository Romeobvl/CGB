package cgb.transfer.exception;

public class DateTransferException extends TransferException{
	
	public DateTransferException() {
		super("Invalid transfer: Date prior to today");
	}


}

