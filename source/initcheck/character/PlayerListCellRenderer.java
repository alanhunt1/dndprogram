package initcheck.character;

import initcheck.DCharacter;
import initcheck.InitColor;
import initcheck.utils.StrManip;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class PlayerListCellRenderer extends GridPanel implements ListCellRenderer<Object> {

	private static final long serialVersionUID = 1L;
	JLabel label = new JLabel();
	JLabel iconLabel = new JLabel();
	JLabel class1Label = new JLabel();
	JLabel class2Label = new JLabel();
	JLabel class3Label = new JLabel();
	JLabel class4Label = new JLabel();
	JLabel class5Label = new JLabel();

	public PlayerListCellRenderer() {
		this.setBorder(null);

		// Don't paint behind the component
		setOpaque(false);
		setWeightY(0.3);
		setWeightX(0.3);
		doLayout(iconLabel, 0, 1, 5);

		doLayout(label, 1);
		doLayout(class1Label, 2);
		incYPos();
		doLayout(class2Label, 2);
		incYPos();
		doLayout(class3Label, 2);
		incYPos();
		doLayout(class4Label, 2);
		incYPos();
		doLayout(class5Label, 2);
		incYPos();

	}

	// This is the only method defined by ListCellRenderer.
	// We just reconfigure the JLabel each time we're called.

	public Component getListCellRendererComponent(JList<?> list,
			Object value, // value to display
			int index, // cell index
			boolean isSelected, // is the cell selected
			boolean cellHasFocus) // the list and the cell have the focus
	{

		DCharacter p = (DCharacter) value;
		label.setToolTipText("" + p.getXp() + "/" + p.getNextLevel());
		ImageIcon ii = new ImageIcon(p.getIcon());
		if (ii.getIconWidth() > 50) {
			ii.setImage(ii.getImage().getScaledInstance(50, 50, Image.SCALE_FAST));
		}

		iconLabel.setIcon(ii);

		String s = p.listFormat();
		label.setText(s);

		class1Label.setText("");
		class2Label.setText("");
		class3Label.setText("");
		class4Label.setText("");
		class5Label.setText("");

		ClassSet classSet = p.getClassSet();
		for (int i = 0; i < classSet.size(); i++) {

			ClassSlot cs = classSet.get(i);
			String cc = cs.getClassName().getCharClass();
			switch (i) {
			case 0:
				class1Label.setText(StrManip.pad(cc + " " + cs.getLevel(), 22));
				break;
			case 1:
				class2Label.setText(StrManip.pad(cc + " " + cs.getLevel(), 22));
				break;
			case 2:
				class3Label.setText(StrManip.pad(cc + " " + cs.getLevel(), 22));
				break;
			case 3:
				class4Label.setText(StrManip.pad(cc + " " + cs.getLevel(), 22));
				break;
			case 4:
				class5Label.setText(StrManip.pad(cc + " " + cs.getLevel(), 22));
				break;
			}

		}

		if (isSelected) {
			setBackground(InitColor.red);
			setOpaque(true);
			// label.setOpaque(true);
			// label.setBackground(list.getSelectionBackground());
			label.setForeground(Color.white);
			class1Label.setForeground(Color.white);
			class2Label.setForeground(Color.white);
			class3Label.setForeground(Color.white);
			class4Label.setForeground(Color.white);
			class5Label.setForeground(Color.white);

			// label.setBorder(new LineBorder(InitColor.red));

		} else {
			setBackground(list.getBackground());
			setOpaque(false);
			// label.setOpaque(false);
			// label.setBorder(null);
			// label.setBackground(list.getBackground());
			if (p.getGender() != null && p.getGender().equals("F")) {
				label.setForeground(InitColor.pink);
				class1Label.setForeground(InitColor.pink);
				class2Label.setForeground(InitColor.pink);
				class3Label.setForeground(InitColor.pink);
				class4Label.setForeground(InitColor.pink);
				class5Label.setForeground(InitColor.pink);
			} else if (p.getGender() != null && p.getGender().equals("M")) {
				label.setForeground(InitColor.blue);
				class1Label.setForeground(InitColor.blue);
				class2Label.setForeground(InitColor.blue);
				class3Label.setForeground(InitColor.blue);
				class4Label.setForeground(InitColor.blue);
				class5Label.setForeground(InitColor.blue);
			} else {
				label.setForeground(list.getForeground());
				class1Label.setForeground(list.getForeground());
				class2Label.setForeground(list.getForeground());
				class3Label.setForeground(list.getForeground());
				class4Label.setForeground(list.getForeground());
				class5Label.setForeground(list.getForeground());
			}

		}
		label.setFont(list.getFont());
		class1Label.setFont(list.getFont());
		class2Label.setFont(list.getFont());
		class3Label.setFont(list.getFont());
		class4Label.setFont(list.getFont());
		class5Label.setFont(list.getFont());

		return this;
	}
}
