package initcheck.character.library;

import initcheck.database.Campaign;
import initcheck.database.Skill;
import initcheck.database.Staffs;
import initcheck.database.StaffsDAO;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class StaffsLibrary implements LibraryData {

		StaffsDAO db = new StaffsDAO();

		public StaffsLibrary(){

		}
		public Vector<LibraryItem> castToLibrary(Vector<Staffs> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (Staffs a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		public Vector<LibraryItem> getListing(){
				return castToLibrary(db.getStaffs());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return castToLibrary(db.getStaffs(s));
		}
		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return castToLibrary(db.getStaffs(s,c));
		}
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateStaffPanel cfp = new CreateStaffPanel((Staffs)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateStaffPanel cfp = new CreateStaffPanel(parent);
		}

		public void deleteListing(Object listing){
				Staffs f  = (Staffs)listing;
				db.deleteStaffs(f);
		}
		public void copyListing(Object listing){
			Staffs f  = (Staffs)listing;
			f.setId(null);
			db.addStaffs(f);
		}
		public ListCellRenderer getCellRenderer(){
				return new LibraryItemCellRenderer();
		}
}
