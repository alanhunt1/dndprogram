package initcheck.character.library;

import initcheck.database.Campaign;
import initcheck.database.Race;
import initcheck.database.RaceDAO;
import initcheck.database.RacialAbility;
import initcheck.database.RacialAbilityDAO;
import initcheck.database.WeaponAbilities;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class RaceLibrary implements LibraryData {

		RaceDAO db = new RaceDAO();

		public RaceLibrary(){

		}
		public Vector<LibraryItem> castToLibrary(Vector<Race> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (Race a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		public Vector<LibraryItem> getListing(){
				return castToLibrary(db.getRaces());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return castToLibrary(db.getRaces(s));
		}
		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return castToLibrary(db.getRaces(s,c));
		}
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateRacesPanel cfp = new CreateRacesPanel((Race)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateRacesPanel cfp = new CreateRacesPanel(parent);
		}

		public void deleteListing(Object listing){
				Race f  = (Race)listing;
				db.deleteRace(f);
		}
		
		public void copyListing(Object listing){
			Race f  = (Race)listing;
			String name = f.getName();
			String id = f.getId();
			
			f.setId(null);
			f.setName(name + " copy");
			int newId = db.addRace(f);
			f.setId(id);
			f.setName(name);
			
			// add the proficiencies and abilities
			RacialAbilityDAO radb = new RacialAbilityDAO();
			Vector<RacialAbility>abilities = radb.getRacialAbilities(name);
			for (int i = 0; i < abilities.size(); i++){
				RacialAbility ra = abilities.get(i);
				ra.setRaceId(""+newId);
				radb.addRacialAbility(ra);
			}
		}
		
		
		public ListCellRenderer getCellRenderer(){
				return new RaceCellRenderer();
		}
}
