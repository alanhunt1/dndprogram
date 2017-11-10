package initcheck.character.library;

import initcheck.database.Artifact;
import initcheck.database.Campaign;
import initcheck.database.SpecificArmor;
import initcheck.database.SpecificArmorDAO;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class SpecificArmorLibrary implements LibraryData {

		SpecificArmorDAO db = new SpecificArmorDAO();

		public SpecificArmorLibrary(){

		}
		public Vector<LibraryItem> castToLibrary(Vector<SpecificArmor> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (SpecificArmor a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		
		public Vector<LibraryItem> getListing(){
				return castToLibrary(db.getSpecificArmor());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return castToLibrary(db.getSpecificArmor(s));
		}
		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return castToLibrary(db.getSpecificArmor(s,c));
		}
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateSpecificArmorPanel cfp = new CreateSpecificArmorPanel((SpecificArmor)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateSpecificArmorPanel cfp = new CreateSpecificArmorPanel(parent);
		}

		public void deleteListing(Object listing){
				SpecificArmor f  = (SpecificArmor)listing;
				db.deleteSpecificArmor(f);
		}
		
		public void copyListing(Object listing){
			SpecificArmor f  = (SpecificArmor)listing;
			String name = f.getName();
			String id = f.getId();
			f.setId(null);
			f.setName(name+" copy");
			db.addSpecificArmor(f);
			f.setName(name);
			f.setId(id);
		}
		
		public ListCellRenderer getCellRenderer(){
				return new LibraryItemCellRenderer();
		}
}
