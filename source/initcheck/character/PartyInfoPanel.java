package initcheck.character;

import initcheck.DCharacter;
import initcheck.PanelButton;
import initcheck.TranslucentGridPanel;
import initcheck.character.chooser.CampaignChooser;
import initcheck.character.chooser.ColorChooser;
import initcheck.character.chooser.PartyTypeChooser;
import initcheck.database.Campaign;
import initcheck.database.Party;
import initcheck.database.PartyDAO;
import initcheck.graphics.Skin;
import initcheck.graphics.TiledPanel;
import initcheck.graphics.TiledTextArea;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class PartyInfoPanel extends TiledPanel {

	
	private static final long serialVersionUID = 1L;

	private CampaignChooser campaignChooser = new CampaignChooser();
	
	private PartyTypeChooser partyType = new PartyTypeChooser();
	
	private ColorChooser partyColor = new ColorChooser();

	private JTextField partyLocation = new JTextField(15);
	
	private JTextField partyTime = new JTextField(15);
	
	private TiledTextArea partyNotes = new TiledTextArea(20, 20);

	JScrollPane noteScroll = new JScrollPane(partyNotes);
	
	private PanelButton saveButton = new PanelButton("Save");
	
	private PanelButton refreshButton = new PanelButton("Refresh");
	
	private Party info;

	private Vector<DCharacter> chars;

	private JLabel levelLabel = new JLabel("Average Level : ");

	private TranslucentGridPanel innerPanel;
	
	public PartyInfoPanel(String name, Vector<DCharacter> chars) {
		super("images/rockLighter.jpg");
		this.chars = chars;

		int level = 0;
		for (int i = 0; i < chars.size(); i++) {
			DCharacter curr = (DCharacter) chars.get(i);
			level += new Double(curr.getLevel()).intValue();
		}
		if (chars.size() > 0) {
			level = level / chars.size();
		}
		levelLabel.setText("Average Level : " + level);

		PartyDAO db = new PartyDAO();
		Party p = new Party();
		p.setPartyName(name);
		info = new Party();
		if (!name.equals("All Parties")) {
			Vector<Party> parties =  db.selectParty(p);
			if (parties.size() > 0){
				info = (Party)parties.get(0);
			}
		}

		innerPanel = new TranslucentGridPanel(false);
		innerPanel.setPadY(0);
		innerPanel.setOpaque(false);
		innerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		innerPanel.doLayout(new JLabel("Campaign"), 0, innerPanel.ypos);
		innerPanel.doLayout(campaignChooser, 1, innerPanel.ypos);
		innerPanel.incYPos();
		
		innerPanel.doLayout(new JLabel("Type"), 0, innerPanel.ypos);
		innerPanel.doLayout(partyType, 1, innerPanel.ypos);
		innerPanel.incYPos();
		
		innerPanel.doLayout(new JLabel("Color"), 0, innerPanel.ypos);
		innerPanel.doLayout(partyColor, 1, innerPanel.ypos);
		innerPanel.incYPos();

		innerPanel.doLayout(new JLabel("Location"), 0, innerPanel.ypos);
		innerPanel.doLayout(partyLocation, 1, innerPanel.ypos);
		innerPanel.incYPos();

		innerPanel.doLayout(new JLabel("Time"), 0, innerPanel.ypos);
		innerPanel.doLayout(partyTime, 1, innerPanel.ypos);
		innerPanel.incYPos();

		
		innerPanel.doLayout(levelLabel, 0, innerPanel.ypos, 2, 1);
		innerPanel.incYPos();

		innerPanel.doLayout(new JLabel("Notes"), 0, innerPanel.ypos, 2, 1);
		innerPanel.incYPos();

		innerPanel.setWeightX(1.0);
		innerPanel.setWeightY(1.0);
		innerPanel.doLayout(noteScroll, 0, innerPanel.ypos, 2, 1);
		innerPanel.setWeightX(0);
		innerPanel.setWeightY(0);
		
		innerPanel.incYPos();

		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.add(refreshButton);
		buttonPanel.add(saveButton);
		
		innerPanel.doLayout(buttonPanel, 0, innerPanel.ypos, 2, 1);

		if (info != null) {
			partyColor.setSelectedItem(info.getPartyColor());
			partyLocation.setText(info.getPartyLocation());
			partyTime.setText(info.getElapsedTime());
			partyNotes.setText(info.getPartyNotes());
			partyType.setSelectedItem(info.getPartyType());
			Campaign c = new Campaign();
			c.setId(info.getCampaignId());
			campaignChooser.setSelectedCampaign(c);
		}

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		
		
		add(innerPanel, BorderLayout.CENTER);
	}

	public void setSkin(Skin s){
		
		partyNotes.setPaintBackground(s.isTileTextAreas());
		this.setPaintBackground(s.isTilePanels());
		//innerPanel.setPaintBackground(s.isTilePanels());
		
	}
	
	public String getColor() {
		return (String) partyColor.getSelectedItem();
	}

	public void setChars(Vector<DCharacter> chars) {
		this.chars = chars;
		int level = 0;
		for (int i = 0; i < chars.size(); i++) {
			DCharacter curr = (DCharacter) chars.get(i);
			level += new Double(curr.getLevel()).intValue();
		}
		if (chars.size() > 0) {
			level = level / chars.size();
		}
		levelLabel.setText("Average Level : " + level);
	}

	public void setParty(String name) {
		if (!name.equals("All Parties")) {

			saveButton.setEnabled(true);
			PartyDAO db = new PartyDAO();
			Party p = new Party();
			p.setPartyName(name);
			info = db.selectParty(p).get(0);
			partyColor.setSelectedItem(info.getPartyColor());
			partyLocation.setText(info.getPartyLocation());
			partyTime.setText(info.getElapsedTime());
			partyNotes.setText(info.getPartyNotes());
			partyType.setSelectedItem(info.getPartyType());
		} else {

			saveButton.setEnabled(false);
		}
	}

	public void refresh(){
		setParty(info.getPartyName());
	}
	
	public void save() {
		info.setPartyColor((String) partyColor.getSelectedItem());
		info.setPartyLocation(partyLocation.getText());
		info.setElapsedTime(partyTime.getText());
		info.setPartyNotes(partyNotes.getText());
		info.setPartyType((String)partyType.getSelectedItem());
		info.setCampaignId(((Campaign)campaignChooser.getSelectedItem()).getId());
		PartyDAO db = new PartyDAO();
		db.updateParty(info);
	}

	/**
	 * Get the Chars value.
	 * 
	 * @return the Chars value.
	 */
	public Vector<DCharacter> getChars() {
		return chars;
	}

}
