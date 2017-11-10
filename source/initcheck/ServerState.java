package initcheck;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ServerState implements Serializable {
	
	private int Round;
	private int listIndex;
	private int oldGroup;
	private int currentGroup;
	private int engineSize;
	private String party;
	private int tabIndex;
	
	public int getCurrentGroup() {
		return currentGroup;
	}
	public void setCurrentGroup(int currentGroup) {
		this.currentGroup = currentGroup;
	}
	public int getEngineSize() {
		return engineSize;
	}
	public void setEngineSize(int engineSize) {
		this.engineSize = engineSize;
	}
	public int getListIndex() {
		return listIndex;
	}
	public void setListIndex(int listIndex) {
		this.listIndex = listIndex;
	}
	public int getOldGroup() {
		return oldGroup;
	}
	public void setOldGroup(int oldGroup) {
		this.oldGroup = oldGroup;
	}
	public String getParty() {
		return party;
	}
	public void setParty(String party) {
		this.party = party;
	}
	public int getRound() {
		return Round;
	}
	public void setRound(int round) {
		Round = round;
	}
	public int getTabIndex() {
		return tabIndex;
	}
	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}
	
	
	
}
