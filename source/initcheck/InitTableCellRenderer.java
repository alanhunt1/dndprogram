package initcheck;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class InitTableCellRenderer extends JLabel implements TableCellRenderer {
	static final long serialVersionUID = 1;
		public InitTableCellRenderer () {
       // Don't paint behind the component
				setOpaque(true);
		}
     
		// This is the only method defined by TableCellRenderer.
		// We just reconfigure the JLabel each time we're called.

		public Component getTableCellRendererComponent(JTable table,
																									 Object value,
																									 boolean isSelected,
																									 boolean hasFocus,
																									 int row,
																									 int column)
		{
				String s = value.toString();
				setText(s);
				//setIcon((s.length() > 10) ? longIcon : shortIcon);
				if (isSelected) {
						//setBackground(list.getSelectionBackground());
						setBackground(InitColor.red);
						setForeground(Color.white);
				}
				else {
						setBackground(table.getBackground());
						setForeground(table.getForeground());
				}
				setFont(table.getFont());
				return this;
     }
}
