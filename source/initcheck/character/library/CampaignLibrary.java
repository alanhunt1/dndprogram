package initcheck.character.library;

import initcheck.character.GenericListCellRenderer;
import initcheck.database.Campaign;
import initcheck.database.CampaignDAO;
import initcheck.database.CampaignSource;
import initcheck.database.CampaignSourceDAO;
import initcheck.database.Spell;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class CampaignLibrary implements LibraryData {

		CampaignDAO db = new CampaignDAO();

		public CampaignLibrary(){

		}
		public Vector<LibraryItem> castToLibrary(Vector<Campaign> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (Campaign a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		public Vector<LibraryItem> getListing(){
				return castToLibrary(db.getCampaigns());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return getListing();
		}
		
		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return getListing();
		}

		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateCampaignPanel cfp = new CreateCampaignPanel((Campaign)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateCampaignPanel cfp = new CreateCampaignPanel(parent);
		}

		public void deleteListing(Object listing){
				Campaign f  = (Campaign)listing;
				db.deleteCampaign(f);
		}
		
		public ListCellRenderer getCellRenderer(){
				return new GenericListCellRenderer();
		}
		
		public void copyListing(Object listing){
			Campaign f  = (Campaign)listing;
			String name = f.getName();
			String id = f.getId();
			f.setId(null);
			f.setName(name+" copy");
			int newId = db.addOrUpdateCampaign(f);
			f.setId(id);
			f.setName(name);
			
			// add the valid sources
			CampaignSourceDAO csdb = new CampaignSourceDAO();
			Vector<CampaignSource> sources = csdb.getCampaignSources(id);
			for (int i = 0; i < sources.size(); i++){
				CampaignSource cs = sources.get(i);
				cs.setCampaignId(""+newId);
				csdb.addCampaignSource(cs);
			}
		}
}
