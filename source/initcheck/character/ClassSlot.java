package initcheck.character;

import java.io.Serializable;

import initcheck.database.CharClass;
import initcheck.database.CharClassDAO;
import initcheck.database.Tag;
import initcheck.graphics.TiledListItem;

public class ClassSlot implements Serializable, TiledListItem {

	private static final long serialVersionUID = 1L;

	CharClass className;

	int level;

	String instanceId;

	int virtualLevel;

	int skillRankMod;

	
	public int getSkillRankMod() {
		return skillRankMod;
	}

	public void setSkillRankMod(int skillRankMod) {
		this.skillRankMod = skillRankMod;
	}

	public ClassSlot(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();

			if (t.getTagName().equals("ClassId")) {
				CharClassDAO ccdb = new CharClassDAO();

				CharClass c = ccdb.getCharClass(t.getTagBody());
				// c.setId(t.getTagBody());
				setClassName(c);
			}
			if (t.getTagName().equals("Level")) {
				setLevel(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("VirtualLevel")) {
				setVirtualLevel(Integer.parseInt(t.getTagBody()));
			}
			s = s.substring(tagName.length() + tagName.length() + 5
					+ tagBody.length(), s.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<ClassSlot>");
		sb.append("<ClassId>" + className.getId() + "</ClassId>");
		sb.append("<Level>" + level + "</Level>");
		sb.append("<VirtualLevel>" + virtualLevel + "</VirtualLevel>");
		sb.append("<InstanceId>" + instanceId + "</InstanceId>");
		sb.append("</ClassSlot>\n");
		return sb.toString();
	}

	/**
	 * Get the InstanceId value.
	 * 
	 * @return the InstanceId value.
	 */
	public String getInstanceId() {
		return instanceId;
	}

	/**
	 * Set the InstanceId value.
	 * 
	 * @param newInstanceId
	 *            The new InstanceId value.
	 */
	public void setInstanceId(String newInstanceId) {
		this.instanceId = newInstanceId;
	}

	public ClassSlot() {

	}

	public String toString() {
		return className + " " + level;
	}

	/**
	 * Get the Level value.
	 * 
	 * @return the Level value.
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Set the Level value.
	 * 
	 * @param newLevel
	 *            The new Level value.
	 */
	public void setLevel(int newLevel) {
		this.level = newLevel;
	}

	/**
	 * Get the ClassName value.
	 * 
	 * @return the ClassName value.
	 */
	public CharClass getClassName() {
		return className;
	}

	/**
	 * Set the ClassName value.
	 * 
	 * @param newClassName
	 *            The new ClassName value.
	 */
	public void setClassName(CharClass newClassName) {
		this.className = newClassName;
	}

	/**
	 * Get the VirtualLevel value.
	 * 
	 * @return the VirtualLevel value.
	 */
	public int getVirtualLevel() {
		return virtualLevel;
	}

	/**
	 * Set the VirtualLevel value.
	 * 
	 * @param newVirtualLevel
	 *            The new VirtualLevel value.
	 */
	public void setVirtualLevel(int newVirtualLevel) {
		this.virtualLevel = newVirtualLevel;
	}
}
