package initcheck.character;

import initcheck.InitColor;
import initcheck.PanelButton;
import initcheck.character.chooser.DomainChooser;
import initcheck.database.Domain;
import initcheck.database.PlayerDomain;
import initcheck.database.PlayerDomainDAO;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PlayerDomainPanel extends GridPanel {

	private static final long serialVersionUID = 1L;

	private JList domainList = new JList();

	private JScrollPane domainScroll = new JScrollPane(domainList);

	private final PlayerStatDialog owner;

	private final PlayerClassFeaturePanel parent;
	
	private PanelButton addDomain = new PanelButton("Add");

	private PanelButton delDomain = new PanelButton("Rem");

	private DomainChooser domainName = new DomainChooser();

	private JPanel domainPanel = new JPanel();

	private JComboBox classChooser;

	private JLabel domainLabel = new JLabel("Domains Available ");

	boolean updateRequired = false;

	boolean error = false;
	
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

	public PlayerDomainPanel(final PlayerStatDialog owner, final PlayerClassFeaturePanel parent) {

		this.owner = owner;
		this.parent = parent;
		
		classChooser = new JComboBox(owner.getChar().getClassSet()
				.getClassVector());

		domainList.setListData(owner.getChar().getDomains());
		domainList.setVisibleRowCount(5);
		domainList.setCellRenderer(new GenericListCellRenderer());

		setBorder(BorderFactory.createEtchedBorder());

		doLayout(domainLabel, 0, ypos);

		ypos++;
		setWeightX(1.0);
		setWeightY(1.0);
		doLayout(domainScroll, 0, ypos);
		setWeightX(0);
		setWeightY(0);
		ypos++;

		domainPanel.add(new JLabel("Name"));
		domainPanel.add(domainName);
		domainPanel.add(classChooser);

		domainPanel.add(addDomain);
		domainPanel.add(delDomain);
		domainPanel.setOpaque(false);
		doLayout(domainPanel, 0, ypos);

		addDomain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addDomain();
			}
		});

		delDomain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeDomain();
			}
		});

		setDomainLabel();

	}

	private void setDomainLabel() {
		int avail = owner.getChar().domainsAvailable();
		if (avail > 0) {
			domainLabel.setForeground(InitColor.red);
			setUpdateRequired(true);
		} else {
			domainLabel.setForeground(Color.black);
			setUpdateRequired(false);
		}
		if (avail < 0){
			error = true;
		}else{
			error = false;
		}
		
		domainLabel.setText("Domains Available " + avail);
		parent.checkUpdatesRequired();
	}

	private void addDomain() {
		PlayerDomain f = new PlayerDomain();
		f.setPlayerId("" + owner.getChar().getID());
		f.setDomainId(((Domain) domainName.getSelectedItem()).getId());
		f.setClassId(((ClassSlot) classChooser.getSelectedItem())
				.getClassName().getId());
		PlayerDomainDAO pldb = new PlayerDomainDAO();
		pldb.addPlayerDomain(f);

		owner.getChar().setDomains(
				pldb.getPlayerDomains("" + owner.getChar().getID()));
		domainList.setListData(owner.getChar().getDomains());
		setDomainLabel();
	}

	private void removeDomain() {
		PlayerDomain pl = (PlayerDomain) domainList.getSelectedValue();
		PlayerDomainDAO pldb = new PlayerDomainDAO();
		pldb.deletePlayerDomain(pl);
		owner.getChar().setDomains(
				pldb.getPlayerDomains("" + owner.getChar().getID()));
		domainList.setListData(owner.getChar().getDomains());
		setDomainLabel();
	}

}
