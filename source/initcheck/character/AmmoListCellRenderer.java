package initcheck.character;

import initcheck.InitColor;
import initcheck.database.Weapon;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class AmmoListCellRenderer extends JLabel implements ListCellRenderer {

	private static final long serialVersionUID = 1L;

	public AmmoListCellRenderer() {
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
		Weapon w = (Weapon) value;

		String s = w.ammoFormat();
		setText(s);
		setToolTipText(w.getToolTip());

		// setIcon((s.length() > 10) ? longIcon : shortIcon);
		if (isSelected) {
			setOpaque(true);
			// setBackground(list.getSelectionBackground());
			setBackground(InitColor.red);
			setForeground(Color.white);
		} else if (!((Weapon) value).isDisplayItem()) {
			setOpaque(false);
			setForeground(InitColor.red);
			setBackground(list.getBackground());
		} else {
			setOpaque(false);
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		setFont(list.getFont());
		return this;
	}
}
