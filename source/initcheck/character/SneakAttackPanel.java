package initcheck.character;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class SneakAttackPanel extends GridPanel {

	private static final long serialVersionUID = 1L;

	private JList sneakList = new JList();

	private JScrollPane sneakScroll = new JScrollPane(sneakList);

	public SneakAttackPanel(final PlayerStatDialog owner) {

		Calculation calc = owner.getChar().getSneakAttack();

		sneakList.setListData(calc.getElements());
		sneakList.setVisibleRowCount(5);
		sneakList.setCellRenderer(new GenericListCellRenderer());

		setBorder(BorderFactory.createEtchedBorder());

		doLayout(new JLabel(calc.getDisplayValue()), 0, ypos);
		ypos++;
		doLayout(new JLabel(calc.getDisplayValue2()), 0, ypos);

		ypos++;
		setWeightX(1.0);
		setWeightY(1.0);
		doLayout(sneakScroll, 0, ypos);
		setWeightX(0);
		setWeightY(0);
		ypos++;

	}

}
