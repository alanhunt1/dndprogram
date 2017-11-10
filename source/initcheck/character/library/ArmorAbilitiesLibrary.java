package initcheck.character.library;

import initcheck.database.ArmorAbilities;
import initcheck.database.ArmorAbilitiesDAO;
import initcheck.database.Campaign;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class ArmorAbilitiesLibrary implements LibraryData {

		ArmorAbilitiesDAO db = new ArmorAbilitiesDAO();

		public ArmorAbilitiesLibrary(){

		}
		
		public Vector<LibraryItem> getListing(){
				Vector<ArmorAbilities> aa =  db.getArmorAbilities();
				Vector<LibraryItem> vl = new Vector<LibraryItem>();
				for (ArmorAbilities a:aa){
					vl.add((LibraryItem)a);
				}
				return vl;
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
			Vector<ArmorAbilities> aa = db.getArmorAbilities(s);
				Vector<LibraryItem> vl = new Vector<LibraryItem>();
				for (ArmorAbilities a:aa){
					vl.add((LibraryItem)a);
				}
				return vl;
		}

		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			Vector<ArmorAbilities> aa = db.getArmorAbilities(s,c);
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (ArmorAbilities a:aa){
				vl.add((LibraryItem)a);
			}
			return vl;
		}
		
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateArmorAbilitiesPanel cfp = new CreateArmorAbilitiesPanel((ArmorAbilities)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateArmorAbilitiesPanel cfp = new CreateArmorAbilitiesPanel(parent);
		}

		public void deleteListing(Object listing){
				ArmorAbilities f  = (ArmorAbilities)listing;
				db.deleteArmorAbilities(f);
		}
		
		public void copyListing(Object listing){
			ArmorAbilities f  = (ArmorAbilities)listing;
			f.setId(null);
			db.addOrUpdateArmorAbilities(f);
		}
		
		public ListCellRenderer getCellRenderer(){
				return new LibraryItemCellRenderer();
		}
}
