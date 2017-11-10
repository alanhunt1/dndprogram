package initcheck.status;

import java.io.Serializable;

public class StatusItem implements Cloneable, Serializable {

	
	private static final long serialVersionUID = 1L;

	protected String description;

	protected String name;

	protected boolean timed = true;

	protected int statusMod;

	protected boolean incap;

	protected boolean fatal;

	protected boolean stacks;

	protected boolean sequential;

	protected int attackMod = 0;

	protected int damageMod = 0;

	protected int saveMod = 0;

	protected int attackModOpp = 0;

	protected int damageModOpp = 0;

	protected int saveModOpp = 0;

	public StatusItem() {

	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public boolean isFatal() {
		return fatal;
	}

	public boolean isTimed() {
		return timed;
	}

	public boolean isIncap() {
		return incap;
	}

	public boolean isStacking() {
		return stacks;
	}

	public boolean isSequential() {
		return sequential;
	}

	public int getStatusMod() {
		return statusMod;
	}

	public int getAttackMod() {
		return attackMod;
	}

	public int getDamageMod() {
		return damageMod;
	}

	public int getSaveMod() {
		return saveMod;
	}

	public int getAttackModOpp() {
		return attackModOpp;
	}

	public int getDamageModOpp() {
		return damageModOpp;
	}

	public int getSaveModOpp() {
		return saveModOpp;
	}

	public void setName(String s) {
		name = s;
	}

	public void setStatusMod(int i) {
		statusMod = i;
	}

	public void setAttackMod(int i) {
		attackMod = i;
	}

	public void setDamageMod(int i) {
		damageMod = i;
	}

	public void setSaveMod(int i) {
		saveMod = i;
	}

	public void setAttackModOpp(int i) {
		attackModOpp = i;
	}

	public void setDamageModOpp(int i) {
		damageModOpp = i;
	}

	public void setSaveModOpp(int i) {
		saveModOpp = i;
	}

	public Object clone() {
		try {
			super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StatusItem clone = new StatusItem();
		clone.description = description;
		clone.name = name;
		clone.timed = timed;
		clone.statusMod = statusMod;
		clone.incap = incap;
		clone.fatal = fatal;
		clone.stacks = stacks;
		clone.sequential = sequential;
		clone.attackMod = attackMod;
		clone.damageMod = damageMod;
		clone.saveMod = saveMod;
		clone.attackModOpp = attackModOpp;
		clone.damageModOpp = damageModOpp;
		clone.saveModOpp = saveModOpp;
		return (Object) clone;
	}
}
