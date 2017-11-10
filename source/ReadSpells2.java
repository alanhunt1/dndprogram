
import initcheck.database.Spell;
import initcheck.database.SpellClass;
import initcheck.database.SpellClassesDAO;
import initcheck.database.SpellDAO;

import java.util.Vector;

public class ReadSpells2 {

	String filename;

	/**
	 * Get the Filename value.
	 * 
	 * @return the Filename value.
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Set the Filename value.
	 * 
	 * @param newFilename
	 *            The new Filename value.
	 */
	public void setFilename(String newFilename) {
		this.filename = newFilename;
	}

	
	public void process() {
		Vector<Spell> v = new Vector<Spell>();
		
		SpellDAO sdb = new SpellDAO();
		SpellClassesDAO scdb = new SpellClassesDAO();
		v = sdb.getSpells();
		
		for (int i = 0; i < v.size(); i++){
			Spell s = (Spell)v.get(i);
			String levels = s.getLevel();
			String [] split = levels.split(",");
			for (int j = 0; j < split.length; j++){
			
				String token = split[j].trim();
				
				if (token.indexOf("anger (MCook; 1/3)") >= 0){
				String className = token.substring(0, token.indexOf(")"));
				String classLevel = token.substring(token.indexOf(")")+1, token.length());
				SpellClass sc = new SpellClass();
				sc.setSpellClass(className);
				sc.setSpellLevel(classLevel);
				sc.setSpellId(s.getId());
				scdb.addSpellClasses(sc);
				}
			}
		}
	}

	/**
	 * Describe <code>main</code> method here.
	 * 
	 * @param args
	 *            a <code>String[]</code> value
	 */
	public static void main(String[] args) {
		final ReadSpells2 app = new ReadSpells2();

		if (args.length > 0) {
			if (args[0] != null) {
				app.setFilename(args[0]);
			}
		}

		app.process();
	}

}
