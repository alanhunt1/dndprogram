package initcheck;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ClientInitListCellRenderer extends JLabel implements
		ListCellRenderer {

	static final long serialVersionUID = 1;

	public ClientInitListCellRenderer() {
		// Don't paint behind the component
		setOpaque(true);
	}

	// This is the only method defined by ListCellRenderer.
	// We just reconfigure the JLabel each time we're called.

	public Component getListCellRendererComponent(JList list, Object value, // value
																			// to
																			// display
			int index, // cell index
			boolean isSelected, // is the cell selected
			boolean cellHasFocus) // the list and the cell have the focus
	{

		Participant p = (Participant) value;
		// setToolTipText("AC:"+p.getPrecalcAc()+" STUN:"+(p.getHP()/5));

		String s = value.toString();
		setText(s);
		// setIcon((s.length() > 10) ? longIcon : shortIcon);
		if (isSelected) {
			// setBackground(list.getSelectionBackground());
			setBackground(InitColor.red);
			setForeground(Color.white);
		} else {
			if (index % 2 == 0) {
				setBackground(list.getBackground());
			} else {
				setBackground(InitColor.lightGray);
			}
			if (p.getGender() != null && p.getGender().equals("F")) {
				setForeground(InitColor.pink);
			} else if (p.getGender() != null && p.getGender().equals("M")) {
				setForeground(InitColor.blue);
			} else {
				setForeground(list.getForeground());
			}
		}
		setFont(list.getFont());
		return this;
	}
}
