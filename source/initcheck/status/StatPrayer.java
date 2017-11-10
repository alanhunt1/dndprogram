package initcheck.status;

public class StatPrayer extends StatusItem {

	private static final long serialVersionUID = 1L;

	public StatPrayer(int i) {
		statusMod = i;
		setDefaults();
	}

	public String getName() {
		return "Prayer";
	}

	public void setDefaults() {
		stacks = true;
		incap = false;
		timed = true;
		name = "Prayer";
		description = "Friendly Attacks are at +1/+1/+1, Enemy Attacks at -1/-1/-1";
		attackMod = 1;
		damageMod = 1;
		saveMod = 1;
		attackModOpp = -1;
		damageModOpp = -1;
		saveModOpp = -1;
	}
}
