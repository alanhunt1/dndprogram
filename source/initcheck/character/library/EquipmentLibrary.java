package initcheck.character.library;

import initcheck.character.GenericListCellRenderer;
import initcheck.database.Campaign;
import initcheck.database.CharClass;
import initcheck.database.Equipment;
import initcheck.database.EquipmentDAO;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class EquipmentLibrary implements LibraryData {

		EquipmentDAO db = new EquipmentDAO();

		public EquipmentLibrary(){

		}
		public Vector<LibraryItem> castToLibrary(Vector<Equipment> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (Equipment a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		public Vector<LibraryItem> getListing(){
				return castToLibrary(db.getEquipment());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return getListing();
		}

		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return getListing();
		}
		
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateEquipmentPanel cfp = new CreateEquipmentPanel((Equipment)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateEquipmentPanel cfp = new CreateEquipmentPanel(parent);
		}

		public void deleteListing(Object listing){
				Equipment f  = (Equipment)listing;
				db.deleteEquipment(f);
		}
		public void copyListing(Object listing){
			Equipment f  = (Equipment)listing;
			f.setId(null);
			db.addEquipment(f);
			
		}
		public ListCellRenderer getCellRenderer(){
				return new GenericListCellRenderer();
		}
}
