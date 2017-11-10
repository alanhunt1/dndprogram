package initcheck.character;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import initcheck.DCharacter;
import initcheck.MessageDialog;
import initcheck.PanelButton;
import initcheck.character.chooser.BonusTypeChooser;
import initcheck.character.chooser.ItemLocationChooser;
import initcheck.database.ItemLocation;
import initcheck.database.LoadLimits;
import initcheck.database.PlayerItems;
import initcheck.database.PlayerItemsDAO;
import initcheck.database.PlayerTempMod;
import initcheck.database.PlayerTempModDAO;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledList;
import initcheck.graphics.TiledListString;

public class PlayerItemPanel extends TiledGridPanel implements
		ListSelectionListener, FocusListener, StatusTab, ItemChooserOwner {

	
	private static final long serialVersionUID = 1L;

	private TiledList itemList = new TiledList();

	private JScrollPane itemScroll = new JScrollPane(itemList);

	private TiledList dropList = new TiledList();

	private JScrollPane dropScroll = new JScrollPane(dropList);

	private final PlayerStatDialog owner;

	private PanelButton addItem = new PanelButton("Add");

	private PanelButton delItem = new PanelButton("Rem");

	private PanelButton addLoc = new PanelButton("Add");

	private PanelButton delLoc = new PanelButton("Rem");

	private PanelButton saveItem = new PanelButton("Save");

	private PanelButton loadItem = new PanelButton("?", 15);

	private JTextField itemName = new JTextField(15);

	private JTextField itemQty = new JTextField(2);

	private JTextField itemWeight = new JTextField(2);

	private JPanel itemPanel = new JPanel();

	private GridPanel moneyPanel = new GridPanel();

	private ItemLocationChooser locChooser = new ItemLocationChooser();

	private ItemLocationChooser dropChooser = new ItemLocationChooser();

	private JTextField notes = new JTextField(15);

	private JTextField cost = new JTextField(5);

	private JTextField cp = new JTextField(5);

	private JTextField sp = new JTextField(5);

	private JTextField gp = new JTextField(5);

	private JTextField pp = new JTextField(5);

	private JTextField stored = new JTextField(5);

	private JCheckBox mod = new JCheckBox("Has Mod");

	private BonusTypeChooser typeChooser = new BonusTypeChooser();

	private JLabel loadLabel = new JLabel();

	private JLabel loadLabel2 = new JLabel();

	private PanelButton decItem = new PanelButton("^", 10);

	private PanelButton incItem = new PanelButton("v", 10);

	DCharacter dc;

	private ItemPopupMenu popupMenu;

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

	public PlayerItemPanel(final PlayerStatDialog owner, DCharacter dc) {
		super("images/rockLighter.jpg");
		this.owner = owner;
		this.dc = dc;

		popupMenu = new ItemPopupMenu(this);

		itemList.setVisibleRowCount(5);
		Vector<PlayerItems> addv = owner.getChar().getLinkedItems();
		addv.addAll(owner.getChar().getItems());
		itemList.setListData(addv);
		itemList.setFont(new Font("Courier", Font.PLAIN, 12));
		itemList.addListSelectionListener(this);
		itemList.setCellRenderer(new GenericListCellRenderer());
		MouseListener popupListener = new ItemPopupListener();
		itemList.addMouseListener(popupListener);

		if (addv.size() > 0) {
			itemList.setSelectedIndex(0);
		}

		dropList.setVisibleRowCount(3);
		dropList.setListData(owner.getChar().getDroppedLocationsList());
		dropList.setCellRenderer(new GenericListCellRenderer());

		JPanel itemMovePanel = new JPanel(new GridLayout(2, 1));
		itemMovePanel.add(decItem);
		itemMovePanel.add(incItem);

		updateLoadLabel();
		loadLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
		doLayout(loadLabel, 0, ypos);
		doLayout(loadLabel2, 1, ypos, 2, 1);
		ypos++;

		moneyPanel.doLayout(new JLabel("PP"), 0);
		moneyPanel.doLayout(pp, 1);
		moneyPanel.incYPos();
		moneyPanel.doLayout(new JLabel("GP"), 0);
		moneyPanel.doLayout(gp, 1);
		moneyPanel.incYPos();
		moneyPanel.doLayout(new JLabel("SP"), 0);
		moneyPanel.doLayout(sp, 1);
		moneyPanel.incYPos();
		moneyPanel.doLayout(new JLabel("CP"), 0);
		moneyPanel.doLayout(cp, 1);
		moneyPanel.incYPos();
		moneyPanel.doLayout(new JLabel("Stored"), 0);
		moneyPanel.doLayout(stored, 1);
		moneyPanel.incYPos();
		moneyPanel.doLayout(new JLabel("Dropped"), 0, 2, 1);
		moneyPanel.incYPos();
		moneyPanel.doLayout(dropScroll, 0, 2, 1);
		moneyPanel.incYPos();
		moneyPanel.doLayout(dropChooser, 0, 2, 1);
		moneyPanel.incYPos();
		moneyPanel.doLayout(addLoc, 0);
		moneyPanel.doLayout(delLoc, 1);
		moneyPanel.setOpaque(false);

		MoneyBlock money = owner.getChar().getMoney();
		cp.setText("" + money.getCp());
		sp.setText("" + money.getSp());
		gp.setText("" + money.getGp());
		pp.setText("" + money.getPp());
		stored.setText("" + money.getStoredGold());
		cp.addFocusListener(this);
		sp.addFocusListener(this);
		gp.addFocusListener(this);
		pp.addFocusListener(this);
		stored.addFocusListener(this);
		setWeightX(1.0);
		setWeightY(1.0);
		doLayout(itemScroll, 0, ypos);
		setWeightX(0);
		doLayout(itemMovePanel, 1, ypos);
		setWeightY(0);
		doLayout(moneyPanel, 2, ypos, 1, 3);
		ypos++;

		itemPanel.add(new JLabel("Name"));
		itemPanel.add(itemName);
		itemPanel.add(loadItem);
		itemPanel.add(new JLabel("Qty"));
		itemPanel.add(itemQty);
		itemPanel.add(new JLabel("Wgt"));
		itemPanel.add(itemWeight);
		itemPanel.add(new JLabel("Loc"));
		itemPanel.add(locChooser);
		itemPanel.add(new JLabel("Cost"));
		itemPanel.add(cost);
		itemPanel.setOpaque(false);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(new JLabel("Notes"));
		buttonPanel.add(notes);
		buttonPanel.add(mod);
		buttonPanel.add(typeChooser);
		buttonPanel.add(addItem);
		buttonPanel.add(delItem);
		buttonPanel.add(saveItem);
		buttonPanel.setOpaque(false);
		doLayout(itemPanel, 0, ypos, 2, 1);
		ypos++;

		doLayout(buttonPanel, 0, ypos, 2, 1);

		addItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addItem();
			}
		});

		delItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeItem();
			}
		});

		addLoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dropLocation();
			}
		});

		delLoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				undropLocation();
			}
		});

		saveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveItem();
			}
		});

		loadItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadItem();
			}
		});

		incItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveItem(1);
			}
		});

		decItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveItem(-1);
			}
		});
	}

	public void moveItem(int i) {
		int idx = itemList.getSelectedIndex();
		PlayerItems f = (PlayerItems) itemList.getSelectedValue();
		int order = f.getPosition();
		if ((i < 0 && order > 1)
				|| (i > 0 && order < owner.getChar().getItems().size())) {

			PlayerItemsDAO pwdb = new PlayerItemsDAO();
			pwdb.updateOrder(f, "" + owner.getChar().getID(), i);
			owner.getChar().setItems(
					pwdb.getPlayerItems("" + owner.getChar().getID()));
			Vector<PlayerItems> addv = owner.getChar().getLinkedItems();
			addv.addAll(owner.getChar().getItems());
			itemList.setListData(addv);

			itemList.setSelectedIndex(idx + i);
		}
	}

	public void loadItem() {
		@SuppressWarnings("unused")
		ItemChooserDialog icd = new ItemChooserDialog(this);
	}

	public void setItem(InventoryItem f) {
		itemName.setText(f.getName());
		if (f.getQuantity() == null || f.getQuantity().equals("")) {
			f.setQuantity("1");
		}
		itemQty.setText(f.getQuantity());
		itemWeight.setText(f.getWeight());
		notes.setText(f.getNotes());
		mod.setSelected(f.isTempMod());
		cost.setText(f.getCost());
		typeChooser.setSelectedItem(f.getModType());

		addItem();
		itemList.setSelectedIndex(itemList.getModel().getSize() - 1);
	}

	public void updateLoadLabel() {
		error = false;
		int carried = owner.getChar().calcWeightCarried();
		LoadLimits ll = owner.getChar().getLoadLimits();
		String loadStr = "Light";
		loadLabel.setForeground(Color.black);

		if (ll != null) {
			int load = Integer.parseInt(ll.getLightLoad());
			if (load < carried) {
				load = Integer.parseInt(ll.getMediumLoad());
				if (load < carried) {
					load = Integer.parseInt(ll.getHeavyLoad());
					if (load < carried){
						error = true;
					}
					loadStr = "Heavy";
					loadLabel.setForeground(Color.red);
				} else {
					loadStr = "Medium";
					loadLabel.setForeground(Color.yellow);
				}
			}
			loadLabel2.setText("Light : " + ll.getLightLoad() + " Medium : "
					+ ll.getMediumLoad() + "Heavy : " + ll.getHeavyLoad());
		}
		loadLabel.setText("Weight Carried : " + carried + " Load : " + loadStr);
		owner.checkUpdatesRequired();
	}

	public MoneyBlock getMoney() {

		return new MoneyBlock(Integer.parseInt(cp.getText()), Integer
				.parseInt(sp.getText()), Integer.parseInt(gp.getText()),
				Integer.parseInt(pp.getText()), Integer.parseInt(stored
						.getText()));
	}

	private void dropLocation() {
		PlayerItemsDAO pidb = new PlayerItemsDAO();
		pidb.dropLocation("" + owner.getChar().getID(),
				((ItemLocation) dropChooser.getSelectedItem()).getLocation());
		owner.getChar().setDroppedLocations(
				pidb.getDroppedLocations("" + owner.getChar().getID()));
		dropList.setListData(owner.getChar().getDroppedLocationsList());
		updateLoadLabel();
	}

	private void undropLocation() {
		PlayerItemsDAO pidb = new PlayerItemsDAO();
		pidb.undropLocation("" + owner.getChar().getID(), ((TiledListString)dropList
				.getSelectedValue()).toString());
		owner.getChar().setDroppedLocations(
				pidb.getDroppedLocations("" + owner.getChar().getID()));
		dropList.setListData(owner.getChar().getDroppedLocationsList());
		updateLoadLabel();
	}

	private void addItem() {

		String field = "";
		try {

			field = "Weight";
			@SuppressWarnings("unused")
			double d = Double.parseDouble(itemWeight.getText());
			field = "Quantity";
			@SuppressWarnings("unused")
			int i = Integer.parseInt(itemQty.getText());

			PlayerItems f = new PlayerItems();
			f.setPlayerId("" + owner.getChar().getID());
			f.setItemName(itemName.getText());
			f.setQuantity(itemQty.getText());
			f.setWeight(itemWeight.getText());
			f.setLocation(((ItemLocation) locChooser.getSelectedItem())
					.getLocation());
			f.setCost(cost.getText());
			f.setNotes(notes.getText());
			f.setHasMod(mod.isSelected());
			f.setModType((String) typeChooser.getSelectedItem());

			PlayerItemsDAO pidb = new PlayerItemsDAO();
			int id = pidb.addPlayerItems(f);
			f.setId("" + id);
			owner.getChar().setItems(
					pidb.getPlayerItems("" + owner.getChar().getID()));
			Vector<PlayerItems> addv = owner.getChar().getLinkedItems();
			addv.addAll(owner.getChar().getItems());
			itemList.setListData(addv);

			if (mod.isSelected() && f.getId() != null) {
				PlayerTempMod ptm = new PlayerTempMod();
				ptm.setPlayerId("" + owner.getChar().getID());
				ptm.setModType((String) typeChooser.getSelectedItem());
				ptm.setLinkType("Item");
				ptm.setLinkId(f.getId());
				ptm.setName(f.getItemName());
				PlayerTempModDAO pwdb = new PlayerTempModDAO();
				pwdb.addPlayerTempMod(ptm);
			}
			updateLoadLabel();

		} catch (NumberFormatException nfe) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error",
					"You must input a numeric value in the " + field
							+ " field.");
		}
	}

	private void removeItem() {
		PlayerItems f = (PlayerItems) itemList.getSelectedValue();
		int idx = itemList.getSelectedIndex();
		if (f.isLinked()) {
			String mesg = "You cannot remove that item, because it is referenced in another panel.  Remove the corresponding panel instead.";
			JOptionPane.showMessageDialog(null, mesg, "Ooh Ooh!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		PlayerItemsDAO pidb = new PlayerItemsDAO();
		pidb.deletePlayerItems(f);
		owner.getChar().setItems(
				pidb.getPlayerItems("" + owner.getChar().getID()));
		Vector<PlayerItems> addv = owner.getChar().getLinkedItems();
		addv.addAll(owner.getChar().getItems());
		itemList.setListData(addv);

		if (itemList.getModel().getSize() - 1 < idx) {
			itemList.setSelectedIndex(itemList.getModel().getSize() - 1);
		} else {
			itemList.setSelectedIndex(idx);
		}

		if (f.isHasMod()) {
			PlayerTempMod ptm = new PlayerTempMod();
			ptm.setPlayerId("" + owner.getChar().getID());
			ptm.setLinkType("Item");
			ptm.setLinkId(f.getId());

			PlayerTempModDAO pwdb = new PlayerTempModDAO();
			pwdb.deletePlayerTempMod(ptm);
		}
		updateLoadLabel();
	}

	private void saveItem() {
		String field = "";
		try {

			field = "Weight";
			@SuppressWarnings("unused")
			double d = Double.parseDouble(itemWeight.getText());
			field = "Quantity";
			@SuppressWarnings("unused")
			int i = Integer.parseInt(itemQty.getText());

			int idx = itemList.getSelectedIndex();
			PlayerItems f = (PlayerItems) itemList.getSelectedValue();

			if (mod.isSelected() && !f.isHasMod()) {
				
				PlayerTempMod ptm = new PlayerTempMod();
				ptm.setPlayerId("" + owner.getChar().getID());
				ptm.setModType((String) typeChooser.getSelectedItem());
				ptm.setLinkType("Item");
				ptm.setLinkId(f.getId());
				ptm.setName(f.getItemName());
				PlayerTempModDAO pwdb = new PlayerTempModDAO();
				pwdb.addPlayerTempMod(ptm);
			} else if (!mod.isSelected() && f.isHasMod()) {
				
				PlayerTempMod ptm = new PlayerTempMod();
				ptm.setPlayerId("" + owner.getChar().getID());
				ptm.setLinkType("Item");
				ptm.setLinkId(f.getId());

				PlayerTempModDAO pwdb = new PlayerTempModDAO();
				pwdb.deletePlayerTempMod(ptm);
			}

			f.setItemName(itemName.getText());
			f.setQuantity(itemQty.getText());
			f.setWeight(itemWeight.getText());
			f.setCost(cost.getText());
			f.setLocation(((ItemLocation) locChooser.getSelectedItem())
					.getLocation());
			f.setNotes(notes.getText());
			f.setHasMod(mod.isSelected());
			f.setModType((String) typeChooser.getSelectedItem());
			PlayerItemsDAO pidb = new PlayerItemsDAO();
			pidb.updatePlayerItems(f);
			owner.getChar().setItems(
					pidb.getPlayerItems("" + owner.getChar().getID()));
			Vector<PlayerItems> addv = owner.getChar().getLinkedItems();
			addv.addAll(owner.getChar().getItems());
			itemList.setListData(addv);
			itemList.setSelectedIndex(idx);

			updateLoadLabel();
		} catch (NumberFormatException nfe) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error",
					"You must input a numeric value in the " + field
							+ " field.");
		}
	}

	public void updateList() {
		Vector<PlayerItems> addv = owner.getChar().getLinkedItems();
		addv.addAll(owner.getChar().getItems());
		itemList.setListData(addv);
		updateLoadLabel();
	}

	public void moveItems(String location) {
		PlayerItemsDAO pidb = new PlayerItemsDAO();
		List<Object> items = itemList.getSelectedValuesList();
		int[] indices = itemList.getSelectedIndices();
		for (Object o:items) {
			PlayerItems item = (PlayerItems) o;
			item.setLocation(location);
			pidb.updatePlayerItems(item);
		}
		owner.getChar().setItems(
				pidb.getPlayerItems("" + owner.getChar().getID()));
		Vector<PlayerItems> addv = owner.getChar().getLinkedItems();
		addv.addAll(owner.getChar().getItems());
		itemList.setListData(addv);
		itemList.setSelectedIndices(indices);

		updateLoadLabel();
	}

	public void valueChanged(ListSelectionEvent e) {

		try {
			PlayerItems f = (PlayerItems) itemList.getSelectedValue();
			itemName.setText(f.getItemName());
			itemQty.setText(f.getQuantity());
			itemWeight.setText(f.getWeight());
			notes.setText(f.getNotes());
			locChooser.setSelectedLocation(f.getLocation());
			mod.setSelected(f.isHasMod());
			cost.setText(f.getCost());
			typeChooser.setSelectedItem(f.getModType());
		} catch (Exception ex) {

		}
	}

	public void focusGained(FocusEvent e) {

	}

	public void focusLost(FocusEvent e) {
		owner.getChar().setMoney(getMoney());
	}

	class ItemPopupListener extends MouseAdapter {

		// on a double click, pop up the status dialog for the
		// list item. On a single click, just update the
		// client list to show the newly selected item.
		public void mouseClicked(MouseEvent e) {
			// if (e.getClickCount() == 2){
			// owner.showStatusDialog();
			// }else if (getSelectedIndex() >= 0){
			// owner.updateClientListPosition(getSelectedIndex());
			// }
		}

		// check all the clicks for a right click, and trigger the
		// popup menu if you see one.
		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}

		private void maybeShowPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

}
