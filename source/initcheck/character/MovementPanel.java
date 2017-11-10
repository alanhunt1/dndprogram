package initcheck.character;

import initcheck.DCharacter;
import initcheck.InitFont;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledList;

import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class MovementPanel extends TiledGridPanel implements FocusListener,
		StatusTab {

	private static final long serialVersionUID = 1L;

	private JTextField initOverride = new JTextField(10);

	private JTextField baseOverride = new JTextField(10);

	private JLabel init = new JLabel();

	private JLabel fiveFootStep = new JLabel();

	private JLabel baseMovement = new JLabel();

	private JLabel fullMovement = new JLabel();

	private TiledList initCalc = new TiledList();

	private JScrollPane initScroll = new JScrollPane(initCalc);

	private TiledList fiveCalc = new TiledList();

	private JScrollPane fiveScroll = new JScrollPane(fiveCalc);

	private TiledList baseCalc = new TiledList();

	private JScrollPane baseScroll = new JScrollPane(baseCalc);

	private TiledList fullCalc = new TiledList();

	private JScrollPane fullScroll = new JScrollPane(fullCalc);

	private JLabel runJumpLabel = new JLabel();

	private JLabel standJumpLabel = new JLabel();

	private JLabel runHighLabel = new JLabel();

	private JLabel standHighLabel = new JLabel();

	private JLabel backJumpLabel = new JLabel();

	private JLabel swimLabel = new JLabel();
	
	private JLabel climbLabel = new JLabel();
	
	private final PlayerStatDialog owner;

	private SaveBlock sb;

	boolean updateRequired;

	private boolean error = false;
	
	public boolean isError() {
		return error;
	}



	public void setError(boolean error) {
		this.error = error;
	}


	/**
	 * Get the UpdateRequired value.
	 * 
	 * @return the UpdateRequired value.
	 */
	public boolean isUpdateRequired() {
		return updateRequired;
	}

	
	
	/**
	 * Set the UpdateRequired value.
	 * 
	 * @param newUpdateRequired
	 *            The new UpdateRequired value.
	 */
	public void setUpdateRequired(boolean newUpdateRequired) {
		this.updateRequired = newUpdateRequired;
	}

	public MovementPanel(final PlayerStatDialog owner, DCharacter dc) {
		super("images/rockLighter.jpg");
		
		this.owner = owner;

		initCalc.setCellRenderer(new GenericListCellRenderer());
		fiveCalc.setCellRenderer(new GenericListCellRenderer());
		baseCalc.setCellRenderer(new GenericListCellRenderer());
		fullCalc.setCellRenderer(new GenericListCellRenderer());

		updateCharacter();

		setBorder(BorderFactory.createEtchedBorder());

		fullMovement.setFont(new Font("SansSerif", Font.BOLD, 24));
		baseMovement.setFont(new Font("SansSerif", Font.BOLD, 24));
		fiveFootStep.setFont(new Font("SansSerif", Font.BOLD, 24));
		init.setFont(new Font("SansSerif", Font.BOLD, 24));

		baseOverride.addFocusListener(this);
		initOverride.addFocusListener(this);
		
		JPanel initPanel = new JPanel();
		initPanel.setOpaque(false);
		initPanel.add(new JLabel("Init"));
		initPanel.add(init);
		doLayout(initPanel, 0, ypos);

		JPanel fiveFootPanel = new JPanel();
		fiveFootPanel.setOpaque(false);
		fiveFootPanel.add(new JLabel("5' Step"));
		fiveFootPanel.add(fiveFootStep);
		doLayout(fiveFootPanel, 1, ypos);

		JPanel baseMovePanel = new JPanel();
		baseMovePanel.setOpaque(false);
		baseMovePanel.add(new JLabel("Base Movement"));
		baseMovePanel.add(baseMovement);
		doLayout(baseMovePanel, 2, ypos);

		JPanel fullMovePanel = new JPanel();
		fullMovePanel.setOpaque(false);
		fullMovePanel.add(new JLabel("Full Movement"));
		fullMovePanel.add(fullMovement);
		doLayout(fullMovePanel, 3, ypos);

		ypos++;

		JPanel initOverridePanel = new JPanel();
		initOverridePanel.setOpaque(false);
		initOverridePanel.add(new JLabel("Mod"));
		initOverridePanel.add(initOverride);
		doLayout(initOverridePanel, 0, ypos);

		JPanel baseOverridePanel = new JPanel();
		baseOverridePanel.setOpaque(false);
		baseOverridePanel.add(new JLabel("Override"));
		baseOverridePanel.add(baseOverride);
		doLayout(baseOverridePanel, 2, ypos);

		ypos++;

		setWeightX(0.25);
		setWeightY(1.0);
		doLayout(initScroll, 0, ypos);
		doLayout(fiveScroll, 1, ypos);
		doLayout(baseScroll, 2, ypos);
		doLayout(fullScroll, 3, ypos);
		
		ypos++;
		setWeightX(0.0);
		setWeightY(0.0);
		
		runJumpLabel.setFont(InitFont.courier);
		runHighLabel.setFont(InitFont.courier);
		standJumpLabel.setFont(InitFont.courier);
		standHighLabel.setFont(InitFont.courier);
		backJumpLabel.setFont(InitFont.courier);
		swimLabel.setFont(InitFont.courier);
		climbLabel.setFont(InitFont.courier);
		
		doLayout(runJumpLabel, 0, ypos, 2, 1);
		doLayout(swimLabel, 2, ypos, 2, 1);
		ypos++;
		doLayout(standJumpLabel, 0, ypos, 2, 1);
		doLayout(climbLabel, 2, ypos, 2, 1);
		ypos++;
		doLayout(runHighLabel, 0, ypos, 2, 1);
		ypos++;
		doLayout(standHighLabel, 0, ypos, 2, 1);
		ypos++;
		doLayout(backJumpLabel, 0, ypos, 2, 1);
	}

	public void updateCharacter() {
		Calculation wc = owner.getChar().calcBaseMovement();
		Calculation fc = owner.getChar().getFullMovement();
		Calculation rc = owner.getChar().getFiveFootStep();
		Calculation ic = owner.getChar().calcMod();

		baseMovement.setText(wc.getDisplayValue());
		fullMovement.setText(fc.getDisplayValue());
		fiveFootStep.setText(rc.getDisplayValue());
		init.setText(ic.getDisplayValue());

		baseCalc.setListData(wc.getListElements());
		fullCalc.setListData(fc.getListElements());
		fiveCalc.setListData(rc.getListElements());
		initCalc.setListData(ic.getListElements());

		baseOverride.setText("" + owner.getChar().getBaseMovementOverride());
		initOverride.setText("" + owner.getChar().getInitMod());
		
		JumpBlock jb = owner.getChar().getJump();
		runJumpLabel.setText("Running Jump      : "+jb.getRunFormula()+" Max : "+jb.getRunMax());
		runHighLabel.setText("Running High Jump : "+jb.getRunHighFormula()+" Max : "+jb.getRunHighMax());
		standJumpLabel.setText("Standing Jump     : "+jb.getStandFormula()+" Max : "+jb.getStandMax());
		standHighLabel.setText("Standing High Jump: "+jb.getStandHighFormula()+" Max : "+jb.getStandHighMax());
		backJumpLabel.setText("Back Jump         : "+jb.getBackFormula()+" Max : "+jb.getBackMax());
	
		int baseMove = Integer.parseInt(wc.getDisplayValue());
		String swimMove = ""+(baseMove / 4);
		if (baseMove % 4 != 0){
			swimMove += " " + baseMove % 4 + "/4'";
		}
		String climbMove = "" + (baseMove / 2);
		if (baseMove % 2 != 0){
			climbMove += " " + baseMove % 2 + "/2'";
		}
		swimLabel.setText("Swim Speed  : "+swimMove);
		climbLabel.setText("Climb Speed : "+climbMove);
	}

	public void focusGained(FocusEvent e) {

	}

	public void focusLost(FocusEvent e) {

		if (e.getSource() == baseOverride) {
		
			owner.getChar().setBaseMovementOverride(
					Integer.parseInt(baseOverride.getText()));
			updateCharacter();
		}
		if (e.getSource() == initOverride) {
		
			owner.getChar().setInitMod(
					Integer.parseInt(initOverride.getText()));
			updateCharacter();
		}

	}

	public SaveBlock getSaves() {
		return sb;
	}

}
