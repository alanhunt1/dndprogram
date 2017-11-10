package initcheck.database;

import initcheck.DCharacter;
import initcheck.InitLogger;
import initcheck.Participant;

import java.io.Serializable;
import java.util.Vector;

@SuppressWarnings("serial")
public class CharacterDB implements Serializable {

	private Vector<DCharacter> characters = new Vector<DCharacter>();
	private Vector<String> playerGroups = new Vector<String>();
	private InitLogger logger = new InitLogger(this);

	public CharacterDB() {

	}

	public CharacterDB(String s) {
		readCharacters(s);
	}

	public Vector<String> getPlayerGroups() {
		return playerGroups;
	}

	public Vector<Participant> getPlayerGroup(int playerGroup) {
		Vector<Participant> v = new Vector<Participant>();
		if (playerGroup < playerGroups.size()) {
			String group = (String) (playerGroups.get(playerGroup));
			for (int i = 0; i < characters.size(); i++) {
				DCharacter c = (DCharacter) (characters.get(i));
				if (c.getParty().equals(group)) {
					v.add(c);
				}
			}
		}
		return v;
	}

	public void setCharacters(Vector<DCharacter> v) {
		characters = v;
	}

	public void clearPlayers(int playerGroup) {

		String group = (String) (playerGroups.get(playerGroup));
		for (int i = 0; i < characters.size(); i++) {
			DCharacter c = (DCharacter) (characters.get(i));
			if (c.getParty().equals(group)) {
				characters.removeElementAt(i);
				i--;
			}
		}
		playerGroups.removeElementAt(playerGroup);
	}

	public void addPlayers(Vector<DCharacter> v) {
		String party = "";
		for (int i = 0; i < v.size(); i++) {
			DCharacter c = (DCharacter) (v.get(i));
			characters.add(c);
			party = c.getParty();
		}
		playerGroups.add(party);
	}

	public int addCharacter(DCharacter c) {
		int i = -1;
		if (c != null) {

			InitDBC db = new InitDBC();
			i = db.addPlayer(c);
			c.setID(i);
			characters.add(c);
		} else {
			logger.log("Attempt to add null character");
		}
		return i;
	}

	public void modifyCharacter(int i, DCharacter c) {
		if (i >= 0) {
			characters.setElementAt(c, i);
		}
		InitDBC db = new InitDBC();
		db.updatePlayer(c);
	}

	public DCharacter getCharacter(int i) {
		return (DCharacter) characters.get(i);
	}

	public void removeCharacter(DCharacter c) {
		characters.remove(c);
	}

	public void deleteCharacter(DCharacter c) {
		characters.remove(c);
		InitDBC db = new InitDBC();
		db.deletePlayer(c.getID());
	}

	public void removeCharacter(int i) {
		removeCharacter(getCharacter(i));
	}

	public void hideCharacter(int i) {
		characters.remove(getCharacter(i));
	}

	public Vector<DCharacter> getParty(String s) {
		DCharacter d = new DCharacter();
		d.setParty(s);

		InitDBC db = new InitDBC();

		Vector<DCharacter> v = db.getPlayers(d);
		if (v == null) {
			v = new Vector<DCharacter>();
		}
		return v;
	}

	public void readCharacters(String s) {
		DCharacter d = new DCharacter();
		d.setParty(s);

		InitDBC db = new InitDBC();

		characters = db.getPlayers(d);
		if (characters == null) {
			characters = new Vector<DCharacter>();
		}
	}

	public Vector<DCharacter> getCharacters() {
		return characters;
	}

}
