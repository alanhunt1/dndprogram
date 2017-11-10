package initcheck.character.library;

import initcheck.database.Campaign;
import initcheck.database.MaterialSource;
import initcheck.database.Rods;
import initcheck.database.RodsDAO;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class RodsLibrary implements LibraryData {

		RodsDAO db = new RodsDAO();

		public RodsLibrary(){

		}
		public Vector<LibraryItem> castToLibrary(Vector<Rods> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (Rods a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		public Vector<LibraryItem> getListing(){
				return castToLibrary(db.getRods());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return castToLibrary(db.getRods(s));
		}
		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return castToLibrary( db.getRods(s,c));
		}
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateRodPanel cfp = new CreateRodPanel((Rods)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateRodPanel cfp = new CreateRodPanel(parent);
		}

		public void deleteListing(Object listing){
				Rods f  = (Rods)listing;
				db.deleteRods(f);
		}
		public void copyListing(Object listing){
			Rods f  = (Rods)listing;
			f.setId(null);
			db.addRods(f);
		}
		public ListCellRenderer getCellRenderer(){
				return new LibraryItemCellRenderer();
		}
}
