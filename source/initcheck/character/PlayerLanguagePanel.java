package initcheck.character;

import initcheck.DCharacter;
import initcheck.PanelButton;
import initcheck.database.PlayerLanguages;
import initcheck.database.PlayerLanguagesDAO;
import initcheck.graphics.TiledList;
import initcheck.graphics.TiledPanel;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class PlayerLanguagePanel extends TiledPanel implements StatusTab {

	private static final long serialVersionUID = 1L;

	private TiledList languageList = new TiledList();

	private JScrollPane languageScroll = new JScrollPane(languageList);

	private final PlayerStatDialog owner;

	private PanelButton addLanguage = new PanelButton("Add");

	private PanelButton delLanguage = new PanelButton("Rem");

	private JTextField languageName = new JTextField(15);

	private JPanel languagePanel = new JPanel();

	private JLabel remaining = new JLabel("Languages Available : ");

	private TiledList calcList = new TiledList();

	boolean updateRequired = false;

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

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints cn = new GridBagConstraints();

	public PlayerLanguagePanel(final PlayerStatDialog owner, DCharacter dc) {
		super("images/rockLighter.jpg");
		this.owner = owner;

		languageList.setVisibleRowCount(5);
		languageList.setListData(owner.getChar().getLanguages());
		languageList.setCellRenderer(new GenericListCellRenderer());

		Calculation calc = owner.getChar().getLanguagesRemaining();
		calcList.setListData(calc.getListElements());
		calcList.setCellRenderer(new GenericListCellRenderer());
		int lr = Integer.parseInt(calc.getDisplayValue());
		remaining.setText("Languages Available : " + lr);
		if (lr > 0) {
			updateRequired = true;
		}
		if (lr < 0){
			error = true;
		}
		setLayout(gridbag);
		cn.fill = GridBagConstraints.BOTH;
		cn.ipadx = 10;
		cn.ipady = 10;

		setBorder(BorderFactory.createEtchedBorder());
		int ypos = 0;
		doLayout(remaining, 0, ypos, 2, 1);
		ypos++;

		cn.weightx = 1.0;
		cn.weighty = 1.0;
		doLayout(languageScroll, 0, ypos);
		doLayout(calcList, 1, ypos);
		cn.weightx = 0;
		cn.weighty = 0;
		ypos++;

		languagePanel.add(new JLabel("Name"));
		languagePanel.add(languageName);

		languagePanel.add(addLanguage);
		languagePanel.add(delLanguage);
		languagePanel.setOpaque(false);
		doLayout(languagePanel, 0, ypos, 2, 1);

		addLanguage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addLanguage();
			}
		});

		delLanguage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeLanguage();
			}
		});

	}

	private void addLanguage() {
		PlayerLanguages f = new PlayerLanguages();
		f.setPlayerId("" + owner.getChar().getID());
		f.setLanguage(languageName.getText());

		PlayerLanguagesDAO pldb = new PlayerLanguagesDAO();
		pldb.addPlayerLanguages(f);

		owner.getChar().setLanguages(
				pldb.getPlayerLanguages("" + owner.getChar().getID()));
		languageList.setListData(owner.getChar().getLanguages());
		updateLabel();
	}

	private void removeLanguage() {
		PlayerLanguages pl = (PlayerLanguages) languageList.getSelectedValue();
		PlayerLanguagesDAO pldb = new PlayerLanguagesDAO();
		pldb.deletePlayerLanguages(pl);
		owner.getChar().setLanguages(
				pldb.getPlayerLanguages("" + owner.getChar().getID()));
		languageList.setListData(owner.getChar().getLanguages());
		updateLabel();
	}

	private void updateLabel() {
		Calculation calc = owner.getChar().getLanguagesRemaining();
		calcList.setListData(calc.getListElements());
		int lr = Integer.parseInt(owner.getChar().getLanguagesRemaining()
				.getDisplayValue());
		remaining.setText("Languages Available : " + lr);
		if (lr > 0) {
			updateRequired = true;
		} else {
			updateRequired = false;
		}
		if (lr < 0) {
			error = true;
		} else {
			error = false;
		}
		owner.checkUpdatesRequired();
	}

	private void doLayout(Component comp, int x, int y) {
		cn.gridx = x;
		cn.gridy = y;
		cn.gridwidth = 1;
		cn.gridheight = 1;
		gridbag.setConstraints(comp, cn);
		add(comp);
	}

	private void doLayout(Component comp, int x, int y, int width, int height) {
		cn.gridx = x;
		cn.gridy = y;
		cn.gridwidth = width;
		cn.gridheight = height;

		gridbag.setConstraints(comp, cn);
		add(comp);
	}

}
