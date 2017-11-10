package initcheck.treasure;

import initcheck.InitColor;
import initcheck.database.TreasureListItem;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class TreasureListCellRenderer extends JLabel implements
		ListCellRenderer {

	private static final long serialVersionUID = 1L;

	public TreasureListCellRenderer() {
		// Don't paint behind the component
		setOpaque(true);
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
		String displayType = "normal";
		try {
			displayType = ((TreasureListItem) value).getDisplayType();
		} catch (Exception e) {

		}
		if (displayType.equals("header")) {
			setFont(new Font("Arial", Font.BOLD, 15));
		} else {
			setFont(list.getFont());
		}
		if (isSelected) {
			setOpaque(true);
			// setBackground(list.getSelectionBackground());
			setBackground(InitColor.red);
			setForeground(Color.white);
		} else {
			setOpaque(false);
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		// setFont(list.getFont());
		return this;
	}
}
