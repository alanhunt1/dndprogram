package initcheck.character.library;

import initcheck.character.GenericListCellRenderer;
import initcheck.database.Campaign;
import initcheck.database.MaterialSource;
import initcheck.database.MaterialSourceDAO;
import initcheck.database.Weapon;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class MaterialSourceLibrary implements LibraryData {

		MaterialSourceDAO db = new MaterialSourceDAO();

		public MaterialSourceLibrary(){

		}
		public Vector<LibraryItem> castToLibrary(Vector<MaterialSource> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (MaterialSource a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		public Vector<LibraryItem> getListing(){
				return castToLibrary(db.getMaterialSources());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return getListing();
		}
		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return getListing();
		}
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateMaterialSourcePanel cfp = new CreateMaterialSourcePanel((MaterialSource)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateMaterialSourcePanel cfp = new CreateMaterialSourcePanel(parent);
		}

		public void deleteListing(Object listing){
				MaterialSource f  = (MaterialSource)listing;
				db.deleteMaterialSource(f);
		}
		public void copyListing(Object listing){
			MaterialSource f  = (MaterialSource)listing;
			f.setSourceId(null);
			db.addMaterialSource(f);
		}
		public ListCellRenderer getCellRenderer(){
				return new GenericListCellRenderer();
		}
}
