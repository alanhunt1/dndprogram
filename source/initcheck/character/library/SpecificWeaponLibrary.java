package initcheck.character.library;

import initcheck.database.Campaign;
import initcheck.database.Monster;
import initcheck.database.SpecificWeapon;
import initcheck.database.SpecificWeaponDAO;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class SpecificWeaponLibrary implements LibraryData {

		SpecificWeaponDAO db = new SpecificWeaponDAO();

		public SpecificWeaponLibrary(){

		}
		public Vector<LibraryItem> castToLibrary(Vector<SpecificWeapon> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (SpecificWeapon a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		public Vector<LibraryItem> getListing(){
				return castToLibrary(db.getSpecificWeapons());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return castToLibrary(db.getSpecificWeapons(s));
		}
		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return castToLibrary(db.getSpecificWeapons(s,c));
		}
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateSpecificWeaponPanel cfp = new CreateSpecificWeaponPanel((SpecificWeapon)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateSpecificWeaponPanel cfp = new CreateSpecificWeaponPanel(parent);
		}

		public void deleteListing(Object listing){
				SpecificWeapon f  = (SpecificWeapon)listing;
				db.deleteSpecificWeapon(f);
		}
		public void copyListing(Object listing){
			SpecificWeapon f  = (SpecificWeapon)listing;
			f.setId(null);
			db.addSpecificWeapon(f);
		}
		public ListCellRenderer getCellRenderer(){
				return new LibraryItemCellRenderer();
		}
}
