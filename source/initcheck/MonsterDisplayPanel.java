package initcheck;

import initcheck.character.chooser.SourceChooser;
import initcheck.character.library.LibraryPanel;
import initcheck.database.Monster;
import initcheck.database.MonsterDAO;
import initcheck.displaycomponents.MonsterAcPanel;
import initcheck.displaycomponents.MonsterAttackListPanel;
import initcheck.displaycomponents.MonsterAttackPanel;
import initcheck.displaycomponents.MonsterHitPanel;
import initcheck.displaycomponents.MonsterImagePanel;
import initcheck.displaycomponents.MonsterInitPanel;
import initcheck.displaycomponents.MonsterNamePanel;
import initcheck.displaycomponents.MonsterNotesPanel;
import initcheck.displaycomponents.MonsterOrgPanel;
import initcheck.displaycomponents.MonsterPoisonPanel;
import initcheck.displaycomponents.MonsterSavePanel;
import initcheck.displaycomponents.MonsterSpecialInfoPanel;
import initcheck.displaycomponents.MonsterSpeedPanel;
import initcheck.displaycomponents.MonsterStatPanel;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledPanel;
import initcheck.images.ImageSelectionOwner;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 * <code>MonsterDisplayPanel</code> is used to display the full information
 * associated with a monster, or to add a new monster to the database, depending
 * on the mode in which it is created.
 * 
 * @author <a href="mailto:ahunt@cse.buffalo.edu">Alan M. Hunt</a>
 * @version 1.0
 */
