package initcheck.character.printsheets;

import initcheck.DCharacter;
import initcheck.character.JumpBlock;
import initcheck.character.MoneyBlock;
import initcheck.database.Feat;
import initcheck.database.LoadLimits;
import initcheck.database.PlayerDomain;
import initcheck.database.PlayerItems;
import initcheck.database.PlayerLanguages;
import initcheck.database.RacialAbility;
import initcheck.database.SpellLevel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.StringTokenizer;
import java.util.Vector;

public class CharacterPrinter2 extends CharacterSheet {


	private static final long serialVersionUID = 1L;

	private JumpBlock jump;

	private int baseMovement;

	private Vector<String> classAbility;

	public CharacterPrinter2() {

	}

	public CharacterPrinter2(DCharacter dc) {
		this.dc = dc;
		setAutoscrolls(true);
		classAbility = dc.getClassAbilities();
		jump = dc.getJump();
		baseMovement = dc.getBaseMovement();
	}

	public void setCharacter(DCharacter dc) {
		this.dc = dc;
		classAbility = dc.getClassAbilities();
		jump = dc.getJump();
		baseMovement = dc.getBaseMovement();
	}

	public void updateCharacter(DCharacter dc) {
		this.dc = dc;
		classAbility = dc.getClassAbilities();
		jump = dc.getJump();
		baseMovement = dc.getBaseMovement();
	}

	public final void paintImage() {
		paintImage(null);
	}

	public final void paintImage(Graphics ig) {

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

		g.setColor(Color.white);
		g.fill(new Rectangle(minx, miny, maxx, maxy));
		g.draw(new Rectangle(minx, miny, maxx, maxy));

		g.setColor(Color.black);
		Font nameFont = new Font("SansSerif", Font.BOLD, 20);
		g.setFont(nameFont);
		g.drawString(dc.getFullName(), minx + 11, miny + 28);
		g.drawLine(minx + 10, miny + 30, minx + 300, miny + 30);
		g.setFont(sansPlain10);
		// g.drawString(dc.getPlayerName(), minx+250, miny+28);
		drawSizedString(g, dc.getPlayerName(), minx + 250, miny + 10, 50, 20,
				sansPlain10, "right");
		g.setFont(sansPlain6);
		g.drawString("CHARACTER NAME", minx + 10, miny + 35);
		g.drawString("PLAYER", minx + 250, miny + 35);

		drawGearHeader(g, minx + 10, miny + 40);
		drawFeatHeader(g, minx + 315, miny + 5);

		int ypos = miny + 61;
		int ypos2 = ypos;

		Vector<PlayerItems> items = dc.getItems();

		int count = 0;

		for (int i = 0; i < 98; i++) {

			// reset the position for the second column of items.
			if (i == 49) {
				ypos = miny + 61;
			}

			if (i < items.size()) {
				PlayerItems pi = (PlayerItems) items.get(i);
				if (i < 49) {
					drawGearBox(g, minx + 10, ypos, pi);
				} else {
					drawGearBox(g, minx + 160, ypos, pi);
				}
			} else {
				if (i < 49) {
					drawGearBox(g, minx + 10, ypos, null);
				} else {
					drawGearBox(g, minx + 160, ypos, null);
				}
			}

			ypos += 10;
			count++;
		}

		ypos2 = ypos + 10;

		// drawMovementBox(g, minx+10, ypos2+10);
		drawJumpBox(g, minx + 10, ypos2);
		drawSwimClimbBox(g, minx + 150, ypos2);
		ypos2 += 50;

		drawWeightBox(g, minx + 10, ypos2 + 10);
		ypos2 += 50;

		drawLanguageBox(g, minx + 10, ypos2 + 10);
		drawMoneyBox(g, minx + 145, ypos2 - 40);

		drawCharNotesBox(g, minx + 215, ypos2 - 100);
		drawVersionString(g, minx + 225, 745);

		ypos = miny + 31;

		// draw race abilities
		drawFeatHeader(g, minx + 315, ypos, dc.getRace());
		ypos += 10;
		Vector<RacialAbility> ra = dc.getRacialAbilities();
		count = 0;

		for (int i = 0; i < ra.size(); i++) {
			RacialAbility rab = (RacialAbility) ra.get(i);
			if (rab.getAbilityName().equals("SPECIAL")) {
				drawFeatLine(g, minx + 315, ypos, "  " + rab.getAbilityValue());
				ypos += 10;
			} else if (rab.getAbilityName().equals("EXTRA")) {
				drawFeatLine(g, minx + 315, ypos, "  " + rab.getAbilityValue()
						+ " " + rab.getAbilityType() + " "
						+ rab.getAbilityType2());
				ypos += 10;
			}
		}

		// draw class abilities
		for (int i = 0; i < classAbility.size(); i++) {

			String ability = (String) classAbility.get(i);
			if (ability.startsWith("CLASS")) {
				ability = ability.substring(5, ability.length());
				drawFeatHeader(g, minx + 315, ypos, ability);
			} else {
				drawFeatLine(g, minx + 315, ypos, "   " + ability);
			}
			ypos += 10;
		}

		// draw domain list
		if (dc.getDomains().size() > 0) {
			
			drawFeatHeader(g, minx + 315, ypos, "DOMAINS");
			ypos += 10;
			for (int i = 0; i < dc.getDomains().size(); i++) {
				PlayerDomain pd = (PlayerDomain) (dc.getDomains().get(i));
				drawFeatLine(g, minx + 315, ypos, "   " + pd.getDomainName());
				ypos += 10;
			}

		}

		// draw feats
		drawFeatHeader(g, minx + 315, ypos, "Character Chosen Feats");
		ypos += 10;
		for (int i = 0; i < dc.getFeatList().size(); i++) {
			drawFeatLine(g, minx + 315, ypos, "   "
					+ ((Feat) dc.getFeatList().get(i)).getSheetFormat());
			ypos += 10;
		}

		for (int i = 0; ypos < (ypos2 - 150); i++) {
			drawFeatLine(g, minx + 315, ypos, "");
			ypos += 10;
		}

		drawSpellBox(g, minx + 315, ypos);

		if (paintImage) {
			repaint();
		}
	}

