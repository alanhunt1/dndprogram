package initcheck.status;

public class StatStunned extends StatusItem {

	private static final long serialVersionUID = 1L;

	public StatStunned() {
		statusMod = 1;
		setDefaults();
	}

	public StatStunned(int i) {
		statusMod = i;
		setDefaults();
	}

	public StatStunned(int i, boolean s) {
		statusMod = i;
		setDefaults();
		sequential = s;
	}

	public void setDefaults() {
		incap = true;
		timed = true;
		stacks = true;
		name = "Stunned";
		description = "Creature is unable to move or attack.";
	}
}
