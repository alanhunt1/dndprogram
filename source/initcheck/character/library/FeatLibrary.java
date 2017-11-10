package initcheck.character.library;

import initcheck.database.Campaign;
import initcheck.database.Feat;
import initcheck.database.FeatDAO;
import initcheck.database.FeatEffects;
import initcheck.database.FeatEffectsDAO;
import initcheck.database.FeatPrereq;
import initcheck.database.FeatPrereqDAO;
import initcheck.database.PlayerFeatsDAO;
import initcheck.database.Wonderous;

import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;

public class FeatLibrary implements LibraryData {

	FeatDAO db = new FeatDAO();

	public FeatLibrary() {

	}
	public Vector<LibraryItem> castToLibrary(Vector<Feat> va){
		Vector<LibraryItem> vl = new Vector<LibraryItem>();
		for (Feat a:va){
			vl.add((LibraryItem) a);
		}
		return vl;
	}
	public Vector<LibraryItem> getListing() {
		return castToLibrary(db.getFeats());
	}

	public Vector<LibraryItem> getSearchListing(String s) {
		return castToLibrary(db.getFeats(s));
	}

	public Vector<LibraryItem> getSearchListing(String s, Campaign c){
		
		return castToLibrary(db.getFeats(s, c));
	}
	
	public void updateListing(Object listing, LibraryPanel parent) {
		@SuppressWarnings("unused")
		CreateFeatPanel cfp = new CreateFeatPanel((Feat) listing, parent);
	}

	public void addListing(LibraryPanel parent) {
		@SuppressWarnings("unused")
		CreateFeatPanel cfp = new CreateFeatPanel(parent);
	}

	public void deleteListing(Object listing) {
		Feat f = (Feat) listing;

		// check and see if there is any players using the feat
		PlayerFeatsDAO pfdb = new PlayerFeatsDAO();
		if (pfdb.isUsed(f.getId())) {
			String mesg = "The feat you are attempting to delete is in use.\n\n It will be removed from all characters.  Do you want to continue?";
			int answer = JOptionPane.showConfirmDialog(null, mesg,
					"Remove Active Feat?", JOptionPane.YES_NO_OPTION);

			if (answer == JOptionPane.YES_OPTION) {
				db.deleteFeat(f);
			}
		} else {
			db.deleteFeat(f);
		}
	}

	public void copyListing(Object listing) {
		Feat f = (Feat) listing;
		String id = f.getId();
		String name = f.getName();
		// add the core record
		f.setId(null);
		f.setName(name+" copy");
		int newId = db.addOrUpdateFeat(f);
		f.setId(id);
		f.setName(name);
		// add the prereqs
		FeatPrereqDAO fpdb = new FeatPrereqDAO();
		Vector<FeatPrereq> prereqs = fpdb.getFeatPrereqs(id);
		for (int i = 0; i < prereqs.size(); i++){
			FeatPrereq fp = prereqs.get(i);
			fp.setFeatId(""+newId);
			fpdb.addFeatPrereq(fp);
		}
		
		// add the effects
		FeatEffectsDAO fedb = new FeatEffectsDAO();
		Vector<FeatEffects> effects = fedb.getFeatEffects(id);
		for (int i = 0; i < effects.size(); i++){
			FeatEffects fe = effects.get(i);
			fe.setFeatId(""+newId);
			fedb.addFeatEffects(fe);
		}
		
	}

	public ListCellRenderer getCellRenderer() {
		return new LibraryItemCellRenderer();
	}
}
