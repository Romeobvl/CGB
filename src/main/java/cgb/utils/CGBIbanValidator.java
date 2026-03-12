package cgb.utils;

import org.apache.commons.validator.routines.IBANValidator;

import cgb.transfer.exception.InvalidIbanFormatException;


public class CGBIbanValidator {


	private static CGBIbanValidator instance = null;

	private CGBIbanValidator() {
	}

	public static CGBIbanValidator getInstanceValidator() {
		if ( instance == null ) {
			instance = new CGBIbanValidator();
		}

		return instance;
	}


	//	• Code du pays : Deux lettres représentant le pays (par exemple, "FR" pour la France).
	//	• Chiffres de contrôle : Deux chiffres utilisés pour la validation de l'IBAN.
	//	• BBAN (Basic Bank Account Number) : Le numéro de compte bancaire de base, dont la structure
	//	varie selon le pays. Il peut inclure le code de la banque, le code de l'agence, et le numéro de
	//	compte.
	public boolean isIbanStructureValide(String iban) throws InvalidIbanFormatException {

			for(int i = 0; i<iban.length(); i++) {
				if(i<=1) {
					if(!Character.isLetter(iban.charAt(i))) { throw new InvalidIbanFormatException(); }
				}else if(i<=3) {
					if(!Character.isDigit(iban.charAt(i))) { throw new InvalidIbanFormatException(); }
				} else {	
					if(!Character.isDigit(iban.charAt(i)) && !Character.isLetter(iban.charAt(i))) { throw new InvalidIbanFormatException(); }
				}
			}
			return true;
	}

	public boolean isIbanValide(String iban) {
		IBANValidator validator = IBANValidator.getInstance();
		return validator.isValid(iban);
	}

	public String getCountryCode(String iban) {
		return iban.substring(0, 1);
	}

	public String getCheckDigits(String iban) {
		return iban.substring(2, 3);
	}

	public String getBBAN(String iban) {
		return iban.substring(4);
	}
}