public class MonsterDisplayPanel extends TiledPanel implements
		ImageSelectionOwner {

	static final long serialVersionUID = InitVersion.VERSION;

	// defines
	public static final int ADD = 1;

	public static final int MOD = 2;

	public static final int DISPLAY = 3;

	private JScrollPane scroll;

	private MonsterNamePanel nameBox;

	private MonsterHitPanel hitPanel;

	private MonsterOrgPanel miscPanel;

	private MonsterInitPanel initPanel;

	private MonsterAcPanel acPanel;

	private MonsterSavePanel savePanel;

	private MonsterStatPanel statPanel;

	private MonsterAttackPanel attackPanel;

	private MonsterSpeedPanel speedPanel;

	private MonsterSpecialInfoPanel specialPanel;

	private MonsterNotesPanel notesPanel;

	private MonsterImagePanel imagePanel;

	private MonsterAttackListPanel attackListPanel;

	private MonsterPoisonPanel poisonPanel;

	private Monster monster;

	private TiledGridPanel bufferPanel = new TiledGridPanel(InitImage.darkRocks);

	private TiledGridPanel secondaryPanel = new TiledGridPanel();

	private LibraryPanel owner;

	private JTabbedPane tabbedPane = new JTabbedPane();

	private SourceChooser sourceChooser = new SourceChooser();
	
	public MonsterDisplayPanel(LibraryPanel owner) {
		// super("Monster Information", true);
		this.owner = owner;
		doInit(null, ADD);
	}

	public MonsterDisplayPanel(LibraryPanel owner, final int mode) {
		// super("Monster Information", true);
		this.owner = owner;
		doInit(null, mode);
	}

	
	public MonsterDisplayPanel(final Monster c, LibraryPanel owner) {
		// super("Monster Information", true);
		this.owner = owner;
		this.monster = c;
		
		doInit(c, DISPLAY);
		setMonster(c);
	}

	public MonsterDisplayPanel(final Monster c, final int mode,
			LibraryPanel owner) {
		// super("Monster Information", true);
		this.owner = owner;
		this.monster = c;
		
		doInit(c, mode);
		setMonster(c);
	}

	public MonsterDisplayPanel() {
		doInit(null, DISPLAY);
	}

	/**
	 * Initialize the dialog components.
	 */
	private void doInit(final Monster c, final int mode) {

		// the main window, containing all but the buttons.

		nameBox = new MonsterNamePanel(c);
		nameBox.setBorder(BorderFactory.createEtchedBorder());

		hitPanel = new MonsterHitPanel(c);
		hitPanel.setBorder(BorderFactory.createEtchedBorder());

		miscPanel = new MonsterOrgPanel(c);
		miscPanel.setBorder(BorderFactory.createEtchedBorder());

		initPanel = new MonsterInitPanel(c);
		initPanel.setBorder(BorderFactory.createEtchedBorder());

		acPanel = new MonsterAcPanel(c);
		acPanel.setBorder(BorderFactory.createEtchedBorder());

		statPanel = new MonsterStatPanel(c);
		statPanel.setBorder(BorderFactory.createEtchedBorder());

		savePanel = new MonsterSavePanel(c);
		savePanel.setBorder(BorderFactory.createEtchedBorder());

		attackPanel = new MonsterAttackPanel(c);
		attackPanel.setBorder(BorderFactory.createEtchedBorder());

		speedPanel = new MonsterSpeedPanel(c);
		speedPanel.setBorder(BorderFactory.createEtchedBorder());

		specialPanel = new MonsterSpecialInfoPanel(c);
		// specialPanel.setBorder(BorderFactory.createEtchedBorder());

		notesPanel = new MonsterNotesPanel(c);
		// notesPanel.setBorder(BorderFactory.createEtchedBorder());

		imagePanel = new MonsterImagePanel(c, this);
		// imagePanel.setBorder(BorderFactory.createEtchedBorder());

		attackListPanel = new MonsterAttackListPanel(c, mode);

		poisonPanel = new MonsterPoisonPanel(c);

		// the monster name panel
		bufferPanel.setWeightX(1);
		bufferPanel.setWeightY(1);
		bufferPanel.doLayout(nameBox, 0, bufferPanel.ypos, 1, 2);
		nameBox.setOpaque(false);

		// the hitpoint panel
		bufferPanel.doLayout(initPanel, 1, bufferPanel.ypos);
		initPanel.setOpaque(false);

		// the stat panel
		bufferPanel.doLayout(statPanel, 2, bufferPanel.ypos, 1, 4);

		// the image panel
		bufferPanel.doLayout(imagePanel, 3, bufferPanel.ypos, 1, 4);

		bufferPanel.incYPos();

		// the list panel
		bufferPanel.doLayout(acPanel, 1, bufferPanel.ypos);

		bufferPanel.incYPos();

		bufferPanel.doLayout(savePanel, 1, bufferPanel.ypos);
		bufferPanel.doLayout(speedPanel, 0, bufferPanel.ypos, 1, 2);
		bufferPanel.incYPos();
		bufferPanel.doLayout(hitPanel, 1, bufferPanel.ypos);
		bufferPanel.incYPos();

		// the org panel
		bufferPanel.doLayout(sourceChooser, 0, bufferPanel.ypos);
		bufferPanel.doLayout(miscPanel, 1, bufferPanel.ypos, 3, 1);

		bufferPanel.incYPos();

		// the init panel
		bufferPanel.doLayout(attackPanel, 0, bufferPanel.ypos);

		// the ac panel
		bufferPanel.doLayout(attackListPanel, 1, bufferPanel.ypos, 3, 1);

		tabbedPane.addTab("Main", bufferPanel);

	
		secondaryPanel.doLayout(poisonPanel, 0, secondaryPanel.ypos);
		secondaryPanel.doLayout(specialPanel, 1, secondaryPanel.ypos);
		secondaryPanel.incYPos();
		secondaryPanel.setWeightX(1.0);
		secondaryPanel.setWeightY(1.0);
		secondaryPanel.doLayout(notesPanel, 0, secondaryPanel.ypos, 2, 1);

		tabbedPane.addTab("Notes", secondaryPanel);

		//bufferPanel.setBorder(BorderFactory.createEmptyBorder(5, // top
		//		5, // left
		//		5, // bottom
		//		5) // right
		//		);

		bufferPanel.setDoubleBuffered(true);

		scroll = new JScrollPane(tabbedPane);
		//JViewport vp = scroll.getViewport();
		//vp.setViewPosition(new Point(0, 0));
		//scroll.setViewport(vp);

		setLayout(new BorderLayout());
		add(scroll, BorderLayout.CENTER);

	}

	public void save(int mode) {
		String nameStr = "";
		Monster m = new Monster();
		nameStr = nameBox.getName();
		if (mode == ADD) {
			//nameStr = nameBox.getName();
		} else {
			m.setID(monster.getID());
			//nameStr = monster.getName();
		}

		m.setName(nameStr);
		try{
			m.setMod(Integer.parseInt(initPanel.getInit()));
		}catch(NumberFormatException nfe){
			m.setMod(0);
		}
		try{
			m.setStrength(Integer.parseInt(statPanel.getStr()));
		}catch(NumberFormatException nfe){
			m.setStrength(0);
		}
		try{
			m.setDexterity(Integer.parseInt(statPanel.getDex()));
		}catch(NumberFormatException nfe){
			m.setDexterity(0);
		}
		try{
			m.setConstitution(Integer.parseInt(statPanel.getCon()));
		}catch(NumberFormatException nfe){
			m.setConstitution(0);
		}
		try{
			m.setWisdom(Integer.parseInt(statPanel.getWis()));
		}catch(NumberFormatException nfe){
			m.setWisdom(0);
		}
		try{
			m.setIntelligence(Integer.parseInt(statPanel.getIntel()));
		}catch(NumberFormatException nfe){
			m.setIntelligence(0);
		}
		try{
			m.setCharisma(Integer.parseInt(statPanel.getCha()));
		}catch(NumberFormatException nfe){
			m.setCharisma(0);
		}
		try{
			m.setAC(Integer.parseInt(acPanel.getAC()));
		}catch(NumberFormatException nfe){
			m.setAC(0);
		}
		try{
			m.setHP(Integer.parseInt(hitPanel.getHP()));
		}catch(NumberFormatException nfe){
			m.setHP(0);
		}
		m.setSize(nameBox.getMonsterSize());
		m.setType(nameBox.getType());
		m.setSubType(nameBox.getSubType());
		m.setHitDie(hitPanel.getHitDie());
		m.setInit(Integer.parseInt(initPanel.getInit()));
		m.setInitNotes(initPanel.getInitNotes());
		m.setLandSpeed(speedPanel.getLandspeed());
		m.setFlySpeed(speedPanel.getFlyspeed());
		m.setFlyType(speedPanel.getFlytype());
		m.setSwimSpeed(speedPanel.getSwimspeed());
		m.setClimbSpeed(speedPanel.getClimbspeed());
		m.setBurrowSpeed(speedPanel.getBurrowspeed());
		m.setACNotes(acPanel.getACNotes());
		m.setAttacks(attackPanel.getAttack());
		m.setDamage(attackPanel.getDamage());
		m.setFace(attackPanel.getReach());
		m.setSpecialAttacks(specialPanel.getSpecialattacks());
		m.setSpecialQualities(specialPanel.getSpecialqualities());
		m.setSkills(specialPanel.getSkills());
		m.setFeats(specialPanel.getFeats());
		m.setTerrain(miscPanel.getTerrain());
		m.setOrg(miscPanel.getOrg());
		m.setChallengeRating(miscPanel.getChallengeRating());
		m.setTreasure(miscPanel.getTreasure());
		m.setAlignment(miscPanel.getAlignment());
		m.setCombatNotes(notesPanel.getCombatnotes());
		m.setGenNotes(notesPanel.getNotes());
		m.setPage("0");
		m.setDieType(hitPanel.getDieType());
		m.setHitDice(hitPanel.getHitDice());
		if (m.getSource() == null){
			m.setSource("Custom Monster");
		}
		m.setPicture(imagePanel.getImage());
		m.setIcon(imagePanel.getIcon());
		m.setPoisonType(poisonPanel.getPoisonType());
		m.setPoisonSaveType(poisonPanel.getPoisonSaveType());
		m.setPoisonSaveDc(poisonPanel.getPoisonSaveDc());
		m.setPoisonInitialDie(poisonPanel.getPoisonInitialDie());
		m.setPoisonSecondaryDie(poisonPanel.getPoisonSecondaryDie());
		m.setDamageReduction(savePanel.getDamageReduction());
		m.setDrType(savePanel.getDrType());
		m.setSource((String)sourceChooser.getSelectedItem());
		
		m.convertNulls();
		
		Vector<String> errors = m.validate();
		if (errors.size() > 0){
			String msg = "";
			for (int i = 0; i < errors.size(); i++){
				msg += errors.get(i);
				msg += "\r\n";
			}
			MessageDialog.display("Errors", msg);	
		} else {
			if (mode == ADD) {
				addMonster(m);
			} else {
				modMonster(m);
			}
		}
	}

	public void showNext() {
		owner.incSelected();
		setMonster((Monster) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setMonster((Monster) owner.getSelected());
	}

	public void setMonster(Monster m) {
		this.monster = m;
		bufferPanel.invalidate();

		nameBox.setMonster(m);
		hitPanel.setMonster(m);
		miscPanel.setMonster(m);
		initPanel.setMonster(m);
		acPanel.setMonster(m);
		statPanel.setMonster(m);
		savePanel.setMonster(m);
		attackPanel.setMonster(m);
		speedPanel.setMonster(m);
		specialPanel.setMonster(m);
		notesPanel.setMonster(m);
		imagePanel.setMonster(m);
		attackListPanel.setMonster(m);
		poisonPanel.setMonster(m);
		sourceChooser.setSelectedItem(m.getSource());
		// SwingUtilities.invokeLater(new Scroller(bufferPanel));
		bufferPanel.validate();
	}

	public void doLayout() {
		super.doLayout();
		//bufferPanel.scrollRectToVisible(new Rectangle(10, 10));
	}

	public void addMonster(Monster m) {
		MonsterDAO db = new MonsterDAO();
		db.addMonster(m);
		owner.updateList();
	}

	public void modMonster(Monster m) {
		MonsterDAO db = new MonsterDAO();
		db.updateMonster(m);
		owner.updateList();
	}

	public void setImage(String s) {
		imagePanel.setImage(s);
	}
}

class Scroller implements Runnable {

	private JComponent c;

	public Scroller(JComponent c) {
		this.c = c;
	}

	public void run() {

		c.scrollRectToVisible(new Rectangle(10, 10));
		c.validate();
	}
}
