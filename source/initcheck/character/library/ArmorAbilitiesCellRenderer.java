package initcheck.character.library;

import initcheck.InitColor;
import initcheck.database.ArmorAbilities;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ArmorAbilitiesCellRenderer extends JLabel implements
		ListCellRenderer {

	
	private static final long serialVersionUID = 1L;

	public ArmorAbilitiesCellRenderer() {
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
		String s = ((ArmorAbilities) value).listFormat();
		setText(s);
		// setIcon((s.length() > 10) ? longIcon : shortIcon);
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
		setFont(list.getFont());
		return this;
	}
}
