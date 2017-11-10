package initcheck.character;

import initcheck.DCharacter;
import initcheck.InitLogger;
import initcheck.PanelButton;
import initcheck.TranslucentGridPanel;
import initcheck.character.chooser.SkillChooser;
import initcheck.database.PlayerSkills;
import initcheck.database.PlayerSkillsDAO;
import initcheck.database.Skill;
import initcheck.graphics.TiledPanel;
import initcheck.graphics.TiledTextArea;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PlayerSkillPanel extends TiledPanel implements
		ListSelectionListener, StatusTab {

	private static final long serialVersionUID = 1L;

	private InitLogger logger = new InitLogger(this);

	private TiledTextArea description = new TiledTextArea(10, 20);

	private JScrollPane descScroll = new JScrollPane(description);

	//private JLabel totalLabel = new JLabel("0");

	//private TiledList skillList = new TiledList();

	//private JScrollPane skillScroll = new JScrollPane(skillList);

	private final PlayerStatDialog owner;

	//private PanelButton addSkill = new PanelButton("Add");

	private PanelButton saveSkill = new PanelButton("Save");

	//private PanelButton delSkill = new PanelButton("Rem");

	//private SkillChooser skillSelect = new SkillChooser();

	//private JTextField skillValue = new JTextField(10);

	private JPanel skillPanel = new JPanel();

	private JPanel buttonPanel = new JPanel();

	private JPanel modPanel = new JPanel();

	private JTextField skillMod = new JTextField(3);

	private JTextField skillMisc = new JTextField(3);

	//private JTextField skillRanks = new JTextField(3);

	private JTextField skillNotes = new JTextField(20);

	//private JCheckBox crossClassOverride = new JCheckBox("Cross Class Override");

	private Vector<PlayerClassSkillList> listVector = new Vector<PlayerClassSkillList>();

	private TranslucentGridPanel listPanel = new TranslucentGridPanel();

	private PlayerClassSkillList currList;
	
	private SkillTotalsList totals;

	boolean updateRequired;

	Vector<ClassSlot> classes = new Vector<ClassSlot>();

	DCharacter dc;

	private boolean error = false;
	
	public boolean isError() {
		return error;
	}



	public void setError(boolean error) {
		this.error = error;
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

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints cn = new GridBagConstraints();

	public PlayerSkillPanel(final PlayerStatDialog owner, DCharacter dc) {
		super("images/rockLighter.jpg");
		this.owner = owner;
		this.dc = dc;

		//totalLabel.setFont(InitFont.bigFont);
		classes = owner.getChar().getClassSet().getClassVector();
		//skillList.setCellRenderer(new GenericListCellRenderer());
		description.setLineWrap(true);
		description.setWrapStyleWord(true);

		listPanel.setOpaque(false);
		listPanel.setWeightX(1);
		listPanel.setWeightY(1);
		
		
		updateRequired = false;
		
		JTabbedPane classTab = new JTabbedPane();
		classTab.setBorder(BorderFactory.createEmptyBorder());
		classTab.setOpaque(false);
		
		for (int i = 0; i < classes.size(); i++) {
			ClassSlot cs = (ClassSlot) classes.get(i);
			PlayerClassSkillList pcsl = new PlayerClassSkillList(owner, this,
					cs);
			pcsl.addListSelectionListener(this);
			if (i == 0) {
				pcsl.setFocus(true);
				setSelectedSkillList(pcsl);
			}

			if (pcsl.isUpdateRequired()) {
				updateRequired = true;
			}
			classTab.addTab(cs.getClassName().getName(),pcsl);
			//listPanel.doLayout(pcsl, i + 1);
			listVector.add(pcsl);
		}

		listPanel.doLayout(classTab, 1);
		

		int ypos = 0;

		setLayout(gridbag);
		cn.fill = GridBagConstraints.BOTH;
		cn.ipadx = 10;
		cn.ipady = 10;
		cn.insets = new Insets(5,5,5,5);
		
		//setBorder(BorderFactory.createEtchedBorder());

		cn.weightx = 1.0;
		cn.weighty = 1.0;

		if (classes.size() > 0) {
			totals = new SkillTotalsList(owner, this, (ClassSlot) classes
					.get(0));
			
			
			doLayout(totals, 4, 0, 1, 4);
		}
		

		if (currList != null && currList.getListSize() > 0) {
			setListIndex(0);
		}
		
		doLayout(listPanel, 0, ypos, 4, 1);
		//cn.weightx = 0;
		//cn.weighty = 0;
		ypos++;

		//skillPanel.add(skillSelect);
		//skillPanel.add(skillValue);
		skillPanel.setOpaque(false);

		//buttonPanel.add(new JLabel("Ranks"));
		//buttonPanel.add(skillRanks);
		//buttonPanel.add(addSkill);
		buttonPanel.add(saveSkill);
		//buttonPanel.add(delSkill);
		buttonPanel.setOpaque(false);

		//crossClassOverride.setOpaque(false);
		modPanel.add(new JLabel("Mod"));
		modPanel.add(skillMod);
		modPanel.add(new JLabel("Misc"));
		modPanel.add(skillMisc);
		modPanel.add(new JLabel("Notes"));
		modPanel.add(skillNotes);
		modPanel.add(saveSkill);
		//modPanel.add(crossClassOverride);
		modPanel.setOpaque(false);
		//cn.weightx = 0.5;
		//doLayout(buttonPanel, 0, ypos);
		//cn.weightx = 0.1;
		//doLayout(totalLabel, 1, ypos, 1, 1);
		//doLayout(skillScroll, 1, ypos + 1, 1, 2);
		//cn.weightx = 0.4;
		

		//doLayout(skillPanel, 0, ypos);
		//ypos++;

		doLayout(modPanel, 0, ypos);
		ypos++;
		cn.weighty = 1.0;
		doLayout(descScroll, 0, ypos);
		
		ypos++;
		
	//	addSkill.addActionListener(new ActionListener() {
		//	public void actionPerformed(ActionEvent e) {
		//		addSkill();
		//	}
		//});

		//delSkill.addActionListener(new ActionListener() {
		//	public void actionPerformed(ActionEvent e) {
		//		removeSkill();
		//	}
		//});

		saveSkill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveSkill();
			}
		});
	}

	public void checkUpdatesRequired() {
		updateRequired = false;
		error = false;
		for (int i = 0; i < listVector.size(); i++) {

			PlayerClassSkillList pcsl = (PlayerClassSkillList) listVector
					.get(i);

			if (pcsl.isUpdateRequired()) {
				updateRequired = true;
			}
			if (pcsl.isError()) {
				error = true;
			}
		}
	}

	private PlayerClassSkillList getPrimaryClassList(){
		return listVector.get(0);
	}
	
	public void setSelectedSkillList(PlayerClassSkillList p) {
		if (currList != null) {
			currList.setFocus(false);
		}
		currList = p;
		p.setFocus(true);
		Skill s = (Skill) currList.getSelectedValue();
		if (s != null) {
			//skillRanks.setText(s.getRanks());
			skillMod.setText(s.getMods());
			skillMisc.setText(s.getMisc());
			//skillSelect.setSelectedSkill(s);
			skillNotes.setText(s.getNotes());
			//crossClassOverride.setSelected(s.getCrossClassOverride()
			//		.equals("Y"));
//
		}else{
			//skillRanks.setText("0");
		}
	}

	@SuppressWarnings("unused")
	private void updateClass() {

		PlayerSkillsDAO psdb = new PlayerSkillsDAO();

		refreshSkillsLeft();
		currList.setListData(psdb.getPlayerSkills("" + owner.getChar().getID(),
				currList.getClassId()));
	}

	public void refreshSkillsLeft() {
		for (int i = 0; i < listVector.size(); i++) {
			((PlayerClassSkillList) listVector.get(i)).refreshSkillsLeft();
		}
	}

	public void updateCharacter() {
		invalidate();
		remove(listPanel);

		Vector<ClassSlot> classes = owner.getChar().getClassSet().getClassVector();

		listPanel.removeAll();

		if (classes.size() > 0) {
			totals = new SkillTotalsList(owner, this, (ClassSlot) classes
					.get(0));
			listPanel.setWeightX(0.0);
			listPanel.doLayout(totals, 0);
		}

		listVector = new Vector<PlayerClassSkillList>();

		listPanel.setWeightX(1.0);
		listPanel.setWeightY(1.0);

		for (int i = 0; i < classes.size(); i++) {
			ClassSlot cs = (ClassSlot) classes.get(i);
			PlayerClassSkillList pcsl = new PlayerClassSkillList(owner, this,
					cs);
			pcsl.addListSelectionListener(this);

			listPanel.doLayout(pcsl, i + 1);
			listVector.add(pcsl);
		}

		cn.weightx = 1.0;
		cn.weighty = 1.0;
		doLayout(listPanel, 0, 0, 2, 1);
		cn.weightx = 0;
		cn.weighty = 0;

		refreshSkillsLeft();

		validate();
	}

	public void updateAbility() {
		refreshSkillsLeft();
	}

	private void saveSkill() {

		System.out.println("SAVING SKILL IN PARENT");
		
		PlayerSkillsDAO psdb = new PlayerSkillsDAO();
		Vector<PlayerSkills> v = psdb.getPlayerSkills("" + owner.getChar().getID());
		for (int idx = 0; idx < v.size(); idx++){
			Skill s = (Skill) v.get(idx);
			if (s.getSkill().equals(((Skill)currList.getSelectedValue()).getSkill())){
				System.out.println("UPDATING SKILL");
				s.setMisc(skillMisc.getText());
				s.setMods(skillMod.getText());
				s.setNotes(skillNotes.getText());
				psdb.updatePlayerSkills(s);
			}
		}

		refreshAllPanels();
		
	}

	public void refreshAllPanels() {
		int idx = currList.getSelectedIndex();
		if (idx < 0) {
			idx = 0;
		}
		refreshAllPanels(idx);
	}

	private void refreshAllPanels(int idx) {
		PlayerSkillsDAO psdb = new PlayerSkillsDAO();

		for (int i = 0; i < listVector.size(); i++) {

			PlayerClassSkillList pcsl = (PlayerClassSkillList) listVector
					.get(i);

			pcsl.setListData(psdb.getPlayerSkills("" + owner.getChar().getID(),
					pcsl.getClassId()));
			pcsl.setSelectedIndex(idx);

		}

		owner.getChar().getSkillSet().setSkillList(
				psdb.getPlayerSkills("" + owner.getChar().getID()));
		refreshSkillsLeft();
		updateTotals();

	}

	private void doLayout(Component comp, int x, int y) {
		cn.gridx = x;
		cn.gridy = y;
		cn.gridwidth = 1;
		cn.gridheight = 1;
		gridbag.setConstraints(comp, cn);
		add(comp);
	}

	private void doLayout(Component comp, int x, int y, int width, int height) {
		cn.gridx = x;
		cn.gridy = y;
		cn.gridwidth = width;
		cn.gridheight = height;

		gridbag.setConstraints(comp, cn);
		add(comp);
	}

	public void setSkill(Skill s, int index){
		
		
		
		System.out.println("Setting skill in parent to "+s.getSkill());
		
		for (int i = 0; i < listVector.size(); i++) {
			PlayerClassSkillList pcsl = (PlayerClassSkillList) listVector.get(i);
			if (pcsl.getSelectedIndex() != index){
				
				pcsl.setSelectedIndex(index);
			}
		}
		
		
	
		
		Skill sp = (Skill) getPrimaryClassList().getSelectedValue();
		skillMod.setText(sp.getMods());
		skillMisc.setText(sp.getMisc());
		skillNotes.setText(sp.getNotes());
		
		totals.setSkill(sp);
		
		
		description.setText(sp.getDescription());
		description.setCaretPosition(0);
		
	}
	
	public void setListIndex(int idx) {
		totals.setSelectedIndex(idx);

		System.out.println("Setting index in parent");
		
		for (int i = 0; i < listVector.size(); i++) {
			PlayerClassSkillList pcsl = (PlayerClassSkillList) listVector.get(i);
			pcsl.setSelectedIndex(idx);
		}
		
		//Skill s = (Skill) currList.getSelectedValue();
		//skillRanks.setText(s.getRanks());
		
		Skill sp = (Skill) getPrimaryClassList().getSelectedValue();
		skillMod.setText(sp.getMods());
		skillMisc.setText(sp.getMisc());
		skillNotes.setText(sp.getNotes());
		
		
		//skillSelect.setSelectedSkill(s);
		//crossClassOverride.setSelected(s.getCrossClassOverride().equals("Y"));
		//skillList.setListData(dc.getScore(s.getSkill()).getElements());
		description.setText(sp.getDescription());
		description.setCaretPosition(0);
		//totalLabel.setText(dc.getScore(s.getSkill()).getDisplayValue());
	}

	public Vector<String> getCalc() {
		return dc.getScore(((Skill) currList.getSelectedValue()).getSkill()).getElements();
	}
	
	public String getTotal(){
		if (currList.getSelectedValue() != null){
			return (dc.getScore(((Skill) currList.getSelectedValue()).getSkill())).getDisplayValue();
		}
		return "UNK";
	}
	
	public void updateTotals() {
		totals.updateList();
	}

	public void valueChanged(ListSelectionEvent e) {

		try {
			//System.out.println("Setting index in parent TO "+((PlayerClassSkillList)e.getSource()).getClassId());
			
			//if ((PlayerClassSkillList)e.getSource() == currList){
			//	System.out.println("CURRENT LIST");
			//}else{
			//	System.out.println("OTHER LIST");
			//}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
