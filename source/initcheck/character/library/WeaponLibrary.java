package initcheck.character.library;

import initcheck.database.Campaign;
import initcheck.database.Fumble;
import initcheck.database.Weapon;
import initcheck.database.WeaponDAO;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class WeaponLibrary implements LibraryData {

		WeaponDAO db = new WeaponDAO();

		public WeaponLibrary(){

		}
		public Vector<LibraryItem> castToLibrary(Vector<Weapon> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (Weapon a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		public Vector<LibraryItem> getListing(){
				return castToLibrary(db.getWeapons());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return castToLibrary(db.getWeapons(s));
		}
		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return castToLibrary(db.getWeapons(s,c));
		}
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateWeaponsPanel cfp = new CreateWeaponsPanel((Weapon)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateWeaponsPanel cfp = new CreateWeaponsPanel(parent);
		}

		public void deleteListing(Object listing){
				Weapon f  = (Weapon)listing;
				db.deleteWeapon(f);
		}
		public void copyListing(Object listing){
			Weapon f  = (Weapon)listing;
			f.setId(null);
			db.addWeapon(f);
		}
		public ListCellRenderer getCellRenderer(){
				return new LibraryItemCellRenderer();
		}
}
