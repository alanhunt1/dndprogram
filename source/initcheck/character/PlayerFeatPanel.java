package initcheck.character;

import initcheck.DCharacter;
import initcheck.DescriptionDialog;
import initcheck.InitImage;
import initcheck.InitLogger;
import initcheck.PanelButton;
import initcheck.TransparentButton;
import initcheck.character.chooser.FeatChooser;
import initcheck.character.chooser.FeatPackageChooser;
import initcheck.character.chooser.SkillChooser;
import initcheck.character.chooser.WeaponClassChooser;
import initcheck.character.library.FeatListCellRenderer;
import initcheck.database.ClassAbilities;
import initcheck.database.Feat;
import initcheck.database.FeatDAO;
import initcheck.database.FeatPackage;
import initcheck.database.FeatPackageDAO;
import initcheck.database.FeatPackageItem;
import initcheck.database.FeatPrereq;
import initcheck.database.FeatPrereqDAO;
import initcheck.database.PlayerFeats;
import initcheck.database.PlayerFeatsDAO;
import initcheck.database.PlayerTempMod;
import initcheck.database.PlayerTempModDAO;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledList;
import initcheck.graphics.TiledTextArea;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PlayerFeatPanel extends TiledGridPanel implements
		ListSelectionListener, StatusTab {

	
	private static final long serialVersionUID = 1L;

	private TiledList featList = new TiledList();

	private JScrollPane featScroll = new JScrollPane(featList);

	private final PlayerStatDialog owner;

	private TiledList abilityList = new TiledList();

	private JScrollPane abilityScroll = new JScrollPane(abilityList);

	private TiledTextArea desc = new TiledTextArea();

	private JScrollPane descScroll = new JScrollPane(desc);

	private PanelButton addFeat = new PanelButton("Add");

	private PanelButton addFeatPackage = new PanelButton("Add Package", 100);

	private PanelButton delFeat = new PanelButton("Rem");

	private PanelButton saveFeat = new PanelButton("Save");

	private FeatChooser featSelect = new FeatChooser();

	private JTextField featValue = new JTextField(10);

	private JPanel featPanel;

	private JPanel featPanel2 = new JPanel();

	private JPanel featPanel3 = new JPanel();

	private static final int NORMAL = 1;

	private static final int WEAPON = 2;

	private static final int SKILL = 3;

	private int mode = NORMAL;

	private JComboBox typeChooser;

	private FeatPackageChooser packageChooser = new FeatPackageChooser();

	private JLabel featLabel;

	private JCheckBox special = new JCheckBox("Special");

	private boolean updateRequired;

	private InitLogger logger = new InitLogger(this);

	private boolean error = false;
	
	private TransparentButton showFeatsButton = new TransparentButton(InitImage.qmarkIcon);

	
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

	

	public PlayerFeatPanel(final PlayerStatDialog owner, DCharacter dc) {
		super("images/rockLighter.jpg");
		this.owner = owner;

		desc.setWrapStyleWord(true);
		desc.setLineWrap(true);
		desc.setPreferredSize(new Dimension(400, 400));

		featList.setVisibleRowCount(5);
		featList.setListData(owner.getChar().getFeatList());
		featList.setCellRenderer(new FeatListCellRenderer());
		featList.addListSelectionListener(this);

		abilityList.setVisibleRowCount(5);
		abilityList.setListData(owner.getChar().getClassSet().getAbilities());
		abilityList.setCellRenderer(new GenericListCellRenderer());
		abilityList.addListSelectionListener(this);

		setBorder(BorderFactory.createEtchedBorder());
		int ypos = 0;
		int fr = Integer.parseInt(owner.getChar().getFeatsRemaining().getDisplayValue());

		JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		headerPanel.setOpaque(false);
		
		featLabel = new JLabel("Feats Remaining " + fr);
		if (fr > 0) {
			updateRequired = true;
		}
		if (fr < 0){
			error = true;
		}
		headerPanel.add(featLabel);
		headerPanel.add(showFeatsButton);
		doLayout(headerPanel, 0, ypos, 2, 1);
		ypos++;

		GridPanel leftPanel = new GridPanel();
		leftPanel.setWeightX(1.0);
		leftPanel.setWeightY(0.66);
		leftPanel.doLayout(featScroll, 0);
		leftPanel.incYPos();
		leftPanel.setWeightY(0.33);
		leftPanel.doLayout(abilityScroll, 0);

		setWeightX(1.0);
		setWeightY(1.0);
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				leftPanel, descScroll);
		split.setResizeWeight(0.3);
		doLayout(split, 0, ypos, 2, 1);
		// doLayout(featScroll, 0, ypos);
		// doLayout(descScroll, 1, ypos);
		setWeightX(0);
		setWeightY(0);
		ypos++;

		featPanel = new JPanel(new GridLayout(2, 1));

		featPanel2.add(featSelect);
		featPanel2.setOpaque(false);
		featPanel3.add(featValue);
		featPanel3.add(special);
		featPanel3.add(addFeat);
		featPanel3.add(delFeat);
		featPanel3.add(saveFeat);
		featPanel3.setOpaque(false);
		featPanel.add(featPanel2);
		featPanel.add(featPanel3);
		featPanel.setOpaque(false);

		JPanel featPackagePanel = new JPanel();
		featPackagePanel.add(packageChooser);
		featPackagePanel.add(addFeatPackage);
		featPackagePanel.setOpaque(false);

		doLayout(featPackagePanel, 0, ypos);
		doLayout(featPanel, 1, ypos);

		addFeatPackage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addPackageFeat();
			}
		});

		addFeat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addFeat();
			}
		});

		delFeat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeFeat();
			}
		});

		saveFeat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFeat();
			}
		});

		featSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFeatModifier();
			}
		});

		showFeatsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showFeatCalc();
			}
		});
		
		validateFeatList();
	}

	private void showFeatCalc(){
		Vector<String> v = owner.getChar().getFeatsRemaining().getElements();
		String feats = "";
		for (String s:v){
			feats += s+"\n";
		}
		DescriptionDialog.display("Feat Calculation", feats);
	}
	
	private void setFeatModifier() {
		Feat f = (Feat) featSelect.getSelectedItem();
		desc.setText(f.getDescription());
		desc.setCaretPosition(0);
		invalidate();
		featPanel3.remove(addFeat);
		if (mode == NORMAL) {
			featPanel3.remove(featValue);
		} else {
			featPanel3.remove(typeChooser);
		}

		if (f.getFeatType().equals("Weapon")) {
			typeChooser = new WeaponClassChooser();
			featPanel3.add(typeChooser);
			mode = WEAPON;
		} else if (f.getFeatType().equals("Skill")) {
			typeChooser = new SkillChooser();
			featPanel3.add(typeChooser);
			mode = SKILL;
		} else {
			featPanel3.add(featValue);
			mode = NORMAL;
		}

		featPanel3.add(addFeat);
		validate();
	}

	private void addPackageFeat() {
		FeatPackageDAO fpdb = new FeatPackageDAO();
		FeatPackage fp = (FeatPackage) packageChooser.getSelectedItem();
		FeatDAO fdb = new FeatDAO();

		fp = fpdb.getFeatPackage(fp.getPackageName());
		if (fp != null) {
			for (int i = 0; i < fp.getFeats().size(); i++) {

				FeatPackageItem fpi = (FeatPackageItem) fp.getFeats().get(i);

				if (!owner.getChar().hasFeat(fpi.getFeatId())
						&& Integer.parseInt(owner.getChar().getFeatsRemaining().getDisplayValue()) > 0) {
					Feat f = fdb.getFeat(fpi.getFeatId());

					addFeat(f);

				}
			}
		} else {
			logger.log("NULL PACKAGE");
		}
	}

	private void addFeat() {
		addFeat((Feat) featSelect.getSelectedItem());
	}

	private void addFeat(Feat f) {

		
		FeatPrereqDAO fpdb = new FeatPrereqDAO();
		Vector<FeatPrereq> pv = fpdb.getFeatPrereqs(f.getId());
		boolean passes = true;
		boolean override = false;
		for (int i = 0; i < pv.size(); i++) {
			FeatPrereq fp = (FeatPrereq) pv.get(i);
		
			if (!owner.getChar().hasPrereq(fp)) {
				
				passes = false;
				while (fp.getChained().equals("Y")) {
					
					i++;
					fp = (FeatPrereq) pv.get(i);
					
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
						override = true;
					} else {
						break;
					}
				}
			}else{
				// if they had the first item in the chain, eat up the others, they don't need 'em.
				while (fp.getChained().equals("Y")) {
					i++;
					fp = (FeatPrereq) pv.get(i);
					
					
				}
			}
		}
		
		if (owner.getChar().getCampaign().isBlackListed(f)){
			int j = JOptionPane.showConfirmDialog(null,
					"The selected feat is not allowed in the current campaign. \nDo you wish to override?",
					"Override?", JOptionPane.YES_NO_OPTION);
			if (j == JOptionPane.YES_OPTION) {
				passes = true;
				override = true;
				error = true;
			}else{
				passes = false;
				
			}
		}
		
		if (passes || override) {

			if (mode == NORMAL) {
				f.setValue(featValue.getText());
			} else {
				f.setValue((String) typeChooser.getSelectedItem());
			}
			if (override) {
				f.setOverride("Y");
				error = true;
			}
			if (special.isSelected()) {
				f.setSpecial("Y");
			} else {
				f.setSpecial("N");
			}

			PlayerFeatsDAO db = new PlayerFeatsDAO();
			db.addPlayerFeats(f, "" + owner.getChar().getID());
			owner.getChar().setFeatList(
					db.getPlayerFeats("" + owner.getChar().getID()));
			featList.setListData(owner.getChar().getFeatList());

			// if the feat we are adding entails a temp bonus, add it to the
			// temp bonus screen.
			if (f.isTempBonus()) {
				PlayerTempMod ptm = new PlayerTempMod();
				ptm.setPlayerId("" + owner.getChar().getID());
				ptm.setModType(f.getTempType());
				ptm.setLinkType("Feat");
				ptm.setLinkId(f.getId());
				ptm.setName(f.getFeatName());
				PlayerTempModDAO pwdb = new PlayerTempModDAO();
				pwdb.addPlayerTempMod(ptm);
			}

		} else {

		}
		updateFeatsRemaining();
	}

	public void updateFeatsRemaining() {
		int fr = Integer.parseInt(owner.getChar().getFeatsRemaining().getDisplayValue());

		if (fr > 0) {
			updateRequired = true;
		} else {
			updateRequired = false;
		}

		if (fr < 0) {
			error = true;
		} else {
			error = false;
		}
		
		featLabel.setText("Feats Remaining "
				+ owner.getChar().getFeatsRemaining().getDisplayValue());
		owner.checkUpdatesRequired();
	}

	private void removeFeat() {
		Feat f = (Feat) featList.getSelectedValue();
		PlayerFeatsDAO db = new PlayerFeatsDAO();
		db.deletePlayerFeats(f);
		owner.getChar().setFeatList(
				db.getPlayerFeats("" + owner.getChar().getID()));
		featList.setListData(owner.getChar().getFeatList());
		updateFeatsRemaining();

		// if we are removing a feat with a temp modifier, we also need to
		// remove
		// the entry in the temp modifier table.
		if (f.isTempBonus()) {
			PlayerTempMod ptm = new PlayerTempMod();
			ptm.setPlayerId("" + owner.getChar().getID());
			ptm.setLinkType("Feat");
			ptm.setLinkId(f.getId());

			PlayerTempModDAO pwdb = new PlayerTempModDAO();
			pwdb.deletePlayerTempMod(ptm);
		}
		validateFeatList();
	}

	private void saveFeat() {
		Feat f = (Feat) featList.getSelectedValue();

		if (mode == NORMAL) {
			f.setValue(featValue.getText());
		} else {
			f.setValue((String) typeChooser.getSelectedItem());
		}

		boolean passes = true;
		
		FeatPrereqDAO fpdb = new FeatPrereqDAO();
		Vector<FeatPrereq> pv = fpdb.getFeatPrereqs(f.getId());
		boolean override = false;
		
		for (int i = 0; i < pv.size(); i++) {
			FeatPrereq fp = (FeatPrereq) pv.get(i);
			
			if (!owner.getChar().hasPrereq(fp)) {
			
				passes = false;
				while (fp.getChained().equals("Y")) {
					i++;
					fp = (FeatPrereq) pv.get(i);
					
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
						override = true;
					} else {
						break;
					}
				}
			}
			else{
				// if they had the first item in the chain, eat up the others, they don't need 'em.
				while (fp.getChained().equals("Y")) {
					i++;
					fp = (FeatPrereq) pv.get(i);
				
					
				}
			}
		}
		
		if (owner.getChar().getCampaign().isBlackListed(f)){
			int j = JOptionPane.showConfirmDialog(null,
					"The selected feat is not allowed in the current campaign. \nDo you wish to override?",
					"Override?", JOptionPane.YES_NO_OPTION);
			if (j == JOptionPane.YES_OPTION) {
				passes = true;
				override = true;
				error = true;
			}else{
				passes = false;
				
			}
		}
		
		
		if (override) {
			f.setOverride("Y");
			error = true;
		} else {
		
			f.setOverride("N");
		}
		
		if (special.isSelected()) {
			f.setSpecial("Y");
		} else {
			f.setSpecial("N");
		}
		
		if (passes || override) {
			
			PlayerFeatsDAO db = new PlayerFeatsDAO();
			db.updatePlayerFeats(f, "" + owner.getChar().getID());
		}
	}

	private void validateFeatList() {
		
		Vector<PlayerFeats> featList = owner.getChar().getFeatList();
		for (int k = 0; k < featList.size(); k++) {
			PlayerFeats f = featList.get(k);
			boolean passes = true;
			PlayerFeatsDAO pfdb = new PlayerFeatsDAO();
			FeatPrereqDAO fpdb = new FeatPrereqDAO();
			Vector<FeatPrereq> pv = fpdb.getFeatPrereqs(f.getId());
			boolean override = false;
			for (int i = 0; i < pv.size(); i++) {
				FeatPrereq fp = (FeatPrereq) pv.get(i);
				if (!owner.getChar().hasPrereq(fp)) {
					passes = false;
					while (fp.getChained().equals("Y") &&  i < pv.size()) {
						i++;
						if ( i < pv.size()){
						fp = (FeatPrereq) pv.get(i);
							if (owner.getChar().hasPrereq(fp)) {
								passes = true;
								break;
							}
						}
					}
					if (!passes) {
						if (f.getOverride().equals("N")) {
							f.setOverride("Y");
							pfdb.updatePlayerFeats(f);
						}
						error = true;
						override = true;
					} else {
						if (f.getOverride().equals("Y")) {
							f.setOverride("N");
							pfdb.updatePlayerFeats(f);
						}
					}
				}else{
					// if they had the first item in the chain, eat up the others, they don't need 'em.
					while (fp.getChained().equals("Y")) {
						i++;
						fp = (FeatPrereq) pv.get(i);
						
						
					}
				}
			}

			if (owner.getChar().getCampaign().isBlackListed(f)) {
				if (f.getOverride().equals("N")) {
					f.setOverride("Y");
					pfdb.updatePlayerFeats(f);
				}
				error = true;
			} else {
				if (f.getOverride().equals("Y") && !override) {
					f.setOverride("N");
					pfdb.updatePlayerFeats(f);
				}
			}
		}
	}
	
	
	public void valueChanged(ListSelectionEvent e) {
		// if the list item implements the "Item" interface, then
		// put its description in the description box
		try {
			if (e.getSource() == featList) {
				Feat f = (Feat) featList.getSelectedValue();
				desc.setText(f.getDescription());
				desc.setCaretPosition(0);
				featList.ensureIndexIsVisible(featList.getSelectedIndex());
			} else {
				ClassAbilities ca = (ClassAbilities) abilityList
						.getSelectedValue();
				desc.setText(ca.getDescription());
				desc.setCaretPosition(0);
				abilityList
						.ensureIndexIsVisible(abilityList.getSelectedIndex());
			}
		} catch (Exception ex) {
			// it didn't implement item (this is a ClassCastException),
			// so do nothing.
		}
	}
}
