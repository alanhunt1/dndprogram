package initcheck.character;

import initcheck.DCharacter;
import initcheck.DescriptionDialog;
import initcheck.InitImage;
import initcheck.PanelButton;
import initcheck.TransparentButton;
import initcheck.character.chooser.AmmoChooser;
import initcheck.database.PlayerAmmoDAO;
import initcheck.database.Weapon;
import initcheck.graphics.TiledList;
import initcheck.graphics.TiledPanel;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PlayerAmmoPanel extends TiledPanel implements
		ListSelectionListener, StatusTab {

	private static final long serialVersionUID = 1L;

	private TiledList ammoList = new TiledList();

	private JScrollPane ammoScroll = new JScrollPane(ammoList);

	private final PlayerStatDialog owner;

	private PanelButton addAmmo = new PanelButton("Add");

	private PanelButton delAmmo = new PanelButton("Rem");

	private PanelButton saveAmmo = new PanelButton("Save");

	private AmmoChooser ammoSelect = new AmmoChooser();

	private JTextField ammoValue = new JTextField(2);

	private JTextField ammoQuantity = new JTextField(2);

	private JCheckBox masterwork = new JCheckBox("Masterwork");

	private JPanel ammoPanel = new JPanel();

	private JTextField ammoNotes = new JTextField(30);

	private TransparentButton ammoButton = new TransparentButton(InitImage.qmarkIcon);
	
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

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints cn = new GridBagConstraints();

	public PlayerAmmoPanel(final PlayerStatDialog owner, DCharacter dc) {
		super("images/rockLighter.jpg");
		this.owner = owner;

		ammoList.addListSelectionListener(this);
		ammoList.setVisibleRowCount(5);
		ammoList.setListData(owner.getChar().getAmmo());
		ammoList.setCellRenderer(new AmmoListCellRenderer());

		setLayout(gridbag);
		cn.fill = GridBagConstraints.BOTH;
		cn.ipadx = 10;
		cn.ipady = 10;

		setBorder(BorderFactory.createEtchedBorder());
		int ypos = 0;

		cn.weightx = 1.0;
		cn.weighty = 1.0;
		doLayout(ammoScroll, 0, ypos);
		cn.weightx = 0;
		cn.weighty = 0;
		ypos++;

		ammoPanel.add(ammoQuantity);
		ammoPanel.add(ammoSelect);
		ammoPanel.add(ammoButton);
		ammoPanel.add(new JLabel(" + "));
		ammoPanel.add(ammoValue);
		ammoPanel.add(masterwork);
		ammoPanel.setOpaque(false);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addAmmo);
		buttonPanel.add(delAmmo);
		buttonPanel.add(saveAmmo);
		buttonPanel.setOpaque(false);
		doLayout(ammoPanel, 0, ypos);
		ypos++;

		doLayout(ammoNotes, 0, ypos);

		ypos++;

		doLayout(buttonPanel, 0, ypos);

		addAmmo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAmmo();
			}
		});

		saveAmmo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAmmo();
			}
		});

		delAmmo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAmmo();
			}
		});

		ammoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAmmoDescription();
			}
		});
	}

	public void showAmmoDescription(){
		Weapon a = (Weapon)ammoSelect.getSelectedItem();
		DescriptionDialog.display("Weapon Description", a.getFullDescription());
	}
	
	private void addAmmo() {
		Weapon f = (Weapon) ((Weapon) ammoSelect.getSelectedItem()).getClone();
		f.setBonus(ammoValue.getText());
		f.setMasterwork(masterwork.isSelected());
		f.setNotes(ammoNotes.getText());
		f.setQuantity(ammoQuantity.getText());
		PlayerAmmoDAO pwdb = new PlayerAmmoDAO();
		f.setId("" + pwdb.addPlayerAmmo(f, "" + owner.getChar().getID()));
		owner.getChar().setAmmo(
				pwdb.getPlayerAmmo("" + owner.getChar().getID()));
		ammoList.setListData(owner.getChar().getAmmo());
	}

	private void saveAmmo() {
		int idx = ammoList.getSelectedIndex();
		Weapon f = (Weapon) ((Weapon) ammoSelect.getSelectedItem()).getClone();
		f.setBonus(ammoValue.getText());
		f.setQuantity(ammoQuantity.getText());
		f.setInstanceId(((Weapon) ammoList.getSelectedValue()).getInstanceId());
		f.setMasterwork(masterwork.isSelected());
		f.setNotes(ammoNotes.getText());

		PlayerAmmoDAO pwdb = new PlayerAmmoDAO();
		pwdb.updatePlayerAmmo(f, "" + owner.getChar().getID());
		owner.getChar().setAmmo(
				pwdb.getPlayerAmmo("" + owner.getChar().getID()));
		ammoList.setListData(owner.getChar().getAmmo());
		if (idx >= 0) {
			ammoList.setSelectedIndex(idx);
		}

	}

	private void removeAmmo() {

		Weapon w = (Weapon) ammoList.getSelectedValue();
		PlayerAmmoDAO pwdb = new PlayerAmmoDAO();
		pwdb.deletePlayerAmmo(w);
		owner.getChar().setAmmo(
				pwdb.getPlayerAmmo("" + owner.getChar().getID()));
		ammoList.setListData(owner.getChar().getAmmo());
	}

	private void doLayout(Component comp, int x, int y) {
		cn.gridx = x;
		cn.gridy = y;
		cn.gridwidth = 1;
		cn.gridheight = 1;
		gridbag.setConstraints(comp, cn);
		add(comp);
	}

	@SuppressWarnings("unused")
	private void doLayout(Component comp, int x, int y, int width, int height) {
		cn.gridx = x;
		cn.gridy = y;
		cn.gridwidth = width;
		cn.gridheight = height;

		gridbag.setConstraints(comp, cn);
		add(comp);
	}

	public void valueChanged(ListSelectionEvent e) {

		try {
			Weapon f = (Weapon) ammoList.getSelectedValue();
			
			ammoSelect.setSelectedWeapon(f);
			ammoValue.setText(f.getBonus());
			ammoNotes.setText(f.getNotes());
			ammoQuantity.setText(f.getQuantity());
			masterwork.setSelected(f.isMasterwork());
		} catch (Exception ex) {

		}
	}
}
