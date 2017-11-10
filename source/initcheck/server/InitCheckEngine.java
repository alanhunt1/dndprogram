package initcheck.server;

import initcheck.DCharacter;
import initcheck.Encounter;
import initcheck.InitLogger;
import initcheck.MonsterGroup;
import initcheck.MonsterSet;
import initcheck.Participant;
import initcheck.database.CharacterDB;
import initcheck.database.Monster;

import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

public class InitCheckEngine implements Serializable {

	
	private static final long serialVersionUID = 1L;

	private Random rnd = new Random();

	private CharacterDB db;

	private MonsterSet monsters;

	private Vector<DCharacter> opponents = new Vector<DCharacter>();
	
	private Vector<Participant> sortedList = new Vector<Participant>();

	private InitLogger logger = new InitLogger(this);

	public InitCheckEngine() {
		db = new CharacterDB();
		monsters = new MonsterSet();
	}

	public InitCheckEngine(String dbStr) {
		db = new CharacterDB(dbStr);
		monsters = new MonsterSet();
	}

	public Vector<Monster> getMonsterPictures() {
		return monsters.getPictures();
	}

	public Vector<MonsterGroup> getMonsterGroups() {
		return monsters.getMonsterGroups();
	}

	public Vector<String> getPlayerGroups() {
		return db.getPlayerGroups();
	}

	public Vector<Participant> getPlayerGroup(int i) {
		return db.getPlayerGroup(i);
	}

	public void clearMonsters(int delGroup) {
		monsters.clearMonsters(delGroup);
	}

	public void clearPlayers(int delGroup) {
		db.clearPlayers(delGroup);
	}

	public void setDB(String s) {
		db = new CharacterDB(s);
	}

	public CharacterDB getDB() {
		return db;
	}

	public void clearMonsters() {
		monsters.clearMonsters();
		for (int j = 0; j < sortedList.size(); j++) {
			Participant p2 = (Participant) (sortedList.get(j));
			if (p2.getPType().equalsIgnoreCase("MONSTER") || p2.getPType().equalsIgnoreCase("Opponent")) {
				sortedList.removeElementAt(j);
				if (p2.getPType().equalsIgnoreCase("OPPONENT")) {
					opponents.remove((Participant) p2);
				}
				j--;
			}
		}

	}

	public Encounter getEncounter(){
		Encounter e = new Encounter();
		e.setName("Current Encounter");
		e.setMonsterGroups(monsters.getMonsterGroups());
		e.setNpcOpponents(opponents);
		return e;
	}
	
	public void addOpponents(Vector<DCharacter> v) {
		
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant)v.get(i);
			p.setPType("Opponent");
			addMonster(p);
			p.setInit(getInitRoll() + p.getMod());
			insertCharacter(p);
		}
	}
	
	public void addMonsters(int numMonsters, Monster m) {
		monsters.addMonsters(numMonsters, m);
		insertParticipants(monsters.getCurrentGroup());

	}

	public void createMonsters(int numMonsters, Monster m) {
		monsters.createMonsters(numMonsters, m);
		insertParticipants(monsters.getCurrentGroup());
	}

	public void addCharacter(int mod, String name) {
		DCharacter c = new DCharacter(mod, name);
		db.addCharacter(c);
	}

	public Vector<Participant> getMonsterGroup(int i) {
		return monsters.getMonsterGroup(i);
	}

	public Vector<Participant> getCurrentGroup() {
		Vector<Participant> currentGroup = monsters.getCurrentGroup();
		
		//for (int i = 0; i < opponents.size(); i++) {
		//	Participant p = opponents.get(i);
		//	if (p.getGroup() == group) {
				
		//	}
		//}
		
		return currentGroup;
		
	}

	public void removeParticipants(Vector<Participant> v) {
		logger.log("Removing participants");
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) (v.get(i));
			for (int j = 0; j < sortedList.size(); j++) {
				Participant p2 = (Participant) (sortedList.get(j));
				if (p.getName().equals(p2.getName())) {
					sortedList.removeElementAt(j);
					if (p2.getPType().equals("MONSTER")) {
						logger.log("Removing monster");
						monsters.removeMonster((Monster) p2);
					} else if (p2.getPType().equalsIgnoreCase("OPPONENT")) {
						opponents.remove((Participant) p2);
					} else {
						logger.log("removing character");
						db.removeCharacter((DCharacter) p2);
					}
					break;
				}
			}
		}
	}

	public void insertParticipants(Vector<Participant> v) {
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) (v.get(i));
			logger.log("Participant " + p);
			p.setInit(getInitRoll() + p.getMod());
			insertCharacter(p);
		}
	}

	public Vector<DCharacter> getCharacters() {
		return db.getCharacters();
	}

	public void doInit() {
		sortedList = new Vector<Participant>(10, 10);

		for (int i = 0; i < db.getCharacters().size(); i++) {
			DCharacter d = (DCharacter) (db.getCharacters().get(i));
			d.setInit(getInitRoll() + d.getMod());
			if (!d.isHidden()) {
				insertCharacter(d);
			}
		}
		for (int i = 0; i < monsters.size(); i++) {
			Monster m = monsters.getMonster(i);
			m.setInit(getInitRoll() + m.getMod());
			insertCharacter(m);
		}
		for (int i = 0; i < opponents.size(); i++) {
			Participant p = (Participant) opponents.get(i);
			p.setInit(getInitRoll() + p.getMod());
			insertCharacter(p);
		}
	}

	private int getInitRoll() {
		return rnd.nextInt(20);
	}

	public void holdInit(int idx) {
		Participant p = ((Participant) sortedList.get(idx));
		sortedList.removeElementAt(idx);
		sortedList.add(p);
	}

	public void changePosition(int idx, int newIdx) {
		Participant p = ((Participant) sortedList.get(idx));
		sortedList.removeElementAt(idx);
		sortedList.add(newIdx, p);
	}

	public void addPlayer(DCharacter d) {
		if (sortedList == null) {
			sortedList = new Vector<Participant>(10, 10);
		}

		db.addCharacter(d);
		sortedList.add(d);
	}

	public void addPlayers(Vector<DCharacter> v) {
		db.setCharacters(v);
	}

	public void addMonster(Participant m) {
		if (sortedList == null) {
			sortedList = new Vector<Participant>(10, 10);
		}
		if (m.getPType().equalsIgnoreCase("MONSTER")) {
			monsters.addMonster((Monster)m);
		}else {
			opponents.add((DCharacter)m);
		}
		//sortedList.add(m);
	}

	public void insertCharacter(Participant p) {

		int idx = 0;
		while (idx < sortedList.size()) {
			if (p.getInit() >= ((Participant) sortedList.get(idx)).getInit()) {
				sortedList.add(idx, p);

				return;
			}
			idx++;
		}
		sortedList.add(p);
	}

	public Vector<Participant> getSortedList() {
		return sortedList;
	}

	public void setSortedList(Vector<Participant> v) {
		sortedList = v;
	}

	public Vector<Participant> getMonsters() {
		Vector<Monster> v = monsters.getMonsters();
		Vector<Participant> total = new Vector<Participant>(opponents);
		for (int i = 0; i < v.size(); i++) {
			total.add((Participant)v.get(i));
		}
		return total;
	}

}
