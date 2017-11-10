package initcheck.database;

public class SpellClass {

	private String spellId;

	private String spellClass;

	private String spellLevel;

	private String id;

	public SpellClass(){
		
	}
	
	public SpellClass(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("SpellId")) {
				setSpellId(t.getTagBody());
			}
			if (t.getTagName().equals("SpellLevel")) {
				setSpellLevel(t.getTagBody());
			}
			if (t.getTagName().equals("SpellClassName")) {
				setSpellClass(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String toString(){
		return spellClass+" "+spellLevel;
	}
	
	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<SpellClass>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<SpellId>" + spellId + "</SpellId>\n");
		sb.append("<SpellLevel>" + spellLevel + "</SpellLevel>\n");
		sb.append("<SpellClassName>" + spellClass + "</SpellClassName>\n");
		sb.append("</SpellClass>\n");
		return sb.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSpellClass() {
		return spellClass;
	}

	public void setSpellClass(String spellClass) {
		this.spellClass = spellClass;
	}

	public String getSpellId() {
		return spellId;
	}

	public void setSpellId(String spellId) {
		this.spellId = spellId;
	}

	public String getSpellLevel() {
		return spellLevel;
	}

	public void setSpellLevel(String spellLevel) {
		this.spellLevel = spellLevel;
	}

}
