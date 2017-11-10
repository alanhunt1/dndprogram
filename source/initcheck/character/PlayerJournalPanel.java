package initcheck.character;

import initcheck.DCharacter;
import initcheck.PanelButton;
import initcheck.database.CampaignJournals;
import initcheck.database.CampaignJournalsDAO;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledTextArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PlayerJournalPanel extends TiledGridPanel implements StatusTab {

	private static final long serialVersionUID = 1L;

	private TiledTextArea notes = new TiledTextArea(20,80);
	
	private JScrollPane notesScroll = new JScrollPane(notes);
	
	//private final PlayerStatDialog owner;

	private PanelButton saveJournal = new PanelButton("Save");

	CampaignJournals cj = new CampaignJournals();
	
	boolean updateRequired = false;

	private boolean error = false;
	
	public boolean isError() {
		return error;
	}

	DCharacter dc;

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



	public PlayerJournalPanel(final PlayerStatDialog owner, DCharacter dc) {
		super("images/rockLighter.jpg");
		this.dc = dc;

		CampaignJournalsDAO pjdb = new CampaignJournalsDAO();
		
		
		cj = pjdb.getCampaignJournals(""+dc.getID());
		notes.setText(cj.getJournalText());

		setBorder(BorderFactory.createEtchedBorder());
		int ypos = 0;
		setWeightX(1);
		setWeightY(1);
		doLayout(notesScroll, 0, ypos);
		setWeightX(0);
		setWeightY(0);
		ypos++;
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.add(saveJournal);

		
		doLayout(buttonPanel, 0, ypos);

		saveJournal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveJournal();
			}
		});

	}

	
	private void saveJournal(){
		CampaignJournalsDAO pjdb = new CampaignJournalsDAO();
		cj.setCampaignId(dc.getCampaign().getId());
		cj.setPartyId(dc.getPartyId());
		cj.setPlayerId(""+dc.getID());
		cj.setJournalText(notes.getText());
		cj.setJournalId(""+pjdb.addOrUpdateCampaignJournals(cj));
		
	}
	
	

}
