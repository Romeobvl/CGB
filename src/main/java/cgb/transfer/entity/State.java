package cgb.transfer.entity;

public enum State {
	WAITING("waiting"),
	FAILURE("failure"),
	SUCCESS("success"),
	CANCELED("canceled"),
	RECEIVED("received"),
	CLOSED("closed");

	private final String nom;

	private State(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

}
