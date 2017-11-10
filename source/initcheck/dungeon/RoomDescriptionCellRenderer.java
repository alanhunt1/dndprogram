package initcheck.dungeon;

import initcheck.InitColor;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class RoomDescriptionCellRenderer extends JLabel implements
		ListCellRenderer {

	private static final long serialVersionUID = 1L;

	public RoomDescriptionCellRenderer() {
		// Don't paint behind the component
		setOpaque(true);
	}

	public Component getListCellRendererComponent(JList list, Object value, // value
			// to
			// display
			int index, // cell index
			boolean isSelected, // is the cell selected
			boolean cellHasFocus) // the list and the cell have the focus
	{
		String s = value.toString();
		if (s.length() > 150) {
			s = s.substring(0, 150);
		}
		setText(s);

		if (isSelected) {
			setBackground(InitColor.red);
			setForeground(Color.white);
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		setFont(list.getFont());
		return this;
	}
}
