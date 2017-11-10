package initcheck.character.library;

import initcheck.character.GenericListCellRenderer;
import initcheck.database.Campaign;
import initcheck.database.ClassAbilities;
import initcheck.database.ClassAbilitiesDAO;
import initcheck.database.Poison;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class ClassAbilitiesLibrary implements LibraryData {

		ClassAbilitiesDAO db = new ClassAbilitiesDAO();

		public ClassAbilitiesLibrary(){

		}
		public Vector<LibraryItem> castToLibrary(Vector<ClassAbilities> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (ClassAbilities a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		public Vector<LibraryItem> getListing(){
				return castToLibrary(db.getClassAbilities());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return castToLibrary(db.getClassAbilities(s));
		}

		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return getListing();
		}
		
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateClassAbilitiesPanel cfp = new CreateClassAbilitiesPanel((ClassAbilities)
				listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateClassAbilitiesPanel cfp = new CreateClassAbilitiesPanel(parent);
		}

		public void deleteListing(Object listing){
				ClassAbilities f  = (ClassAbilities)listing;
				db.deleteClassAbilities(f);
		}
		
		public void copyListing(Object listing){
			ClassAbilities f  = (ClassAbilities)listing;
			f.setId(null);
			db.addOrUpdateClassAbilities(f);
		}
		
		public ListCellRenderer getCellRenderer(){
				return new GenericListCellRenderer();
		}
}
