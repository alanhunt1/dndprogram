package initcheck.character;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import initcheck.InitColor;
import initcheck.graphics.TiledListString;

public class SaveListCellRenderer extends JLabel implements ListCellRenderer<Object> {

	
	private static final long serialVersionUID = 1L;

	public SaveListCellRenderer() {
		// Don't paint behind the component
		setOpaque(true);
	}

	// final static ImageIcon longIcon = new ImageIcon("long.gif");
	// final static ImageIcon shortIcon = new ImageIcon("short.gif");

	// This is the only method defined by ListCellRenderer.
	// We just reconfigure the JLabel each time we're called.

	public Component getListCellRendererComponent(JList<? extends Object> list, Object value, // value
																			// to
																			// display
			int index, // cell index
			boolean isSelected, // is the cell selected
			boolean cellHasFocus) // the list and the cell have the focus
	{
		String s = ((TiledListString)value).getInternalString();
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
