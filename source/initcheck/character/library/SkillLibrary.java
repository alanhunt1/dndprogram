package initcheck.character.library;

import initcheck.database.Campaign;
import initcheck.database.Services;
import initcheck.database.Skill;
import initcheck.database.SkillDAO;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class SkillLibrary implements LibraryData {

		
		SkillDAO sdb = new SkillDAO();
		
		public SkillLibrary(){

		}
		public Vector<LibraryItem> castToLibrary(Vector<Skill> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (Skill a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		public Vector<LibraryItem> getListing(){
				return castToLibrary(sdb.getSkills());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return castToLibrary(sdb.getSkills(s));
		}
		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return castToLibrary(sdb.getSkills(s, c));
		}
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateSkillsPanel cfp = new CreateSkillsPanel((Skill)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateSkillsPanel cfp = new CreateSkillsPanel(parent);
		}

		public void deleteListing(Object listing){
				Skill f  = (Skill)listing;
				sdb.deleteSkill(f);
		}
		public void copyListing(Object listing){
			Skill f  = (Skill)listing;
			String id = f.getId();
			String name = f.getSkill();
			f.setId(null);
			f.setSkill(name+" copy");
			sdb.addSkill(f);
			f.setId(id);
			f.setSkill(name);
		}
		public ListCellRenderer getCellRenderer(){
				return new SkillCellRenderer();
		}
}
