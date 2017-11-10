package initcheck.character.printsheets;

import initcheck.DCharacter;
import initcheck.database.PlayerSpells;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

public class SpellSheetPrinter extends CharacterSheet {

	private static final long serialVersionUID = 1L;

	Vector<PlayerSpells> spells;

	Vector<PlayerSpells> memorized;

	int sheetCount;

	int numSheets;

	int ypos = 0;

	/**
	 * Get the NumSheets value.
	 * 
	 * @return the NumSheets value.
	 */
	public int getNumSheets() {
		return numSheets;
	}

	/**
	 * Set the NumSheets value.
	 * 
	 * @param newNumSheets
	 *            The new NumSheets value.
	 */
	public void setNumSheets(int newNumSheets) {
		this.numSheets = newNumSheets;
	}

	/**
	 * Get the SheetCount value.
	 * 
	 * @return the SheetCount value.
	 */
	public int getSheetCount() {
		return sheetCount;
	}

	/**
	 * Set the SheetCount value.
	 * 
	 * @param newSheetCount
	 *            The new SheetCount value.
	 */
	public void setSheetCount(int newSheetCount) {
		this.sheetCount = newSheetCount;
	}

	public SpellSheetPrinter(Vector<PlayerSpells> spells) {
		this.spells = spells;
		setLandscape();
	}

	public void setCharacter(DCharacter dc) {
		this.dc = dc;
		memorized = dc.getSpellsMemorized();
		// spells = dc.getSpellsKnown();
	}

	public void updateCharacter(DCharacter dc) {
		this.dc = dc;
		memorized = dc.getSpellsMemorized();
		// spells = dc.getSpellsKnown();
	}

	public final void paintImage() {
		paintImage(null);
	}

	public final void paintImage(Graphics ig) {

		// determine if we need to rebuild the icon
		boolean paintImage = false;
		if (ig == null) {
			paintImage = true;
			if (icon == null || icon.getImage() == null
					|| icon.getImage().getGraphics() == null) {
				return;
			}
			ig = icon.getImage().getGraphics();
		}

		// cast to a graphics2d object for performance improvements
		Graphics2D g = (Graphics2D) ig;
		g.scale(scale, scale);

		// draw the graph lines, if desired
		if (drawgraph) {
			g.setColor(Color.blue);
			for (int i = miny; i <= miny + maxy; i += 10) {
				g.drawLine(minx, i, minx + maxx, i);
			}
			for (int i = minx; i <= minx + maxx; i += 10) {
				g.drawLine(i, miny, i, miny + maxy);
			}
		}

		// fill with white
		g.setColor(Color.white);
		g.fill(new Rectangle(minx, miny, maxx, maxy));
		g.draw(new Rectangle(minx, miny, maxx, maxy));

		ypos = miny + 5;
		int marginx = minx + 10;

		drawSpellsHeader(g, marginx, ypos);
		ypos += 15;

		drawSpellsKnown(g, marginx, ypos);

		// drawFooter(g, marginx, ypos);

		if (paintImage) {
			repaint();
		}

	}

	public void drawFooter(Graphics2D g, int x, int y) {
		if (dc != null) {
			g.drawString(dc.getName(), x + 5, y);
		}
		g.setFont(sansBold10);
		g.drawString("" + sheetCount + " of " + numSheets, x + 40, y);
	}

	public void drawSpellsHeader(Graphics2D g, int x, int y) {
		g.setColor(Color.gray);
		g.fill(new Rectangle(x, y, 730, 15));
		g.setColor(Color.white);
		g.setFont(sansBold8);
		int xpos = x + 5;
		int ypos = y + 13;

		g.drawString("Spell", xpos, ypos);
		xpos += 75;
		g.drawString("Lvl", xpos, ypos);
		xpos += 20;
		g.drawString("DC", xpos, ypos);
		xpos += 25;
		g.drawString("Save", xpos, ypos);
		xpos += 80;
		g.drawString("Range", xpos, ypos);
		xpos += 100;
		g.drawString("Res", xpos, ypos);
		xpos += 20;
		g.drawString("Dur", xpos, ypos);
		xpos += 60;
		g.drawString("Desc", xpos, ypos);
		xpos += 230;
		if (dc != null) {
			g.drawString(dc.getName(), xpos, ypos);
		}

		g.drawString("(" + sheetCount + " of " + numSheets + ")", xpos + 80,
				ypos);
	}

	public void drawSpellsKnown(Graphics2D g, int x, int y) {
		// int ypos = y;
		g.setColor(Color.black);
		g.setFont(sansPlain6);

		int count = 0;

		for (int k = 0; k < spells.size(); k++) {

			if (count % 2 == 0) {
				g.setColor(Color.lightGray);
				g.fill(new Rectangle(x, ypos, 730, 10));
				g.setColor(Color.black);
			}
			PlayerSpells ps = (PlayerSpells) spells.get(k);
			int xpos = x + 5;
			drawSizedString(g, "" + ps.getSpellName(), xpos, ypos, 75, 10,
					sansPlain6, "left");
			xpos += 75;
			if (ps.getSpellLevel() != null) {
				drawSizedString(g, "" + ps.getSpellLevel(), xpos, ypos, 20, 10,
						sansPlain6, "left");
			}
			xpos += 20;
			if (ps.getSpellLevel() != null) {
				drawSizedString(g, (10 + ps.getBonus() + ps.getSpellLevel())
						+ "", xpos, ypos, 25, 10, sansPlain6, "left");
			}
			xpos += 25;
			if (ps.getSavingThrow() != null) {
				drawSizedString(g, "" + ps.getSavingThrow(), xpos, ypos, 80,
						10, sansPlain6, "left");
			}
			xpos += 80;
			if (ps.getRange() != null) {
				drawSizedString(g, "" + ps.getRange(), xpos, ypos, 100, 10,
						sansPlain6, "left");
			}
			xpos += 100;
			if (ps.getResist() != null) {
				String resist = "" + ps.getResist();
				if (resist.length() > 3) {
					resist = resist.substring(0, 3);
				}

				drawSizedString(g, resist, xpos, ypos, 20, 10, sansPlain6,
						"left");
			}
			xpos += 20;
			if (ps.getDuration() != null) {
				drawSizedString(g, "" + ps.getDuration(), xpos, ypos, 60, 10,
						sansPlain6, "left");
			}
			xpos += 60;
			String desc = ps.getShortDesc();
			if (desc == null || desc.equals("null") || desc.equals("")) {
				desc = ps.getSpellName();
			}

			drawSizedString(g, "" + desc, xpos, ypos, 250, 10, sansPlain6,
					"left");

			xpos += 300;
			int memct = 0;
			for (int i = 0; i < memorized.size(); i++) {
				PlayerSpells pls = (PlayerSpells) memorized.get(i);
				if (pls.getSpellName().equals(ps.getSpellName())) {
					memct++;
				}
			}
			if (memct > 0) {
				drawSizedString(g, "" + memct, xpos, ypos, 15, 10, sansPlain6,
						"right");
			}
			ypos += 10;
			count++;

		}
	}

}
