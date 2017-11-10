package initcheck;

import java.io.Serializable;

import initcheck.database.Monster;
import initcheck.database.Tag;
import initcheck.graphics.TiledListItem;

public class MonsterGroup implements Cloneable, Serializable, EncounterElement, TiledListItem {

	static final long serialVersionUID = 1;

	private Monster m;

	int count = 0;
	int startIndex = 0;
	int endIndex = 0;
	
	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public String getType(){
		return m.getPType();
	}
	
	public String getName(){
		return m.getName();
	}
	
	public String serverDisplayFormat(){
		return m.name + startIndex + " to " + endIndex;
	}
	
	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<MonsterGroup>\n");
		sb.append(m.exportFormat());
		sb.append("<Count>" + count + "</Count>\n");
		sb.append("</MonsterGroup>\n");
		return sb.toString();
	}

	public Object clone() {
		MonsterGroup clone = new MonsterGroup((Monster)m.clone(), count, startIndex, endIndex);
		
		return clone;
	}
	
	public MonsterGroup(String s) {

		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();

			if (t.getTagName().equals("Monster")) {
				m = new Monster(t.getTagBody());
			}
			if (t.getTagName().equals("Count")) {
				count = Integer.parseInt(t.getTagBody());
			}

			s = s.substring(tagName.length() + tagName.length() + 5
					+ tagBody.length(), s.length());
			t = Tag.getTag(s);
		}
	}

	/**
	 * Get the Count value.
	 * 
	 * @return the Count value.
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Set the Count value.
	 * 
	 * @param newCount
	 *            The new Count value.
	 */
	public void setCount(int newCount) {
		this.count = newCount;
	}

	/**
	 * Get the M value.
	 * 
	 * @return the M value.
	 */
	public Monster getM() {
		return m;
	}

	/**
	 * Set the M value.
	 * 
	 * @param newM
	 *            The new M value.
	 */
	public void setM(Monster newM) {
		this.m = newM;
	}

	public MonsterGroup(Monster m, int count) {
		this.m = m;
		this.count = count;
	}

	public MonsterGroup(Monster m, int count, int startIndex, int endIndex){
		this.m = m; 
		this.count = count;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}
	
	public String toString() {
		if (m != null) {
			return count + " " + m.getName() + " (" + m.getChallengeRating()
					+ ")";
		}
		return "INVALID";
	}

}
