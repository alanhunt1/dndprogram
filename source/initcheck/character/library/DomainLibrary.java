package initcheck.character.library;

import initcheck.database.Campaign;
import initcheck.database.Domain;
import initcheck.database.DomainDAO;
import initcheck.database.Rings;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class DomainLibrary implements LibraryData {

	DomainDAO db = new DomainDAO();

	public DomainLibrary() {

	}
	public Vector<LibraryItem> castToLibrary(Vector<Domain> va){
		Vector<LibraryItem> vl = new Vector<LibraryItem>();
		for (Domain a:va){
			vl.add((LibraryItem) a);
		}
		return vl;
	}
	public Vector<LibraryItem> getListing() {
		return castToLibrary(db.getDomains());
	}

	public Vector<LibraryItem> getSearchListing(String s) {
		return castToLibrary(db.getDomains(s));
	}

	public Vector<LibraryItem> getSearchListing(String s, Campaign c){
		return castToLibrary(db.getDomains(s,c));
	}
	
	public void updateListing(Object listing, LibraryPanel parent) {
		@SuppressWarnings("unused") CreateDomainPanel cfp = new CreateDomainPanel((Domain) listing, parent);
	}

	public void addListing(LibraryPanel parent) {
		@SuppressWarnings("unused") CreateDomainPanel cfp = new CreateDomainPanel(parent);
	}

	public void deleteListing(Object listing) {
		Domain f = (Domain) listing;
		db.deleteDomain(f);
	}

	public void copyListing(Object listing){
		Domain f = (Domain) listing;
		f.setId(null);
		db.addDomain(f);
	}
	
	public ListCellRenderer getCellRenderer() {
		return new LibraryItemCellRenderer();
	}
}
