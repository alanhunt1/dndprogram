package initcheck;

import initcheck.graphics.TiledGridPanel;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class DieRollerPanel extends TiledGridPanel {
	static final long serialVersionUID = 1;

	private DieRoller dr1 = new DieRoller();

	private DieRoller dr2 = new DieRoller();

	private DieRoller dr3 = new DieRoller();

	private DieRoller dr4 = new DieRoller();

	public DieRollerPanel(final InitServer owner, ImageIcon bg) {
		super(bg);

		setWeightY(0.25);
		doLayout(dr1, 0);
		incYPos();
		doLayout(dr2, 0);
		incYPos();
		doLayout(dr3, 0);
		//incYPos();
		//doLayout(dr4, 0);
				
		

		setBorder(BorderFactory.createEmptyBorder(20, // top
				0, // left
				20, // bottom
				20) // right
		);
	}

	public void notifyAutoRoll() {
		dr1.autoRoll();
		dr2.autoRoll();
		dr3.autoRoll();
		dr4.autoRoll();
	}
}
