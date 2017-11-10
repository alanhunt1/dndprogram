package initcheck.character;

import initcheck.database.RacialAbility;
import initcheck.database.RacialAbilityDAO;
import initcheck.database.Tag;

import java.io.Serializable;
import java.util.Vector;

public class StatBlock implements Serializable {

	private static final long serialVersionUID = 1L;

	// base stats - rolled
	int baseStr = 10;

	int baseDex = 10;

	int baseCon = 10;

	int baseInt = 10;

	int baseWis = 10;

	int baseCha = 10;

	// racial mods
	int raceStr = 0;

	int raceDex = 0;

	int raceCon = 0;

	int raceInt = 0;

	int raceWis = 0;

	int raceCha = 0;

	// size mods
	int sizeStr = 0;

	int sizeDex = 0;

	int sizeCon = 0;

	// stat buy mods (1 per 4 levels)
	int levelStr = 0;

	int levelDex = 0;

	int levelCon = 0;

	int levelInt = 0;

	int levelWis = 0;

	int levelCha = 0;

	// misc mods (item, character, etc)
	int miscStr = 0;

	int miscDex = 0;

	int miscCon = 0;

	int miscInt = 0;

	int miscWis = 0;

	int miscCha = 0;

	// description fields for the misc mods
	String miscStrText;

	String miscDexText;

	String miscConText;

	String miscIntText;

	String miscWisText;

	String miscChaText;

	// mods from manuals/tomes
	int bookStr = 0;

	int bookDex = 0;

	int bookCon = 0;

	int bookInt = 0;

	int bookWis = 0;

	int bookCha = 0;

	public StatBlock() {

	}

