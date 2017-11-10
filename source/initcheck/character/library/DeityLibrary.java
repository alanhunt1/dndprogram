package initcheck.character.library;

import initcheck.database.Campaign;
import initcheck.database.ClassAbilities;
import initcheck.database.Deity;
import initcheck.database.DeityDAO;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class DeityLibrary implements LibraryData {

		DeityDAO db = new DeityDAO();

		public DeityLibrary(){

		}
		public Vector<LibraryItem> castToLibrary(Vector<Deity> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (Deity a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		public Vector<LibraryItem> getListing(){
				return castToLibrary(db.getDeities());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return castToLibrary(db.getDeities(s));
		}

		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return castToLibrary(db.getDeities(s,c));
		}
		
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateDeityPanel cfp = new CreateDeityPanel((Deity)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateDeityPanel cfp = new CreateDeityPanel(parent);
		}

		public void deleteListing(Object listing){
				Deity f  = (Deity)listing;
				db.deleteDeity(f);
		}
		
		public void copyListing(Object listing){
			Deity f  = (Deity)listing;
			f.setId(null);
			db.addDeity(f);
		}
		
		public ListCellRenderer getCellRenderer(){
				return new LibraryItemCellRenderer();
		}
}
