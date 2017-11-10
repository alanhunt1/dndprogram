package initcheck.character;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class TurnUndeadPanel extends GridPanel {

	private static final long serialVersionUID = 1L;

	private JList attemptList = new JList();

	private JScrollPane attemptScroll = new JScrollPane(attemptList);
	
	private JList checkList = new JList();

	private JScrollPane checkScroll = new JScrollPane(checkList);
	
	private JList damageList = new JList();

	private JScrollPane damageScroll = new JScrollPane(damageList);

	public TurnUndeadPanel(final PlayerStatDialog owner) {

		Calculation attempt = owner.getChar().getTurnAttempts();
		Calculation check = owner.getChar().getTurnCheck();
		Calculation damage = owner.getChar().getTurnDamage();
		
		attemptList.setListData(attempt.getElements());
		attemptList.setVisibleRowCount(5);
		attemptList.setCellRenderer(new GenericListCellRenderer());

		checkList.setListData(check.getElements());
		checkList.setVisibleRowCount(5);
		checkList.setCellRenderer(new GenericListCellRenderer());
		
		damageList.setListData(damage.getElements());
		damageList.setVisibleRowCount(5);
		damageList.setCellRenderer(new GenericListCellRenderer());
		
		setBorder(BorderFactory.createEtchedBorder());

		
		
		doLayout(new JLabel("Turn Attempts : "+attempt.getDisplayValue()), 0, ypos);
		doLayout(new JLabel("Turn Check : "+check.getDisplayValue()), 1, ypos);
		doLayout(new JLabel("Turn Damage : "+damage.getDisplayValue()), 2, ypos);
		ypos++;
		
		setWeightX(1.0);
		setWeightY(1.0);
		doLayout(attemptScroll, 0, ypos);
		doLayout(checkScroll, 1, ypos);
		doLayout(damageScroll, 2, ypos);
		setWeightX(0);
		setWeightY(0);
		ypos++;

	}

}
