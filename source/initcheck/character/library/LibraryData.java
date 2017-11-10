package initcheck.character.library;

import initcheck.database.Campaign;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public interface LibraryData {
		
		public Vector<LibraryItem> getListing();
		public Vector<LibraryItem> getSearchListing(String s);
		public Vector<LibraryItem> getSearchListing(String s, Campaign c);
		public void updateListing(Object listing, LibraryPanel parent);
		public void addListing(LibraryPanel parent);
		public void deleteListing(Object listing);
		public void copyListing(Object listing);
		public ListCellRenderer getCellRenderer();
		
}
