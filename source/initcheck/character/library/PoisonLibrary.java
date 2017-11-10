package initcheck.character.library;

import initcheck.database.Campaign;
import initcheck.database.Poison;
import initcheck.database.PoisonDAO;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class PoisonLibrary implements LibraryData {

		PoisonDAO db = new PoisonDAO();

		public PoisonLibrary(){

		}
		public Vector<LibraryItem> castToLibrary(Vector<Poison> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (Poison a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		public Vector<LibraryItem> getListing(){
				return castToLibrary(db.getPoisons());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return castToLibrary(db.getPoisons(s));
		}
		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return castToLibrary(db.getPoisons(s,c));
		}
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreatePoisonPanel cfp = new CreatePoisonPanel((Poison)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreatePoisonPanel cfp = new CreatePoisonPanel(parent);
		}

		public void deleteListing(Object listing){
				Poison f  = (Poison)listing;
				db.deletePoison(f);
		}
		public void copyListing(Object listing){
			Poison f  = (Poison)listing;
			f.setId(null);
			db.addPoison(f);
		}
		public ListCellRenderer getCellRenderer(){
				return new LibraryItemCellRenderer();
		}
}
