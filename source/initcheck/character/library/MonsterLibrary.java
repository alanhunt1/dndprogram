package initcheck.character.library;

import initcheck.MonsterDisplayPanel;
import initcheck.MonsterStatDialog;
import initcheck.database.Campaign;
import initcheck.database.Equipment;
import initcheck.database.Monster;
import initcheck.database.MonsterAttacks;
import initcheck.database.MonsterAttacksDAO;
import initcheck.database.MonsterDAO;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class MonsterLibrary implements LibraryData {

	MonsterDAO db = new MonsterDAO();

	public MonsterLibrary() {

	}
	public Vector<LibraryItem> castToLibrary(Vector<Monster> va){
		Vector<LibraryItem> vl = new Vector<LibraryItem>();
		for (Monster a:va){
			vl.add((LibraryItem) a);
		}
		return vl;
	}
	public Vector<LibraryItem> getListing() {
		return castToLibrary(db.getMonsters());
	}

	public Vector<LibraryItem> getSearchListing(String s) {
		return castToLibrary(db.getMonsters(s, null));
	}
	public Vector<LibraryItem> getSearchListing(String s, Campaign c){
		return castToLibrary(db.getMonsters(s, null));
	}
	
	public void updateListing(Object listing, LibraryPanel parent) {
		@SuppressWarnings("unused")
		MonsterStatDialog cfp = new MonsterStatDialog((Monster) listing,
				MonsterDisplayPanel.MOD, parent);
	}

	public void addListing(LibraryPanel parent) {
		@SuppressWarnings("unused")
		MonsterStatDialog cfp = new MonsterStatDialog(parent, MonsterDisplayPanel.ADD);
	}

	public void deleteListing(Object listing) {
		Monster f = (Monster) listing;
		db.deleteMonster(f);
	}
	public void copyListing(Object listing){
		Monster f = (Monster) listing;
		String name = f.getName();
		int id = f.getID();
		f.setID(0);
		f.setName(name+" copy");
		int newId = db.addOrUpdateMonster(f);
		f.setID(id);
		f.setName(name);
		
		// copy the attacks
		MonsterAttacksDAO db = new MonsterAttacksDAO();
		Vector<MonsterAttacks> attacks = db.getMonsterAttacks(""+id);
		for (int i = 0; i < attacks.size(); i++){
			MonsterAttacks ma = attacks.get(i);
			ma.setMonsterId(""+newId);
			db.addMonsterAttacks(ma);
		}
	}
	
	public ListCellRenderer getCellRenderer() {
		return new MonsterListCellRenderer();
	}
}
