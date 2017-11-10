package initcheck.character.library;

import initcheck.database.Campaign;
import initcheck.database.Race;
import initcheck.database.Rings;
import initcheck.database.RingsDAO;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class RingsLibrary implements LibraryData {

		RingsDAO db = new RingsDAO();

		public RingsLibrary(){

		}
		public Vector<LibraryItem> castToLibrary(Vector<Rings> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (Rings a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		public Vector<LibraryItem> getListing(){
				return castToLibrary(db.getRings());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return castToLibrary(db.getRings(s));
		}
		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return castToLibrary(db.getRings(s,c));
		}
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateRingPanel cfp = new CreateRingPanel((Rings)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateRingPanel cfp = new CreateRingPanel(parent);
		}

		public void deleteListing(Object listing){
				Rings f  = (Rings)listing;
				db.deleteRings(f);
		}
		public void copyListing(Object listing){
			Rings f  = (Rings)listing;
			f.setId(null);
			db.addRings(f);
		}
		public ListCellRenderer getCellRenderer(){
				return new LibraryItemCellRenderer();
		}
}
