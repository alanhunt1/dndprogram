package initcheck.character.library;

import initcheck.database.Armor;
import initcheck.database.Artifact;
import initcheck.database.ArtifactDAO;
import initcheck.database.Campaign;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class ArtifactLibrary implements LibraryData {

		ArtifactDAO db = new ArtifactDAO();

		public ArtifactLibrary(){

		}
		
		public Vector<LibraryItem> castToLibrary(Vector<Artifact> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (Artifact a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		
		public Vector<LibraryItem> getListing(){
				return castToLibrary(db.getArtifacts());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return castToLibrary(db.getArtifacts(s));
		}

		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return castToLibrary(db.getArtifacts(s,c));
		}
		
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateArtifactPanel cfp = new CreateArtifactPanel((Artifact)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateArtifactPanel cfp = new CreateArtifactPanel(parent);
		}

		public void deleteListing(Object listing){
				Artifact f  = (Artifact)listing;
				db.deleteArtifacts(f);
		}
		
		public void copyListing(Object listing){
			Artifact f  = (Artifact)listing;
			f.setId(null);
			db.addOrUpdateArtifact(f);
			
		}
		
		public ListCellRenderer getCellRenderer(){
				return new LibraryItemCellRenderer();
		}
}
