package initcheck.character.library;

import initcheck.character.GenericListCellRenderer;
import initcheck.database.Campaign;
import initcheck.database.Rods;
import initcheck.database.Services;
import initcheck.database.ServicesDAO;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class ServicesLibrary implements LibraryData {

	ServicesDAO db = new ServicesDAO();

	public ServicesLibrary() {

	}
	public Vector<LibraryItem> castToLibrary(Vector<Services> va){
		Vector<LibraryItem> vl = new Vector<LibraryItem>();
		for (Services a:va){
			vl.add((LibraryItem) a);
		}
		return vl;
	}
	public Vector<LibraryItem> getListing() {
		return castToLibrary(db.getServices());
	}

	public Vector<LibraryItem> getSearchListing(String s) {
		return getListing();
	}
	public Vector<LibraryItem> getSearchListing(String s, Campaign c){
		return getListing();
	}
	public void updateListing(Object listing, LibraryPanel parent) {
		@SuppressWarnings("unused")
		CreateServicesPanel cfp = new CreateServicesPanel(
				(Services) listing, parent);
	}

	public void addListing(LibraryPanel parent) {
		@SuppressWarnings("unused")
		CreateServicesPanel cfp = new CreateServicesPanel(parent);
	}

	public void deleteListing(Object listing) {
		Services f = (Services) listing;
		db.deleteServices(f);
	}
	public void copyListing(Object listing){
		Services f = (Services) listing;
		f.setServiceId(null);
		db.addServices(f);
	}
	public ListCellRenderer getCellRenderer() {
		return new GenericListCellRenderer();
	}
}
