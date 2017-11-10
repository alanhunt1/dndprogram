package initcheck;

import initcheck.status.StatusItem;

import java.io.Serializable;
import java.util.Vector;

@SuppressWarnings("serial")
public class Status implements Serializable, Cloneable {

	private Vector<StatusItem> statVector = new Vector<StatusItem>();

	private boolean spellEnd = false;

	private Vector<StatusItem> endSpells = new Vector<StatusItem>();

	private InitLogger logger = new InitLogger(this);

	public Status() {
		statVector = new Vector<StatusItem>();
	}

	public boolean isStunned() {
		return (getStatusItem("Stunned") != null);

	}

	public boolean isDead() {
		return (getStatusItem("Dead") != null);
	}

	@SuppressWarnings("unchecked")
	public Object clone() {
		try {
			Status s = (Status) super.clone();
			s.statVector = (Vector<StatusItem>) statVector.clone();
			s.endSpells = (Vector) endSpells.clone();
			return s;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addStatusItem(StatusItem s) throws StatusException {
		boolean found = false;
		if (s.isFatal()) {
			statVector = new Vector<StatusItem>();
		}
		for (int i = 0; i < statVector.size(); i++) {
			StatusItem si = (StatusItem) statVector.get(i);
			if (si.isFatal()) {
				throw (new StatusException());
			}
			if (si.getName().equals(s.getName())) {
				if (!si.isStacking()) {
					throw (new StatusException());
				} else {
					si.setStatusMod(si.getStatusMod() + 1);
					found = true;
				}
			}
		}
		if (!found) {
			statVector.add(s);
		}
	}

	public void removeStatusItem(String name) {
		for (int i = 0; i < statVector.size(); i++) {
			StatusItem si = (StatusItem) statVector.get(i);
			if (si.getName().equals(name)) {
				statVector.removeElementAt(i);
				break;
			}
		}
	}

	public void modifyStatusItem(String name, int mod) {
		for (int i = 0; i < statVector.size(); i++) {
			StatusItem si = (StatusItem) statVector.get(i);
			if (si.getName().equals(name)) {
				si.setStatusMod(mod);
				break;
			}
		}
	}

	public void incrementStatusItem(String name) {
		for (int i = 0; i < statVector.size(); i++) {
			StatusItem si = (StatusItem) statVector.get(i);
			if (si.getName().equals(name)) {
				si.setStatusMod(si.getStatusMod() + 1);
				break;
			}
		}
	}

	public void decrementStatusItem(String name) {
		for (int i = 0; i < statVector.size(); i++) {
			StatusItem si = (StatusItem) statVector.get(i);
			if (si.getName().equals(name)) {
				si.setStatusMod(si.getStatusMod() - 1);
				if (si.getStatusMod() <= 0) {
					statVector.removeElementAt(i);
				}
				break;
			}
		}
	}

	public boolean advanceStatus() {
		boolean incap = false;
		spellEnd = false;
		endSpells = new Vector<StatusItem>();
		for (int i = 0; i < statVector.size(); i++) {
			StatusItem si = (StatusItem) statVector.get(i);
			if (si.isIncap()) {
				incap = true;
			}
			if (si.isTimed()) {
				if (i == 0 || !si.isSequential()) {
					logger.log("Advancing " + si.getName());
					logger.log("Value " + si.getStatusMod());
					si.setStatusMod(si.getStatusMod() - 1);
					if (si.getStatusMod() <= 0) {
						endSpells.add(statVector.get(i));
						statVector.removeElementAt(i);
						spellEnd = true;
					}
				}
			}
		}
		return incap;
	}

	public boolean isSpellEnding() {
		return spellEnd;
	}

	public Vector<StatusItem> getEndSpells() {
		return endSpells;
	}

	public StatusItem getStatusItem(int i) {
		return (StatusItem) statVector.get(i);
	}

	public int getStatSize() {
		return statVector.size();
	}

	public StatusItem getStatusItem(String name) {
		for (int i = 0; i < statVector.size(); i++) {
			StatusItem si = (StatusItem) statVector.get(i);
			if (si.getName().equals(name)) {
				return si;
			}
		}
		return null;
	}

	public String toString() {
		String statText = "";
		if (statVector.size() == 0) {
			statText = "He's feeling fine";
		} else {
			for (int i = 0; i < statVector.size(); i++) {
				StatusItem si = (StatusItem) statVector.get(i);
				statText += si.getName();
				if (i < statVector.size()-1){
					statText += ",";
				}
			}
		}
		return statText;
	}
	
	public String getStatusText() {
		String statText = "";
		if (statVector.size() == 0) {
			statText = "He's feeling fine";
		} else {
			for (int i = 0; i < statVector.size(); i++) {
				StatusItem si = (StatusItem) statVector.get(i);
				statText += si.getDescription();
				statText += "\r\n";
			}
		}
		return statText;
	}

}