	public StatBlock(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Str")) {
				setBaseStr(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Wis")) {
				setBaseWis(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Dex")) {
				setBaseDex(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Con")) {
				setBaseCon(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Cha")) {
				setBaseCha(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Intel")) {
				setBaseInt(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("LevelStr")) {
				setLevelStr(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("LevelDex")) {
				setLevelDex(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("LevelInt")) {
				setLevelInt(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("LevelCon")) {
				setLevelCon(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("LevelWis")) {
				setLevelWis(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("LevelCha")) {
				setLevelCha(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("MiscStr")) {
				setMiscStr(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("MiscDex")) {
				setMiscDex(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("MiscCon")) {
				setMiscCon(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("MiscInt")) {
				setMiscInt(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("MiscWis")) {
				setMiscWis(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("MiscCha")) {
				setMiscCha(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("BookStr")) {
				setBookStr(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("BookDex")) {
				setBookDex(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("BookCon")) {
				setBookCon(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("BookInt")) {
				setBookInt(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("BookWis")) {
				setBookWis(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("BookCha")) {
				setBookCha(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("MiscStrText")) {
				setMiscStrText(t.getTagBody());
			}
			if (t.getTagName().equals("MiscDexText")) {
				setMiscDexText(t.getTagBody());
			}
			if (t.getTagName().equals("MiscConText")) {
				setMiscConText(t.getTagBody());
			}
			if (t.getTagName().equals("MiscIntText")) {
				setMiscIntText(t.getTagBody());
			}
			if (t.getTagName().equals("MiscWisText")) {
				setMiscWisText(t.getTagBody());
			}
			if (t.getTagName().equals("MiscChaText")) {
				setMiscChaText(t.getTagBody());
			}

			s = s.substring(tagName.length() + tagName.length() + 5
					+ tagBody.length(), s.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<StatBlock>\n");
		sb.append("<Str>" + baseStr + "</Str>\n");
		sb.append("<Wis>" + baseWis + "</Wis>\n");
		sb.append("<Dex>" + baseDex + "</Dex>\n");
		sb.append("<Con>" + baseCon + "</Con>\n");
		sb.append("<Cha>" + baseCha + "</Cha>\n");
		sb.append("<Intel>" + baseInt + "</Intel>\n");
		sb.append("<LevelStr>" + levelStr + "</LevelStr>\n");
		sb.append("<LevelDex>" + levelDex + "</LevelDex>\n");
		sb.append("<LevelInt>" + levelInt + "</LevelInt>\n");
		sb.append("<LevelCon>" + levelCon + "</LevelCon>\n");
		sb.append("<LevelWis>" + levelWis + "</LevelWis>\n");
		sb.append("<LevelCha>" + levelCha + "</LevelCha>\n");
		sb.append("<MiscStr>" + miscStr + "</MiscStr>\n");
		sb.append("<MiscDex>" + miscDex + "</MiscDex>\n");
		sb.append("<MiscCon>" + miscCon + "</MiscCon>\n");
		sb.append("<MiscInt>" + miscInt + "</MiscInt>\n");
		sb.append("<MiscWis>" + miscWis + "</MiscWis>\n");
		sb.append("<MiscCha>" + miscCha + "</MiscCha>\n");
		sb.append("<BookStr>" + bookStr + "</BookStr>\n");
		sb.append("<BookDex>" + bookDex + "</BookDex>\n");
		sb.append("<BookCon>" + bookCon + "</BookCon>\n");
		sb.append("<BookInt>" + bookInt + "</BookInt>\n");
		sb.append("<BookWis>" + bookWis + "</BookWis>\n");
		sb.append("<BookCha>" + bookCha + "</BookCha>\n");
		sb.append("<MiscStrText>" + miscStrText + "</MiscStrText>\n");
		sb.append("<MiscDexText>" + miscDexText + "</MiscDexText>\n");
		sb.append("<MiscConText>" + miscConText + "</MiscConText>\n");
		sb.append("<MiscIntText>" + miscIntText + "</MiscIntText>\n");
		sb.append("<MiscWisText>" + miscWisText + "</MiscWisText>\n");
		sb.append("<MiscChaText>" + miscChaText + "</MiscChaText>\n");
		sb.append("</StatBlock>\n");
		return sb.toString();
	}

	public StatBlock(String race, String size) {
		RacialAbilityDAO radb = new RacialAbilityDAO();

		Vector<RacialAbility> v = radb.getRacialAbilities(race);

		for (int i = 0; i < v.size(); i++) {
			RacialAbility ra = (RacialAbility) v.get(i);
			if (ra.getAbilityName().equals("STAT")) {
				if (ra.getAbilityType().equals("STRENGTH")) {
					raceStr = Integer.parseInt(ra.getAbilityValue());
				}
				if (ra.getAbilityType().equals("DEXTERITY")) {
					raceDex = Integer.parseInt(ra.getAbilityValue());
				}
				if (ra.getAbilityType().equals("CONSTITUTION")) {
					raceCon = Integer.parseInt(ra.getAbilityValue());
				}
				if (ra.getAbilityType().equals("INTELLIGENCE")) {
					raceInt = Integer.parseInt(ra.getAbilityValue());
				}
				if (ra.getAbilityType().equals("WISDOM")) {
					raceWis = Integer.parseInt(ra.getAbilityValue());
				}
				if (ra.getAbilityType().equals("CHARISMA")) {
					raceCha = Integer.parseInt(ra.getAbilityValue());
				}
			}
		}

	}

	public int getStat(String s) {
		if (s.equals("INTELLIGENCE")) {
			return getInt();
		} else if (s.equals("DEXTERITY")) {
			return getDex();
		} else if (s.equals("WISDOM")) {
			return getWis();
		} else if (s.equals("STRENGTH")) {
			return getStr();
		} else if (s.equals("CHARISMA")) {
			return getCha();
		} else if (s.equals("CONSTITUTION")) {
			return getCon();
		}
		return 0;
	}

	public int getLevelPoints() {
		return levelStr + levelDex + levelCon + levelInt + levelWis + levelCha;
	}

	public int getStr() {
		return baseStr + raceStr + sizeStr + levelStr + miscStr + bookStr;
	}

	public int getDex() {
		return baseDex + raceDex + sizeDex + levelDex + miscDex + bookDex;
	}

	public int getCon() {
		return baseCon + raceCon + sizeCon + levelCon + miscCon + bookCon;
	}

	public int getInt() {
		return baseInt + raceInt + levelInt + miscInt + bookInt;
	}

	public int getWis() {
		return baseWis + raceWis + levelWis + miscWis + bookWis;
	}

	public int getCha() {
		return baseCha + raceCha + levelCha + miscCha + bookCha;
	}

	/**
	 * Get the BookCha value.
	 * 
	 * @return the BookCha value.
	 */
	public int getBookCha() {
		return bookCha;
	}

	/**
	 * Set the BookCha value.
	 * 
	 * @param newBookCha
	 *            The new BookCha value.
	 */
	public void setBookCha(int newBookCha) {
		this.bookCha = newBookCha;
	}

	/**
	 * Get the BookWis value.
	 * 
	 * @return the BookWis value.
	 */
	public int getBookWis() {
		return bookWis;
	}

	/**
	 * Set the BookWis value.
	 * 
	 * @param newBookWis
	 *            The new BookWis value.
	 */
	public void setBookWis(int newBookWis) {
		this.bookWis = newBookWis;
	}

	/**
	 * Get the BookInt value.
	 * 
	 * @return the BookInt value.
	 */
	public int getBookInt() {
		return bookInt;
	}

	/**
	 * Set the BookInt value.
	 * 
	 * @param newBookInt
	 *            The new BookInt value.
	 */
	public void setBookInt(int newBookInt) {
		this.bookInt = newBookInt;
	}

	/**
	 * Get the BookCon value.
	 * 
	 * @return the BookCon value.
	 */
	public int getBookCon() {
		return bookCon;
	}

	/**
	 * Set the BookCon value.
	 * 
	 * @param newBookCon
	 *            The new BookCon value.
	 */
	public void setBookCon(int newBookCon) {
		this.bookCon = newBookCon;
	}

	/**
	 * Get the BookDex value.
	 * 
	 * @return the BookDex value.
	 */
	public int getBookDex() {
		return bookDex;
	}

	/**
	 * Set the BookDex value.
	 * 
	 * @param newBookDex
	 *            The new BookDex value.
	 */
	public void setBookDex(int newBookDex) {
		this.bookDex = newBookDex;
	}

	/**
	 * Get the BookStr value.
	 * 
	 * @return the BookStr value.
	 */
	public int getBookStr() {
		return bookStr;
	}

	/**
	 * Set the BookStr value.
	 * 
	 * @param newBookStr
	 *            The new BookStr value.
	 */
	public void setBookStr(int newBookStr) {
		this.bookStr = newBookStr;
	}

	/**
	 * Get the MiscChaText value.
	 * 
	 * @return the MiscChaText value.
	 */
	public String getMiscChaText() {
		return miscChaText;
	}

	/**
	 * Set the MiscChaText value.
	 * 
	 * @param newMiscChaText
	 *            The new MiscChaText value.
	 */
	public void setMiscChaText(String newMiscChaText) {
		this.miscChaText = newMiscChaText;
	}

	/**
	 * Get the MiscWisText value.
	 * 
	 * @return the MiscWisText value.
	 */
	public String getMiscWisText() {
		return miscWisText;
	}

	/**
	 * Set the MiscWisText value.
	 * 
	 * @param newMiscWisText
	 *            The new MiscWisText value.
	 */
	public void setMiscWisText(String newMiscWisText) {
		this.miscWisText = newMiscWisText;
	}

	/**
	 * Get the MiscIntText value.
	 * 
	 * @return the MiscIntText value.
	 */
	public String getMiscIntText() {
		return miscIntText;
	}

	/**
	 * Set the MiscIntText value.
	 * 
	 * @param newMiscIntText
	 *            The new MiscIntText value.
	 */
	public void setMiscIntText(String newMiscIntText) {
		this.miscIntText = newMiscIntText;
	}

	/**
	 * Get the MiscConText value.
	 * 
	 * @return the MiscConText value.
	 */
	public String getMiscConText() {
		return miscConText;
	}

	/**
	 * Set the MiscConText value.
	 * 
	 * @param newMiscConText
	 *            The new MiscConText value.
	 */
	public void setMiscConText(String newMiscConText) {
		this.miscConText = newMiscConText;
	}

	/**
	 * Get the MiscDexText value.
	 * 
	 * @return the MiscDexText value.
	 */
	public String getMiscDexText() {
		return miscDexText;
	}

	/**
	 * Set the MiscDexText value.
	 * 
	 * @param newMiscDexText
	 *            The new MiscDexText value.
	 */
	public void setMiscDexText(String newMiscDexText) {
		this.miscDexText = newMiscDexText;
	}

	/**
	 * Get the MiscStrText value.
	 * 
	 * @return the MiscStrText value.
	 */
	public String getMiscStrText() {
		return miscStrText;
	}

	/**
	 * Set the MiscStrText value.
	 * 
	 * @param newMiscStrText
	 *            The new MiscStrText value.
	 */
	public void setMiscStrText(String newMiscStrText) {
		this.miscStrText = newMiscStrText;
	}

	/**
	 * Get the MiscCha value.
	 * 
	 * @return the MiscCha value.
	 */
	public int getMiscCha() {
		return miscCha;
	}

	/**
	 * Set the MiscCha value.
	 * 
	 * @param newMiscCha
	 *            The new MiscCha value.
	 */
	public void setMiscCha(int newMiscCha) {
		this.miscCha = newMiscCha;
	}

	/**
	 * Get the MiscWis value.
	 * 
	 * @return the MiscWis value.
	 */
	public int getMiscWis() {
		return miscWis;
	}

	/**
	 * Set the MiscWis value.
	 * 
	 * @param newMiscWis
	 *            The new MiscWis value.
	 */
	public void setMiscWis(int newMiscWis) {
		this.miscWis = newMiscWis;
	}

	/**
	 * Get the MiscInt value.
	 * 
	 * @return the MiscInt value.
	 */
	public int getMiscInt() {
		return miscInt;
	}

	/**
	 * Set the MiscInt value.
	 * 
	 * @param newMiscInt
	 *            The new MiscInt value.
	 */
	public void setMiscInt(int newMiscInt) {
		this.miscInt = newMiscInt;
	}

	/**
	 * Get the MiscCon value.
	 * 
	 * @return the MiscCon value.
	 */
	public int getMiscCon() {
		return miscCon;
	}

	/**
	 * Set the MiscCon value.
	 * 
	 * @param newMiscCon
	 *            The new MiscCon value.
	 */
	public void setMiscCon(int newMiscCon) {
		this.miscCon = newMiscCon;
	}

	/**
	 * Get the MiscDex value.
	 * 
	 * @return the MiscDex value.
	 */
	public int getMiscDex() {
		return miscDex;
	}

	/**
	 * Set the MiscDex value.
	 * 
	 * @param newMiscDex
	 *            The new MiscDex value.
	 */
	public void setMiscDex(int newMiscDex) {
		this.miscDex = newMiscDex;
	}

	/**
	 * Get the MiscStr value.
	 * 
	 * @return the MiscStr value.
	 */
	public int getMiscStr() {
		return miscStr;
	}

	/**
	 * Set the MiscStr value.
	 * 
	 * @param newMiscStr
	 *            The new MiscStr value.
	 */
	public void setMiscStr(int newMiscStr) {
		this.miscStr = newMiscStr;
	}

	/**
	 * Get the LevelCha value.
	 * 
	 * @return the LevelCha value.
	 */
	public int getLevelCha() {
		return levelCha;
	}

	/**
	 * Set the LevelCha value.
	 * 
	 * @param newLevelCha
	 *            The new LevelCha value.
	 */
	public void setLevelCha(int newLevelCha) {
		this.levelCha = newLevelCha;
	}

	/**
	 * Get the LevelWis value.
	 * 
	 * @return the LevelWis value.
	 */
	public int getLevelWis() {
		return levelWis;
	}

	/**
	 * Set the LevelWis value.
	 * 
	 * @param newLevelWis
	 *            The new LevelWis value.
	 */
	public void setLevelWis(int newLevelWis) {
		this.levelWis = newLevelWis;
	}

	/**
	 * Get the LevelInt value.
	 * 
	 * @return the LevelInt value.
	 */
	public int getLevelInt() {
		return levelInt;
	}

	/**
	 * Set the LevelInt value.
	 * 
	 * @param newLevelInt
	 *            The new LevelInt value.
	 */
	public void setLevelInt(int newLevelInt) {
		this.levelInt = newLevelInt;
	}

	/**
	 * Get the LevelCon value.
	 * 
	 * @return the LevelCon value.
	 */
	public int getLevelCon() {
		return levelCon;
	}

	/**
	 * Set the LevelCon value.
	 * 
	 * @param newLevelCon
	 *            The new LevelCon value.
	 */
	public void setLevelCon(int newLevelCon) {
		this.levelCon = newLevelCon;
	}

	/**
	 * Get the LevelDex value.
	 * 
	 * @return the LevelDex value.
	 */
	public int getLevelDex() {
		return levelDex;
	}

	/**
	 * Set the LevelDex value.
	 * 
	 * @param newLevelDex
	 *            The new LevelDex value.
	 */
	public void setLevelDex(int newLevelDex) {
		this.levelDex = newLevelDex;
	}

	/**
	 * Get the LevelStr value.
	 * 
	 * @return the LevelStr value.
	 */
	public int getLevelStr() {
		return levelStr;
	}

	/**
	 * Set the LevelStr value.
	 * 
	 * @param newLevelStr
	 *            The new LevelStr value.
	 */
	public void setLevelStr(int newLevelStr) {
		this.levelStr = newLevelStr;
	}

	/**
	 * Get the SizeCon value.
	 * 
	 * @return the SizeCon value.
	 */
	public int getSizeCon() {
		return sizeCon;
	}

	/**
	 * Set the SizeCon value.
	 * 
	 * @param newSizeCon
	 *            The new SizeCon value.
	 */
	public void setSizeCon(int newSizeCon) {
		this.sizeCon = newSizeCon;
	}

	/**
	 * Get the SizeDex value.
	 * 
	 * @return the SizeDex value.
	 */
	public int getSizeDex() {
		return sizeDex;
	}

	/**
	 * Set the SizeDex value.
	 * 
	 * @param newSizeDex
	 *            The new SizeDex value.
	 */
	public void setSizeDex(int newSizeDex) {
		this.sizeDex = newSizeDex;
	}

	/**
	 * Get the SizeStr value.
	 * 
	 * @return the SizeStr value.
	 */
	public int getSizeStr() {
		return sizeStr;
	}

	/**
	 * Set the SizeStr value.
	 * 
	 * @param newSizeStr
	 *            The new SizeStr value.
	 */
	public void setSizeStr(int newSizeStr) {
		this.sizeStr = newSizeStr;
	}

	/**
	 * Get the RaceCha value.
	 * 
	 * @return the RaceCha value.
	 */
	public int getRaceCha() {
		return raceCha;
	}

	/**
	 * Set the RaceCha value.
	 * 
	 * @param newRaceCha
	 *            The new RaceCha value.
	 */
	public void setRaceCha(int newRaceCha) {
		this.raceCha = newRaceCha;
	}

	/**
	 * Get the RaceWis value.
	 * 
	 * @return the RaceWis value.
	 */
	public int getRaceWis() {
		return raceWis;
	}

	/**
	 * Set the RaceWis value.
	 * 
	 * @param newRaceWis
	 *            The new RaceWis value.
	 */
	public void setRaceWis(int newRaceWis) {
		this.raceWis = newRaceWis;
	}

	/**
	 * Get the RaceInt value.
	 * 
	 * @return the RaceInt value.
	 */
	public int getRaceInt() {
		return raceInt;
	}

	/**
	 * Set the RaceInt value.
	 * 
	 * @param newRaceInt
	 *            The new RaceInt value.
	 */
	public void setRaceInt(int newRaceInt) {
		this.raceInt = newRaceInt;
	}

	/**
	 * Get the RaceCon value.
	 * 
	 * @return the RaceCon value.
	 */
	public int getRaceCon() {
		return raceCon;
	}

	/**
	 * Set the RaceCon value.
	 * 
	 * @param newRaceCon
	 *            The new RaceCon value.
	 */
	public void setRaceCon(int newRaceCon) {
		this.raceCon = newRaceCon;
	}

	/**
	 * Get the RaceDex value.
	 * 
	 * @return the RaceDex value.
	 */
	public int getRaceDex() {
		return raceDex;
	}

	/**
	 * Set the RaceDex value.
	 * 
	 * @param newRaceDex
	 *            The new RaceDex value.
	 */
	public void setRaceDex(int newRaceDex) {
		this.raceDex = newRaceDex;
	}

	/**
	 * Get the RaceStr value.
	 * 
	 * @return the RaceStr value.
	 */
	public int getRaceStr() {
		return raceStr;
	}

	/**
	 * Set the RaceStr value.
	 * 
	 * @param newRaceStr
	 *            The new RaceStr value.
	 */
	public void setRaceStr(int newRaceStr) {
		this.raceStr = newRaceStr;
	}

	/**
	 * Get the BaseCha value.
	 * 
	 * @return the BaseCha value.
	 */
	public int getBaseCha() {
		return baseCha;
	}

	/**
	 * Set the BaseCha value.
	 * 
	 * @param newBaseCha
	 *            The new BaseCha value.
	 */
	public void setBaseCha(int newBaseCha) {
		this.baseCha = newBaseCha;
	}

	/**
	 * Get the BaseWis value.
	 * 
	 * @return the BaseWis value.
	 */
	public int getBaseWis() {
		return baseWis;
	}

	/**
	 * Set the BaseWis value.
	 * 
	 * @param newBaseWis
	 *            The new BaseWis value.
	 */
	public void setBaseWis(int newBaseWis) {
		this.baseWis = newBaseWis;
	}

	/**
	 * Get the BaseInt value.
	 * 
	 * @return the BaseInt value.
	 */
	public int getBaseInt() {
		return baseInt;
	}

	/**
	 * Set the BaseInt value.
	 * 
	 * @param newBaseInt
	 *            The new BaseInt value.
	 */
	public void setBaseInt(int newBaseInt) {
		this.baseInt = newBaseInt;
	}

	/**
	 * Get the BaseCon value.
	 * 
	 * @return the BaseCon value.
	 */
	public int getBaseCon() {
		return baseCon;
	}

	/**
	 * Set the BaseCon value.
	 * 
	 * @param newBaseCon
	 *            The new BaseCon value.
	 */
	public void setBaseCon(int newBaseCon) {
		this.baseCon = newBaseCon;
	}

	/**
	 * Get the BaseDex value.
	 * 
	 * @return the BaseDex value.
	 */
	public int getBaseDex() {
		return baseDex;
	}

	/**
	 * Set the BaseDex value.
	 * 
	 * @param newBaseDex
	 *            The new BaseDex value.
	 */
	public void setBaseDex(int newBaseDex) {
		this.baseDex = newBaseDex;
	}

	/**
	 * Get the BaseStr value.
	 * 
	 * @return the BaseStr value.
	 */
	public int getBaseStr() {
		return baseStr;
	}

	/**
	 * Set the BaseStr value.
	 * 
	 * @param newBaseStr
	 *            The new BaseStr value.
	 */
	public void setBaseStr(int newBaseStr) {
		this.baseStr = newBaseStr;
	}

}
