package initcheck.character.printsheets;

import initcheck.DCharacter;
import initcheck.InitColor;
import initcheck.character.Calculation;
import initcheck.character.ClassSlot;
import initcheck.database.Armor;
import initcheck.database.PlayerDomain;
import initcheck.database.PlayerFavoredEnemy;
import initcheck.database.PlayerSkills;
import initcheck.database.Skill;
import initcheck.database.SkillDAO;
import initcheck.database.Weapon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.StringTokenizer;
import java.util.Vector;

public class CharacterPrinter extends CharacterSheet {

	
	private static final long serialVersionUID = 1L;

	String baseAttackBonus;

	String meleeAttackBonus;

	String rangedAttackBonus;

	String unarmedAttackBonus;

	String sizeMod;

	String armorClass;

	String touchArmorClass;

	String flatFootedArmorClass;
	
	String restArmorClass;

	String restTouchArmorClass;
	
	Skill s = new Skill();

	Vector<Skill> sv;

	boolean critOverride = true;

	Vector<PlayerSkills> sv2;

	int classSkillsUsed = 0;

	int crossClassSkillsUsed = 0;

	int availableRanks = 0;

	public CharacterPrinter() {

	}

	public CharacterPrinter(DCharacter dc) {
		this.dc = dc;
		baseAttackBonus = dc.getBaseAttackBonus();
		meleeAttackBonus = dc.getMeleeAttackBonus();
		rangedAttackBonus = dc.getRangedAttackBonus();
		unarmedAttackBonus = dc.getUnarmedAttackBonus();
		sizeMod = "" + dc.getSizeMod();
		armorClass = "" + dc.getAC();
		touchArmorClass = "" + dc.getTouchAc();
		flatFootedArmorClass = ""+dc.getFlatFootedAc();
		restTouchArmorClass = ""+dc.getRestTouchAc();
		restArmorClass = "" + dc.getRestAc();
		SkillDAO sdb = new SkillDAO();
		Skill s = new Skill();
		s.setTrainedOnly("N");
		s.setArmorCheck(null);
		sv = sdb.selectSkill(s);
		sv2 = dc.getSkillSet().getSkillList();
		setAutoscrolls(true);
		classSkillsUsed = dc.getSkillSet().getClassSkillsUsed();
		crossClassSkillsUsed = dc.getSkillSet().getCrossClassSkillsUsed();
		availableRanks = dc.getSkillsRemaining() - dc.getSkillSet().getSkillsUsed();

		// paintImage();
	}

	public void setCharacter(DCharacter dc) {
		this.dc = dc;
		baseAttackBonus = dc.getBaseAttackBonus();
		meleeAttackBonus = dc.getMeleeAttackBonus();
		rangedAttackBonus = dc.getRangedAttackBonus();
		unarmedAttackBonus = dc.getUnarmedAttackBonus();
		sizeMod = "" + dc.getSizeMod();
		armorClass = "" + dc.getAC();
		touchArmorClass = "" + dc.getTouchAc();
		flatFootedArmorClass = ""+dc.getFlatFootedAc();
		restTouchArmorClass = ""+dc.getRestTouchAc();
		restArmorClass = "" + dc.getRestAc();
		SkillDAO sdb = new SkillDAO();
		s = new Skill();
		s.setTrainedOnly("N");
		s.setArmorCheck(null);
		sv = sdb.selectSkill(s);
		sv2 = dc.getSkillSet().getSkillList();
		setAutoscrolls(true);
		classSkillsUsed = dc.getSkillSet().getClassSkillsUsed();
		crossClassSkillsUsed = dc.getSkillSet().getCrossClassSkillsUsed();
		availableRanks = dc.getSkillsRemaining() - dc.getSkillSet().getSkillsUsed();
		// paintImage();
	}

