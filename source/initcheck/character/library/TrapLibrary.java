package initcheck.character.library;

import initcheck.database.Campaign;
import initcheck.database.Critical;
import initcheck.database.Trap;
import initcheck.database.TrapDAO;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class TrapLibrary implements LibraryData {

		TrapDAO db = new TrapDAO();

		public TrapLibrary(){

		}
		public Vector<LibraryItem> castToLibrary(Vector<Trap> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (Trap a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		public Vector<LibraryItem> getListing(){
				return castToLibrary(db.getTraps());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return castToLibrary(db.getTraps(s));
		}
		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return castToLibrary(db.getTraps(s,c));
		}
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateTrapPanel cfp = new CreateTrapPanel((Trap)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateTrapPanel cfp = new CreateTrapPanel(parent);
		}

		public void deleteListing(Object listing){
				Trap f  = (Trap)listing;
				db.deleteTrap(f);
		}
		public void copyListing(Object listing){
			Trap f  = (Trap)listing;
			f.setTrapId(null);
			db.addTrap(f);
		}
		public ListCellRenderer getCellRenderer(){
				return new LibraryItemCellRenderer();
		}
}
