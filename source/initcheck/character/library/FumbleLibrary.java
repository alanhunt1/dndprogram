package initcheck.character.library;

import initcheck.database.Campaign;
import initcheck.database.FeatPackage;
import initcheck.database.Fumble;
import initcheck.database.FumbleDAO;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class FumbleLibrary implements LibraryData {

		FumbleDAO db = new FumbleDAO();

		public FumbleLibrary(){

		}
		public Vector<LibraryItem> castToLibrary(Vector<Fumble> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (Fumble a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		public Vector<LibraryItem> getListing(){
				return castToLibrary(db.getFumbles());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return getListing();
		}
		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return getListing();
		}
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateFumblePanel cfp = new CreateFumblePanel((Fumble)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateFumblePanel cfp = new CreateFumblePanel(parent);
		}

		public void deleteListing(Object listing){
				Fumble f  = (Fumble)listing;
				db.deleteFumble(f);
		}
		public void copyListing(Object listing){
			Fumble f  = (Fumble)listing;
			f.setId(null);
			db.addFumble(f);
		}
		public ListCellRenderer getCellRenderer(){
				return new FumbleListCellRenderer();
		}
}
