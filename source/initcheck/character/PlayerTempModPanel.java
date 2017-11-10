package initcheck.character;

import initcheck.DCharacter;
import initcheck.InitImage;
import initcheck.MessageDialog;
import initcheck.PanelButton;
import initcheck.character.chooser.BonusTypeChooser;
import initcheck.database.PlayerTempMod;
import initcheck.database.PlayerTempModDAO;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledList;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PlayerTempModPanel extends TiledGridPanel implements
		ListSelectionListener, StatusTab {

	
	private static final long serialVersionUID = 1L;

	private TiledList playerTempModList = new TiledList();

	private JScrollPane playerTempModScroll = new JScrollPane(playerTempModList);

	private final PlayerStatDialog owner;

	private PanelButton addPlayerTempMod = new PanelButton("Add");

	private PanelButton delPlayerTempMod = new PanelButton("Rem");

	private PanelButton savePlayerTempMod = new PanelButton("Save");

	private JTextField name = new JTextField(30);

	private BonusTypeChooser typeChooser = new BonusTypeChooser();

	private JTextField strMod = new JTextField(3);

	private JTextField dexMod = new JTextField(3);

	private JTextField conMod = new JTextField(3);

	private JTextField intMod = new JTextField(3);

	private JTextField wisMod = new JTextField(3);

	private JTextField chaMod = new JTextField(3);

	private JTextField natMod = new JTextField(3);

	private JTextField acMod = new JTextField(3);

	private JTextField touchMod = new JTextField(3);

	private JTextField restMod = new JTextField(3);

	private JTextField meleeMod = new JTextField(3);

	private JTextField rangeMod = new JTextField(3);

	private JTextField damageMod = new JTextField(3);

	private JTextField fortMod = new JTextField(3);

	private JTextField refMod = new JTextField(3);

	private JTextField willMod = new JTextField(3);

	private JTextField attackMod = new JTextField(3);

	private JCheckBox conditional = new JCheckBox("Conditional");

	private JTextField strCheckMod = new JTextField(3);

	private JTextField dexCheckMod = new JTextField(3);

	private JTextField conCheckMod = new JTextField(3);

	private JTextField intCheckMod = new JTextField(3);

	private JTextField wisCheckMod = new JTextField(3);

	private JTextField chaCheckMod = new JTextField(3);

	private JTextField featMod = new JTextField(3);
	
	boolean updateRequired;

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

	public PlayerTempModPanel(final PlayerStatDialog owner, DCharacter dc) {
		super(InitImage.lightRocks);
		
		this.owner = owner;
		
		playerTempModList.addListSelectionListener(this);
		playerTempModList.setVisibleRowCount(5);
		playerTempModList.setListData(owner.getChar().getTempMods());
		playerTempModList.setFont(new Font("Courier", Font.PLAIN, 12));
		playerTempModList.setCellRenderer(new TempModCellRenderer());

		setWeightX(1.0);
		setWeightY(1.0);
		doLayout(playerTempModScroll, 0, ypos);
		setWeightX(0);
		setWeightY(0);
		ypos++;

		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.add(addPlayerTempMod);
		buttonPanel.add(delPlayerTempMod);
		buttonPanel.add(savePlayerTempMod);

		JPanel critPanel = new JPanel();
		critPanel.setOpaque(false);
		critPanel.add(new JLabel("NAT"));
		critPanel.add(natMod);
		critPanel.add(new JLabel("AC"));
		critPanel.add(acMod);
		critPanel.add(new JLabel("TOUCH"));
		critPanel.add(touchMod);
		critPanel.add(new JLabel("REST"));
		critPanel.add(restMod);
		critPanel.add(new JLabel("MELEE"));
		critPanel.add(meleeMod);
		critPanel.add(new JLabel("RANGED"));
		critPanel.add(rangeMod);
		critPanel.add(new JLabel("DMG"));
		critPanel.add(damageMod);
		critPanel.add(new JLabel("FORT"));
		critPanel.add(fortMod);
		critPanel.add(new JLabel("REF"));
		critPanel.add(refMod);
		critPanel.add(new JLabel("WILL"));
		critPanel.add(willMod);
		critPanel.add(new JLabel("ATTACK"));
		critPanel.add(attackMod);
		critPanel.add(new JLabel("FEAT"));
		critPanel.add(featMod);

		JPanel choosePanel = new JPanel();
		choosePanel.setOpaque(false);
		choosePanel.add(name);
		choosePanel.add(typeChooser);

		JPanel namePanel = new JPanel();
		namePanel.setOpaque(false);
		namePanel.add(new JLabel("STR"));
		namePanel.add(strMod);
		namePanel.add(new JLabel("DEX"));
		namePanel.add(dexMod);
		namePanel.add(new JLabel("CON"));
		namePanel.add(conMod);
		namePanel.add(new JLabel("INT"));
		namePanel.add(intMod);
		namePanel.add(new JLabel("WIS"));
		namePanel.add(wisMod);
		namePanel.add(new JLabel("CHA"));
		namePanel.add(chaMod);
		namePanel.add(conditional);


		JPanel namePanel2 = new JPanel();
		namePanel2.setOpaque(false);
		namePanel2.add(new JLabel("STR CHECK"));
		namePanel2.add(strCheckMod);
		namePanel2.add(new JLabel("DEX CHECK"));
		namePanel2.add(dexCheckMod);
		namePanel2.add(new JLabel("CON CHECK"));
		namePanel2.add(conCheckMod);
		namePanel2.add(new JLabel("INT CHECK"));
		namePanel2.add(intCheckMod);
		namePanel2.add(new JLabel("WIS CHECK"));
		namePanel2.add(wisCheckMod);
		namePanel2.add(new JLabel("CHA CHECK"));
		namePanel2.add(chaCheckMod);
		
		
		doLayout(choosePanel, 0, ypos);
		ypos++;

		doLayout(namePanel, 0, ypos);
		ypos++;
		
		doLayout(namePanel2, 0, ypos);
		ypos++;

		doLayout(critPanel, 0, ypos);
		ypos++;

		doLayout(buttonPanel, 0, ypos);

		addPlayerTempMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addPlayerTempMod();
			}
		});

		savePlayerTempMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				savePlayerTempMod();
			}
		});

		delPlayerTempMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removePlayerTempMod();
			}
		});

	}

	public void updateTempMods() {
		PlayerTempModDAO pwdb = new PlayerTempModDAO();
		owner.getChar().setTempMods(
				pwdb.getPlayerTempMods("" + owner.getChar().getID()));
		playerTempModList.setListData(owner.getChar().getTempMods());
	}

	private void addPlayerTempMod() {
		PlayerTempMod f = new PlayerTempMod();
		f.setPlayerId("" + owner.getChar().getID());
		f.setStrMod(strMod.getText());
		f.setDexMod(dexMod.getText());
		f.setIntMod(intMod.getText());
		f.setName(name.getText());
		f.setModType((String) typeChooser.getSelectedItem());
		f.setConMod(conMod.getText());
		f.setWisMod(wisMod.getText());
		f.setChaMod(chaMod.getText());
		f.setNatMod(natMod.getText());
		f.setAcMod(acMod.getText());
		f.setRestMod(restMod.getText());
		f.setTouchMod(touchMod.getText());
		f.setMeleeMod(meleeMod.getText());
		f.setRangeMod(rangeMod.getText());
		f.setDamageMod(damageMod.getText());
		f.setFortMod(fortMod.getText());
		f.setRefMod(refMod.getText());
		f.setWillMod(willMod.getText());
		f.setAttackMod(attackMod.getText());
		f.setConditional(conditional.isSelected());
		f.setStrCheckMod(strCheckMod.getText());
		f.setDexCheckMod(dexCheckMod.getText());
		f.setIntCheckMod(intCheckMod.getText());
		f.setConCheckMod(conCheckMod.getText());
		f.setWisCheckMod(wisCheckMod.getText());
		f.setChaCheckMod(chaCheckMod.getText());
		f.setFeatMod(featMod.getText());
		Vector<String> v = f.validate();

		String errors = "";
		for (int i = 0; i < v.size(); i++) {
			errors += v.get(i) + "\n";
		}

		if (!errors.equals("")) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error", errors);
		} else {
			PlayerTempModDAO pwdb = new PlayerTempModDAO();
			pwdb.addPlayerTempMod(f);
			owner.getChar().setTempMods(
					pwdb.getPlayerTempMods("" + owner.getChar().getID()));
			playerTempModList.setListData(owner.getChar().getTempMods());
		}
	}

	private void savePlayerTempMod() {

		PlayerTempMod f = (PlayerTempMod) playerTempModList.getSelectedValue();
		f.setName(name.getText());
		f.setModType((String) typeChooser.getSelectedItem());
		f.setStrMod(strMod.getText());
		f.setDexMod(dexMod.getText());
		f.setIntMod(intMod.getText());
		f.setConMod(conMod.getText());
		f.setWisMod(wisMod.getText());
		f.setChaMod(chaMod.getText());
		f.setNatMod(natMod.getText());
		f.setAcMod(acMod.getText());
		f.setRestMod(restMod.getText());
		f.setTouchMod(touchMod.getText());
		f.setMeleeMod(meleeMod.getText());
		f.setRangeMod(rangeMod.getText());
		f.setDamageMod(damageMod.getText());
		f.setFortMod(fortMod.getText());
		f.setRefMod(refMod.getText());
		f.setWillMod(willMod.getText());
		f.setAttackMod(attackMod.getText());
		f.setConditional(conditional.isSelected());
		f.setStrCheckMod(strCheckMod.getText());
		f.setDexCheckMod(dexCheckMod.getText());
		f.setIntCheckMod(intCheckMod.getText());
		f.setConCheckMod(conCheckMod.getText());
		f.setWisCheckMod(wisCheckMod.getText());
		f.setChaCheckMod(chaCheckMod.getText());
		f.setFeatMod(featMod.getText());
		Vector<String> v = f.validate();

		String errors = "";
		for (int i = 0; i < v.size(); i++) {
			errors += v.get(i) + "\n";
		}

		if (!errors.equals("")) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error", errors);
		} else {

			PlayerTempModDAO pwdb = new PlayerTempModDAO();
			pwdb.updatePlayerTempMod(f);
			owner.getChar().setTempMods(
					pwdb.getPlayerTempMods("" + owner.getChar().getID()));
			playerTempModList.setListData(owner.getChar().getTempMods());
		}
	}

	private void removePlayerTempMod() {

		PlayerTempMod w = (PlayerTempMod) playerTempModList.getSelectedValue();
		boolean run = true;
		if (w.getLinkType() != null && !w.getLinkType().equals("")) {
			String mesg = "The temporary modifier you are trying to remove is linked to a "
					+ w.getLinkType() + ".  Do you wish to delete this anyway?";
			int j = JOptionPane.showConfirmDialog(null, mesg, "Ooh Ooh!",
					JOptionPane.YES_NO_OPTION);
			if (j == JOptionPane.NO_OPTION) {
				run = false;
			}
		}

		if (run) {
			PlayerTempModDAO pwdb = new PlayerTempModDAO();
			pwdb.deletePlayerTempMod(w);
			owner.getChar().setTempMods(
					pwdb.getPlayerTempMods("" + owner.getChar().getID()));
			playerTempModList.setListData(owner.getChar().getTempMods());
		}
	}

	public void valueChanged(ListSelectionEvent e) {

		try {
			PlayerTempMod f = (PlayerTempMod) playerTempModList
					.getSelectedValue();

			name.setText(f.getName());
			typeChooser.setSelectedItem(f.getModType());
			strMod.setText(f.getStrMod());
			dexMod.setText(f.getDexMod());
			intMod.setText(f.getIntMod());
			wisMod.setText(f.getWisMod());
			chaMod.setText(f.getChaMod());
			conMod.setText(f.getConMod());
			natMod.setText(f.getNatMod());
			acMod.setText(f.getAcMod());
			restMod.setText(f.getRestMod());
			touchMod.setText(f.getTouchMod());
			meleeMod.setText(f.getMeleeMod());
			rangeMod.setText(f.getRangeMod());
			damageMod.setText(f.getDamageMod());
			refMod.setText(f.getRefMod());
			fortMod.setText(f.getFortMod());
			willMod.setText(f.getWillMod());
			attackMod.setText(f.getAttackMod());
			conditional.setSelected(f.isConditional());
			strCheckMod.setText(f.getStrCheckMod());
			dexCheckMod.setText(f.getDexCheckMod());
			intCheckMod.setText(f.getIntCheckMod());
			wisCheckMod.setText(f.getWisCheckMod());
			chaCheckMod.setText(f.getChaCheckMod());
			conCheckMod.setText(f.getConCheckMod());
			featMod.setText(f.getFeatMod());
		} catch (Exception ex) {

		}
	}
}
