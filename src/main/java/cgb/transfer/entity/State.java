package cgb.transfer.entity;

public enum State {
	WAITING("waiting"),
	FAILURE("failure"),
	SUCCESS("success"),
	CANCELLED("cancelled"),
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
