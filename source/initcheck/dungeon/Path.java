package initcheck.dungeon;

import java.io.Serializable;
import java.util.ArrayList;

public class Path implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	public ArrayList<Square> nodes = new ArrayList<Square>();

	int cost;

	public ArrayList<Square> getNodes() {
		return nodes;
	}

	@SuppressWarnings("unchecked")
	public Object clone() {
		Path clone = new Path();
		clone.nodes = (ArrayList<Square>) nodes.clone();
		clone.cost = cost;
		return clone;
	}

	public Object get(int i) {
		return nodes.get(i);
	}

	public boolean contains(Square s1) {
		for (int i = 0; i < nodes.size(); i++) {
			Square s2 = (Square) nodes.get(i);
			if (s1.getX() == s2.getX() && s1.getY() == s2.getY()
					&& s1.getZ() == s2.getZ()) {
				return true;
			}
		}
		return false;
	}

	public int size() {
		return nodes.size();
	}

	public void add(Square s) {
		nodes.add(s);
		cost++;
	}

	/**
	 * Get the Cost value.
	 * 
	 * @return the Cost value.
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Set the Cost value.
	 * 
	 * @param newCost
	 *            The new Cost value.
	 */
	public void setCost(int newCost) {
		this.cost = newCost;
	}

	public String toString() {
		String str = "";
		for (int i = 0; i < nodes.size(); i++) {
			Square s = (Square) nodes.get(i);
			str += s.getX() + "," + s.getY() + "," + s.getZ() + "->";
		}
		return str;
	}

}
