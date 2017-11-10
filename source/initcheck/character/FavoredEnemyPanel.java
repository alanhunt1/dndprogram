package initcheck.character;

import initcheck.InitColor;
import initcheck.PanelButton;
import initcheck.database.PlayerFavoredEnemy;
import initcheck.database.PlayerFavoredEnemyDAO;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class FavoredEnemyPanel extends GridPanel {

	private static final long serialVersionUID = 1L;

	private JList enemyList = new JList();

	private JScrollPane enemyScroll = new JScrollPane(enemyList);

	private PanelButton addEnemy = new PanelButton("Add");

	private PanelButton delEnemy = new PanelButton("Rem");

	private final PlayerStatDialog owner;

	private JTextField enemyName = new JTextField(10);

	private JComboBox classChooser;

	private JPanel enemyPanel = new JPanel();

	boolean updateRequired = false;

	boolean error = false;
	
	final PlayerClassFeaturePanel parent;
	
	private JLabel favoredEnemiesLabel = new JLabel("Favored Enemies : ");
	
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

	public FavoredEnemyPanel(final PlayerStatDialog owner, final PlayerClassFeaturePanel parent) {
		this.owner = owner;
		this.parent = parent;
		enemyList.setListData(owner.getChar().getFavoredEnemies());

		classChooser = new JComboBox(owner.getChar().getClassSet()
				.getClassVector());
		doLayout(favoredEnemiesLabel, 0, ypos);
		ypos++;
		setWeightX(1.0);
		setWeightY(1.0);
		doLayout(enemyScroll, 0, ypos);
		setWeightX(0);
		setWeightY(0);

		enemyPanel.add(new JLabel("Name"));
		enemyPanel.add(enemyName);
		enemyPanel.add(classChooser);
		enemyPanel.add(addEnemy);
		enemyPanel.add(delEnemy);
		enemyPanel.setOpaque(false);
		ypos++;
		doLayout(enemyPanel, 0, ypos);

		addEnemy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEnemy();
			}
		});

		delEnemy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeEnemy();
			}
		});
		setDomainLabel();
	}

	private void setDomainLabel() {
		int avail = owner.getChar().getFavoredEnemiesRemaining();
		if (avail > 0) {
			favoredEnemiesLabel.setForeground(InitColor.red);
			setUpdateRequired(true);
		} else {
			favoredEnemiesLabel.setForeground(Color.black);
			setUpdateRequired(false);
		}
		if (avail < 0){
			error = true;
		}else{
			error = false;
		}
		
		favoredEnemiesLabel.setText("Favored Enemies : " + avail);
		parent.checkUpdatesRequired();
	}
	
	private void addEnemy() {
		PlayerFavoredEnemy f = new PlayerFavoredEnemy();
		f.setPlayerId("" + owner.getChar().getID());
		f.setFavoredEnemy(enemyName.getText());
		f.setClassId(((ClassSlot) classChooser.getSelectedItem())
				.getClassName().getId());
		PlayerFavoredEnemyDAO pldb = new PlayerFavoredEnemyDAO();
		pldb.addPlayerFavoredEnemy(f);

		owner.getChar().setFavoredEnemies(
				pldb.getFavoredEnemies("" + owner.getChar().getID()));
		enemyList.setListData(owner.getChar().getFavoredEnemies());
		setDomainLabel();
	}

	private void removeEnemy() {
		if (enemyList.getSelectedIndex() > 0){
		PlayerFavoredEnemy pl = (PlayerFavoredEnemy) enemyList
				.getSelectedValue();
		PlayerFavoredEnemyDAO pldb = new PlayerFavoredEnemyDAO();
		pldb.deletePlayerFavoredEnemy(pl);
		owner.getChar().setFavoredEnemies(
				pldb.getFavoredEnemies("" + owner.getChar().getID()));
		enemyList.setListData(owner.getChar().getFavoredEnemies());
		setDomainLabel();
		}
	}

}
