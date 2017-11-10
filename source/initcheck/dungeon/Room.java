package initcheck.dungeon;

import java.io.Serializable;
import java.util.Vector;

/*
 * Room.java
 *
 * Encapsulates information about a dungeon room
 */

import initcheck.MonsterGroup;
import initcheck.database.Tag;
import initcheck.graphics.TiledListItem;

public class Room implements Serializable, Cloneable, TiledListItem {

	private static final long serialVersionUID = 1L;

	int level;

	String title;

	String text = "";

	int top;

	int left;

	int bottom;

	int right;

	Vector<Square> doors = new Vector<Square>();

	Vector<MonsterGroup> monsters = new Vector<MonsterGroup>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Room>\n");
		sb.append("<Level>" + level + "</Level>\n");
		sb.append("<Title>" + title + "</Title>\n");
		sb.append("<Text>" + text + "</Text>\n");
		sb.append("<Top>" + top + "</Top>\n");
		sb.append("<Left>" + left + "</Left>\n");
		sb.append("<Bottom>" + bottom + "</Bottom>\n");
		sb.append("<Right>" + right + "</Right>\n");
		for (int i = 0; i < doors.size(); i++) {
			sb.append(((Square) doors.get(i)).exportFormat());
		}
		for (int i = 0; i < monsters.size(); i++) {
			sb.append(((MonsterGroup) monsters.get(i)).exportFormat());
		}
		sb.append("</Room>\n");
		return sb.toString();
	}

	public String toString(){
		return "Level "+level+", "+title;
	}
	
	public Room(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();

			if (t.getTagName().equals("Level")) {
				level = Integer.parseInt(t.getTagBody());
			}
			if (t.getTagName().equals("Title")) {
				title = t.getTagBody();
			}
			if (t.getTagName().equals("Text")) {
				text = t.getTagBody();
			}
			if (t.getTagName().equals("Top")) {
				top = Integer.parseInt(t.getTagBody());
			}
			if (t.getTagName().equals("Left")) {
				left = Integer.parseInt(t.getTagBody());
			}
			if (t.getTagName().equals("Bottom")) {
				bottom = Integer.parseInt(t.getTagBody());
			}
			if (t.getTagName().equals("Right")) {
				right = Integer.parseInt(t.getTagBody());
			}
			if (t.getTagName().equals("MonsterGroup")) {
				monsters.add(new MonsterGroup(t.getTagBody()));
			}
			if (t.getTagName().equals("Square")) {
				doors.add(new Square(t.getTagBody()));
			}
			s = s.substring(tagName.length() + tagName.length() + 5
					+ tagBody.length(), s.length());
			t = Tag.getTag(s);
		}
	}

	
	public Object clone() {
		Room clone = new Room();
		clone.level = level;
		clone.title = title;
		clone.text = text;
		clone.top = top;
		clone.left = left;
		clone.bottom = bottom;
		clone.right = right;
		Vector<Square> dclone = new Vector<Square>();
		for (Square s:doors) {
			dclone.add((Square)s.clone());
		}
		clone.doors = dclone;
		
		Vector<MonsterGroup> mgclone = new Vector<MonsterGroup>();
		for (MonsterGroup mg:monsters) {
			mgclone.add((MonsterGroup)mg.clone());
		}
		clone.monsters = mgclone;
		return clone;
	}

	/**
	 * Get the Monsters value.
	 * 
	 * @return the Monsters value.
	 */
	public Vector<MonsterGroup> getMonsters() {
		return monsters;
	}

	/**
	 * Set the Monsters value.
	 * 
	 * @param newMonsters
	 *            The new Monsters value.
	 */
	public void setMonsters(Vector<MonsterGroup> newMonsters) {
		this.monsters = newMonsters;
	}

	/**
	 * Get the Text value.
	 * 
	 * @return the Text value.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Set the Text value.
	 * 
	 * @param newText
	 *            The new Text value.
	 */
	public void setText(String newText) {
		this.text = newText;
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

	public void addDoor(int x, int y) {
		Square door = new Square(x, y, 0);
		doors.add(door);
	}

	public void removeDoor(int x, int y) {
		for (int i = 0; i < doors.size(); i++) {
			Square door = (Square) doors.get(i);
			if (door.getX() == x || door.getY() == y) {
				doors.remove(i);
				return;
			}
		}
	}

	
	public Vector<Square> getDoors() {
		return doors;
	}

	public boolean hasDoor(int x, int y) {
		for (int i = 0; i < doors.size(); i++) {
			Square door = (Square) doors.get(i);
			if (door.getX() == x || door.getY() == y) {
				return true;
			}
		}
		return false;
	}

	public int getWidth() {
		if (right - left < 1) {
			return left - right;
		}
		return right - left;
	}

	public int getHeight() {
		if (bottom - top < 1) {
			return top - bottom;
		}
		return bottom - top;
	}

	/**
	 * Get the Right value.
	 * 
	 * @return the Right value.
	 */
	public int getRight() {
		return right;
	}

	/**
	 * Set the Right value.
	 * 
	 * @param newRight
	 *            The new Right value.
	 */
	public void setRight(int newRight) {
		this.right = newRight;
	}

	/**
	 * Get the Bottom value.
	 * 
	 * @return the Bottom value.
	 */
	public int getBottom() {
		return bottom;
	}

	/**
	 * Set the Bottom value.
	 * 
	 * @param newBottom
	 *            The new Bottom value.
	 */
	public void setBottom(int newBottom) {
		this.bottom = newBottom;
	}

	/**
	 * Get the Left value.
	 * 
	 * @return the Left value.
	 */
	public int getLeft() {
		return left;
	}

	/**
	 * Set the Left value.
	 * 
	 * @param newLeft
	 *            The new Left value.
	 */
	public void setLeft(int newLeft) {
		this.left = newLeft;
	}

	/**
	 * Get the Top value.
	 * 
	 * @return the Top value.
	 */
	public int getTop() {
		return top;
	}

	/**
	 * Set the Top value.
	 * 
	 * @param newTop
	 *            The new Top value.
	 */
	public void setTop(int newTop) {
		this.top = newTop;
	}

	public Room() {

	}

	public Room(int top, int bottom, int left, int right, int z, String name,
			String desc) {
		this.text = desc;
		this.title = name;
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
		level = z;
	}

	public Room(int x, int y, int z) {
		top = bottom = y;
		left = right = x;
		level = z;
	}

}
