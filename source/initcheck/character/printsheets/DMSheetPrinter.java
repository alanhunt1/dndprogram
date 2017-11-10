package initcheck.character.printsheets;

import initcheck.DCharacter;
import initcheck.character.ClassSlot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

import util.Rounding;

public class DMSheetPrinter extends PrintPage {

	
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

	double avgAc = 0;

	double avgRestAc = 0;

	double avgTouchAc = 0;

	double avgHp = 0;

	double avgFort = 0;

	double avgRef = 0;

	double avgWill = 0;

	double avgInit = 0;

	double avgLevel = 0;

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
		updateImage();
		repaint();
	}

	public DMSheetPrinter() {

	}

	public DMSheetPrinter(Vector<DCharacter> v) {
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

		ypos += 40;

		for (int i = 0; i < chars.size(); i++) {
			drawDMSheetRow(g, (DCharacter) chars.get(i), minx, ypos);
			ypos += 30;
		}

		int partySize = chars.size();
		
		
		avgAc = avgAc / partySize;
		avgRestAc = avgRestAc / partySize;
		avgTouchAc = avgTouchAc / partySize;
		avgHp = avgHp / partySize;
		avgFort = avgFort / partySize;
		avgRef = avgRef / partySize;
		avgWill = avgWill / partySize;
		avgInit = avgInit / partySize;
		avgLevel = avgLevel / partySize;
		drawDMSheetHeader(g, minx, miny);

	}

	public void drawDMSheetHeader(Graphics2D g, int x, int y) {
		g.setColor(color);
		g.fill(new Rectangle(x, y, maxx, 40));
		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, maxx, 10));
		g.draw(new Rectangle(x, y + 10, maxx, 30));

		g.draw(new Rectangle(x, y + 10, 150, 30));
		g.draw(new Rectangle(x + 150, y + 25, 40, 15));
		g.draw(new Rectangle(x + 190, y + 25, 30, 15));
		g.draw(new Rectangle(x + 220, y + 25, 30, 15));
		g.draw(new Rectangle(x + 250, y + 10, 40, 30));
		g.draw(new Rectangle(x + 290, y + 25, 20, 15));
		g.draw(new Rectangle(x + 310, y + 25, 20, 15));
		g.draw(new Rectangle(x + 330, y + 25, 20, 15));
		g.draw(new Rectangle(x + 350, y + 10, 20, 30));
		g.draw(new Rectangle(x + 370, y + 10, 25, 30));
		g.draw(new Rectangle(x + 395, y + 10, 65, 30));
		g.draw(new Rectangle(x + 460, y + 10, 30, 30));
		g.draw(new Rectangle(x + 490, y + 10, 30, 30));
		g.draw(new Rectangle(x + 520, y + 10, 47, 30));

		g.setFont(sansBold10);
		g.drawString(party, x + 5, y + 8);
		g.drawString(Rounding.toString(avgAc, 1), x + 152, y + 8);
		g.drawString(Rounding.toString(avgRestAc, 1), x + 192, y + 8);
		g.drawString(Rounding.toString(avgTouchAc, 1), x + 222, y + 8);
		g.drawString(Rounding.toString(avgHp, 1), x + 252, y + 8);
		g.drawString(Rounding.toString(avgFort, 1), x + 292, y + 8);
		g.drawString(Rounding.toString(avgRef, 1), x + 312, y + 8);
		g.drawString(Rounding.toString(avgWill, 1), x + 332, y + 8);
		g.drawString(Rounding.toString(avgInit, 1), x + 352, y + 8);
		g.drawString(Rounding.toString(avgLevel, 1), x + 372, y + 8);

		g.drawString(initcheck.utils.DateUtil.getDateString(
				new java.util.Date(), "MM/dd/yyyy"), x + 397, y + 8);
		g.drawString("Avg", x + 520, y + 8);

		g.setFont(sansBold14);
		g.drawString(chars.size() + " Characters", x + 10, y + 38);

		g.setFont(sansBold10);
		g.drawString("Armor Class", x + 160, y + 23);
		g.drawString("Armor", x + 152, y + 38);
		g.setFont(sansBold8);
		g.drawString("@Rest", x + 192, y + 38);
		g.drawString("Touch", x + 222, y + 38);
		g.setFont(sansBold10);
		g.drawString("HP", x + 252, y + 38);
		g.drawString("Saves", x + 300, y + 23);
		g.drawString("F", x + 292, y + 38);
		g.drawString("R", x + 312, y + 38);
		g.drawString("W", x + 332, y + 38);
		g.drawString("Init", x + 352, y + 38);
		g.drawString("Lvl", x + 372, y + 38);
		g.drawString("Class", x + 397, y + 38);

		g.drawString("Sleep", x + 462, y + 28);
		g.drawString("Shift", x + 462, y + 38);

		g.setFont(sansBold8);
		g.drawString("March", x + 492, y + 28);
		g.drawString("Order", x + 492, y + 38);
		g.setFont(sansBold10);
		g.drawString("Player", x + 523, y + 38);
	}

	public void drawDMSheetRow(Graphics2D g, DCharacter dc, int x, int y) {

		g.setColor(Color.black);

		g.draw(new Rectangle(x, y, 150, 30));
		g.draw(new Rectangle(x + 150, y, 40, 30));
		g.draw(new Rectangle(x + 190, y, 30, 30));
		g.draw(new Rectangle(x + 220, y, 30, 30));
		g.draw(new Rectangle(x + 250, y, 40, 30));
		g.draw(new Rectangle(x + 290, y, 20, 30));
		g.draw(new Rectangle(x + 310, y, 20, 30));
		g.draw(new Rectangle(x + 330, y, 20, 30));
		g.draw(new Rectangle(x + 350, y, 20, 30));
		g.draw(new Rectangle(x + 370, y, 25, 30));
		g.draw(new Rectangle(x + 395, y, 65, 30));
		g.draw(new Rectangle(x + 460, y, 30, 30));
		g.draw(new Rectangle(x + 490, y, 30, 30));
		g.draw(new Rectangle(x + 520, y, 47, 30));

		g.setFont(sansBold18);
		g.drawString(format(dc.getName()), x + 5, y + 28);

		int ac = dc.getAC();
		avgAc += ac;
		g.setFont(sansBold18);
		g.drawString("" + ac, x + 154, y + 28);

		int restAc = dc.getRestAc();
		avgRestAc += restAc;
		g.setFont(sansBold14);
		g.drawString("" + restAc, x + 192, y + 28);

		int touchAc = dc.getTouchAc();
		avgTouchAc += touchAc;
		g.drawString("" + touchAc, x + 222, y + 28);

		int hp = Integer.parseInt(dc.getHpCalc().getDisplayValue());
		avgHp += hp;
		g.setFont(sansBold18);
		g.drawString("" + hp, x + 252, y + 28);

		int fort = Integer.parseInt(dc.calcFortitudeSave().getDisplayValue());
		avgFort += fort;
		g.setFont(sansBold10);
		g.drawString("" + fort, x + 292, y + 28);

		int ref = Integer.parseInt(dc.calcReflexSave().getDisplayValue());
		avgRef += ref;
		g.drawString("" + ref, x + 312, y + 28);

		int will = Integer.parseInt(dc.calcWillSave().getDisplayValue());
		avgWill += will;
		g.drawString("" + will, x + 332, y + 28);

		int init = dc.getMod();
		avgInit += init;
		g.drawString("" + init, x + 352, y + 28);

		int level = new Double(dc.getLevel()).intValue();
		avgLevel += level;
		g.drawString("" + level, x + 372, y + 28);

		g.setFont(sansPlain8);
		Vector<ClassSlot> v = dc.getClassSet().getClassVector();
		int ypos = 9;

		for (int i = 0; i < v.size(); i++) {
			ClassSlot cs = (ClassSlot) v.get(i);
			g.drawString(
					cs.getClassName().getCharClass() + " " + cs.getLevel(),
					x + 397, y + ypos);
			ypos += 10;
		}

		g.setFont(sansBold18);
		g.drawString("" + dc.getSleepShift(), x + 462, y + 28);
		g.drawString("" + dc.getMarchOrder(), x + 492, y + 28);
		g.setFont(sansBold10);
		g.drawString("" + dc.getPlayerName(), x + 522, y + 28);

	}

	public String format(String s) {
		if (s == null || s.equals("null")) {
			return "";
		}
		return s;
	}
}
