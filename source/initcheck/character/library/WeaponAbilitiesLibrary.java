package initcheck.character.library;

import initcheck.database.Campaign;
import initcheck.database.Trap;
import initcheck.database.WeaponAbilities;
import initcheck.database.WeaponAbilitiesDAO;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class WeaponAbilitiesLibrary implements LibraryData {

		WeaponAbilitiesDAO db = new WeaponAbilitiesDAO();

		public WeaponAbilitiesLibrary(){

		}
		public Vector<LibraryItem> castToLibrary(Vector<WeaponAbilities> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (WeaponAbilities a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		public Vector<LibraryItem> getListing(){
				return  castToLibrary(db.getWeaponAbilities());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return  castToLibrary(db.getWeaponAbilities(s));
		}
		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return  castToLibrary(db.getWeaponAbilities(s,c));
		}
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateWeaponAbilitiesPanel cfp = new CreateWeaponAbilitiesPanel((WeaponAbilities)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateWeaponAbilitiesPanel cfp = new CreateWeaponAbilitiesPanel(parent);
		}

		public void deleteListing(Object listing){
				WeaponAbilities f  = (WeaponAbilities)listing;
				db.deleteWeaponAbilities(f);
		}
		public void copyListing(Object listing){
			WeaponAbilities f  = (WeaponAbilities)listing;
			f.setId(null);
			db.addWeaponAbilities(f);
		}
		public ListCellRenderer getCellRenderer(){
				return new LibraryItemCellRenderer();
		}
}
