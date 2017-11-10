package initcheck.character;

import initcheck.HitSheetModel;
import initcheck.InitLogger;
import initcheck.InitTableCellRenderer;
import initcheck.PanelButton;
import initcheck.TableSorter;
import initcheck.character.chooser.BattlePartyChooser;
import initcheck.database.BattleHistory;
import initcheck.database.BattleHistoryDAO;
import initcheck.database.PlayerBattles;
import initcheck.database.PlayerBattlesDAO;
import initcheck.graphics.TiledDialog;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

public class BattleHistoryDialog extends TiledDialog implements
		TableModelListener, ListSelectionListener {

	
	private static final long serialVersionUID = 1L;

	private HitSheetModel hsModel;

	private JTable battleTable;

	private JScrollPane scrollPane = new JScrollPane(battleTable);

	private Vector<String> columns = new Vector<String>();

	private Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();

	private TiledPanel tabelPanel = new TiledPanel();

	TableSorter sorter;

	private JList battleList = new JList();

	private JScrollPane battleScroll = new JScrollPane(battleList);

	private PanelButton lifetime = new PanelButton("Find Battles", 100);

	private PanelButton summary = new PanelButton("View Summary", 100);

	private TiledGridPanel selectionPanel = new TiledGridPanel();

	private TiledPanel inputPanel = new TiledPanel("images/rockLighter.jpg");

	BattlePartyChooser partyChooser = new BattlePartyChooser();

	private InitLogger logger = new InitLogger(this);
	
	public BattleHistoryDialog() {
		

		selectionPanel.setBorder(BorderFactory.createEmptyBorder(20, // top
				20, // left
				20, // bottom
				20) // right
				);

		inputPanel.add(partyChooser);
		inputPanel.add(lifetime);
		inputPanel.add(summary);

		selectionPanel.doLayout(inputPanel);
		selectionPanel.incYPos();
		selectionPanel.setWeightX(1.0);
		selectionPanel.setWeightY(0.3);

		selectionPanel.doLayout(battleScroll, 0, selectionPanel.ypos);
		selectionPanel.incYPos();
		selectionPanel.setWeightY(0.7);
		selectionPanel.doLayout(tabelPanel, 0, selectionPanel.ypos);
		battleList.addListSelectionListener(this);

		lifetime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadBattles();
			}
		});

		summary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadSummary();
			}
		});

		// set the columns (one per monster)
		columns.add("Name");
		columns.add("Stuns");
		columns.add("Stunned");
		columns.add("Dead");
		columns.add("Kills");
		columns.add("Best Hit");
		columns.add("Best Round");
		columns.add("Total Damage");
		columns.add("Total Taken");
		columns.add("Crits");
		columns.add("Fumbles");
		columns.add("Double Ds");
		columns.add("Hit Ratio");
		columns.add("Average Damage");

		getContentPane().add(selectionPanel);
		initSheet(new Vector<PlayerBattles>());
		tabelPanel.setPreferredSize(new Dimension(800, 350));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void loadBattles() {
		String s = (String) partyChooser.getSelectedItem();
		if (s.equals("All Parties")) {
			BattleHistoryDAO bhdb = new BattleHistoryDAO();
			Vector<BattleHistory> v = bhdb.selectBattleHistory(new BattleHistory());
			battleList.setListData(v);
		} else {
			BattleHistoryDAO bhdb = new BattleHistoryDAO();
			BattleHistory bh = new BattleHistory();
			bh.setParty(s);
			Vector<BattleHistory> v = bhdb.selectBattleHistory(bh);
			battleList.setListData(v);
		}
	}

	private void loadSummary() {
		String s = (String) partyChooser.getSelectedItem();
		if (s.equals("All Parties")) {
			loadLifetimeStats(null);
		} else {
			loadLifetimeStats(s);
		}
	}

	private void initSheet(Vector<PlayerBattles> chars) {

		rowData.clear();

		for (int j = 0; j < chars.size(); j++) {
			PlayerBattles m = (PlayerBattles) chars.get(j);
			Vector<Object> row = new Vector<Object>();
			row.add(m.getPlayerName());	
			row.add(new Double(Double.parseDouble(m.getNumStuns())));
			row.add(new Double(Double.parseDouble(m.getRoundsStunned())));
			row.add(new Double(Double.parseDouble(m.getRoundsDead())));
			row.add(new Double(Double.parseDouble(m.getNumKills())));

			row.add(new Double(Double.parseDouble(m.getMaxDamage())));
			row.add(new Double(Double.parseDouble(m.getMaxRoundDamage())));
			row.add(new Double(Double.parseDouble(m.getTotalDamage())));
			row.add(new Double(Double.parseDouble(m.getTotalTaken())));
			row.add(new Double(Double.parseDouble(m.getNumCrits())));
			row.add(new Double(Double.parseDouble(m.getNumFumbles())));
			row.add(new Double(Double.parseDouble(m.getNumDoubleDamage())));
			row.add(new Double(Double.parseDouble(m.getHitPercentage())));
			row.add(new Double(Double.parseDouble(m.getAvgDamage())));

			rowData.add(row);
		}
		// create the new table based on the query results
		hsModel = new HitSheetModel(rowData, columns);

		sorter = new TableSorter(hsModel);
		battleTable = new JTable(sorter);
		sorter.setTableHeader(battleTable.getTableHeader());
		sorter.getTableModel().addTableModelListener((TableModelListener) this);
		battleTable.setDefaultRenderer(Object.class,
				new InitTableCellRenderer());

		// battleTable.getModel().addTableModelListener((TableModelListener)this);
		// initColumns();

		battleTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabelPanel.setLayout(new BorderLayout());

		tabelPanel.add(scrollPane, BorderLayout.CENTER);

		initColumnSizes(battleTable, hsModel);
		// reset the viewpane so that the scroller is looking at the correct
		// table
		scrollPane.setViewportView(battleTable);

		// and revalidate so it gets redrawn
		scrollPane.revalidate();
		scrollPane.repaint();

	}

	

	public int getRanking(int column, int value) {
		int rows = sorter.getTableModel().getRowCount();
		int ranking = 0;
		Integer val = new Integer(value);

		for (int i = 0; i < rows; i++) {
			if (((Integer) sorter.getTableModel().getValueAt(i, column))
					.compareTo(val) >= 0) {
				ranking++;
			}
		}
		return ranking;
	}

	private void initColumnSizes(JTable table, HitSheetModel model) {
		TableColumn column = null;

		column = table.getColumnModel().getColumn(0);
		column.setPreferredWidth(120);

		column = table.getColumnModel().getColumn(1);
		column.setPreferredWidth(50);

		column = table.getColumnModel().getColumn(2);
		column.setPreferredWidth(50);

		column = table.getColumnModel().getColumn(3);
		column.setPreferredWidth(50);

		column = table.getColumnModel().getColumn(4);
		column.setPreferredWidth(50);

		column = table.getColumnModel().getColumn(5);
		column.setPreferredWidth(50);

		column = table.getColumnModel().getColumn(6);
		column.setPreferredWidth(50);

		column = table.getColumnModel().getColumn(7);
		column.setPreferredWidth(50);

		column = table.getColumnModel().getColumn(8);
		column.setPreferredWidth(50);

		column = table.getColumnModel().getColumn(9);
		column.setPreferredWidth(50);

		column = table.getColumnModel().getColumn(10);
		column.setPreferredWidth(50);

		column = table.getColumnModel().getColumn(11);
		column.setPreferredWidth(50);

		column = table.getColumnModel().getColumn(12);
		column.setPreferredWidth(50);

		column = table.getColumnModel().getColumn(13);
		column.setPreferredWidth(50);

	}

	public void loadLifetimeStats(String s) {
		PlayerBattlesDAO pbdb = new PlayerBattlesDAO();
		Vector<PlayerBattles> v = pbdb.getLifetimeSummaries(s);
		
		initSheet(v);
	}

	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			try {
				BattleHistory bh = (BattleHistory) battleList
						.getSelectedValue();
				PlayerBattlesDAO pbdb = new PlayerBattlesDAO();
				PlayerBattles pb = new PlayerBattles();
				pb.setBattleId(bh.getId());
				
				Vector<PlayerBattles> v = pbdb.selectPlayerBattles(pb);
				
				initSheet(v);

			} catch (Exception ex) {

				logger.log("error", "Error in battle panel " + ex);
			}
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	public void tableChanged(TableModelEvent e) {

	}

}
