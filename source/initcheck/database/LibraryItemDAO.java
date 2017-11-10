package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.util.Vector;

public interface LibraryItemDAO {

	public Vector<LibraryItem> getItems (LibraryItem i);
	
}
