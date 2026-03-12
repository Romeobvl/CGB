package cgb.transfer.exception;

public class InvalidUnCheckableIbanException extends InvalideIbanException{


	public InvalidUnCheckableIbanException () {
		super("IBAN is Uncheckable");
	}
}
