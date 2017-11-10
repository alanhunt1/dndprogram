package initcheck.character.library;

import initcheck.InitColor;
import initcheck.InitFont;
import initcheck.database.Critical;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class CriticalListCellRenderer extends JLabel implements
		ListCellRenderer {

	
	private static final long serialVersionUID = 1L;

	public CriticalListCellRenderer() {
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
		String s = ((Critical) value).listFormat();

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
		setFont(InitFont.courier);
		// setFont(list.getFont());
		return this;
	}
}
