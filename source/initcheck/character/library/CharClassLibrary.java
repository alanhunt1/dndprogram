package initcheck.character.library;

import initcheck.database.Campaign;
import initcheck.database.CharClass;
import initcheck.database.CharClassDAO;
import initcheck.database.CharClassPrereq;
import initcheck.database.ClassAbilityXref;
import initcheck.database.ClassAbilityXrefDAO;
import initcheck.database.ClassAdvancement;
import initcheck.database.ClassAdvancementDAO;
import initcheck.database.ClassProficiency;
import initcheck.database.ClassProficiencyDAO;
import initcheck.database.ClassSkills;
import initcheck.database.ClassSkillsDAO;
import initcheck.database.Deity;
import initcheck.database.SpellLevel;
import initcheck.database.SpellLevelDAO;

import java.util.Vector;

import javax.swing.ListCellRenderer;

public class CharClassLibrary implements LibraryData {

		CharClassDAO db = new CharClassDAO();

		public CharClassLibrary(){

		}
		public Vector<LibraryItem> castToLibrary(Vector<CharClass> va){
			Vector<LibraryItem> vl = new Vector<LibraryItem>();
			for (CharClass a:va){
				vl.add((LibraryItem) a);
			}
			return vl;
		}
		public Vector<LibraryItem> getListing(){
				return castToLibrary(db.getClasses());
		}
		
		public Vector<LibraryItem> getSearchListing(String s){
				return castToLibrary(db.getClasses(s));
		}

		public Vector<LibraryItem> getSearchListing(String s, Campaign c){
			return castToLibrary(db.getClasses(s,c));
		}
		
		public void updateListing(Object listing, LibraryPanel parent){
				@SuppressWarnings("unused") CreateCharClassPanel cfp = new CreateCharClassPanel((CharClass)listing, parent);
		}

		public void addListing(LibraryPanel parent){
				@SuppressWarnings("unused") CreateCharClassPanel cfp = new CreateCharClassPanel(parent);
		}

		public void copyListing(Object listing){
			// copy the base record
			CharClass f  = (CharClass)listing;
			f.setName(f.getName()+" Copy");
			String oldId = f.getId();
			f.setId(null);
			int cid = db.addOrUpdateCharClass(f);
			f.setId(oldId);
			
			// copy the advancement records
			
			ClassAdvancementDAO cadb = new ClassAdvancementDAO();
			Vector<ClassAdvancement> advancement = cadb.getClassAdvancement(f.getId());
			for (int i = 0; i < advancement.size(); i++){
				ClassAdvancement ca = advancement.get(i);
				ca.setClassId(""+cid);
				cadb.addClassAdvancement(ca);
			}
			
			// copy the prereqs
			Vector<CharClassPrereq> prereqs = db.getCharClassPrereqs(f.getId());
			for (int i = 0; i < prereqs.size(); i++){
				CharClassPrereq ccp = prereqs.get(i);
				ccp.setId(null);
				ccp.setCharClassId(""+cid);
				db.addOrUpdateCharClassPrereq(ccp);
			}
			
			// copy class proficiencies
			
			ClassProficiencyDAO cpdb = new ClassProficiencyDAO();
			Vector<ClassProficiency> proficiencies = cpdb.getClassProficiency(f);
			for (int i = 0; i < proficiencies.size(); i++){
				ClassProficiency cp = proficiencies.get(i);
				cp.setClassId(""+cid);
				cpdb.addClassProficiency(cp);
			}
			
			// copy class skills
			
			ClassSkillsDAO csdb = new ClassSkillsDAO();
			Vector<ClassSkills> skills = csdb.getClassSkills(f.getId());
			for (int i = 0; i < skills.size(); i++ ){
				ClassSkills cs = skills.get(i);
				cs.setClassId(""+cid);
				csdb.addClassSkills(cs);
			}
			
			// copy spells
		
			SpellLevelDAO sldb = new SpellLevelDAO();
			Vector<SpellLevel> spellsKnown = sldb.getSpellsKnown(f.getId());
			for (int i = 0; i < spellsKnown.size(); i++){
				SpellLevel sl = spellsKnown.get(i);
				sl.setClassId(""+cid);
				sldb.addSpellLevel(sl);
			}
			
			Vector<SpellLevel> spellsPerDay = sldb.getSpellsPerDay(f.getId());
			for (int i = 0; i < spellsPerDay.size(); i++){
				SpellLevel sl = spellsPerDay.get(i);
				sl.setClassId(""+cid);
				sldb.addSpellLevel(sl);
			}
			
			// copy abilities
			
			ClassAbilityXrefDAO cabdb = new ClassAbilityXrefDAO();
			ClassAbilityXref cax = new ClassAbilityXref();
			cax.setClassId(f.getId());
			Vector<ClassAbilityXref> classAbilities = cabdb.selectClassAbilityXref(cax);
			for (int i = 0; i < classAbilities.size(); i++){
				
				cax = classAbilities.get(i);
				cax.setClassId(""+cid);
				cabdb.addClassAbilityXref(cax);
			}
			
		}
		
		public void deleteListing(Object listing){
				CharClass f  = (CharClass)listing;
				db.deleteCharClass(f);
		}
		
		public ListCellRenderer getCellRenderer(){
				return new CharClassCellRenderer();
		}
}
