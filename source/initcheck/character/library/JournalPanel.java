package initcheck.character.library;

import initcheck.DCharacterListing;
import initcheck.InitImage;
import initcheck.PanelButton;
import initcheck.character.chooser.PartyChooser;
import initcheck.character.chooser.PlayerChooser;
import initcheck.database.CampaignJournals;
import initcheck.database.CampaignJournalsDAO;
import initcheck.database.Party;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledList;
import initcheck.graphics.TiledPanel;
import initcheck.graphics.TiledTextArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JournalPanel extends TiledGridPanel implements
ListSelectionListener {

	private static final long serialVersionUID = 1L;

	private TiledTextArea notes = new TiledTextArea(20,80);
	
	private JScrollPane notesScroll = new JScrollPane(notes);
	
	private PartyChooser party = new PartyChooser();
	
	private PlayerChooser player = new PlayerChooser();
	
	private TiledList journalList = new TiledList();
	
	private CreateCampaignPanel owner;
	
	private PanelButton addJournal = new PanelButton("Add");
	
	private PanelButton saveJournal = new PanelButton("Save");
	
	private CampaignJournals currentJournal = new CampaignJournals();
	
	
	
	public JournalPanel(CreateCampaignPanel owner) {
		super(InitImage.lightRocks);
		this.owner = owner;
		init();

	}
	
	public void init() {
	
		notes.setWrapStyleWord(true);
		notes.setLineWrap(true);
		notes.setText("");
		
		
		
		doLayout(journalList, 0, ypos);
		doLayout(notesScroll, 1, ypos);
		incYPos();
		
		
		journalList.addListSelectionListener(this);
		TiledPanel buttonPanel = new TiledPanel();
		buttonPanel.add(party);
		buttonPanel.add(player);
		buttonPanel.add(addJournal);
		buttonPanel.add(saveJournal);
		doLayout (buttonPanel, 0, ypos);
		
		addJournal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addJournal();
			}
		});
		
		saveJournal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveJournal();
			}
		});
		
		party.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePlayerList();
			}
		});
		
		setCampaign();
	}
	
	private void updatePlayerList(){
		if (party.getSelectedItem() != null){
			player.setParty(party.getSelectedItem().toString());
		}
		updateJournalList();
	}
	
	private void updateJournalList(){
		CampaignJournalsDAO csdb = new CampaignJournalsDAO();
		CampaignJournals cj = new CampaignJournals();
		cj.setCampaignId(owner.getCampaign().getId());
		try{
			cj.setPartyId(((Party)party.getSelectedItem()).getId());
		}catch(NullPointerException npe){
			
		}
		journalList.setListData(csdb.selectCampaignJournals(cj));
	}
	
	public void setCampaign(){
		
		CampaignJournalsDAO csdb = new CampaignJournalsDAO();
		CampaignJournals cj = new CampaignJournals();
		cj.setCampaignId(owner.getCampaign().getId());
		journalList.setListData(csdb.selectCampaignJournals(cj));
		party.setCampaign(owner.getCampaign().getId(), false);
		
	}
	
	public void addJournal(){
		CampaignJournalsDAO csdb = new CampaignJournalsDAO();
		CampaignJournals cj = new CampaignJournals();
		cj.setCampaignId(owner.getCampaign().getId());
		cj.setJournalText(notes.getText());
		cj.setPartyName(((Party)party.getSelectedItem()).getName());
		cj.setPartyId(((Party)party.getSelectedItem()).getId());
		cj.setPlayerName(((DCharacterListing)player.getSelectedItem()).getName());
		cj.setPlayerId(((DCharacterListing)player.getSelectedItem()).getId());
		csdb.addOrUpdateCampaignJournals(cj);
		updateJournalList();
	}

	public void saveJournal(){
		CampaignJournalsDAO csdb = new CampaignJournalsDAO();
		currentJournal.setJournalText(notes.getText());
		csdb.addOrUpdateCampaignJournals(currentJournal);
	}
	
	public void valueChanged(ListSelectionEvent e) {
		
		try {
		
			CampaignJournals f = (CampaignJournals) journalList.getSelectedValue();
			currentJournal = f;
			notes.setText(f.getJournalText());
			notes.setCaretPosition(0);
			journalList.ensureIndexIsVisible(journalList.getSelectedIndex());
			 
		} catch (Exception ex) {
		
		}
	}
	
}
