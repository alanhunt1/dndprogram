package initcheck;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class HitSheetModel extends AbstractTableModel {

	static final long serialVersionUID = 1;

	Vector<String> columns;

	Vector<Vector<Object>> rowData;

	public HitSheetModel(Vector<Vector<Object>> rowData, Vector<String> columns) {
		this.columns = columns;
		this.rowData = rowData;
	}

	public Vector<String> getColumns() {
		return columns;
	}

	public String getColumnName(int col) {
		return (String) columns.get(col);
	}

	public int getRowCount() {
		return rowData.size();
	}

	public int getColumnCount() {
		return columns.size();
	}

	public Object getValueAt(int row, int column) {
		Vector<Object> rrow = (Vector<Object>) rowData.get(row);
		return rrow.get(column);
	}

	public void setValueAt(Object value, int row, int col) {
		Vector<Object> rowv = (Vector<Object>)rowData.get(row);
		rowv.setElementAt(value, col);
		fireTableCellUpdated(row, col);
	}

	public boolean isCellEditable(int row, int col) {
		return true;
	}

	public Vector<Vector<Object>> getRowData() {
		return rowData;
	}

	public Class<?> getColumnClass(int columnIndex) {
		if (rowData.size() > 0) {
			Vector<Object> rrow = (Vector<Object>) rowData.get(0);
			if (rrow.get(columnIndex) != null) {
				return rrow.get(columnIndex).getClass();
			}
		}

		return Object.class;

	}
}
