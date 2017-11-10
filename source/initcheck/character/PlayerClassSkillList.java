package initcheck.character;

import initcheck.InitColor;
import initcheck.InitLogger;
import initcheck.MessageDialog;
import initcheck.PanelButton;
import initcheck.TranslucentGridPanel;
import initcheck.character.chooser.SkillChooser;
import initcheck.database.PlayerSkills;
import initcheck.database.PlayerSkillsDAO;
import initcheck.database.Skill;
import initcheck.graphics.TiledList;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PlayerClassSkillList extends GridPanel implements FocusListener,
		ListSelectionListener {

	private static final long serialVersionUID = 1L;

	private TiledList skillList = new TiledList();

	private JScrollPane skillScroll = new JScrollPane(skillList);

	private ClassSlot currClass;

	private PlayerStatDialog owner;

	private JLabel skillCount = new JLabel("Skills Remaining ");

	private PlayerSkillPanel parent;

	private JTextField ranks = new JTextField(5);

	private PanelButton inc = new PanelButton("+", 30);

	private PanelButton dec = new PanelButton("-", 30);

	boolean updateRequired = false;

	private InitLogger logger = new InitLogger(this);

	private SkillChooser skillSelect = new SkillChooser();
	
	private PanelButton addSkill = new PanelButton("Add", 50);
	
	private JCheckBox crossClassOverride = new JCheckBox("Cross Class Override");
	
	private JTextField skillValue = new JTextField(10);
	
	private PanelButton delSkill = new PanelButton("Rem", 50);
	
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

	public PlayerClassSkillList(PlayerStatDialog owner,
			PlayerSkillPanel parent, ClassSlot cclass) {
		
		super(false);
		this.setOpaque(false);
		this.owner = owner;
		this.parent = parent;

		currClass = cclass;
		skillCount.setForeground(Color.white);
		skillList.setVisibleRowCount(5);
		skillList.addFocusListener(this);
		skillList.addListSelectionListener(this);
		skillList.setCellRenderer(new GenericListCellRenderer());
		skillList.setFont(new Font("Courier", Font.PLAIN, 12));

		PlayerSkillsDAO psdb = new PlayerSkillsDAO();
		refreshSkillsLeft();

		if (currClass != null) {
			skillList.setListData(psdb
					.getPlayerSkills("" + owner.getChar().getID(), currClass
							.getClassName().getId()));
		}

		doLayout(skillCount, 0, ypos);
		ypos++;
		
		JPanel addRemPanel = new JPanel();
		addRemPanel.setOpaque(false);
		addRemPanel.add(skillSelect);
		addRemPanel.add(skillValue);
		addRemPanel.add(addSkill);
		addRemPanel.add(delSkill);
		doLayout(addRemPanel);
		ypos++;
		
		setInsets(5);
		setWeightX(1.0);
		setWeightY(1.0);
		doLayout(skillScroll, 0, ypos);
		setWeightX(0);
		setWeightY(0);
		ypos++;
		
		
		//TranslucentGridPanel rankPanel = new TranslucentGridPanel(false);
		//Color ppColor = new Color(0, 0, 0, 90); //r,g,b,alpha
	
		//rankPanel.setColor(ppColor);
		//rankPanel.setOpaque(false);
		//rankPanel.setWeightX(1);
		//rankPanel.setWeightY(1);
		//rankPanel.setPadY(0);
		
		//rankPanel.doLayout(skillSelect, 0, rankPanel.ypos,3, 1 );
		
		
		
		
		//rankPanel.doLayout(ranks, 3);
		//rankPanel.doLayout(inc, 4);
		//rankPanel.doLayout(dec, 5);
		//rankPanel.incYPos();
		//rankPanel.doLayout(skillValue, 0);
		//rankPanel.doLayout(addSkill, 1);
		//rankPanel.doLayout(delSkill, 2);
		
		//rankPanel.doLayout(crossClassOverride, 3, rankPanel.ypos, 3, 1);
		JPanel rankPanel = new JPanel();
		rankPanel.setOpaque(false);
		rankPanel.add(ranks);
		rankPanel.add(inc);
		rankPanel.add(dec);
		rankPanel.add(crossClassOverride);
		
		doLayout(rankPanel, 0, ypos);
		ypos++;

		inc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				incSkill();
			}
		});

		dec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				decSkill();
			}
		});
		

		addSkill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addSkill();
			}
		});

		delSkill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeSkill();
			}
		});

	}

	private void removeSkill() {
		int idx = skillList.getSelectedIndex();

		Skill s = (Skill) skillList.getSelectedValue();
		
		PlayerSkillsDAO psdb = new PlayerSkillsDAO();
		psdb.deletePlayerSkills(s, "" + owner.getChar().getID());
		psdb.resetConnection();

		parent.refreshAllPanels();
	}
	
	public void incSkill() {
		try {
			int i = Integer.parseInt(ranks.getText());
			ranks.setText("" + (i + 1));

			modSkill();
		} catch (NumberFormatException e) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error",
					"You must enter an integer in the ranks field");
		}
	}

	public void decSkill() {
		try {
			int i = Integer.parseInt(ranks.getText());
			ranks.setText("" + (i - 1));

			modSkill();
		} catch (NumberFormatException e) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error",
					"You must enter an integer in the ranks field");
		}
	}

	public void modSkill() {
		Skill sk = (Skill) skillList.getSelectedValue();

		if (!ranks.getText().equals(sk.getRanks())) {

			PlayerSkillsDAO psdb = new PlayerSkillsDAO();
			Vector<PlayerSkills> v = psdb.getPlayerSkills("" + owner.getChar().getID(),
					getClassId());
			int idx = skillList.getSelectedIndex();
			Skill s = (Skill) v.get(idx);
			
			int myranks = Integer.parseInt(ranks.getText());

			if (myranks <= new Double(owner.getChar().getLevel()).intValue() + 3) {

				s.setRanks(ranks.getText());

				if (crossClassOverride.isSelected()) {
					s.setCrossClassOverride("Y");
				} else {
					s.setCrossClassOverride("N");
				}
				
				s.setMods(null);
				s.setMisc(null);
				s.setNotes(null);
				
				psdb.updatePlayerSkills(s);

				psdb.resetConnection();

				owner.getChar().getSkillSet().setSkillList(
						psdb.getPlayerSkills("" + owner.getChar().getID()));

				skillList.setListData(psdb.getPlayerSkills(""
						+ owner.getChar().getID(), getClassId()));
				skillList.setSelectedIndex(idx);

				refreshSkillsLeft();
			} else {
				JOptionPane
						.showMessageDialog(
								null,
								"Ranks exceeds maximum for that skill in the selected class",
								"Ranks Maxed Out!", JOptionPane.ERROR_MESSAGE);
			}

			parent.updateTotals();
		}

	}

	public int getListSize() {
		return skillList.getModel().getSize();
	}

	public void setFocus(boolean isFocused) {

		if (isFocused) {
			this.setBackground(InitColor.fadedRed);
		} else {
			this.setBackground(Color.lightGray);
		}
	}

	public String getClassId() {
		return currClass.getClassName().getId();
	}

	public void setSelectedIndex(int i) {
		skillList.setSelectedIndex(i);
	}

	public Object getSelectedValue() {
		return skillList.getSelectedValue();
	}

	public int getSelectedIndex() {
		return skillList.getSelectedIndex();
	}

	public void setListData(Vector<PlayerSkills> v) {
		skillList.setListData(v);
	}

	public void refreshSkillsLeft() {

		if (currClass != null) {
			int avail = owner.getChar().getSkillsRemaining(
					currClass.getClassName().getId());
			int remaining = owner.getChar().getSkillSet().getSkillsUsed(
					currClass.getClassName().getId());

			if (remaining < 0) {
				skillCount.setForeground(Color.red);
			} else {
				skillCount.setForeground(InitColor.woodText);
			}
			skillCount.setText(currClass.getClassName().getCharClass()
					+ " Skills Remaining " + (avail - remaining) + "/" + avail);
			if (avail - remaining > 0) {
				updateRequired = true;

			} else {
				updateRequired = false;
			}
			if (avail - remaining < 0) {
				error = true;

			} else {
				error = false;
			}
			parent.checkUpdatesRequired();
			owner.checkUpdatesRequired();

		}
	}

	public void addListSelectionListener(ListSelectionListener l) {
		skillList.addListSelectionListener(l);
	}

	public void addFocusListener(FocusListener l) {

		if (skillList == null) {
			skillList = new TiledList();
		}
		skillList.addFocusListener(l);
	}

	public void focusLost(FocusEvent e) {

	}

	public void focusGained(FocusEvent e) {
		parent.setSelectedSkillList(this);
	}

	public void valueChanged(ListSelectionEvent e) {

		try {
			Skill s = (Skill) skillList.getSelectedValue();
			if (s != null) {
				
				skillList.ensureIndexIsVisible(skillList.getSelectedIndex());
				ranks.setText(s.getRanks());
				skillSelect.setSelectedSkill(s);
				parent.setSkill(s, skillList.getSelectedIndex());
				crossClassOverride.setSelected(s.getCrossClassOverride().equals("Y"));
			}
		} catch (Exception ex) {
			logger.log("error", ex.toString());
		}
	}

	private void addSkill() {
		Skill f = (Skill) ((Skill) skillSelect.getSelectedItem()).getClone();

		PlayerSkillsDAO psdb = new PlayerSkillsDAO();
		logger.log("Adding skills to lists");
		

			if (owner.getChar().getSkillSet().hasSkill(f.getSkill(),
					getClassId())) {
				JOptionPane.showMessageDialog(
								null,
								"You already have ranks in that skill.  Increment them instead.",
								"Skill Exists", JOptionPane.ERROR_MESSAGE);
			} else {
				
				ClassSet cs = owner.getChar().getClassSet();
				for (ClassSlot cc:cs.getClassVector()){
					
					f.setClassId(cc.getClassName().getId());
					if (f.getName().equals(currClass.getClass())){
						f.setRanks(ranks.getText());
					}else{
						f.setRanks("0");
					}
					
					
					if (crossClassOverride.isSelected()) {
						f.setCrossClassOverride("Y");
					} else {
						f.setCrossClassOverride("N");
					}
	
					psdb.addPlayerSkills(f, "" + owner.getChar().getID());
					psdb.resetConnection();
				}
			}
		

		parent.refreshAllPanels();
	}
	
	
}
