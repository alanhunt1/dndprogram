package initcheck.character;

import initcheck.DCharacter;
import initcheck.HitSheetModel;
import initcheck.InitLogger;
import initcheck.InitTableCellRenderer;
import initcheck.PanelButton;
import initcheck.TableSorter;
import initcheck.database.Weapon;
import initcheck.graphics.TiledDialog;
import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class PlayerComparisonDialog extends TiledDialog implements
		TableModelListener {


	private static final long serialVersionUID = 1L;

	private Vector<DCharacter> chars = new Vector<DCharacter>();

	private HitSheetModel hsModel;

	private JTable hitTable;

	private JScrollPane scrollPane = new JScrollPane(hitTable);

	private Vector<String> columns = new Vector<String>();

	private Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();

	private TiledPanel main = new TiledPanel();

	private InitLogger logger = new InitLogger(this);

	TableSorter sorter;

	private PanelButton exportButton = new PanelButton("Export to CSV");

	public PlayerComparisonDialog(Vector<DCharacter> chars) {

		this.chars = chars;

		logger.log("BUILDING HIT SHEET");
		boolean backup = false;
		if (!backup) {

			columns = new Vector<String>();
			rowData = new Vector<Vector<Object>>();

			// set the columns (one per monster)
			columns.add("Name");
			columns.add("HP");
			columns.add("Level");
			columns.add("AC");
			columns.add("Rest AC");
			columns.add("Touch AC");
			columns.add("Wpn Pos");
			columns.add("Weapon");
			columns.add("To Hit");
			columns.add("Damage");
			columns.add("Avg 0");
			columns.add("Avg 15");
			columns.add("Avg 20");
			columns.add("Avg 25");
			columns.add("F");
			columns.add("R");
			columns.add("W");

			for (int j = 0; j < chars.size(); j++) {
				DCharacter m = (DCharacter) chars.get(j);
				Vector<Object> row = new Vector<Object>();
				row.add(m.getName());
				row.add(new Integer(Integer.parseInt(m.getHpCalc()
						.getDisplayValue())));
				row.add(new Integer(new Double(m.getLevel()).intValue()));
				row.add(new Integer(m.getAC()));
				row.add(new Integer(m.getRestAc()));
				row.add(new Integer(m.getTouchAc()));
				row.add("1");
				Vector<Weapon> weapons = m.getWeapons();
				Weapon w = null;
				if (weapons.size() > 0) {
					w = (Weapon) weapons.get(0);
					row.add(w.toString());
					String toHit = "";
					if (w.getRangedmelee().equalsIgnoreCase("MELEE")) {
						toHit = (m.calcMeleeToHit(w).getDisplayValue());
					} else {
						toHit = (m.calcRangedToHit(w).getDisplayValue());
					}
					row.add(toHit);
					String dmgStr = m.calcDamage(w).getDisplayValue();
					row.add(dmgStr);
					row.add(new Integer(m.calcAverageDamage(0)));
					row.add(new Integer(m.calcAverageDamage(15)));
					row.add(new Integer(m.calcAverageDamage(20)));
					row.add(new Integer(m.calcAverageDamage(25)));
				} else {
					row.add("");
					row.add("");
					row.add("");
					row.add(new Integer("0"));
					row.add(new Integer("0"));
					row.add(new Integer("0"));
					row.add(new Integer("0"));

				}

				row.add(new Integer(m.calcFortitudeSave().getDisplayValue()));
				row.add(new Integer(m.calcReflexSave().getDisplayValue()));
				row.add(new Integer(m.calcWillSave().getDisplayValue()));
				
				rowData.add(row);
			}

		} else {
			logger.log("RESTORING FROM FILE");
			// readFromFile(i);
		}

		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportToCSV();
			}
		});

		// create the new table based on the query results
		hsModel = new HitSheetModel(rowData, columns);
		initHitSheet();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(exportButton, BorderLayout.NORTH);
		getContentPane().add(main, BorderLayout.CENTER);

		pack();
		setVisible(true);
	}

	private void exportToCSV() {
		// HitSheetModel hsm = (HitSheetModel)sorter.getTableModel();
		Vector<Vector<Object>> v = rowData;// hsm.getRowData();

		JFileChooser fc = new JFileChooser("export");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fc.showOpenDialog(getRootPane());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {

				String filename = fc.getSelectedFile().getAbsolutePath();
				FileOutputStream fos = new FileOutputStream(filename);
				PrintWriter writer = new PrintWriter(fos);

				for (int i = 0; i < v.size(); i++) {
				

					String s = "";
					Vector<Object> row = v.get(i);
					for (int j = 0; j < row.size(); j++) {
						s += "\"" + row.get(j) + "\"";
						if (j < row.size() - 1) {
							s += ",";
						}
					}
					s += "\n";
					writer.write(s);
				}

				writer.close();
				fos.close();
			} catch (Exception e) {
				logger.log("error", "Error in Export All : " + e.toString());
			}
		}
	}

	private void initHitSheet() {
		sorter = new TableSorter(hsModel);
		hitTable = new JTable(sorter);
		sorter.setTableHeader(hitTable.getTableHeader());
		sorter.getTableModel().addTableModelListener((TableModelListener) this);
		hitTable.setDefaultRenderer(Object.class, new InitTableCellRenderer());

		// hitTable.getModel().addTableModelListener((TableModelListener)this);
		initColumns();

		hitTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		main.setLayout(new BorderLayout());

		main.add(scrollPane, BorderLayout.CENTER);

		main.setBorder(BorderFactory.createEmptyBorder(0, // top
				20, // left
				20, // bottom
				20) // right
				);

		initColumnSizes(hitTable, hsModel);
		// reset the viewpane so that the scroller is looking at the correct
		// table
		scrollPane.setViewportView(hitTable);

		// and revalidate so it gets redrawn
		scrollPane.revalidate();
		scrollPane.repaint();
		main.setPreferredSize(new Dimension(700, 150));
	}

	private void initColumns() {
		logger.log("Setting Columns");

		JComboBox jcb = new JComboBox();
		jcb.addItem("1");
		jcb.addItem("2");
		jcb.addItem("3");
		jcb.addItem("4");
		jcb.addItem("5");
		jcb.addItem("6");

		logger.log("Found " + jcb.getItemCount() + " players");
		DefaultCellEditor playerDropdown = new DefaultCellEditor(jcb);

		TableColumn fightingColumn = hitTable.getColumnModel().getColumn(6);
		fightingColumn.setCellEditor(playerDropdown);

		// Set up tool tips for the sport cells.
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for player selection");
		// fightingColumn.setCellRenderer(renderer);

		logger.log("SET COLUMNS");
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
		column.setPreferredWidth(30);

		column = table.getColumnModel().getColumn(2);
		column.setPreferredWidth(30);

		column = table.getColumnModel().getColumn(3);
		column.setPreferredWidth(30);

		column = table.getColumnModel().getColumn(4);
		column.setPreferredWidth(30);

		column = table.getColumnModel().getColumn(5);
		column.setPreferredWidth(30);

		column = table.getColumnModel().getColumn(6);
		column.setPreferredWidth(30);

		column = table.getColumnModel().getColumn(7);
		column.setPreferredWidth(180);

		column = table.getColumnModel().getColumn(8);
		column.setPreferredWidth(80);

		column = table.getColumnModel().getColumn(9);
		column.setPreferredWidth(70);

		column = table.getColumnModel().getColumn(10);
		column.setPreferredWidth(30);

		column = table.getColumnModel().getColumn(11);
		column.setPreferredWidth(30);

		column = table.getColumnModel().getColumn(12);
		column.setPreferredWidth(30);

		column = table.getColumnModel().getColumn(13);
		column.setPreferredWidth(30);

		column = table.getColumnModel().getColumn(14);
		column.setPreferredWidth(30);

		column = table.getColumnModel().getColumn(15);
		column.setPreferredWidth(30);

		column = table.getColumnModel().getColumn(16);
		column.setPreferredWidth(30);

	}

	public void tableChanged(TableModelEvent e) {
		
		// if they modified the "weapon pos" column, recalc the to hit and dmg
		if (e.getColumn() == 6) {
		
			// find out which weapon they selected.
			int val = Integer.parseInt((String) sorter.getTableModel()
					.getValueAt(e.getFirstRow(), e.getColumn()));
			// find out which player we're modifying
			DCharacter m = (DCharacter) chars.get(e.getFirstRow());

			Vector<Weapon> weapons = m.getWeapons();
			Weapon w = null;
			if (weapons.size() >= val) {
				w = (Weapon) weapons.get(val - 1);
				sorter.getTableModel().setValueAt(w.toString(),
						e.getFirstRow(), 7);

				String toHit = "";
				if (w.getRangedmelee().equalsIgnoreCase("MELEE")) {
					toHit = (m.calcMeleeToHit(w).getDisplayValue());
				} else {
					toHit = (m.calcRangedToHit(w).getDisplayValue());
				}
				sorter.getTableModel().setValueAt(toHit, e.getFirstRow(), 8);
				String dmgStr = m.calcDamage(w).getDisplayValue();
				sorter.getTableModel().setValueAt(dmgStr, e.getFirstRow(), 9);

				sorter.getTableModel().setValueAt(
						new Integer(m.calcAverageDamage(w, 0)),
						e.getFirstRow(), 10);
				sorter.getTableModel().setValueAt(
						new Integer(m.calcAverageDamage(w, 15)),
						e.getFirstRow(), 11);
				sorter.getTableModel().setValueAt(
						new Integer(m.calcAverageDamage(w, 20)),
						e.getFirstRow(), 12);
				sorter.getTableModel().setValueAt(
						new Integer(m.calcAverageDamage(w, 25)),
						e.getFirstRow(), 13);

			} else {
				sorter.getTableModel().setValueAt("", e.getFirstRow(), 7);
				sorter.getTableModel().setValueAt("", e.getFirstRow(), 8);
				sorter.getTableModel().setValueAt("", e.getFirstRow(), 9);
				sorter.getTableModel().setValueAt(new Integer("0"),
						e.getFirstRow(), 10);
				sorter.getTableModel().setValueAt(new Integer("0"),
						e.getFirstRow(), 11);
				sorter.getTableModel().setValueAt(new Integer("0"),
						e.getFirstRow(), 12);
				sorter.getTableModel().setValueAt(new Integer("0"),
						e.getFirstRow(), 13);
			}

		}

	}

}
