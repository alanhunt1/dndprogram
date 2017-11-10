package initcheck.dungeon;

import initcheck.database.Tag;

import java.io.Serializable;

public class Square implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	int x;

	int y;

	int z;

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Square>\n");
		sb.append("<XPos>" + x + "</XPos>\n");
		sb.append("<YPos>" + y + "</YPos>\n");
		sb.append("<ZPos>" + z + "</ZPos>\n");
		sb.append("</Square>\n");
		return sb.toString();
	}

	public Square(String s) {

		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();

			if (t.getTagName().equals("XPos")) {
				x = Integer.parseInt(t.getTagBody());
			}
			if (t.getTagName().equals("YPos")) {
				y = Integer.parseInt(t.getTagBody());
			}
			if (t.getTagName().equals("ZPos")) {
				z = Integer.parseInt(t.getTagBody());
			}

			s = s.substring(tagName.length() + tagName.length() + 5
					+ tagBody.length(), s.length());
			t = Tag.getTag(s);
		}
	}

	public Object clone() {
		Square clone = new Square(x, y, z);
		return clone;
	}

	public Square incX() {
		return new Square(x + 1, y, z);
	}

	public Square incY() {
		return new Square(x, y + 1, z);
	}

	public Square incZ() {
		return new Square(x, y, z + 1);
	}

	public Square decX() {
		return new Square(x - 1, y, z);
	}

	public Square decY() {
		return new Square(x, y - 1, z);
	}

	public Square decZ() {
		return new Square(x, y, z - 1);
	}

	/**
	 * Get the Z value.
	 * 
	 * @return the Z value.
	 */
	public int getZ() {
		return z;
	}

	/**
	 * Set the Z value.
	 * 
	 * @param newZ
	 *            The new Z value.
	 */
	public void setZ(int newZ) {
		this.z = newZ;
	}

	/**
	 * Get the Y value.
	 * 
	 * @return the Y value.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Set the Y value.
	 * 
	 * @param newY
	 *            The new Y value.
	 */
	public void setY(int newY) {
		this.y = newY;
	}

	/**
	 * Get the X value.
	 * 
	 * @return the X value.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Set the X value.
	 * 
	 * @param newX
	 *            The new X value.
	 */
	public void setX(int newX) {
		this.x = newX;
	}

	public Square(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

}
