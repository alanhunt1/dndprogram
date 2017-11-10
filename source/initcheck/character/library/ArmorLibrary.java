package initcheck.character.library;

import initcheck.database.Armor;
import initcheck.database.ArmorDAO;
import initcheck.database.Campaign;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class ArmorLibrary implements LibraryData {

		ArmorDAO db = new ArmorDAO();

		public ArmorLibrary(){

		}
		
		public Vector<LibraryItem> castToLibrary(Vector<Armor> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (Armor a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		
		public Vector<LibraryItem> getListing(){
			return castToLibrary(db.getArmor());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return castToLibrary(db.getArmor(s));
		}

		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return castToLibrary(db.getArmor(s,c));
		}
		
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateArmorsPanel cfp = new CreateArmorsPanel((Armor)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateArmorsPanel cfp = new CreateArmorsPanel(parent);
		}

		public void deleteListing(Object listing){
				Armor f  = (Armor)listing;
				db.deleteArmor(f);
		}
		
		public void copyListing(Object listing){
			Armor f  = (Armor)listing;
			String name = f.getName();
			String id = f.getId();
			f.setId(null);
			f.setName(name+" copy");
			db.addOrUpdateArmor(f);
			f.setName(name);
			f.setId(id);
		}
		
		public ListCellRenderer getCellRenderer(){
				return new ArmorListCellRenderer();
		}
}
