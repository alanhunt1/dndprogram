package initcheck.character.library;

import initcheck.database.Campaign;
import initcheck.database.SpecificWeapon;
import initcheck.database.Wonderous;
import initcheck.database.WonderousItemsDAO;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class WonderousItemsLibrary implements LibraryData {

		WonderousItemsDAO db = new WonderousItemsDAO();

		public WonderousItemsLibrary(){

		}
		public Vector<LibraryItem> castToLibrary(Vector<Wonderous> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (Wonderous a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		public Vector<LibraryItem> getListing(){
				return castToLibrary(db.getWonderousItems());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return castToLibrary(db.getWonderousItems(s));
		}
		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return castToLibrary(db.getWonderousItems(s,c));
		}
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateWonderousItemPanel cfp = new CreateWonderousItemPanel((Wonderous)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateWonderousItemPanel cfp = new CreateWonderousItemPanel(parent);
		}

		public void deleteListing(Object listing){
				Wonderous f  = (Wonderous)listing;
				db.deleteWonderousItems(f);
		}
		public void copyListing(Object listing){
			Wonderous f  = (Wonderous)listing;
			f.setId(null);
			db.addWonderousItems(f);
		}
		public ListCellRenderer getCellRenderer(){
				return new LibraryItemCellRenderer();
		}
}
