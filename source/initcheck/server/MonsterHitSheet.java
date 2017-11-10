package initcheck.server;

import initcheck.DCharacter;
import initcheck.HitSheetModel;
import initcheck.InitLogger;
import initcheck.InitServer;
import initcheck.Participant;
import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class MonsterHitSheet extends TiledPanel implements Serializable,
		TableModelListener {

	static final long serialVersionUID = 1;

	private JTable hitTable;

	private JScrollPane scrollPane = new JScrollPane(hitTable);

	private InitServer owner;

	private HitSheetModel hsModel;

	private Vector<Participant> monsters = new Vector<Participant>();

	private Vector<String> columns = new Vector<String>();

	private Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();

	private Random rnd = new Random();

	private DefaultCellEditor playerDropdown;

	private InitLogger logger = new InitLogger(this);

	private String currentAttacker = null;

	int currentDamage = 0;

	int currentHits = 0;

	int maxDamage = 0;

	public MonsterHitSheet(final InitServer owner, HitSheetModel hsm) {

		super();
		this.owner = owner;

		hsModel = hsm;
		columns = hsModel.getColumns();
		rowData = hsModel.getRowData();

		initHitSheet();

	}

	public MonsterHitSheet(final InitServer owner, HitSheetModel hsm,
			Vector<Participant> monsters) {

		super();
		this.owner = owner;
		this.monsters = monsters;

		logger.log("REBUILDING HIT SHEET");

		hsModel = hsm;
		columns = hsModel.getColumns();
		rowData = hsModel.getRowData();

		initHitSheet();

	}

	public void updateMonsterStun(String name) {
		monsters = owner.getEngine().getMonsters();
		for (int i = 0; i < monsters.size(); i++) {
			if (((String) hitTable.getModel().getValueAt(i, 0)).equals(name)) {
				hitTable.getModel().setValueAt("0", i, 10);
			}
		}
	}

	public MonsterHitSheet(final InitServer owner, int i, ImageIcon bg) {

		super(bg);
		this.owner = owner;

		logger.log("BUILDING HIT SHEET");

		monsters = owner.getEngine().getMonsters();
		columns = new Vector<String>();
		rowData = new Vector<Vector<Object>>();

		// set the columns (one per monster)
		columns.add("Name");
		columns.add("HP");
		columns.add("ST");
		columns.add("CR");
		columns.add("Killed By");
		columns.add("Fighting");
		columns.add("Fighting");
		columns.add("Notes");
		columns.add("Damage");
		columns.add("Heal");
		columns.add("Calc");

		for (int j = 0; j < monsters.size(); j++) {
			Participant m = (Participant) monsters.get(j);
			Vector<Object> row = new Vector<Object>();
			row.add(m.getName());
			row.add("" + m.getHP());
			row.add("" + m.getHP() / 5);
			row.add("" + m.getLevel());
			row.add("");
			row.add("");
			row.add("");
			row.add("");
			row.add("");
			row.add("");
			row.add("0");
			rowData.add(row);
		}

		// create the new table based on the query results
		hsModel = new HitSheetModel(rowData, columns);
		initHitSheet();
	}

	private void initHitSheet() {
		// TableSorter sorter = new TableSorter(hsModel);
		hitTable = new JTable(hsModel);
		// sorter.setTableHeader(table.getTableHeader());
		hitTable.getModel().addTableModelListener((TableModelListener) this);
		initColumns();

		hitTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		BorderLayout bl = new BorderLayout();
		bl.setVgap(0);
		setLayout(bl);

		add(scrollPane, BorderLayout.CENTER);

		setBorder(BorderFactory.createEmptyBorder(0, // top
				20, // left
				0, // bottom
				20) // right
		);

		initColumnSizes(hitTable, hsModel);
		// reset the viewpane so that the scroller is looking at the correct
		// table
		scrollPane.setViewportView(hitTable);

		// and revalidate so it gets redrawn
		scrollPane.revalidate();
		scrollPane.repaint();
		setPreferredSize(new Dimension(100, 150));
	}

	public HitSheetModel getModel() {
		return hsModel;
	}

	private void initColumns() {
		logger.log("Setting Columns");

		JComboBox jcb = getPlayerSelector();
		logger.log("Found " + jcb.getItemCount() + " players");
		playerDropdown = new DefaultCellEditor(jcb);

		TableColumn fightingColumn = hitTable.getColumnModel().getColumn(4);
		fightingColumn.setCellEditor(playerDropdown);

		TableColumn fightingColumn2 = hitTable.getColumnModel().getColumn(5);
		fightingColumn2.setCellEditor(playerDropdown);

		TableColumn fightingColumn3 = hitTable.getColumnModel().getColumn(6);
		fightingColumn3.setCellEditor(playerDropdown);

		// Set up tool tips for the sport cells.
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for player selection");
		fightingColumn.setCellRenderer(renderer);

		logger.log("SET COLUMNS");
	}

	private void initColumnSizes(JTable table, HitSheetModel model) {
		TableColumn column = null;

		column = table.getColumnModel().getColumn(0);
		column.setPreferredWidth(120);

		column = table.getColumnModel().getColumn(1);
		column.setPreferredWidth(30);

		column = table.getColumnModel().getColumn(2);
		column.setPreferredWidth(30);

		column = table.getColumnModel().getColumn(3);
		column.setPreferredWidth(30);

		column = table.getColumnModel().getColumn(4);
		column.setPreferredWidth(120);

		column = table.getColumnModel().getColumn(5);
		column.setPreferredWidth(120);

		column = table.getColumnModel().getColumn(6);
		column.setPreferredWidth(120);
	}

	public JComboBox getPlayerSelector() {
		JComboBox ps = new JComboBox();
		Vector<DCharacter> chars = owner.getEngine().getDB().getCharacters();
		ps.addItem("");
		ps.addItem("Reassign");
		for (int i = 0; i < chars.size(); i++) {
			logger.log("Adding CHar " + ((DCharacter) chars.get(i)).getName());
			ps.addItem(((DCharacter) chars.get(i)).getName());
		}

		return ps;
	}

	public void tableChanged(TableModelEvent e) {
		// if they modified the "hit point" column, do the calculation to reduce
		// the hit points and calculate the stun for the affected monster.
		if (e.getColumn() == 8) {
			
			updateDamageFigures(e.getFirstRow(), e.getColumn(),
					currentAttacker, true, false);
		}
		if (e.getColumn() == 9) {
			
			updateDamageFigures(e.getFirstRow(), e.getColumn(),
					currentAttacker, true, true);
		}

	}

	@SuppressWarnings("unchecked")
	public void updateDamageFigures(int row, int column, String killer,
			boolean upd, boolean heal) {

		Vector rowv = rowData.get(row);
		String val = (String) rowv.get(column);
		
		// position 10 holds the current damage taken this round
		String stunval = (String) rowv.get(10);
		
		String monsterName = (String) rowv.get(0);

		Participant m = getMonster(monsterName);
		if (m == null) {
			monsters = owner.getEngine().getMonsters();
			m = getMonster(monsterName);
		}

		if (m != null && val != "") {

			String stunStr = (String) rowv.get(2);

			int hp = m.getCurrentHitPoints();
			int stun = 0;
			int damage = 0;
			int sval = 0;
			try {
				stun = Integer.parseInt(stunStr);
				damage = Integer.parseInt(val);
				sval = Integer.parseInt(stunval);
			} catch (Exception e) {

			}

			int stundmg = damage + sval;

			if (killer == null) {
				killer = owner.getCurrentParticipantName();
			}
			if (!heal){
				owner.registerDamage(damage, killer);
			}
			currentDamage += damage;
			if (damage > maxDamage) {
				maxDamage = damage;
			}
			currentHits++;

			
			int remainder = hp - damage;
			if (heal){
				remainder = hp + damage;
			}
			rowv.setElementAt("" + remainder, 1);
			
			if (!heal){
				rowv.setElementAt("", 8);
				rowv.setElementAt("" + stundmg, 10);
			}else{
				rowv.setElementAt("", 9);
			}
			m.setCurrentHitPoints(remainder);
			if (!heal){
				m.setDamageTaken(m.getDamageTaken() + damage);
			}
			owner.damagePlayer(m.getName(), ""+damage);
			
			if (!heal && stundmg >= stun && remainder > 0) {
				owner.stunMonster(m.getName(), false);
			}

			if (remainder < 1) {
				
				owner.killMonster(m.getName());
				rowv.setElementAt("" + remainder, 1);
				if (killer != null) {
					rowv.setElementAt(killer, 4);
				}
			}
		}
		hsModel = new HitSheetModel(rowData, columns);
		refreshTable();

	}

	public Participant getMonster(String monsterName) {
		for (int i = 0; i < monsters.size(); i++) {
			Participant m = (Participant) monsters.get(i);
			if (m.getName().equals(monsterName)) {
				return m;
			}
		}
		return null;
	}

	public void refreshTable() {
		initColumns();
		// reset the viewpane so that the scroller is looking at the correct
		// table
		scrollPane.setViewportView(hitTable);

		// and revalidate so it gets redrawn
		scrollPane.revalidate();
		scrollPane.repaint();
	}

	public void setSelectedMonster(int i) {
		hitTable.editCellAt(i, 1);
		scrollToVisible(i, 0);
	}

	public Vector<Vector<Object>> getRowData() {
		return hsModel.getRowData();
	}

	public void refreshPlayers() {
		refreshTable();
	}

	private int getRandom(int i) {
		int j = rnd.nextInt(i);
		if (j == 0) {
			return 1;
		}
		return j + 1;
	}

	@SuppressWarnings("unchecked")
	public void assignRandomOpponent() {

		Vector chars = owner.getEngine().getDB().getCharacters();

		for (int i = 0; i < rowData.size(); i++) {
			
			Vector row = rowData.get(i);
			
			String s = row.get(4).toString();
			

			if (s == null || s.equals("") || s.equals("Reassign")) {
				DCharacter dc = (DCharacter) chars
						.get(getRandom(chars.size()) - 1);
				if (!dc.isDead()) {
					row.set(4, dc.getName());
				}
			}
		}
		hsModel = new HitSheetModel(rowData, columns);
		refreshTable();

	}

	public void addDamage(String dc, String monster, int damage) {
		currentAttacker = dc;
		int i = 0;
		for (i = 0; i < rowData.size(); i++) {
			
			Vector<Object> row = (Vector<Object>) rowData.get(i);

			
			String name = row.get(0).toString();
			if (monster.equals(name)) {
			
				hitTable.getModel().setValueAt("" + damage, i, 8);
				
				break;
			}
		}
		

		refreshTable();

	}

	@SuppressWarnings("unchecked")
	public void setOpponent(String dc, String monster) {
		for (int i = 0; i < rowData.size(); i++) {
			
			Vector row = rowData.get(i);

			
			String name = row.get(0).toString();
			if (monster.equals(name)) {
				if (row.get(5).toString().equals("")) {
					row.set(5, dc);
				} else {
					row.set(6, dc);
				}
			}
		}
		hsModel = new HitSheetModel(rowData, columns);
		refreshTable();
	}

	public String getOpponent(String s) {
		for (int i = 0; i < rowData.size(); i++) {
			
			Vector<Object> row = (Vector<Object>) rowData.get(i);

			
			String name = row.get(0).toString();
			if (s.equals(name)) {
				return row.get(4).toString();
			}
		}
		return "";
	}

	public String getPlayerOpponent(String s) {
		for (int i = 0; i < rowData.size(); i++) {
			
			Vector<Object>row = (Vector<Object>) rowData.get(i);

			
			String name = row.get(4).toString();
			String name2 = row.get(5).toString();
			String name3 = row.get(6).toString();

			if (s.equals(name) || s.equals(name2) || s.equals(name3)) {
				if (Integer.parseInt((String) (hitTable.getModel().getValueAt(
						i, 1))) > 0) {
					return row.get(0).toString();
				}
			}
		}
		return "";
	}

	public void addMonsters(Vector<Participant> v) {
		logger.log("Adding " + v.size() + " Monsters");
		for (int j = 0; j < v.size(); j++) {
			Participant m = v.get(j);
			Vector<Object> row = new Vector<Object>();
			row.add(m.getName());
			row.add("" + m.getHP());
			logger.log("Stun Pct is " + m.getStunPercentage());
			double stun = m.getHP()
					* (Double.parseDouble(m.getStunPercentage()) / 100.0);
			row.add("" + new Double(stun).intValue());
			row.add("" + m.getLevel());
			row.add("");
			row.add("");
			row.add("");
			row.add("");
			row.add("");
			row.add("");
			row.add("0");
			rowData.add(row);
		}
		hsModel = new HitSheetModel(rowData, columns);
		refreshTable();
	}

	
	
	public void removeMonsters(Vector<Participant> v) {
		for (int j = 0; j < v.size(); j++) {
			if (v.get(j).getPType().equalsIgnoreCase("Monster")) {
				Participant m = ((Participant) v.get(j));
				
				for (int k = 0; k < rowData.size(); k++) {
					Vector<Object> data = (Vector<Object>) (rowData.get(k));
					if (m.getName().equals(data.get(0))) {
						rowData.removeElementAt(k);
						break;
					}
				}
			}
		}
		hsModel = new HitSheetModel(rowData, columns);
		refreshTable();
	}

	public void removeMonsters() {
		rowData.removeAllElements();
		hsModel = new HitSheetModel(rowData, columns);
		refreshTable();
	}

	// Assumes table is contained in a JScrollPane.
	// Scrolls the cell (rowIndex, vColIndex) so that it is visible
	// within the viewport.
	public void scrollToVisible(int rowIndex, int vColIndex) {
		if (!(hitTable.getParent() instanceof JViewport)) {
			return;
		}
		JViewport viewport = (JViewport) hitTable.getParent();

		// This rectangle is relative to the table where the
		// northwest corner of cell (0,0) is always (0,0).
		Rectangle rect = hitTable.getCellRect(rowIndex, vColIndex, true);

		// The location of the viewport relative to the table
		Point pt = viewport.getViewPosition();

		// Translate the cell location so that it is relative
		// to the view, assuming the northwest corner of the
		// view is (0,0)
		rect.setLocation(rect.x - pt.x, rect.y - pt.y);

		// Scroll the area into view
		viewport.scrollRectToVisible(rect);
	}

	public InitServer getOwner() {
		return owner;
	}

	public void resetStats() {
		currentDamage = 0;
		currentHits = 0;
		maxDamage = 0;
	}

	public int getCurrentHits() {
		return currentHits;
	}

	/**
	 * Get the CurrentDamage value.
	 * 
	 * @return the CurrentDamage value.
	 */
	public int getCurrentDamage() {
		return currentDamage;
	}

	/**
	 * Set the CurrentDamage value.
	 * 
	 * @param newCurrentDamage
	 *            The new CurrentDamage value.
	 */
	public void setCurrentDamage(int newCurrentDamage) {
		this.currentDamage = newCurrentDamage;
	}

	/**
	 * Get the MaxDamage value.
	 * 
	 * @return the MaxDamage value.
	 */
	public int getMaxDamage() {
		return maxDamage;
	}

	/**
	 * Set the MaxDamage value.
	 * 
	 * @param newMaxDamage
	 *            The new MaxDamage value.
	 */
	public void setMaxDamage(int newMaxDamage) {
		this.maxDamage = newMaxDamage;
	}

	public Vector<String> getColumns() {
		return columns;
	}

	public void setColumns(Vector<String> columns) {
		this.columns = columns;
	}

	public Vector<Participant> getMonsters() {
		return monsters;
	}

	public void setMonsters(Vector<Participant> monsters) {
		this.monsters = monsters;
	}

	public HitSheetModel getHsModel() {
		return hsModel;
	}

	public void setHsModel(HitSheetModel hsModel) {
		this.hsModel = hsModel;
	}

}
