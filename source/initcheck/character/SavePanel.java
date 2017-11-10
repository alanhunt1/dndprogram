package initcheck.character;

import initcheck.DCharacter;
import initcheck.InitFont;
import initcheck.MessageDialog;
import initcheck.graphics.TiledList;
import initcheck.graphics.TiledPanel;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class SavePanel extends TiledPanel implements FocusListener, StatusTab {

	private static final long serialVersionUID = 1L;

	private JTextField refMiscSave = new JTextField(3);

	private JTextField willMiscSave = new JTextField(3);

	private JTextField fortMiscSave = new JTextField(3);

	private JTextField refMiscSaveText = new JTextField(15);

	private JTextField willMiscSaveText = new JTextField(15);

	private JTextField fortMiscSaveText = new JTextField(15);

	private JLabel totalRefSave = new JLabel();

	private JLabel totalWillSave = new JLabel();

	private JLabel totalFortSave = new JLabel();

	private TiledList refCalc = new TiledList();

	private JScrollPane refScroll = new JScrollPane(refCalc);

	private TiledList willCalc = new TiledList();

	private JScrollPane willScroll = new JScrollPane(willCalc);

	private TiledList fortCalc = new TiledList();

	private JScrollPane fortScroll = new JScrollPane(fortCalc);

	boolean updateRequired;

	private final PlayerStatDialog owner;

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints cn = new GridBagConstraints();

	private boolean error = false;
	
	public boolean isError() {
		return error;
	}



	public void setError(boolean error) {
		this.error = error;
	}

	
	public SavePanel(final PlayerStatDialog owner, DCharacter dc) {
		super("images/rockLighter.jpg");
		this.owner = owner;

		refCalc.setCellRenderer(new GenericListCellRenderer());
		willCalc.setCellRenderer(new GenericListCellRenderer());
		fortCalc.setCellRenderer(new GenericListCellRenderer());
		updateCharacter();

		setBorder(BorderFactory.createEtchedBorder());
		setLayout(gridbag);

		totalFortSave.setFont(InitFont.bigFont);
		totalWillSave.setFont(InitFont.bigFont);
		totalRefSave.setFont(InitFont.bigFont);

		refMiscSave.addFocusListener(this);
		fortMiscSave.addFocusListener(this);
		willMiscSave.addFocusListener(this);

		refMiscSaveText.addFocusListener(this);
		fortMiscSaveText.addFocusListener(this);
		willMiscSaveText.addFocusListener(this);

		cn.fill = GridBagConstraints.BOTH;
		cn.ipadx = 10;
		cn.ipady = 10;

		int ypos = 0;

		JPanel fortPanel1 = new JPanel();
		fortPanel1.add(new JLabel("Fortitude"));
		fortPanel1.add(totalFortSave);
		fortPanel1.setOpaque(false);
		doLayout(fortPanel1, 0, ypos);

		JPanel refPanel1 = new JPanel();
		refPanel1.add(new JLabel("Reflex"));
		refPanel1.add(totalRefSave);
		refPanel1.setOpaque(false);
		doLayout(refPanel1, 1, ypos);

		JPanel willPanel1 = new JPanel();
		willPanel1.add(new JLabel("Will"));
		willPanel1.add(totalWillSave);
		willPanel1.setOpaque(false);
		doLayout(willPanel1, 2, ypos);

		ypos++;

		JPanel fortPanel2 = new JPanel();
		fortPanel2.add(new JLabel("Misc"));
		fortPanel2.add(fortMiscSave);
		fortPanel2.setOpaque(false);
		doLayout(fortPanel2, 0, ypos);

		JPanel refPanel2 = new JPanel();
		refPanel2.add(new JLabel("Misc"));
		refPanel2.add(refMiscSave);
		refPanel2.setOpaque(false);
		doLayout(refPanel2, 1, ypos);

		JPanel willPanel2 = new JPanel();
		willPanel2.add(new JLabel("Misc"));
		willPanel2.add(willMiscSave);
		willPanel2.setOpaque(false);
		doLayout(willPanel2, 2, ypos);

		ypos++;

		JPanel fortPanel3 = new JPanel();
		fortPanel3.add(new JLabel("Misc Notes"));
		fortPanel3.add(fortMiscSaveText);
		fortPanel3.setOpaque(false);
		doLayout(fortPanel3, 0, ypos);

		JPanel refPanel3 = new JPanel();
		refPanel3.add(new JLabel("Misc Notes"));
		refPanel3.add(refMiscSaveText);
		refPanel3.setOpaque(false);
		doLayout(refPanel3, 1, ypos);

		JPanel willPanel3 = new JPanel();
		willPanel3.add(new JLabel("Misc Notes"));
		willPanel3.add(willMiscSaveText);
		willPanel3.setOpaque(false);
		doLayout(willPanel3, 2, ypos);

		ypos++;

		doLayout(fortScroll, 0, ypos);
		doLayout(refScroll, 1, ypos);
		doLayout(willScroll, 2, ypos);

	}

	public void updateCharacter() {
		Calculation wc = owner.getChar().calcWillSave();
		Calculation fc = owner.getChar().calcFortitudeSave();
		Calculation rc = owner.getChar().calcReflexSave();

		willMiscSave.setText("" + owner.getChar().getSaves().getMiscWill());
		willMiscSaveText.setText(owner.getChar().getSaves().getMiscWillText());

		totalWillSave.setText(wc.getDisplayValue());

		fortMiscSave.setText("" + owner.getChar().getSaves().getMiscFort());
		fortMiscSaveText.setText(owner.getChar().getSaves().getMiscFortText());

		totalFortSave.setText(fc.getDisplayValue());

		refMiscSave.setText("" + owner.getChar().getSaves().getMiscRef());
		refMiscSaveText.setText(owner.getChar().getSaves().getMiscRefText());

		totalRefSave.setText(rc.getDisplayValue());
		willCalc.setListData(wc.getListElements());
		fortCalc.setListData(fc.getListElements());
		refCalc.setListData(rc.getListElements());
	}

	private void doLayout(Component comp, int x, int y) {
		cn.gridx = x;
		cn.gridy = y;
		cn.gridwidth = 1;
		cn.gridheight = 1;
		gridbag.setConstraints(comp, cn);
		add(comp);
	}

	@SuppressWarnings("unused")
	private void doLayout(Component comp, int x, int y, int width, int height) {
		cn.gridx = x;
		cn.gridy = y;
		cn.gridwidth = width;
		cn.gridheight = height;

		gridbag.setConstraints(comp, cn);
		add(comp);
	}

	public void updateAbility() {
		updateCharacter();
	}

	public void focusGained(FocusEvent e) {
	}

	public void focusLost(FocusEvent e) {
		try {
			if (e.getSource() == willMiscSave) {
				owner.getChar().getSaves().setMiscWill(
						Integer.parseInt(willMiscSave.getText()));
				updateCharacter();
			} else if (e.getSource() == willMiscSaveText) {
				owner.getChar().getSaves().setMiscWillText(
						willMiscSaveText.getText());
			}

			else if (e.getSource() == fortMiscSave) {
				owner.getChar().getSaves().setMiscFort(
						Integer.parseInt(fortMiscSave.getText()));
				updateCharacter();
			} else if (e.getSource() == fortMiscSaveText) {
				owner.getChar().getSaves().setMiscFortText(
						fortMiscSaveText.getText());
			} else if (e.getSource() == refMiscSave) {
				owner.getChar().getSaves().setMiscRef(
						Integer.parseInt(refMiscSave.getText()));
				updateCharacter();
			} else if (e.getSource() == refMiscSaveText) {
				owner.getChar().getSaves().setMiscRefText(
						refMiscSaveText.getText());
			}
		} catch (NumberFormatException nfe) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error",
					"You must enter a numbers in the misc save boxes, or leave them empty.");
		}
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

}
