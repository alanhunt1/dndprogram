package initcheck.character;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;

import initcheck.DCharacter;
import initcheck.DescriptionListDialog;
import initcheck.InitImage;
import initcheck.InitLogger;
import initcheck.MessageDialog;
import initcheck.PanelButton;
import initcheck.TranslucentGridPanel;
import initcheck.TranslucentPanel;
import initcheck.TransparentButton;
import initcheck.character.chooser.ClassChooser;
import initcheck.database.CharClass;
import initcheck.database.CharClassDAO;
import initcheck.database.CharClassPrereq;
import initcheck.database.ClassAdvancement;
import initcheck.database.ClassAdvancementDAO;
import initcheck.database.PlayerClassDAO;
import initcheck.database.PlayerHp;
import initcheck.database.PlayerHpDAO;
import initcheck.database.PlayerSkills;
import initcheck.database.PlayerSkillsDAO;
import initcheck.database.Skill;
import initcheck.graphics.TiledList;
import initcheck.graphics.TiledPanel;
import initcheck.graphics.TiledTextArea;

public class PlayerClassPanel extends TiledPanel implements
		ListSelectionListener, StatusTab {

	private static final long serialVersionUID = 1L;

	private TiledList classList = new TiledList();

	private JScrollPane classScroll = new JScrollPane(classList);

	private final PlayerStatDialog owner;

	private TiledTextArea description = new TiledTextArea(10, 50);

	private JScrollPane descScroll = new JScrollPane(description);

	private TiledList hpList = new TiledList();

	private JScrollPane hpScroll = new JScrollPane(hpList);

	private TiledList featList = new TiledList();

	private JScrollPane featScroll = new JScrollPane(featList);

	private PanelButton incClassButton = new PanelButton("Increment");

	private PanelButton decClassButton = new PanelButton("Decrement");

	private PanelButton newClassButton = new PanelButton("Add");

	private PanelButton remClassButton = new PanelButton("Remove");

	private PanelButton saveClassButton = new PanelButton("Save");

	private JTextField virtualLevel = new JTextField(4);

	private JTextField skillRankMod = new JTextField(4);
	
	private ClassChooser classes = new ClassChooser();

	boolean dirty = false;

	private JLabel levelLabel = new JLabel("Character Level : ");

	private JLabel hpLabel = new JLabel("");

	boolean updateRequired;

	private InitLogger logger = new InitLogger(this);

	private TransparentButton showFeatsButton = new TransparentButton(InitImage.qmarkIcon);
	
	

	private boolean error = false;
	
	public boolean isError() {
		return error;
	}



	public void setError(boolean error) {
		this.error = error;
	}

	
	public PlayerClassPanel(final PlayerStatDialog owner, DCharacter dc) {
		
		super("images/rockLighter.jpg");
		
		this.owner = owner;

		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		classList.setVisibleRowCount(5);
		classList.setListData(owner.getChar().getClassSet().getClassVector());
		classList.addListSelectionListener(this);
		classList.setCellRenderer(new GenericListCellRenderer());

		hpList.setVisibleRowCount(5);
		hpList.setListData(owner.getChar().getHpList());
		hpList.addListSelectionListener(this);
		hpList.setCellRenderer(new GenericListCellRenderer());

		featList.setListData(owner.getChar().getHpCalc().getListElements());
		featList.setCellRenderer(new GenericListCellRenderer());

		showFeatsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showFeats();
			}
		});
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		TranslucentPanel labelPanel = new TranslucentPanel();
		updateLabels();
		labelPanel.add(levelLabel);
		labelPanel.add(hpLabel);
		labelPanel.add(showFeatsButton);
		labelPanel.setOpaque(false);
		
		TranslucentGridPanel detailPanel = new TranslucentGridPanel(false);
		detailPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		detailPanel.setWeightY(1.0);
		detailPanel.setWeightX(0.3);
		detailPanel.setInsets(5);
		detailPanel.doLayout(hpScroll);
		
		detailPanel.setWeightX(0.7);
		detailPanel.doLayout(descScroll, 1);
		
		
		
		

		TranslucentGridPanel virtualPanel = new TranslucentGridPanel(false);
		virtualPanel.setPadY(0);
		virtualPanel.doLayout(new JLabel("Effective Level"));
		virtualPanel.doLayout(virtualLevel, 1);
		virtualPanel.incYPos();
		virtualPanel.doLayout(new JLabel("Skill Rank Mod"));
		virtualPanel.doLayout(skillRankMod,1);
		virtualPanel.incYPos();
		virtualPanel.doLayout(saveClassButton, 0, 2, 1);
		virtualPanel.setOpaque(false);
		

		TranslucentGridPanel buttonPanel = new TranslucentGridPanel(false);
		buttonPanel.setPadY(0);
		buttonPanel.setInsets(5);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		buttonPanel.setWeightX(1.0);
		buttonPanel.setWeightY(1.0);
		buttonPanel.doLayout(classScroll, 0, 1, 3);
		buttonPanel.setWeightX(0);
		buttonPanel.doLayout(incClassButton, 1);
		buttonPanel.incYPos();
		buttonPanel.doLayout(decClassButton, 1);
		buttonPanel.incYPos();
		buttonPanel.doLayout(remClassButton, 1);
		buttonPanel.setOpaque(false);
		
		TranslucentGridPanel addPanel = new TranslucentGridPanel(false);
		addPanel.setPadY(0);
		
		addPanel.doLayout(new JLabel("Add A New Class"));
		
		addPanel.incYPos();
		addPanel.doLayout(classes);
		addPanel.incYPos();
		addPanel.doLayout(newClassButton);
		addPanel.setOpaque(false);
		
		JPanel classPanel = new JPanel();
		classPanel.setLayout(new BoxLayout(classPanel, BoxLayout.X_AXIS));
		classPanel.add(addPanel);
		classPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		classPanel.add(buttonPanel);
		classPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		classPanel.add(virtualPanel);
		
		classPanel.setOpaque(false);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topPanel.add(labelPanel);
		topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		topPanel.add(classPanel);
		topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		topPanel.setOpaque(false);
		detailPanel.setOpaque(false);
		
		add(topPanel, BorderLayout.NORTH);
		add(detailPanel, BorderLayout.CENTER);

		incClassButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				incrementCurrentClass();
			}
		});

		newClassButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewClass();
			}
		});

		remClassButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remClass();
			}
		});

		decClassButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				decClass();
			}
		});

		saveClassButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveClass();
			}
		});

		classes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadClassDesc();
			}
		});

		hpList.addMouseListener(new MouseInputAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					savePlayerHp();
				}
			}
		});

		classList.addMouseListener(new MouseInputAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					refreshHpList();
				}
			}
		});

		if (owner.getChar().getClassSet().getClassVector().size() > 0) {
			classList.setSelectedIndex(0);
		}

		if (owner.getChar().canLevelUp()) {
			updateRequired = true;
		}
	}

	public void showFeats(){
		
		DescriptionListDialog.display("Alignment Description", owner.getChar().getHpCalc().getElements());
	}
	
	public void updateLabels() {
		levelLabel.setText("Character Level : " + owner.getChar().getLevel());
		hpLabel.setText("Total HP : "
				+ owner.getChar().getHpCalc().getDisplayValue());
		featList.setListData(owner.getChar().getHpCalc().getListElements());
		
	}

	public void loadClassDesc() {
		description.setText(getSelectedClass().getDescription());
		description.setCaretPosition(0);
	}

	// set the effective (virtual) level for the class
	public void saveClass() {

		try {
			ClassSlot cs = (ClassSlot) classList.getSelectedValue();
			if (cs.getSkillRankMod() != Integer.parseInt(skillRankMod.getText())){
				dirty = true;
			}
			cs.setVirtualLevel(Integer.parseInt(virtualLevel.getText()));
			cs.setSkillRankMod(Integer.parseInt(skillRankMod.getText()));
			PlayerClassDAO db = new PlayerClassDAO();
			db.updatePlayerClass(cs, owner.getChar().getID());
			
			int idx = classList.getSelectedIndex();
			
			owner.getChar().getClassSet().setClassVector(db.getPlayerClasses("" + owner.getChar().getID()));
			classList.setListData(owner.getChar().getClassSet().getClassVector());
			
			classList.setSelectedIndex(idx);
			
			
			if (dirty){
				checkUpdates();
			}
		} catch (NumberFormatException nfe) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error",
					"Virtual Level and Skill Mod must be numbers.");
		}
	}

	public void addNewClass() {
		CharClassDAO fdb = new CharClassDAO();
		Vector<CharClassPrereq> pv = fdb.getCharClassPrereqs(getSelectedClass().getId());
		boolean passes = true;
		// find out if they already have the class they are trying to add.
		for (int i = 0; i < owner.getChar().getClassSet().size(); i++) {
			ClassSlot cs = (ClassSlot) owner.getChar().getClassSet().get(i);
			if (cs.getClassName().getId().equals(getSelectedClass().getId())) {
				return;
			}
		}

		// find out if they meet the prerequisites for the class they are trying
		// to
		// add.
		for (int i = 0; i < pv.size(); i++) {
			CharClassPrereq fp = (CharClassPrereq) pv.get(i);
			if (!owner.getChar().hasPrereq(fp)) {
				passes = false;
				while (fp.getChained().equals("Y")) {
					i++;
					fp = (CharClassPrereq) pv.get(i);
					if (owner.getChar().hasPrereq(fp)) {
						passes = true;
						break;
					}
				}
				if (!passes) {
					int j = JOptionPane.showConfirmDialog(null,
							"You do not meet the prerequesite "
									+ fp.getPrereqName()
									+ ". \nDo you wish to override?",
							"Override?", JOptionPane.YES_NO_OPTION);
					if (j == JOptionPane.YES_OPTION) {
						passes = true;
					} else {
						break;
					}
				}
			}
		}

		// if they meet (or override) all of the prereqs for the class, add it.
		// otherwise, do nothing.
		if (passes) {
			ClassSlot cs = new ClassSlot();
			cs.setClassName(getSelectedClass());
			cs.setLevel(1);

			PlayerClassDAO db = new PlayerClassDAO();
			db.addPlayerClass(cs, owner.getChar().getID());
			owner.getChar().getClassSet().setClassVector(
					db.getPlayerClasses("" + owner.getChar().getID()));
			classList.setListData(owner.getChar().getClassSet()
					.getClassVector());
			classList
					.setSelectedIndex(owner.getChar().getClassSet().size() - 1);
			dirty = true;

			// ask for the initial HP for this new class
			addPlayerHp();

			// update the screen for the new calcs
			updateLabels();

			// add the skills to the new class
			PlayerSkillsDAO psdb = new PlayerSkillsDAO();
			ClassSlot ccs = (ClassSlot) owner.getChar().getClassSet().get(0);
			Vector<PlayerSkills>skills = psdb.getPlayerSkills("" + owner.getChar().getID(),
					ccs.getClassName().getId());
			for (int i = 0; i < skills.size(); i++) {
				Skill s = (Skill) skills.get(i);
				s.setRanks("0");
				s.setClassId(cs.getClassName().getId());
				psdb.addPlayerSkills(s, "" + owner.getChar().getID());
			}

		}
		checkUpdates();

	}

	public void incrementCurrentClass() {
		if (!getClassList().isSelectionEmpty()) {
			int idx = getClassList().getSelectedIndex();
			ClassAdvancementDAO cadb = new ClassAdvancementDAO();
			ClassAdvancement ca = new ClassAdvancement();

			ClassSlot cs = (ClassSlot) classList.getSelectedValue();
			cs.setLevel(cs.getLevel() + 1);
			ca.setClassId(cs.getClassName().getId());
			ca.setLevel("" + cs.getLevel());
			Vector<ClassAdvancement> advanceList = cadb.selectClassAdvancement(ca);
			if (advanceList.size() > 0) {
				PlayerClassDAO db = new PlayerClassDAO();

				db.updatePlayerClass(cs, owner.getChar().getID());
				owner.getChar().getClassSet().setClassVector(
						db.getPlayerClasses("" + owner.getChar().getID()));
				classList.setListData(owner.getChar().getClassSet()
						.getClassVector());
				classList.setSelectedIndex(idx);
				dirty = true;

				addPlayerHp();

				checkUpdates();
			} else {
				cs.setLevel(cs.getLevel() - 1);
				@SuppressWarnings("unused")
				MessageDialog md = new MessageDialog("Error",
						"You have advanced in that class as far as possible.");
			}
		} else {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error",
					"Please select a class.");
		}
	}

	public void decClass() {

		int idx = getClassList().getSelectedIndex();

		ClassSlot cs = (ClassSlot) classList.getSelectedValue();
		if (cs.getLevel() < 2) {
			return;
		}
		cs.setLevel(cs.getLevel() - 1);

		PlayerClassDAO db = new PlayerClassDAO();

		db.updatePlayerClass(cs, owner.getChar().getID());
		owner.getChar().getClassSet().setClassVector(
				db.getPlayerClasses("" + owner.getChar().getID()));
		classList.setListData(owner.getChar().getClassSet().getClassVector());
		classList.setSelectedIndex(idx);
		dirty = true;
		removePlayerHp();

		checkUpdates();
	}

	public void remClass() {
		ClassSlot cs = (ClassSlot) classList.getSelectedValue();
		int j = JOptionPane.showConfirmDialog(null,
				"Do you really want to delete all levels of "+cs.getClassName()+"?",
				"Delete?", JOptionPane.YES_NO_OPTION);
		if (j != JOptionPane.YES_OPTION) {
			return;
		}
		
		int idx = getClassList().getSelectedIndex();
		
		PlayerClassDAO db = new PlayerClassDAO();

		// remove the hp for the class
		PlayerHpDAO hdb = new PlayerHpDAO();
		hdb.clearPlayerClassHp("" + owner.getChar().getID(), cs.getClassName()
				.getId());
		owner.getChar()
				.setHpList(hdb.getPlayerHp("" + owner.getChar().getID()));
		hpList.setListData(owner.getChar().getHpList());

		// remove all of the child records from the player skill table.
		PlayerSkillsDAO psdb = new PlayerSkillsDAO();
		psdb.clearPlayerClassSkills("" + owner.getChar().getID(), cs
				.getClassName().getId());

		// then delete the class from the player class table
		db.deletePlayerClass(cs);
		owner.getChar().getClassSet().setClassVector(
				db.getPlayerClasses("" + owner.getChar().getID()));
		// refresh the lists
		classList.setListData(owner.getChar().getClassSet().getClassVector());
		if (idx <= classList.getModel().getSize()) {
			idx--;
		}
		classList.setSelectedIndex(idx);
		dirty = true;

		cs = (ClassSlot) classList.getSelectedValue();
		updateHpListSelection(cs);
		updateLabels();
		checkUpdates();
	}

	private void checkUpdates() {
		if (owner.getChar().canLevelUp()) {
			updateRequired = true;
		} else {
			updateRequired = false;
		}
		owner.checkUpdatesRequired();
	}

	public JList getClassList() {
		return classList;
	}

	public CharClass getSelectedClass() {
		return (CharClass) classes.getSelectedItem();
	}

	private void addPlayerHp() {
		
		String hp = JOptionPane
				.showInputDialog("Please enter the number of hit points for this level.");

		ClassSlot cs = (ClassSlot) classList.getSelectedValue();
		CharClass cc = null;
		if (cs == null) {
			cc = getSelectedClass();
		} else {
			cc = cs.getClassName();
		}
		PlayerHp php = new PlayerHp();
		php.setPlayerId("" + owner.getChar().getID());
		php.setHitPoints(hp);
		php.setClassId(cc.getId());

		php.setLevel("" + cs.getLevel());
		php.setClassName(cc.getCharClass());
		PlayerHpDAO db = new PlayerHpDAO();
		db.addPlayerHp(php);
		owner.getChar().setHpList(db.getPlayerHp("" + owner.getChar().getID()));
		hpList.setListData(owner.getChar().getHpList());
		updateHpListSelection(cs);
		updateLabels();
	}

	private void removePlayerHp() {
		ClassSlot cs = (ClassSlot) classList.getSelectedValue();
		
		// updateHpListSelection(cs);

		List<Object> selectedHps = hpList.getSelectedValuesList();

		PlayerHp f = (PlayerHp) (selectedHps.get(selectedHps.size() - 1));

		PlayerHpDAO db = new PlayerHpDAO();
		db.deletePlayerHp(f);
		owner.getChar().setHpList(db.getPlayerHp("" + owner.getChar().getID()));
		hpList.setListData(owner.getChar().getHpList());

		updateHpListSelection(cs);

		updateLabels();
	}

	private void savePlayerHp() {

		ClassSlot cs = (ClassSlot) classList.getSelectedValue();

		String hp = JOptionPane
				.showInputDialog("Please enter the number of hit points for this level.");

		int idx = hpList.getSelectedIndex();
		if (idx >= 0) {
			PlayerHp php = (PlayerHp) hpList.getSelectedValue();
			php.setHitPoints(hp);

			PlayerHpDAO db = new PlayerHpDAO();
			db.updatePlayerHp(php);
			owner.getChar().setHpList(
					db.getPlayerHp("" + owner.getChar().getID()));
			hpList.setListData(owner.getChar().getHpList());

			updateHpListSelection(cs);
			updateLabels();
		}
	}

	

	/**
	 * Get the Dirty value.
	 * 
	 * @return the Dirty value.
	 */
	public boolean isDirty() {
		return dirty;
	}

	/**
	 * Set the Dirty value.
	 * 
	 * @param newDirty
	 *            The new Dirty value.
	 */
	public void setDirty(boolean newDirty) {
		this.dirty = newDirty;
	}

	public void updateHpListSelection(ClassSlot cs) {
		if (cs == null) {
			return;
		}
		hpList.setSelectedIndex(-1);
		description.setText(cs.getClassName().getDescription());
		description.setCaretPosition(0);
		int[] indices = new int[cs.getLevel()];
		
		Vector<PlayerHp> currhp = owner.getChar().getHpList();

		int idx = 0;
		for (int i = 0; i < currhp.size(); i++) {
			PlayerHp hp = (PlayerHp) currhp.get(i);

			if (hp.getClassId().equals(cs.getClassName().getId())) {
				indices[idx] = i;
				idx++;
			}
		}
		
		hpList.setSelectedIndices(indices);
		
	}

	public void valueChanged(ListSelectionEvent e) {

		try {
			if (e.getSource() == classList) {
				ClassSlot cs = (ClassSlot) classList.getSelectedValue();
				if (cs != null) {

					refreshHpList(cs);

					virtualLevel.setText("" + cs.getVirtualLevel());
					skillRankMod.setText("" + cs.getSkillRankMod());
				}
			}
		} catch (Exception ex) {
			logger.log("error", "ERROR IN VALUE CHANGED : " + ex);
		}
	}

	public void refreshHpList() {
		ClassSlot cs = (ClassSlot) classList.getSelectedValue();
		refreshHpList(cs);
	}

	public void refreshHpList(ClassSlot cs) {
		if (cs != null) {
			updateHpListSelection(cs);
		}
	}

	/**
	 * Get the UpdateRequired value.
	 * 
	 * @return the UpdateRequired value.
	 */
	public boolean isUpdateRequired() {
		return updateRequired;
	}

	/**
	 * Set the UpdateRequired value.
	 * 
	 * @param newUpdateRequired
	 *            The new UpdateRequired value.
	 */
	public void setUpdateRequired(boolean newUpdateRequired) {
		this.updateRequired = newUpdateRequired;
	}

}
