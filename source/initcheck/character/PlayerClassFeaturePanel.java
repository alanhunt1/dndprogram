package initcheck.character;

import initcheck.DCharacter;
import initcheck.graphics.TiledPanel;

import java.awt.GridLayout;

import javax.swing.BorderFactory;

public class PlayerClassFeaturePanel extends TiledPanel implements StatusTab {

	
	private static final long serialVersionUID = 1L;

	private PlayerDomainPanel domainPanel;

	private FavoredEnemyPanel enemyPanel;

	private SneakAttackPanel sneakPanel;

	private LayHandsPanel layHandsPanel;
	
	private TurnUndeadPanel turnUndeadPanel;
	
	boolean updateRequired = false;

	private boolean error = false;
	
	PlayerStatDialog owner;
	
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

	public void checkUpdatesRequired(){
		
		updateRequired = (domainPanel != null && domainPanel.isUpdateRequired())||
			(enemyPanel != null && enemyPanel.isUpdateRequired());
		error = (domainPanel != null &&domainPanel.isError()) ||
			(enemyPanel != null && enemyPanel.isError());
		owner.checkUpdatesRequired();
	}
	
	public PlayerClassFeaturePanel(final PlayerStatDialog owner, DCharacter dc) {

		super("images/rockLighter.jpg");
		
		this.owner = owner;
		
		domainPanel = new PlayerDomainPanel(owner, this);
		enemyPanel = new FavoredEnemyPanel(owner, this);
		sneakPanel = new SneakAttackPanel(owner);
		layHandsPanel = new LayHandsPanel(owner);
		turnUndeadPanel = new TurnUndeadPanel(owner);
		
		setBorder(BorderFactory.createEtchedBorder());
		setLayout(new GridLayout(2,3));
		
		if (owner.getChar().hasDomainAccess()) {
			add(domainPanel);
		}
		if (owner.getChar().hasFavoredEnemies()) {
			add(enemyPanel);
		}
		if (owner.getChar().hasSneakAttack()) {
			add(sneakPanel);
		}
		if (owner.getChar().canLayHands()){
			add(layHandsPanel);
		}
		if (owner.getChar().getTurnUndeadLevel() > 0){
			add(turnUndeadPanel);
		}
	}

}
