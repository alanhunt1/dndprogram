package initcheck.character.library;

import initcheck.MessageDialog;
import initcheck.PanelButton;
import initcheck.character.GenericListCellRenderer;
import initcheck.character.chooser.AbilityChooser;
import initcheck.character.chooser.AlignmentChooser;
import initcheck.character.chooser.ClassAbilityChooser;
import initcheck.character.chooser.ClassChooser;
import initcheck.character.chooser.DomainChooser;
import initcheck.character.chooser.FeatChooser;
import initcheck.character.chooser.FeatTypeChooser;
import initcheck.character.chooser.LevelChooser;
import initcheck.character.chooser.PrereqTypeChooser;
import initcheck.character.chooser.ProficiencyTypeChooser;
import initcheck.character.chooser.RaceChooser;
import initcheck.character.chooser.SaveTypeChooser;
import initcheck.character.chooser.SizeChooser;
import initcheck.character.chooser.SkillChooser;
import initcheck.character.chooser.StatChooser;
import initcheck.database.Feat;
import initcheck.database.FeatPrereq;
import initcheck.database.FeatPrereqDAO;
import initcheck.database.Skill;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class FeatPrereqPanel extends TiledGridPanel {

	
	private static final long serialVersionUID = 1L;

	private TiledList prereqList = new TiledList();

	private JScrollPane prereqScroll = new JScrollPane(prereqList);

	private JPanel prereqPanel = new JPanel();

	private PanelButton remButton = new PanelButton("Del");

	private PrereqTypeChooser reqType = new PrereqTypeChooser();

	private JComboBox reqName = new JComboBox();

	private JTextField reqValue = new JTextField(5);

	private PanelButton addReqButton = new PanelButton("Add");

	private JCheckBox chained = new JCheckBox("Chain To Selected");

	private Vector<FeatPrereq> prereq;

	private String featId;

	public FeatPrereqPanel() {
		super("images/rockLighter.jpg");
		prereq = new Vector<FeatPrereq>();
		init();
		doLayout(new JLabel("Feat Prerequisites"));
		incYPos();
		setWeightX(1.0);
		setWeightY(1.0);
		doLayout(prereqScroll, 0);
		setWeightX(0);
		setWeightY(0);
		incYPos();
		prereqPanel.setOpaque(false);
		doLayout(prereqPanel, 0);
	}

	public void setFeat(String id) {
		featId = id;

		FeatPrereqDAO fpdb = new FeatPrereqDAO();

		prereq = fpdb.getFeatPrereqs(id);
		prereqList.setListData(prereq);
	}

	public void init() {
		prereqList.setCellRenderer(new GenericListCellRenderer());
		
		prereqPanel.add(reqType);
		prereqPanel.add(reqName);
		prereqPanel.add(reqValue);
		prereqPanel.add(chained);
		prereqPanel.add(addReqButton);

		prereqPanel.add(remButton);

		reqType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectPrereqName();
			}
		});

		remButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removePrereq();
			}
		});

		addReqButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addPrereq();
			}
		});
	}

	public void addPrereq() {
		FeatPrereqDAO fpdb = new FeatPrereqDAO();

		// don't let them save any prereqs unless the feat already ]
		// exists in the DB, because it has to link to something.
		if (featId == null || featId.equals("")) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog(
					"Error",
					"You cannot add prerequisites to a feat\n until that feat has been saved.\n  Please save the feat first.");
			return;
		}

		// don't let them chain a feat to one that is already chained -
		// you should chain to the base feat in the chain.
		FeatPrereq curr = null;

		if (chained.isSelected()) {
			curr = (FeatPrereq) prereqList.getSelectedValue();
			if (curr.getChained().equals("Y")) {
				@SuppressWarnings("unused")
				MessageDialog md = new MessageDialog(
						"Error",
						"You cannot chain a prerequisite to one \n that is already chained.  Please chain \n it to the base prereq.");
				return;
			}
		}
		
		FeatPrereq fp = new FeatPrereq();
		String s = (String) reqType.getSelectedItem();
		if (s.equalsIgnoreCase("SKILL")){
			Skill sk = (Skill)reqName.getSelectedItem();
			fp.setCrossRefId(sk.getId());
		}else if (s.equalsIgnoreCase("FEAT")){
			Feat f = (Feat)reqName.getSelectedItem();
			fp.setCrossRefId(f.getId());
		}
		fp.setPrereqType((String) reqType.getSelectedItem());
		fp.setPrereqName(reqName.getSelectedItem().toString());
		fp.setPrereqVal(reqValue.getText());
		fp.setFeatId(featId);
		if (chained.isSelected()) {
			fp.setChained("Y");
			fp.setChainedTo(curr.getId());
			fpdb.addFeatPrereq(curr);
		}
		
		fpdb.addOrUpdateFeatPrereq(fp);
		prereq = fpdb.getFeatPrereqs(featId);

		prereqList.setListData(prereq);

	}

	public void removePrereq() {
		FeatPrereqDAO fpdb = new FeatPrereqDAO();
		int idx = prereqList.getSelectedIndex();
		FeatPrereq fp = prereq.get(idx);
		fpdb.deleteFeatPrereq(fp);
		prereq = fpdb.getFeatPrereqs(featId);
		prereqList.setListData(prereq);
	}

	public void selectPrereqName() {
		String s = (String) reqType.getSelectedItem();

		invalidate();
		prereqPanel.remove(reqName);
		prereqPanel.remove(reqValue);
		prereqPanel.remove(chained);
		prereqPanel.remove(addReqButton);
		prereqPanel.remove(remButton);
		if (s.equalsIgnoreCase("FEAT")) {
			reqName = new FeatChooser();
		} else if (s.equalsIgnoreCase("Stat")) {
			reqName = new StatChooser();
		} else if (s.equalsIgnoreCase("Skill")) {
			reqName = new SkillChooser();
		} else if (s.equalsIgnoreCase("Level")) {
			reqName = new LevelChooser();
		} else if (s.equalsIgnoreCase("Ability")) {
			reqName = new ClassAbilityChooser();
		} else if (s.equalsIgnoreCase("Alignment")) {
			reqName = new AlignmentChooser();
		} else if (s.equalsIgnoreCase("Class")) {
			reqName = new ClassChooser();
		} else if (s.equalsIgnoreCase("Proficiency")) {
			reqName = new ProficiencyTypeChooser();
		} else if (s.equalsIgnoreCase("Feat Class")) {
			reqName = new FeatTypeChooser();
		} else if (s.equalsIgnoreCase("Domain")) {
			reqName = new DomainChooser();
		} else if (s.equalsIgnoreCase("Save")) {
			reqName = new SaveTypeChooser();
		} else if (s.equalsIgnoreCase("Race")) {
			reqName = new RaceChooser();
		} else if (s.equalsIgnoreCase("Misc")) {
			reqName = new AbilityChooser();
		} else if (s.equalsIgnoreCase("Min Size") || s.equalsIgnoreCase("Max Size")) {
			reqName = new SizeChooser();
		}

		prereqPanel.add(reqName);
		prereqPanel.add(reqValue);
		prereqPanel.add(chained);
		prereqPanel.add(addReqButton);
		prereqPanel.add(remButton);
		validate();
	}

	/*
	 * db.clearPrereqs("" + id); int chainVal = -1;
	 * 
	 * for (int i = 0; i < prereq.size(); i++) { FeatPrereq fp = (FeatPrereq)
	 * prereq.get(i); if (fp.getChained().equals("Y")) { fp.setChainedTo("" +
	 * chainVal); } fp.setFeatId("" + id); chainVal =
	 * db.addOrUpdateFeatPrereq(fp); }
	 * 
	 */

}
