package initcheck.generator;

import initcheck.InitColor;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

public class StoreListCellRenderer extends JLabel implements ListCellRenderer {

	
	private static final long serialVersionUID = 1L;

	public StoreListCellRenderer() {
		// Don't paint behind the component
		setOpaque(false);
	}

	// final static ImageIcon longIcon = new ImageIcon("long.gif");
	// final static ImageIcon shortIcon = new ImageIcon("short.gif");

	// This is the only method defined by ListCellRenderer.
	// We just reconfigure the JLabel each time we're called.

	public Component getListCellRendererComponent(JList list, Object value, // value
																			// to
																			// display
			int index, // cell index
			boolean isSelected, // is the cell selected
			boolean cellHasFocus) // the list and the cell have the focus
	{
		String s = value.toString();
		setText(s);
		// setIcon((s.length() > 10) ? longIcon : shortIcon);
		if (isSelected) {
			setOpaque(true);
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
			setBorder(new LineBorder(InitColor.red));
		
		} else {
			setOpaque(false);
			setBorder(null);
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		setFont(list.getFont());
		return this;
	}
}
