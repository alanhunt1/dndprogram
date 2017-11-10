package initcheck.character.library;

import initcheck.database.Campaign;
import initcheck.database.Critical;
import initcheck.database.CriticalDAO;
import initcheck.database.Materials;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class CriticalLibrary implements LibraryData {

		CriticalDAO db = new CriticalDAO();

		public CriticalLibrary(){

		}
		public Vector<LibraryItem> castToLibrary(Vector<Critical> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (Critical a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		public Vector<LibraryItem> getListing(){
				return castToLibrary(db.getCriticals());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return getListing();
		}

		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return getListing();
		}
		
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateCriticalPanel cfp = new CreateCriticalPanel((Critical)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateCriticalPanel cfp = new CreateCriticalPanel(parent);
		}

		public void deleteListing(Object listing){
				Critical f  = (Critical)listing;
				db.deleteCritical(f);
		}
		
		public void copyListing(Object listing){
			Critical f  = (Critical)listing;
			f.setId(null);
			db.addCritical(f);
		}
		public ListCellRenderer getCellRenderer(){
				return new CriticalListCellRenderer();
		}
}
