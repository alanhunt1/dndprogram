package initcheck;

import java.io.Serializable;
import java.util.Vector;

public class Encounter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Vector<MonsterGroup> monsterGroups = new Vector<MonsterGroup>();

	private Vector<DCharacter> npcOpponents = new Vector<DCharacter>();
	
	private String name = "";

	public Encounter() {

	}

	public Vector<EncounterElement> getAllElements(){
		Vector<EncounterElement> elements = new Vector<EncounterElement>();
		elements.addAll(monsterGroups);
		if (npcOpponents != null){
			elements.addAll(npcOpponents);
		}
		return elements;
	}
	
	public Encounter(Vector<MonsterGroup> mg) {
		monsterGroups = mg;
	}

	public Encounter(Vector<MonsterGroup> mg, Vector<DCharacter> npc) {
		monsterGroups = mg;
		npcOpponents = npc;
	}
	
	public void addMonsterGroup(MonsterGroup m) {
		monsterGroups.add(m);
	}

	public void addOpponent(DCharacter p){
		npcOpponents.add(p);
	}
	
	public String toString() {
		return name;
	}

	public Vector<MonsterGroup> getMonsterGroups() {
		return monsterGroups;
	}

	public void setMonsterGroups(Vector<MonsterGroup> monsterGroups) {
		this.monsterGroups = monsterGroups;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector<DCharacter> getNpcOpponents() {
		return npcOpponents;
	}

	public void setNpcOpponents(Vector<DCharacter> npcOpponents) {
		this.npcOpponents = npcOpponents;
	}

}
