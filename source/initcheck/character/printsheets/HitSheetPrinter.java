package initcheck.character.printsheets;

import initcheck.DCharacter;
import initcheck.character.ClassSlot;
import initcheck.utils.DateUtil;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

public class HitSheetPrinter extends PrintPage {

	private static final long serialVersionUID = 1L;

	Vector<DCharacter> chars = new Vector<DCharacter>();

	Color color = Color.gray;

	Font sansPlain5 = new Font("SansSerif", Font.PLAIN, 5);

	Font sansPlain6 = new Font("SansSerif", Font.PLAIN, 6);

	Font sansPlain8 = new Font("SansSerif", Font.PLAIN, 8);

	Font sansPlain10 = new Font("SansSerif", Font.PLAIN, 10);

	Font sansPlain12 = new Font("SansSerif", Font.PLAIN, 12);

	Font sansPlain14 = new Font("SansSerif", Font.PLAIN, 14);

	Font sansBold8 = new Font("SansSerif", Font.BOLD, 8);

	Font sansBold14 = new Font("SansSerif", Font.BOLD, 14);

	Font sansBold10 = new Font("SansSerif", Font.BOLD, 10);

	Font sansBold12 = new Font("SansSerif", Font.BOLD, 12);

	Font sansBold18 = new Font("SansSerif", Font.BOLD, 18);

	Font sansBold20 = new Font("SansSerif", Font.BOLD, 30);

	Font sansItalic8 = new Font("SansSerif", Font.ITALIC, 8);

	String party = "GOOD";

	/**
	 * Get the Party value.
	 * 
	 * @return the Party value.
	 */
	public String getParty() {
		return party;
	}

	/**
	 * Set the Party value.
	 * 
	 * @param newParty
	 *            The new Party value.
	 */
	public void setParty(String newParty) {
		this.party = newParty;
	}

	/**
	 * Get the Color value.
	 * 
	 * @return the Color value.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Set the Color value.
	 * 
	 * @param newColor
	 *            The new Color value.
	 */
	public void setColor(Color newColor) {
		this.color = newColor;
	}

	/**
	 * Get the Chars value.
	 * 
	 * @return the Chars value.
	 */
	public Vector<DCharacter> getChars() {
		return chars;
	}

	/**
	 * Set the Chars value.
	 * 
	 * @param newChars
	 *            The new Chars value.
	 */
	public void setChars(Vector<DCharacter> newChars) {
		this.chars = newChars;
	}

	public HitSheetPrinter() {

	}

	public HitSheetPrinter(Vector<DCharacter> v) {
		chars = v;
	}

	public void paintImage(Graphics ig) {
		if (ig == null) {
			if (icon == null || icon.getImage() == null
					|| icon.getImage().getGraphics() == null) {
				return;
			}
			ig = icon.getImage().getGraphics();
		}

		// cast to a graphics2d object for performance improvements
		Graphics2D g = (Graphics2D) ig;

		g.setColor(Color.white);
		g.fill(new Rectangle(minx, miny, maxx, maxy));

		int ypos = miny;
		drawHitSheetHeader(g, minx, ypos);
		ypos += 30;

		for (int i = 0; i < chars.size(); i++) {
			drawHitSheetRow(g, (DCharacter) chars.get(i), minx, ypos);
			ypos += 49;
		}

	}

	public void drawHitSheetHeader(Graphics2D g, int x, int y) {
		g.setColor(color);
		g.fill(new Rectangle(x, y, maxx, 30));
		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 200, 30));
		g.draw(new Rectangle(x + 200, y, 60, 30));
		g.draw(new Rectangle(x + 260, y, 60, 30));
		g.draw(new Rectangle(x + 320, y, 120, 30));
		g.draw(new Rectangle(x + 440, y, 60, 30));
		g.draw(new Rectangle(x + 500, y, 67, 30));

		g.setFont(sansBold14);
		g.drawString(
				DateUtil.getDateString(new java.util.Date(), "MM/dd/yyyy"),
				x + 5, y + 15);
		g.drawString("March", x + 5, y + 28);
		g.drawString("Character", x + 110, y + 28);

		g.setFont(sansBold10);
		g.drawString("Player", x + 202, y + 10);
		g.drawString("Move   Init", x + 202, y + 17);
		g.drawString("F    R    W", x + 202, y + 28);

		g.drawString("Level", x + 262, y + 10);
		g.drawString("AC", x + 300, y + 28);

		g.drawString("Misc", x + 365, y + 28);

		g.drawString("HP", x + 462, y + 28);
		g.drawString(party, x + 515, y + 28);
	}

	public void drawHitSheetRow(Graphics2D g, DCharacter dc, int x, int y) {

		g.setColor(Color.black);

		g.draw(new Rectangle(x, y, 200, 49));
		g.draw(new Rectangle(x + 200, y, 60, 49));
		g.draw(new Rectangle(x + 260, y, 60, 49));
		g.draw(new Rectangle(x + 320, y, 120, 49));
		g.draw(new Rectangle(x + 440, y, 60, 49));
		g.draw(new Rectangle(x + 500, y, 67, 49));

		g.setFont(sansBold18);
		g.drawString(format(dc.getName()), x + 2, y + 20);
		g.setFont(sansBold10);
		g.drawString(format(dc.getMarchOrder()), x + 2, y + 48);
		g.drawString(format(dc.getSleepShift()) + " Shift", x + 30, y + 48);
		g.drawString(format(dc.getRace()), x + 70, y + 48);
		if (dc.getPlayerName() != null) {
			g.drawString(format(dc.getPlayerName()), x + 202, y + 10);
		}
		g.drawString(dc.getBaseMovement() + "    " + dc.getMod(), x + 202,
				y + 25);
		g.drawString((dc.calcFortitudeSave().getDisplayValue()) + "  "
				+ dc.calcReflexSave().getDisplayValue() + "  "
				+ dc.calcWillSave().getDisplayValue(), x + 202, y + 48);

		Vector<ClassSlot> v = dc.getClassSet().getClassVector();
		int ypos = 10;

		for (int i = 0; i < v.size(); i++) {
			ClassSlot cs = (ClassSlot) v.get(i);
			g.drawString(
					cs.getClassName().getCharClass() + " " + cs.getLevel(),
					x + 262, y + ypos);
			ypos += 10;
		}

		g.drawString("" + dc.getAC() + "/" + dc.getRestAc() + "/"
				+ dc.getTouchAc(), x + 262, y + 48);

		g.setFont(sansBold20);

		int hp = Integer.parseInt(dc.getHpCalc().getDisplayValue());
		int stun = hp / 5;
		if (hp % 5 != 0) {
			stun++;
		}
		g.drawString("" + hp, x + 450, y + 40);
		g.setFont(sansBold10);
		g.drawString("" + stun, x + 488, y + 48);
	}

	public String format(String s) {
		if (s == null || s.equals("null")) {
			return "";
		}
		return s;
	}

}
