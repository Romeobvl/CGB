package cgb.transfer.exception;

public class InvalidIbanFormatException extends InvalideIbanException{
	
	public InvalidIbanFormatException () {
		super("IBAN Format is Invalid");
	}
}