	public void drawSpellBox(Graphics2D g, int x, int y) {
		g.setColor(Color.gray);
		g.fill(new Rectangle(x, y, 250, 10));
		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 250, 10));
		g.draw(new Rectangle(x, y + 10, 250, 30));
		g.setColor(Color.white);
		g.setFont(sansPlain8);
		g.drawString("Spells per day", x + 5, y + 8);
		Vector<SpellLevel> v = dc.getAllSpellsPerDay();
		int ypos = y + 12;
		g.setFont(courierPlain6);
		g.setColor(Color.black);
		for (int i = 0; i < v.size(); i++) {
			SpellLevel sl = (SpellLevel) v.get(i);
			drawSizedString(g, sl.listFormat(), x, ypos, 250, 10,
					courierPlain6, "left");
			ypos += 10;
		}
	}

	public void drawJumpBox(Graphics2D g, int x, int y) {
		g.setColor(Color.gray);
		g.fill(new Rectangle(x, y, 10, 50));
		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 10, 50));

		g.draw(new Rectangle(x + 10, y, 35, 10));
		g.draw(new Rectangle(x + 10, y + 10, 35, 10));
		g.draw(new Rectangle(x + 10, y + 20, 35, 10));
		g.draw(new Rectangle(x + 10, y + 30, 35, 10));
		g.draw(new Rectangle(x + 10, y + 40, 35, 10));

		g.draw(new Rectangle(x + 45, y, 75, 10));
		g.draw(new Rectangle(x + 45, y + 10, 75, 10));
		g.draw(new Rectangle(x + 45, y + 20, 75, 10));
		g.draw(new Rectangle(x + 45, y + 30, 75, 10));
		g.draw(new Rectangle(x + 45, y + 40, 75, 10));

		g.draw(new Rectangle(x + 120, y, 15, 10));
		g.draw(new Rectangle(x + 120, y + 10, 15, 10));
		g.draw(new Rectangle(x + 120, y + 20, 15, 10));
		g.draw(new Rectangle(x + 120, y + 30, 15, 10));
		g.draw(new Rectangle(x + 120, y + 40, 15, 10));

		g.setFont(sansPlain6);
		g.drawString("Running", x + 12, y + 8);
		g.drawString("Standing", x + 12, y + 18);
		g.drawString("Run High", x + 12, y + 28);
		g.drawString("Stand High", x + 12, y + 38);
		g.drawString("Jump Back", x + 12, y + 48);

		
		g.drawString(jump.getRunFormula(), x + 47, y + 8);
		g.drawString(jump.getStandFormula(), x + 47, y + 18);
		g.drawString(jump.getRunHighFormula(), x + 47, y + 28);
		g.drawString(jump.getStandHighFormula(), x + 47, y + 38);
		g.drawString(jump.getBackFormula(), x + 47, y + 48);

		g.drawString(jump.getRunMax(), x + 122, y + 8);
		g.drawString(jump.getStandMax(), x + 122, y + 18);
		g.drawString(jump.getRunHighMax(), x + 122, y + 28);
		g.drawString(jump.getStandHighMax(), x + 122, y + 38);
		g.drawString(jump.getBackMax(), x + 122, y + 48);

	}

	public void drawCharNotesBox(Graphics2D g, int x, int y) {
		g.setColor(Color.gray);
		g.fill(new Rectangle(x, y, 350, 10));

		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 350, 10));
		g.draw(new Rectangle(x, y + 10, 350, 120));

		g.setColor(Color.white);
		g.setFont(sansBold8);
		g.drawString("Character Notes", x + 2, y + 9);

		g.setColor(Color.black);
		g.setFont(sansPlain8);
		drawSizedString(g, dc.getHeight() + "," + dc.getWeight() + " lbs.,"
				+ dc.getAge() + " yrs," + dc.getGender() + "," + dc.getRace(),
				x, y, 350, 10, sansPlain8, "right");
		// g.drawString(dc.getHeight()+","+dc.getWeight()+","+dc.getAge()+","+dc.getGender()+","+dc.getRace(),
		// x+70, y+9);

		if (dc.getPlayerNotes() != null) {
			String s = dc.getPlayerNotes();
			StringTokenizer split = new StringTokenizer(s, "\n");
			int notesY = y + 20;
			while (split.hasMoreTokens()) {
				g.drawString(split.nextToken(), x + 10, notesY);
				notesY += 10;
			}
		}

	}

	public void drawMoneyBox(Graphics2D g, int x, int y) {
		g.setColor(Color.gray);
		g.fill(new Rectangle(x, y, 40, 10));
		g.fill(new Rectangle(x, y + 50, 40, 15));

		g.setColor(Color.white);
		g.setFont(sansBold8);
		g.drawString("Money", x + 2, y + 9);

		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 40, 10));
		g.draw(new Rectangle(x, y + 10, 40, 10));
		g.draw(new Rectangle(x, y + 20, 40, 10));
		g.draw(new Rectangle(x, y + 30, 40, 10));
		g.draw(new Rectangle(x, y + 40, 40, 10));
		g.draw(new Rectangle(x, y + 50, 40, 15));
		g.draw(new Rectangle(x, y + 65, 40, 15));

		MoneyBlock money = dc.getMoney();
		g.setFont(sansPlain8);
		g.drawString("PP: " + money.getPp(), x + 2, y + 18);
		g.drawString("GP: " + money.getGp(), x + 2, y + 28);
		g.drawString("SP: " + money.getSp(), x + 2, y + 38);
		g.drawString("CP: " + money.getCp(), x + 2, y + 48);
		g.drawString("St: " + money.getStoredGold(), x + 2, y + 58);
		int value = ((money.getCp()) + (money.getSp() * 10)
				+ (money.getGp() * 100) + (money.getPp() * 1000))
				/ 100 + money.getStoredGold();

		g.drawString("" + value, x + 2, y + 78);
	}

	public void drawSwimClimbBox(Graphics2D g, int x, int y) {
		g.setColor(Color.gray);
		g.fill(new Rectangle(x, y, 50, 10));
		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 50, 10));
		g.draw(new Rectangle(x, y + 10, 50, 15));
		g.draw(new Rectangle(x, y + 25, 50, 15));
		g.setColor(Color.white);
		g.setFont(sansBold8);

		g.drawString("Swim/Climb", x + 5, y + 8);
		g.setColor(Color.black);
		String swimMove = ""+(baseMovement / 4);
		if (baseMovement % 4 != 0){
			swimMove += " " + baseMovement % 4 + "/4'";
		}
		String climbMove = "" + (baseMovement / 2);
		if (baseMovement % 2 != 0){
			climbMove += " " + baseMovement % 2 + "/2'";
		}
		g.drawString(swimMove, x + 5, y + 23);
		g.drawString(climbMove, x + 5, y + 38);

	}

	public void drawLanguageBox(Graphics2D g, int x, int y) {
		g.setColor(Color.gray);
		g.fill(new Rectangle(x, y, 105, 10));

		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 105, 10));
		g.draw(new Rectangle(x, y + 10, 105, 30));

		g.setColor(Color.white);
		g.setFont(sansBold10);
		g.drawString("Languages", x + 5, y + 9);
		Vector<PlayerLanguages> v = dc.getLanguages();

		g.setColor(Color.black);
		g.setFont(sansPlain8);

		int bon = Integer
				.parseInt(dc.getLanguagesRemaining().getDisplayValue());
		drawSizedString(g, bon + " Avail", x, y, 105, 10, sansPlain8, "right");
		// g.drawString(bon+" Avail", x+70, y+9);

		g.setFont(sansPlain6);

		int ypos = y + 18;
		String lang = "";
		for (int i = 0; i < v.size(); i++) {
			lang += ((PlayerLanguages) v.get(i)).getLanguage() + ",";
			if (i % 4 == 0) {
				g.drawString(lang, x + 10, ypos);
				ypos += 8;
				lang = "";
			}
		}
		g.drawString(lang, x + 10, ypos);
	}

	public void drawMovementBox(Graphics2D g, int x, int y) {

		g.setColor(Color.gray);
		g.fill(new Rectangle(x, y, 10, 40));

		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 10, 40));
		g.draw(new Rectangle(x + 10, y, 30, 10));
		g.draw(new Rectangle(x + 10, y + 10, 30, 10));
		g.draw(new Rectangle(x + 10, y + 20, 30, 10));
		g.draw(new Rectangle(x + 10, y + 30, 30, 10));

		g.draw(new Rectangle(x + 40, y, 65, 10));
		g.draw(new Rectangle(x + 40, y + 10, 65, 10));
		g.draw(new Rectangle(x + 40, y + 20, 65, 10));
		g.draw(new Rectangle(x + 40, y + 30, 65, 10));

		g.setFont(sansPlain6);
		g.drawString("Walk", x + 11, y + 7);
		g.drawString("Hustle (x2)", x + 11, y + 17);
		g.drawString("Run (x3)", x + 11, y + 27);
		g.drawString("Run (x4)", x + 11, y + 37);

		g.drawString("move-equiv.", x + 65, y + 7);
		g.drawString("standard Action", x + 65, y + 17);
		g.drawString("full-round Action", x + 65, y + 27);
		g.drawString("full-round Action", x + 65, y + 37);

		g.setFont(sansPlain8);
		g.drawString("" + baseMovement, x + 45, y + 9);
		g.drawString("" + (baseMovement * 2), x + 45, y + 19);
		g.drawString("" + (baseMovement * 3), x + 45, y + 29);
		if (!dc.hasHeavyArmor()) {
			g.drawString("" + (baseMovement * 4), x + 45, y + 39);
		} else {
			g.drawString("--", x + 45, y + 39);
		}
	}

	public void drawWeightBox(Graphics2D g, int x, int y) {
		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 35, 10));
		g.draw(new Rectangle(x + 35, y, 35, 10));
		g.draw(new Rectangle(x + 70, y, 35, 10));
		g.draw(new Rectangle(x, y + 10, 105, 10));
		g.draw(new Rectangle(x, y + 20, 35, 10));
		g.draw(new Rectangle(x + 35, y + 20, 35, 10));
		g.draw(new Rectangle(x + 70, y + 20, 35, 10));

		g.setFont(sansBold8);
		int carried = dc.calcWeightCarried();
		g.drawString("Carried : " + carried + " lbs", x + 20, y + 18);
		LoadLimits ll = dc.getLoadLimits();
		g.setFont(sansPlain8);

		Color lightLoadColor = (carried <= Integer.parseInt(ll.getLightLoad()) ? Color.blue
				: Color.black);
		Color mediumLoadColor = (carried > Integer.parseInt(ll.getLightLoad())
				&& carried <= Integer.parseInt(ll.getMediumLoad()) ? Color.blue
				: Color.black);
		Color heavyLoadColor = (carried > Integer.parseInt(ll.getMediumLoad()) ? Color.blue
				: Color.black);

		if (carried > Integer.parseInt(ll.getHeavyLoad())) {
			lightLoadColor = mediumLoadColor = heavyLoadColor = Color.red;
		}

		g.setColor(lightLoadColor);
		g.drawString(ll.getLightLoad(), x + 3, y + 8);
		g.setColor(mediumLoadColor);
		g.drawString(ll.getMediumLoad(), x + 38, y + 8);
		g.setColor(heavyLoadColor);
		g.drawString(ll.getHeavyLoad(), x + 73, y + 8);

		g.drawString(ll.getLift(), x + 3, y + 28);
		g.drawString(ll.getLiftGround(), x + 38, y + 28);
		g.drawString(ll.getPushDrag(), x + 73, y + 28);

		g.setFont(sansPlain5);
		g.drawString("Light load", x + 10, y - 1);
		g.drawString("Medium load", x + 37, y - 1);
		g.drawString("Heavy load", x + 76, y - 1);
		g.drawString("Lift Over", x + 8, y + 37);
		g.drawString("Head", x + 10, y + 44);
		g.drawString("Lift Off", x + 42, y + 37);
		g.drawString("Ground", x + 40, y + 44);
		g.drawString("Push or", x + 77, y + 37);
		g.drawString("Drag", x + 80, y + 44);

	}

	public void drawFeatHeader(Graphics2D g, int x, int y, String text) {
		g.setColor(Color.yellow);
		g.fill(new Rectangle(x, y - 9, 250, 9));
		g.setColor(Color.black);
		g.drawLine(x, y, x + 250, y);

		if (text != null) {
			g.setFont(sansBold8);
			g.drawString(text, x + 3, y - 1);
		}
	}

	public void drawFeatLine(Graphics2D g, int x, int y, String text) {
		g.setColor(Color.black);

		g.drawLine(x, y, x + 250, y);
		if (text != null) {
			g.setFont(sansPlain8);
			g.drawString(text, x + 3, y - 1);
		}
	}

	public void drawGearHeader(Graphics2D g, int x, int y) {
		g.setColor(Color.gray);
		g.fill(new Rectangle(x, y, 300, 14));

		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 300, 14));
		g.draw(new Rectangle(x, y + 14, 135, 7));
		g.draw(new Rectangle(x + 135, y + 14, 15, 7));
		g.draw(new Rectangle(x + 150, y + 14, 135, 7));
		g.draw(new Rectangle(x + 285, y + 14, 15, 7));
		g.setFont(sansPlain5);
		g.drawString("Item", x + 60, y + 20);
		g.drawString("Wt", x + 137, y + 20);
		g.drawString("Item", x + 210, y + 20);
		g.drawString("Wt", x + 287, y + 20);

		g.setColor(Color.white);
		g.setFont(sansBold12);
		g.drawString("Gear", x + 5, y + 12);

	}

	public void drawGearBoxRow(Graphics2D g, int x, int y, PlayerItems a,
			PlayerItems b) {
		drawGearBox(g, x, y, a);
		drawGearBox(g, x + 150, y, b);
	}

	public void drawGearBox(Graphics2D g, int x, int y, PlayerItems pi) {
		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 135, 10));
		g.draw(new Rectangle(x + 135, y, 15, 10));
		if (pi != null) {
			g.setFont(sansPlain8);
			String itemStr = "";
			if (pi.getQuantity() != null) {
				itemStr += pi.getQuantity() + " ";
			}
			if (pi.getItemName() != null) {
				drawSizedString(g, itemStr + pi.getItemName() + ":"
						+ pi.getNotes(), x, y, 135, 10, sansPlain10, "left");
				// g.drawString(pi.getItemName(),x+7, y+9);
			}
			if (pi.getWeight() != null) {
				drawSizedString(g, pi.getWeight(), x + 135, y, 15, 10,
						sansPlain10, "center");
				// g.drawString(pi.getWeight(),x+139, y+9);
			}
		}

	}

	public void drawFeatHeader(Graphics2D g, int x, int y) {
		g.setColor(Color.gray);
		g.fill(new Rectangle(x, y, 250, 14));

		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 250, 14));

		g.setColor(Color.white);
		g.setFont(sansBold10);
		g.drawString("Special Abilities/Feats ", x + 5, y + 12);
		g.setColor(Color.black);
		g.setFont(sansPlain8);
		drawSizedString(g, dc.getFeatsRemaining().getDisplayValue() + " Available", x, y, 250,
				14, sansPlain8, "right");
		// g.drawString(dc.getFeatsRemaining()+" Available", x+135, y+12);

	}

}
