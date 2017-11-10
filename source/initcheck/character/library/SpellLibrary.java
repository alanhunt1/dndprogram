package initcheck.character.library;

import initcheck.database.Campaign;
import initcheck.database.Domain;
import initcheck.database.Spell;
import initcheck.database.SpellDAO;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class SpellLibrary implements LibraryData {

		SpellDAO db = new SpellDAO();

		public SpellLibrary(){

		}
		public Vector<LibraryItem> castToLibrary(Vector<Spell> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (Spell a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		public Vector<LibraryItem> getListing(){
				return castToLibrary(db.getSpells());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return castToLibrary(db.getSpells(s));
		}
		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return castToLibrary(db.getSpells(s,c));
		}
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateSpellPanel cfp = new CreateSpellPanel((Spell)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateSpellPanel cfp = new CreateSpellPanel(parent);
		}

		public void deleteListing(Object listing){
				Spell f  = (Spell)listing;
				db.deleteSpell(f);
		}
		public void copyListing(Object listing){
			Spell f  = (Spell)listing;
			f.setId(null);
			db.addSpell(f);
		}
		public ListCellRenderer getCellRenderer(){
				return new LibraryItemCellRenderer();
		}
}