	public void updateCharacter(DCharacter dc) {
		this.dc = dc;
		baseAttackBonus = dc.getBaseAttackBonus();
		meleeAttackBonus = dc.getMeleeAttackBonus();
		rangedAttackBonus = dc.getRangedAttackBonus();
		unarmedAttackBonus = dc.getUnarmedAttackBonus();
		sizeMod = "" + dc.getSizeMod();
		armorClass = "" + dc.getAC();
		touchArmorClass = "" + dc.getTouchAc();
		flatFootedArmorClass = ""+dc.getFlatFootedAc();
		restTouchArmorClass = ""+dc.getRestTouchAc();
		restArmorClass = "" + dc.getRestAc();
		classSkillsUsed = dc.getSkillSet().getClassSkillsUsed();
		crossClassSkillsUsed = dc.getSkillSet().getCrossClassSkillsUsed();
		availableRanks = dc.getSkillsRemaining() - dc.getSkillSet().getSkillsUsed();
		// paintImage();
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

		if (dc.getGender().equals("M")) {
			g.setColor(InitColor.blue);
		} else {
			g.setColor(InitColor.pink);
		}
		g.setFont(nameFont);
		g.drawString(dc.getFullName(), minx + 11, miny + 28);
		g.setColor(Color.black);
		g.drawLine(minx + 10, miny + 30, minx + 240, miny + 30);

		g.setFont(playerNameFont);
		drawSizedString(g, dc.getPlayerName(), minx + 250, miny + 10, 150, 20,
				playerNameFont, "right");
		g.drawLine(minx + 250, miny + 30, minx + 400, miny + 30);

		g.setFont(sansPlain6);
		g.drawString("CHARACTER NAME", minx + 10, miny + 35);
		g.drawString("PLAYER NAME", minx + 350, miny + 35);
		g.drawString("PARTY", minx + 300, miny + 55);
		g.drawLine(minx + 10, miny + 50, minx + 40, miny + 50);
		g.drawString("LEVEL", minx + 10, miny + 55);

		g.drawLine(minx + 45, miny + 50, minx + 400, miny + 50);
		g.drawString("CLASS(es)/Level(s)", minx + 45, miny + 55);

		g.setFont(sansPlain10);
		g.drawString("" + dc.getLevel(), minx + 10, miny + 48);

		String classStr = "";
		for (int i = 0; i < dc.getClassSet().getClassVector().size(); i++) {
			ClassSlot cs = (ClassSlot) dc.getClassSet().getClassVector().get(i);
			if (i > 0) {
				classStr += ",   ";
			}
			classStr += cs.getClassName().getCharClass() + " " + cs.getLevel();
		}
		g.drawString(classStr, minx + 45, miny + 48);

		drawSizedString(g, dc.getParty(), minx + 250, miny + 35, 150, 15,
				partyNameFont, "right");
		// g.drawString(dc.getParty(), minx+300, miny+48);

		g.drawLine(minx + 60, miny + 70, minx + 160, miny + 70);
		g.setFont(sansPlain6);
		g.drawString("RACE", minx + 60, miny + 75);

		g.drawLine(minx + 165, miny + 70, minx + 185, miny + 70);
		g.drawString("SEX", minx + 165, miny + 75);

		g.drawLine(minx + 190, miny + 70, minx + 280, miny + 70);
		g.drawString("ALIGN", minx + 190, miny + 75);

		g.drawLine(minx + 285, miny + 70, minx + 370, miny + 70);
		g.drawString("DEITY", minx + 285, miny + 75);

		g.setFont(sansPlain10);

		g.drawString(format(dc.getRace()), minx + 65, miny + 68);
		g.drawString(format(dc.getGender()), minx + 170, miny + 68);
		g.drawString(format(dc.getAlignment()), minx + 195, miny + 68);
		g.drawString(format(dc.getDeity()), minx + 290, miny + 68);

		drawMovementBox(g, minx + 375, miny + 63);

		drawExpBox(g, minx + 410, miny + 5, dc.getXp(), new Double(dc.getLevel()).intValue());

		drawStatBox(g, 30, 100, "Str", "Strength", dc.getStat("STRENGTH"));
		drawStatBox(g, 30, 120, "Dex", "Dexterity", dc.getStat("DEXTERITY"));
		drawStatBox(g, 30, 140, "Con", "Constitution", dc
				.getStat("CONSTITUTION"));
		drawStatBox(g, 30, 160, "Int", "Intelligence", dc
				.getStat("INTELLIGENCE"));
		drawStatBox(g, 30, 180, "Wis", "Wisdom", dc.getStat("WISDOM"));
		drawStatBox(g, 30, 200, "Cha", "Charisma", dc.getStat("CHARISMA"));

		drawSaveBoxes(g, 30, 230);

		drawHpBox(g, 165, 164);
		drawInitBox(g, 165, 190, "Init", "Dexterity", "" + dc.getMod(), dc
				.getBonus(dc.getStat("DEXTERITY")), 0);
		drawConditionalBox(g, 165, 215);
		drawAcBox(g, 165, 120);
		g.setFont(sansPlain8);
		int levelPoints = dc.getLevelPointsRemaining();
		if (levelPoints != 0) {
			g.drawString("Stat Points Available : " + levelPoints, 170, 118);
		}

		int ypos = 305;
		drawClassFeatures(g, 30, ypos - 10);
		ypos += 55;

		drawAttackBoxes(g, 30, ypos, baseAttackBonus);
		ypos += 44;

		for (int i = 1; i < 7; i++) {
			drawWeaponBox(g, 30, ypos, i);
			ypos += 40;
		}

		// add a space between weapons and armor
		ypos += 5;

		drawShieldBox(g, 30, ypos);
		ypos += 40;
		drawArmorBox(g, 30, ypos);
		ypos += 40;
		drawVersionString(g, 30, ypos);

		drawSkillHeader(g, 348, 170, false);

		ypos = 195;
		for (int i = 0; i < sv.size(); i++) {
			s = (Skill) sv.get(i);
			Skill ps = dc.getSkillSet().getSkill(s.getSkill());
			if (ps == null) {
				ps = s;
			}

			drawSkillLine(g, 348, ypos, ps);
			ypos += 10;
		}

		drawSkillHeader(g, 348, ypos, true);
		ypos += 25;

		int count = 0;
		Vector<Skill> trained = new Vector<Skill>();
		for (int i = 0; i < sv2.size(); i++) {
			s = (Skill) sv2.get(i);
			boolean found = false;
			for (int j = 0; j < trained.size(); j++) {
				Skill shown = (Skill) trained.get(j);
				if (s.getSkill().equals(shown.getSkill())) {
					found = true;
				}
			}
			if (!found) {

				Skill ps = dc.getSkillSet().getSkill(s.getSkill());
				trained.add(s);
				if (s.getTrainedOnly().equals("Y")) {

					drawSkillLine(g, 348, ypos, ps);
					ypos += 10;
					count++;
				}
			}
		}

		for (int i = 0; i < 20 - count; i++) {
			drawSkillLine(g, 348, ypos, null);
			ypos += 10;
		}

		drawAmmoBoxes(g, 348, ypos - 5);

		if (paintImage) {
			repaint();
		}
	}

	public String getSkillAbbrev(String s) {

		if (s.equals("INTELLIGENCE")) {
			s = "Int";
		} else if (s.equals("STRENGTH")) {
			s = "Str";
		} else if (s.equals("WISDOM")) {
			s = "Wis";
		} else if (s.equals("CONSTITUTION")) {
			s = "Con";
		} else if (s.equals("CHARISMA")) {
			s = "Cha";
		} else if (s.equals("DEXTERITY")) {
			s = "Dex";
		}
		return s;
	}

	public double getRadians(double degrees) {
		return (degrees * 3.14159) / 180;
	}

