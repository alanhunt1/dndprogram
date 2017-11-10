package initcheck.character.library;

import initcheck.InitColor;
import initcheck.InitFont;
import initcheck.character.GridPanel;
import initcheck.database.Monster;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class MonsterListCellRenderer extends GridPanel implements ListCellRenderer {

	
	private static final long serialVersionUID = 1L;
	
	JLabel label = new JLabel();
	JLabel iconLabel = new JLabel();
	JLabel typeLabel = new JLabel();
	
	public MonsterListCellRenderer() {
		this.setBorder(null);
		
		// Don't paint behind the component
		setOpaque(false);
		setWeightY(1);
		setWeightX(1);
		doLayout(iconLabel, 0, ypos, 1, 2);	
		setWeightX(0);
		doLayout(label, 1);
		incYPos();
		doLayout(typeLabel, 1);
	}

	public Component getListCellRendererComponent(JList list, Object value, // value
																			// to
																			// display
			int index, // cell index
			boolean isSelected, // is the cell selected
			boolean cellHasFocus) // the list and the cell have the focus
	{
		Monster p = ((Monster) value);
		
		iconLabel.setIcon(new ImageIcon(p.getIcon()));
		
		String s = p.listFormat();
		label.setText(s);
		typeLabel.setText(p.getType()+"   "+p.getAlignment());
		
		
		if (isSelected) {
			setBackground(InitColor.red);
			setOpaque(true);
			label.setForeground(Color.white);
			typeLabel.setForeground(Color.white);
		} else {
			setOpaque(false);
			setBackground(list.getBackground());
			label.setForeground(list.getForeground());
			typeLabel.setForeground(list.getForeground());
		}
		label.setFont(InitFont.courier);
		typeLabel.setFont(InitFont.courier);
		// setFont(list.getFont());
		return this;
	}
}
