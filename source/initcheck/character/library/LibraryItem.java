package initcheck.character.library;

import initcheck.database.Exportable;
import initcheck.graphics.TiledListItem;

public interface LibraryItem extends Exportable, TiledListItem{
		
		public String getDescription();
		public String getFullDescription();
		public String listFormat();
		public String getSource();
		public String getId();
		public String getName();
}
