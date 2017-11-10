package initcheck.dungeon;

/*
 * Hexagon.java
 *
 * Created on February 25, 2004, 1:10 PM
 */
import java.awt.Graphics;
import java.awt.Polygon;

public class Hexagon extends Polygon {

	private static final long serialVersionUID = 1L;

	double side;

	double h, r;

	public Hexagon(double sidelength, int x, int y) {
		side = sidelength / 3;
		h = side;
		// h=0.5*side;
		// r=0.866025404*side;
		r = 0.5 * sidelength;
		createHexagon(side);
		translate(x, y);
	}

	public double getSide() {
		return side;
	}

	public double getR() {
		return r;
	}

	public double getH() {
		return h;
	}

	public void draw(Graphics g, int x, int y) {
		translate(x, y);

		translate(-x, -y);
	}

	public void fill(Graphics g, int x, int y) {
		translate(x, y);

		translate(-x, -y);
	}

	private void createHexagon(double side) {

		double topx = r;
		double topy = 0;
		double ulx = 0;
		double uly = h;
		double llx = 0;
		double lly = h + side;
		double urx = 2 * r;
		double ury = h;
		double lrx = 2 * r;
		double lry = h + side;
		double botx = r;
		double boty = (2 * h) + side;
		addPoint((int) topx, (int) topy);
		addPoint((int) urx, (int) ury);
		addPoint((int) lrx, (int) lry);
		addPoint((int) botx, (int) boty);
		addPoint((int) llx, (int) lly);
		addPoint((int) ulx, (int) uly);

	}
}
