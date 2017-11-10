package initcheck.character.library;

import initcheck.character.GenericListCellRenderer;
import initcheck.database.Campaign;
import initcheck.database.Feat;
import initcheck.database.FeatPackage;
import initcheck.database.FeatPackageDAO;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class FeatPackageLibrary implements LibraryData {

		FeatPackageDAO db = new FeatPackageDAO();

		public FeatPackageLibrary(){

		}
		public Vector<LibraryItem> castToLibrary(Vector<FeatPackage> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (FeatPackage a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		public Vector<LibraryItem> getListing(){
				return castToLibrary(db.getFeatPackages());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return getListing();
		}
		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return getListing();
		}
		
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateFeatPackagesPanel cfp = new CreateFeatPackagesPanel((FeatPackage)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateFeatPackagesPanel cfp = new CreateFeatPackagesPanel(parent);
		}

		public void deleteListing(Object listing){
				FeatPackage f  = (FeatPackage)listing;
				db.deleteFeatPackage(f);
		}
		public void copyListing(Object listing){
			FeatPackage f  = (FeatPackage)listing;
			f.setId(null);
			db.addFeatPackage(f);
			
		}
		public ListCellRenderer getCellRenderer(){
				return new GenericListCellRenderer();
		}
}
