package initcheck.character;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class LayHandsPanel extends GridPanel {

	private static final long serialVersionUID = 1L;

	private JList layHandsList = new JList();

	private JScrollPane layHandsScroll = new JScrollPane(layHandsList);

	public LayHandsPanel(final PlayerStatDialog owner) {

		Calculation calc = owner.getChar().getLayHands();

		layHandsList.setListData(calc.getElements());
		layHandsList.setVisibleRowCount(5);
		layHandsList.setCellRenderer(new GenericListCellRenderer());

		setBorder(BorderFactory.createEtchedBorder());

		doLayout(new JLabel("Lay Hands Amount"), 0, ypos);

		ypos++;
		setWeightX(1.0);
		setWeightY(1.0);
		doLayout(layHandsScroll, 0, ypos);
		setWeightX(0);
		setWeightY(0);
		ypos++;

	}

}
