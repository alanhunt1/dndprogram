package initcheck.character.library;

import initcheck.database.Campaign;
import initcheck.database.Materials;
import initcheck.database.MaterialsDAO;
import initcheck.database.SpecificArmor;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class MaterialsLibrary implements LibraryData {

	MaterialsDAO db = new MaterialsDAO();

	public MaterialsLibrary() {

	}

	public Vector<LibraryItem> castToLibrary(Vector<Materials> va){
		Vector<LibraryItem> vl = new Vector<LibraryItem>();
		for (Materials a:va){
			vl.add((LibraryItem) a);
		}
		return vl;
	}
	
	public Vector<LibraryItem> getListing() {
		return castToLibrary(db.getMaterials());
	}

	public Vector<LibraryItem> getSearchListing(String s) {
		return castToLibrary(db.getMaterials(s));
	}

	public Vector<LibraryItem> getSearchListing(String s, Campaign c) {
		return castToLibrary(db.getMaterials(s,c));
	}

	public void updateListing(Object listing, LibraryPanel parent) {
		@SuppressWarnings("unused")
		CreateMaterialsPanel cfp = new CreateMaterialsPanel(
				(Materials) listing, parent);
	}

	public void addListing(LibraryPanel parent) {
		@SuppressWarnings("unused")
		CreateMaterialsPanel cfp = new CreateMaterialsPanel(parent);
	}

	public void deleteListing(Object listing) {
		Materials f = (Materials) listing;
		db.deleteMaterials(f);
	}

	public void copyListing(Object listing) {
		Materials f = (Materials) listing;
		f.setId(null);
		db.addMaterials(f);
	}

	public ListCellRenderer getCellRenderer() {
		return new LibraryItemCellRenderer();
	}
}