	public void drawSaveBoxes(Graphics2D g, int x, int y) {

		g.setFont(sansPlain5);
		g.setColor(Color.black);

		g.drawString("SAVES", x + 2, y - 1);
		g.drawString("TOTAL", x + 35, y - 1);
		g.drawString("Base", x + 62, y - 1);
		g.drawString("Abil", x + 82, y - 1);
		g.drawString("Misc", x + 102, y - 1);

		drawSaveBox(g, x, y, "Fort", "Constitution", dc.calcFortitudeSave()
				.getDisplayValue(), dc.getSaves().getBaseFort(), dc.getBonus(dc
				.getStats().getCon()));
		drawSaveBox(g, x, y + 20, "Ref", "Dexterity", dc.calcReflexSave()
				.getDisplayValue(), dc.getSaves().getBaseRef(), dc.getBonus(dc
				.getStats().getDex()));
		drawSaveBox(g, x, y + 40, "Will", "Wisdom", dc.calcWillSave()
				.getDisplayValue(), dc.getSaves().getBaseWill(), dc.getBonus(dc
				.getStats().getWis()));
	}

	public void drawClassFeatures(Graphics2D g, int x, int y) {
		g.setColor(Color.gray);
		g.fill(new Rectangle(x, y, 120, 10));

		g.setColor(Color.white);
		g.setFont(sansPlain6);
		g.drawString("Class Features", x + 10, y + 8);

		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 120, 10));
		g.draw(new Rectangle(x, y + 10, 120, 40));

		int ypos = y + 18;

		if (dc.hasSneakAttack()) {
			g.drawString(dc.getSneakAttack().getDisplayValue(), x + 3, ypos);
			ypos += 10;
			g.drawString(dc.getSneakAttack().getDisplayValue2(), x + 3, ypos);
			ypos += 10;
		}

		if (dc.hasDomainAccess()) {
			Vector<PlayerDomain> domains = dc.getDomains();

			for (int i = 0; i < domains.size(); i++) {
				String domStr = (domains.get(i)).toString();
				g.drawString(domStr, x + 3, ypos);
				ypos += 10;
			}
		}

		if (dc.hasFavoredEnemies()) {
			Vector<PlayerFavoredEnemy> favoredEnemies = dc.getFavoredEnemies();
			String domStr = "FAV:";
			for (int i = 0; i < favoredEnemies.size(); i++) {

				domStr +=  favoredEnemies.get(i);
				if (i < favoredEnemies.size() - 1) {
					domStr += ", ";
				}
				if (i > 0 && i % 3 == 0) {
					g.drawString(domStr, x + 3, ypos);
					domStr = "FAV:";
					ypos += 10;
				}
			}
			g.drawString(domStr, x + 3, ypos);
			ypos += 10;
		}

		if (dc.canSmite()) {
			g.drawString("Smite " + dc.getSmite(), x + 3, ypos);
			ypos += 10;
		}
		
		if (dc.getTurnUndeadLevel() > 0){
			g.drawString("TRN:"+dc.getTurnAttempts().getDisplayValue()+
					" CHK:"+dc.getTurnCheck().getDisplayValue()+
					" DMG:"+dc.getTurnDamage().getDisplayValue(), x+3, ypos);
			
			ypos += 10;
		}

		if (Integer.parseInt(dc.getLayHands().getDisplayValue()) > 0) {
			g.drawString("Lay Hands " + dc.getLayHands().getDisplayValue()
					+ "hp/day", x + 3, ypos);
			ypos += 10;
		}
	}

	public void drawSkillNotes(Graphics2D g, int x, int y) {
		g.setColor(Color.gray);
		g.fill(new Rectangle(x, y, 120, 10));

		g.setColor(Color.white);
		g.setFont(sansPlain6);
		g.drawString("Skill Notes", x + 10, y + 8);

		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 120, 10));
		g.draw(new Rectangle(x, y + 10, 120, 30));

		g.drawString("SKILL INDEX : ", x + 122, y + 7);
		g.drawString("U Untrained skill, can use at 0 ranks", x + 127, y + 14);
		g.drawString("C Cross-class skill, gain at 0.5 rank", x + 127, y + 21);
		g.drawString("R Restricted skill you can't train in", x + 127, y + 28);
		g.drawString("* ARMOR CHECK PENALTY, applies", x + 127, y + 35);
		g.drawString("** Weight Penalty, -1 per 5lbs", x + 127, y + 42);
	}

	public void drawAmmoBoxes(Graphics2D g, int x, int y) {
		drawAmmoBox(g, x, y, 1);
		drawAmmoBox(g, x + 74, y, 2);
		drawAmmoBox(g, x + 148, y, 3);

		drawAmmoBox(g, x, y + 30, 4);
		drawAmmoBox(g, x + 74, y + 30, 5);
		drawAmmoBox(g, x + 148, y + 30, 6);
	}

	public void drawAmmoBox(Graphics2D g, int x, int y, int idx) {
		g.setColor(Color.gray);
		g.fill(new Rectangle(x, y + 15, 35, 7));
		g.setColor(Color.white);

		g.setFont(sansPlain5);
		g.drawString("Wt", x + 2, y + 21);
		g.drawString("#", x + 20, y + 21);

		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 74, 15));
		g.draw(new Rectangle(x, y + 15, 15, 15));
		g.draw(new Rectangle(x + 15, y + 15, 20, 15));
		g.draw(new Rectangle(x + 35, y + 15, 39, 15));

		Vector<Weapon> v = (dc.getAmmo());
		if (v.size() >= idx) {
			Weapon w = (Weapon) v.get(idx - 1);

			drawSizedString(g, w.getSheetFormat(), x, y, 74, 15, sansPlain10);

			drawSizedString(g, w.getWeight(), x, y + 16, 15, 15, sansPlain6);
			drawSizedString(g, w.getQuantity(), x + 15, y + 16, 20, 15,
					sansPlain6);
		}

	}

	public void drawSkillHeader(Graphics2D g, int x, int y, boolean trained) {
		g.setColor(Color.gray);
		g.fill(new Rectangle(x, y, 220, 14));
		g.setColor(Color.white);
		g.setFont(sansBold10);
		if (trained) {
			g.drawString("Trained Skills", x + 5, y + 13);
			g.setColor(Color.white);
			g.setFont(sansPlain8);
			drawSizedString(g, "Ranks used : " + classSkillsUsed + " class, "
					+ crossClassSkillsUsed + " cross", x, y, 220, 14,
					sansPlain8, "right");

		} else {
			g.drawString("Untrained Skills", x + 5, y + 13);
			g.setColor(Color.white);
			g.setFont(sansPlain10);
			drawSizedString(g, availableRanks + " ranks available", x, y, 220,
					14, sansPlain8, "right");

		}
		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 220, 14));
	}

	public void drawSkillLine(Graphics2D g, int x, int y, Skill s) {

		g.setColor(Color.lightGray);
		g.draw(new Rectangle(x, y - 10, 25, 10));
		g.draw(new Rectangle(x + 25, y - 10, 122, 10));
		g.draw(new Rectangle(x + 147, y - 10, 13, 10));
		g.draw(new Rectangle(x + 160, y - 10, 20, 10));
		g.draw(new Rectangle(x + 180, y - 10, 20, 10));
		g.draw(new Rectangle(x + 200, y - 10, 20, 10));
		g.setColor(Color.black);
		g.setFont(sansPlain8);

		if (s != null) {
				double base = Double.parseDouble(dc.getSkillSet().getBaseRanks(Integer.parseInt(s.getId())));
			double ranks = Double.parseDouble(dc.getRanks(s.getSkill()));
			boolean classSkill = false;
			if (dc.getClassSet().isClassSkill(s)) {
				classSkill = true;
			}
			int ability = 0;
			if (s.getAbility() != null && !s.getAbility().equals("")
					&& !s.getAbility().equals("NONE")) {
				ability = dc.getBonus(dc.getStat(s.getAbility()));
			}

			int mods = dc.getSkillSet().getMod(s.getSkill());
			int misc = dc.getSkillSet().getMisc(s.getSkill());
		
			misc += new Double(ranks - base).intValue();

			g.drawString(s.getSkill(), x + 30, y - 1);
			g.setFont(sansPlain8);
			if (!s.getAbility().equals("NONE")) {
				g.drawString("(" + getSkillAbbrev(s.getAbility()) + ")",
						x + 127, y - 1);
			}
			g.drawString("" + ability, x + 153, y - 1);

			if (base > 0) {
				String rankString = "" + base;
				if (classSkill) {
					rankString = "" + (new Double(base)).intValue();
				}
				g.drawString("" + rankString, x + 166, y - 1);
			}

			if (mods != 0) {
				g.drawString("" + mods, x + 186, y - 1);
			}
			
			if (misc != 0) {
				g.drawString("" + misc, x + 206, y - 1);
			}
			
			if (classSkill) {
				String rankString = ""
						+ (new Double(base + ability + mods + misc))
								.intValue();

				g.drawString(rankString, x + 2, y - 1);
			} else {
				g
						.drawString("" + (ranks + ability + mods + misc),
								x + 2, y - 1);
			}
		}
	}

	public void drawAcBox(Graphics2D g, int x, int y) {
		g.setColor(Color.gray);
		g.fill(new Rectangle(x, y, 30, 40));
		g.fill(new Rectangle(x + 30, y + 18, 30, 4));
		g.fill(new Rectangle(x + 53, y, 7, 40));

		g.setColor(Color.white);
		g.setFont(sansBold12);
		g.drawString("A", x + 5, y + 15);
		g.drawString("C", x + 5, y + 32);

		g.setFont(sansPlain5);
		//g.drawString("@rest", x + 62, y + 23);
		g.rotate(getRadians(270));
		g.drawString("Armor", 0 - (x - 30), y + 70);
		g.drawString("Touch", 0 - (x - 5), y + 70);
		g.drawString("Rest", 0 - (x - 30), y + 105);
		g.drawString("Touch", 0 - (x - 7), y + 105);
		
		g.rotate(0 - getRadians(270));

		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 20, 40));
		g.draw(new Rectangle(x + 20, y, 10, 18));
		g.draw(new Rectangle(x + 20, y + 22, 10, 18));
		g.draw(new Rectangle(x + 30, y, 30, 18));
		g.setFont(sansPlain12);
		g.drawString(armorClass, x + 35, y + 14);
		//g.drawString(flatFootedArmorClass, x + 62, y + 23);
		g.draw(new Rectangle(x + 30, y + 22, 30, 18));
		g.drawString(touchArmorClass, x + 35, y + 36);

		g.draw(new Rectangle(x + 60, y + 12, 20, 16));

		g.draw(new Rectangle(x + 60, y, 20, 12));
		g.drawString(restArmorClass, x + 62, y + 11);
		
		g.drawString(flatFootedArmorClass, x + 62, y + 25);
		
		g.draw(new Rectangle(x + 60, y + 28, 20, 12));
		g.drawString(restTouchArmorClass, x + 62, y + 39);

		// AC component boxes
		g.setFont(sansPlain5);
		Rectangle base = new Rectangle(x + 85, y + 12, 20, 18);
		g.draw(base);
		g.drawString("Base", x + 87, y + 36);

		Rectangle armor = new Rectangle(x + 107, y + 12, 20, 18);
		g.draw(armor);
		g.drawString("Armor", x + 109, y + 36);
		g.drawString("Bonus", x + 109, y + 42);

		Rectangle shield = new Rectangle(x + 129, y + 12, 20, 18);
		g.draw(shield);
		g.drawString("Shield", x + 131, y + 36);
		g.drawString("Bonus", x + 131, y + 42);

		Rectangle dex = new Rectangle(x + 151, y + 12, 20, 18);
		g.draw(dex);
		g.drawString("Dex", x + 160, y + 36);
		g.drawString("Mod", x + 160, y + 42);

		Rectangle size = new Rectangle(x + 173, y + 12, 20, 18);
		g.draw(size);
		g.drawString("Size", x + 180, y + 36);
		g.drawString("Mod", x + 180, y + 42);

		Rectangle nat = new Rectangle(x + 195, y + 12, 20, 18);
		g.draw(nat);
		g.drawString("Natural", x + 197, y + 36);
		g.drawString("Armor", x + 197, y + 42);

		Rectangle misc = new Rectangle(x + 217, y + 12, 20, 18);
		g.draw(misc);
		g.drawString("Misc", x + 220, y + 36);
		g.drawString("Mod", x + 220, y + 42);

		Rectangle temp = new Rectangle(x + 239, y + 12, 20, 18);
		g.draw(temp);
		g.drawString("Temp", x + 242, y + 36);
		g.drawString("Mod", x + 244, y + 42);

		drawSizedString(g, dc.getBaseAc().getDisplayValue(), base, sansPlain12,
				"center");

		// spell fail, armor check, resist, dr boxes
		g.setFont(sansPlain5);
		Rectangle arcane = new Rectangle(x + 270, y + 12, 30, 18);
		g.draw(arcane);
		g.drawString("Arcane", x + 275, y + 36);
		g.drawString("Spell Fail", x + 273, y + 42);

		Rectangle check = new Rectangle(x + 305, y + 12, 30, 18);
		g.draw(check);
		g.drawString("Armor Check", x + 307, y + 36);
		g.drawString("Penalty", x + 315, y + 42);

		Rectangle resist = new Rectangle(x + 340, y + 12, 30, 18);
		g.draw(resist);
		g.drawString("Spell", x + 350, y + 36);
		g.drawString("Resist.", x + 350, y + 42);

		Rectangle dr = new Rectangle(x + 375, y + 12, 30, 18);
		g.draw(dr);
		g.drawString("Damage", x + 377, y + 36);
		g.drawString("Reduct.", x + 377, y + 42);

		// fill in the values for the boxes
		g.setFont(sansPlain12);
		int ab = 0;
		int ab2 = 0;
		int sb = 0;
		int sb2 = 0;

		if (dc.getArmor() != null) {
			ab = Integer.parseInt(dc.getArmor().getArmorbonus());
			ab2 = Integer.parseInt(dc.getArmor().getBonus());
			drawSizedString(g, "" + ab, armor, sansPlain12, "center");
		}
		if (dc.getShield() != null) {
			sb = Integer.parseInt(dc.getShield().getArmorbonus());
			sb2 = Integer.parseInt(dc.getShield().getBonus());
			drawSizedString(g, "" + sb, shield, sansPlain12, "center");
		}

		drawSizedString(g, "" + dc.getMaxDex(), dex, sansPlain12, "center");
		drawSizedString(g, sizeMod, size, sansPlain12, "center");
		drawSizedString(g, "" + dc.getNaturalArmor(), nat, sansPlain12,
				"center");
		drawSizedString(g, "" + dc.getTempMods().getTempAc(), temp, sansPlain12, "center");

		int displayTotal = Integer.parseInt(dc.getBaseAc().getDisplayValue())
				+ ab + sb + ab2 + sb2 + dc.getMaxDex() + dc.getNaturalArmor()
				+ dc.getMiscArmor() + dc.getTempMods().getTempAc();

		int diff = Integer.parseInt(armorClass) - displayTotal;
		drawSizedString(g, "" + (ab2 + sb2 + dc.getMiscArmor() + diff), misc,
				sansPlain12, "center");
		drawSizedString(g, dc.getArcaneFail() + "%", arcane, sansPlain12,
				"center");
		drawSizedString(g, dc.getArmorCheckPenalty().getDisplayValue(), check,
				sansPlain12, "center");

		if (dc.getSpellResist() != null) {
			drawSizedString(g, dc.getSpellResist(), resist, sansPlain12,
					"center");
		}
		if (dc.getDamageReduction() != null) {
			drawSizedString(g, dc.getDamageReduction(), dr, sansPlain12,
					"center");
		}
	}

	public void drawHpBox(Graphics2D g, int x, int y) {
		g.setColor(Color.gray);
		g.fill(new Rectangle(x, y, 30, 18));

		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 30, 18));
		Rectangle hp = new Rectangle(x + 30, y, 30, 18);
		g.draw(hp);
		g.draw(new Rectangle(x + 60, y, 115, 18));

		drawSizedString(g, dc.getHpCalc().getDisplayValue(), hp, sansPlain12,
				"center");
		g.setColor(Color.white);
		g.setFont(sansBold12);
		g.drawString("HP", x + 5, y + 14);

	}

	public void drawConditionalBox(Graphics2D g, int x, int y) {
		g.setColor(Color.gray);
		g.fill(new Rectangle(x, y, 175, 15));

		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 175, 15));
		g.draw(new Rectangle(x, y + 15, 175, 118));

		g.setColor(Color.white);
		g.setFont(sansPlain8);
		g.drawString("CONDITIONAL MODIFIERS", x + 20, y + 13);
		g.setColor(Color.black);
		g.setFont(sansPlain8);

		if (dc.getModNotes() != null) {
			String s = dc.getModNotes();
			StringTokenizer split = new StringTokenizer(s, "\n");
			int notesY = y + 22;
			while (split.hasMoreTokens()) {
				g.drawString(split.nextToken(), x + 10, notesY);
				notesY += 10;
			}
		}

	}

	public void drawWeaponBox(Graphics2D g, int x, int y, int idx) {

		g.setColor(Color.gray);
		g.fill(new Rectangle(x + 120, y, 190, 7));
		g.fill(new Rectangle(x, y + 20, 310, 7));

		g.setColor(Color.black);

		g.draw(new Rectangle(x, y, 120, 20)); // name
		g.draw(new Rectangle(x + 120, y, 50, 20)); // melee
		g.draw(new Rectangle(x + 170, y, 50, 20)); // ranged
		g.draw(new Rectangle(x + 220, y, 35, 20)); // damage
		g.draw(new Rectangle(x + 255, y, 30, 20)); // crit
		g.draw(new Rectangle(x + 285, y, 25, 20)); // range
		g.drawLine(x + 120, y + 7, x + 310, y + 7);

		g.draw(new Rectangle(x, y + 20, 60, 20)); // use
		g.draw(new Rectangle(x + 60, y + 20, 15, 20)); // weight
		g.draw(new Rectangle(x + 75, y + 20, 235, 20)); // notes
		g.drawLine(x, y + 27, x + 310, y + 27);

		Vector<Weapon> v = (dc.getWeapons());

		if (v.size() >= idx) {

			Weapon w = v.get(idx - 1);
			if (w.isDisplaySheet()) {

				String name = w.getSheetFormat();
				// String name = w.getSizeOverride();
				// if (name == null){
				// name = "";
				// }
				// name += " "+w.getName();
				String type = w.getRangedmelee();

				// if (w.getBonus() != null && !w.getBonus().equals("")){
				// name += " +"+w.getBonus();
				// }
				
				if (w.isNamed()){
					drawSizedString(g, w.getTrueName(), x, y-4, 120, 20, sansBold12);
					drawSizedString(g, name, x, y+5, 120, 20, sansBold12);
				}else{
					drawSizedString(g, name, x, y, 120, 20, sansBold12);
				}
				drawSizedString(g, dc.calcDamage(w).getDisplayValue(), x + 220,
						y + 7, 35, 13, sansPlain8);

				//String crit = w.getCrit();
				Calculation critRangeCalc = dc.calcCritRange(w);
				Calculation critMultCalc = dc.calcCritMult(w);
				drawSizedString(g, critRangeCalc.getDisplayValue()+"/"+critMultCalc.getDisplayValue(), x + 255, y + 7, 25, 13, sansPlain8);
				if (dc.hasFeat("100")) {
					String range = w.getRange();
					try {
						if (range.indexOf("ft") > 0) {
							range = range.substring(0, range.indexOf("ft"));
						}
						int rangeInt = Integer.parseInt(range);
						if (type.equalsIgnoreCase("RANGED")) {
							rangeInt = new Double(rangeInt * 1.5).intValue();
						} else {
							rangeInt = rangeInt * 2;
						}
						range = "" + rangeInt;
					} catch (Exception e) {
						range = "-";
					}
					drawSizedString(g, range, x + 280, y + 7, 25, 13,
							sansPlain8);
				} else {
					drawSizedString(g, w.getRange(), x + 285, y + 7, 25, 13,
							sansPlain8);
				}

				drawSizedString(g, w.getQuantity(), x + 60, y + 27, 15, 13,
						sansPlain8);

				String use = w.getUse();
				drawSizedString(g, use, x, y + 27, 60, 15, sansPlain8);

				if (type.equalsIgnoreCase("RANGED")
						|| !w.getRange().equals("-")) {
					
					String rangedHit = dc.calcRangedToHit(w).getDisplayValue();
					if (rangedHit.length() > 14 && rangedHit.indexOf('/',14) > 0){
						String firstHalf = rangedHit.substring(0, rangedHit.indexOf('/',14));
						String secondHalf = rangedHit.substring(rangedHit.indexOf('/',14)+1);
						drawSizedString(g, firstHalf,
								x + 170, y + 6, 50, 10, sansPlain8);
						drawSizedString(g, secondHalf,
								x + 170, y + 11, 50, 10, sansPlain8);
						
					}else{
					drawSizedString(g, rangedHit,
							x + 170, y + 7, 50, 13, sansPlain8);
					}
				}

				if (type.equalsIgnoreCase("MELEE")) {
					drawSizedString(g, dc.calcMeleeToHit(w).getDisplayValue(),
							x + 120, y + 7, 50, 13, sansPlain8);
				}

				if (w.getNotesAndAbilities() != null) {
					drawSizedString(g, w.getNotesAndAbilities(), x + 75,
							y + 27, 220, 13, sansPlain8);
				}
			}
		}

		g.setFont(sansPlain5);
		g.setColor(Color.white);
		g.drawString("Melee Attack", x + 130, y + 6);
		g.drawString("Ranged Attack", x + 180, y + 6);
		g.drawString("Damage", x + 223, y + 6);
		g.drawString("Critical", x + 258, y + 6);
		g.drawString("Range", x + 288, y + 6);
		g.drawString("Use", x + 30, y + 26);
		g.drawString("#", x + 63, y + 26);
		g.drawString("Special Properties", x + 80, y + 26);

	}

	public void drawShieldBox(Graphics2D g, int x, int y) {

		g.setColor(Color.gray);
		g.fill(new Rectangle(x + 120, y, 190, 7));
		g.fill(new Rectangle(x, y + 20, 310, 7));

		g.setColor(Color.black);

		g.draw(new Rectangle(x, y, 120, 20));
		g.draw(new Rectangle(x + 120, y, 100, 20));

		g.draw(new Rectangle(x + 220, y, 25, 20));
		g.draw(new Rectangle(x + 245, y, 65, 20));

		g.drawLine(x + 120, y + 7, x + 310, y + 7);

		g.draw(new Rectangle(x, y + 20, 310, 20));
		g.drawLine(x, y + 27, x + 310, y + 27);

		g.setFont(sansPlain5);
		g.setColor(Color.white);
		g.drawString("Armor Bonus", x + 130, y + 6);

		g.drawString("Check", x + 223, y + 6);
		g.drawString("Spell Fail", x + 248, y + 6);

		g.drawString("Special Properties", x + 20, y + 26);

		g.setColor(Color.black);
		g.setFont(sansBold12);
		Armor a = dc.getShield();
		String name = a.getName();
		if (a.getBonus() != null && !a.getBonus().equals("0")) {
			name += " +" + a.getBonus();
		}
		g.drawString(format(name), x + 5, y + 16);

		g.setFont(sansPlain8);
		int bonus = 0;
		if (a.getArmorbonus() != null) {
			bonus += Integer.parseInt(a.getArmorbonus());
		}
		if (a.getBonus() != null) {
			bonus += Integer.parseInt(a.getBonus());
		}
		g.drawString(format("" + bonus), x + 130, y + 16);
		g.drawString(format(a.getCheckpenalty()), x + 223, y + 16);
		g.drawString(format(a.getArcanefail()), x + 248, y + 16);

		g.drawString(format(a.getProperties()), x + 20, y + 36);

	}

	public void drawArmorBox(Graphics2D g, int x, int y) {

		g.setColor(Color.gray);
		g.fill(new Rectangle(x + 120, y, 190, 7));
		g.fill(new Rectangle(x, y + 20, 310, 7));

		g.setColor(Color.black);

		g.draw(new Rectangle(x, y, 120, 20));
		g.draw(new Rectangle(x + 120, y, 30, 20));
		g.draw(new Rectangle(x + 150, y, 30, 20));
		g.draw(new Rectangle(x + 180, y, 40, 20));
		g.draw(new Rectangle(x + 220, y, 30, 20));
		g.draw(new Rectangle(x + 250, y, 60, 20));

		g.drawLine(x + 120, y + 7, x + 310, y + 7);

		g.draw(new Rectangle(x, y + 20, 310, 20));
		g.drawLine(x, y + 27, x + 310, y + 27);

		g.setFont(sansPlain5);
		g.setColor(Color.white);

		g.drawString("Type", x + 122, y + 6);
		g.drawString("Speed", x + 152, y + 6);
		g.drawString("Armor Bonus", x + 182, y + 6);
		g.drawString("Check", x + 222, y + 6);
		g.drawString("Spell Fail", x + 252, y + 6);

		g.drawString("Special Properties", x + 20, y + 26);

		g.setColor(Color.black);
		g.setFont(sansBold12);
		Armor a = dc.getArmor();
		String name = format(a.getName());
		if (a.getBonus() != null && !a.getBonus().equals("0")) {
			name += " +" + a.getBonus();
		}
		g.drawString(name, x + 5, y + 16);

		g.setFont(sansPlain8);
		g.drawString(format(a.getGrade()), x + 122, y + 16);
		// modify for base speed

		g.drawString(format("" + a.getSpeed(dc.baseMovement)), x + 152, y + 16);

		String bonus = "";
		if (a.getArmorbonus() != null) {
			bonus += Integer.parseInt(a.getArmorbonus());
		}
		if (a.getBonus() != null) {
			bonus += "+" + (Integer.parseInt(a.getBonus()));
		}
		g.drawString(format("" + bonus), x + 182, y + 16);
		g.drawString(format(a.getCheckpenalty()), x + 222, y + 16);
		g.drawString(format(a.getArcanefail()), x + 252, y + 16);

		g.drawString(format(a.getProperties() + a.getDescription()), x + 20,
				y + 36);
	}

	public void drawAttackBoxes(Graphics2D g, int x, int y, String base) {
		g.setColor(Color.gray);
		g.fill(new Rectangle(x, y, 50, 20));
		g.fill(new Rectangle(x, y + 20, 50, 20));
		g.fill(new Rectangle(x + 110, y, 65, 10));

		g.setColor(Color.white);
		g.setFont(sansBold12);
		g.drawString("Melee", x + 3, y + 11);
		g.drawString("Ranged", x + 3, y + 33);
		g.setFont(sansPlain5);
		g.drawString("Strength", x + 3, y + 17);
		g.drawString("Dexterity", x + 3, y + 39);
		g.setFont(sansPlain8);
		g.drawString("Base Attack", x + 126, y + 9);

		g.setColor(Color.black);
		g.setFont(sansPlain5);
		g.draw(new Rectangle(x, y, 50, 20));
		g.draw(new Rectangle(x, y + 20, 50, 20));
		g.draw(new Rectangle(x + 110, y, 65, 10));
		g.draw(new Rectangle(x + 50, y, 60, 20));
		g.draw(new Rectangle(x + 50, y + 20, 60, 20));
		g.draw(new Rectangle(x + 110, y + 10, 65, 30));

		g.drawString("Abil", x + 185, y - 1);
		g.draw(new Rectangle(x + 175, y, 25, 20));

		g.drawString("Size", x + 207, y - 1);
		g.draw(new Rectangle(x + 200, y, 25, 20));

		g.drawString("Temp", x + 229, y - 1);
		g.draw(new Rectangle(x + 225, y, 25, 20));

		g.drawString("Epic", x + 251, y - 1);
		g.draw(new Rectangle(x + 250, y, 25, 20));
		g.draw(new Rectangle(x + 275, y, 25, 20));

		g.draw(new Rectangle(x + 175, y + 20, 25, 20));
		g.draw(new Rectangle(x + 200, y + 20, 25, 20));
		g.draw(new Rectangle(x + 225, y + 20, 25, 20));
		g.draw(new Rectangle(x + 250, y + 20, 25, 20));
		g.draw(new Rectangle(x + 275, y + 20, 25, 20));

		drawSizedString(g, baseAttackBonus, x + 115, y + 10, 60, 30, sansBold12);
		drawSizedString(g, meleeAttackBonus, x + 50, y, 60, 18, sansBold12);
		drawSizedString(g, rangedAttackBonus, x + 50, y + 22, 60, 18,
				sansBold12);
		drawSizedString(g, "" + dc.getBonus(dc.getStat("STRENGTH")), x + 180,
				y, 20, 18, sansBold12);
		drawSizedString(g, "" + dc.getBonus(dc.getStat("DEXTERITY")), x + 180,
				y + 20, 20, 18, sansBold12);
		drawSizedString(g, "" + dc.getSizeMod(), x + 202, y, 20, 18, sansBold12);
		drawSizedString(g, "" + dc.getSizeMod(), x + 202, y + 20, 20, 18,
				sansBold12);
	}

	public void drawMovementBox(Graphics2D g, int x, int y) {
		g.setColor(Color.gray);
		g.fill(new Rectangle(x, y, 100, 18));
		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 100, 11));
		g.draw(new Rectangle(x, y + 11, 33, 23));
		g.draw(new Rectangle(x + 33, y + 11, 33, 23));
		g.draw(new Rectangle(x + 66, y + 11, 34, 23));
		g.drawLine(x, y + 18, x + 100, y + 18);

		g.setColor(Color.white);
		g.setFont(sansPlain8);
		g.drawString("MOVEMENT", x + 25, y + 9);
		g.setFont(sansPlain5);
		g.drawString("5-FOOT", x + 7, y + 17);
		g.drawString("NORMAL", x + 40, y + 17);
		g.drawString("FULL (" + dc.getFullMoveModifier() + ")", x + 75, y + 17);

		g.setColor(Color.black);
		g.setFont(sansPlain14);

		drawSizedString(g, dc.getFiveFootStep().getDisplayValue() + "'", x,
				y + 18, 33, 14, sansPlain14);
		drawSizedString(g, dc.getBaseMovement() + "'", x + 33, y + 18, 33, 14,
				sansPlain14);
		drawSizedString(g, dc.getFullMovement().getDisplayValue() + "'",
				x + 66, y + 18, 34, 14, sansPlain14);
	}

	public void drawExpBox(Graphics2D g, int x, int y, int xp, int level) {
		g.setColor(Color.gray);
		g.fill(new Rectangle(x, y, 70, 10));
		g.fill(new Rectangle(x, y + 35, 70, 7));
		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 70, 10));
		g.draw(new Rectangle(x, y + 10, 70, 25));

		g.setFont(sansPlain8);
		g.setColor(Color.white);
		g.drawString("EXPERIENCE", x + 8, y + 9);
		g.setColor(Color.black);

		g.setFont(sansPlain14);
		g.drawString(renderAsNumber("" + xp), x + 10, y + 30);
		g.setFont(sansPlain5);
		g.setColor(Color.white);
		g.drawString("Next Level", x + 20, y + 40);
		g.setColor(Color.black);
		g.draw(new Rectangle(x, y + 42, 70, 10));
		int next = 0;
		for (int i = 1; i <= level; i++) {
			next += i;
		}
		next = next * 1000;
		g.setFont(sansPlain8);
		g.drawString(renderAsNumber("" + next), x + 20, y + 50);

		g.draw(new Rectangle(x + 70, y, 70, 100));

	}

	public void drawInitBox(Graphics2D g, int x, int y, String save,
			String stat, String val, int base, int bonus) {

		g.setColor(Color.gray);
		g.fill(new Rectangle(x, y, 30, 20));

		g.setFont(sansBold12);
		g.setColor(Color.white);
		g.drawString(save, x + 5, y + 10);
		g.setFont(sansPlain5);
		g.drawString(stat, x + 5, y + 16);

		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 60, 20));
		g.draw(new Rectangle(x + 30, y, 30, 20));
		g.draw(new Rectangle(x + 60, y, 20, 20));
		g.draw(new Rectangle(x + 80, y, 20, 20));

		g.setFont(sansPlain12);

		drawSizedString(g, "" + val, x + 30, y, 30, 20, playerNameFont,
				"center");
		drawSizedString(g, "" + base, x + 60, y, 20, 20, playerNameFont,
				"center");
		drawSizedString(g, "" + bonus, x + 80, y, 20, 20, playerNameFont,
				"center");

	}

	public void drawSaveBox(Graphics2D g, int x, int y, String save,
			String stat, String val, int base, int bonus) {

		g.setColor(Color.gray);
		Rectangle header = new Rectangle(x, y, 30, 20);
		g.fill(header);

		g.setFont(sansBold12);
		g.setColor(Color.white);
		g.drawString(save, x + 5, y + 10);
		g.setFont(sansPlain5);
		g.drawString(stat, x + 5, y + 16);

		g.setColor(Color.black);
		g.draw(header);

		Rectangle total = new Rectangle(x + 30, y, 30, 20);
		Rectangle baser = new Rectangle(x + 60, y, 20, 20);
		Rectangle misc = new Rectangle(x + 80, y, 20, 20);
		Rectangle temp = new Rectangle(x + 100, y, 20, 20);

		g.draw(total);
		g.draw(baser);
		g.draw(misc);
		g.draw(new Rectangle(x + 100, y, 20, 20));

		int parts = base + bonus;
		int ttl = Integer.parseInt(val);
		int tmp = ttl - parts;

		drawSizedString(g, "" + val, total, playerNameFont, "center");
		drawSizedString(g, "" + base, baser, playerNameFont, "center");
		drawSizedString(g, "" + bonus, misc, playerNameFont, "center");
		drawSizedString(g, "" + tmp, temp, playerNameFont, "center");

	}

	public void drawStatBox(Graphics2D g, int x, int y, String stat,
			String stat2, int val) {

		g.setColor(Color.black);
		Rectangle statScore = new Rectangle(x + 30, y, 22, 20);
		Rectangle statBonus = new Rectangle(x + 52, y, 22, 20);
		g.draw(statScore);
		g.draw(statBonus);
		g.draw(new Rectangle(x + 74, y, 22, 20));
		g.draw(new Rectangle(x + 96, y, 22, 20));

		g.setColor(Color.gray);
		g.fill(new Rectangle(x, y, 30, 20));
		g.setColor(Color.black);
		g.draw(new Rectangle(x, y, 30, 20));

		g.setColor(Color.white);

		g.setFont(sansBold12);
		g.drawString(stat, x + 3, y + 10);

		g.setFont(sansPlain5);
		g.drawString(stat2, x + 3, y + 16);
		g.setColor(Color.black);

		g.setFont(sansPlain12);
		drawSizedString(g, "" + val, statScore, playerNameFont, "center");
		drawSizedString(g, "" + dc.getBonus(val), statBonus, playerNameFont,
				"center");
	}

	public String format(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}

}
