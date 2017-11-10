package initcheck.server;

import initcheck.Encounter;
import initcheck.EncounterElement;
import initcheck.MonsterDialog;
import initcheck.MonsterDialogParent;
import initcheck.MonsterDisplayPanel;
import initcheck.MonsterGroup;
import initcheck.PanelButton;
import initcheck.database.Monster;
import initcheck.graphics.Skin;
import initcheck.graphics.TiledGridPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class EncounterTab extends JPanel implements ListSelectionListener,
		MonsterDialogParent,Serializable, CampaignNotesTab {

	private static final long serialVersionUID = 1L;

	private Vector<Encounter> encounters = new Vector<Encounter>();

	private JList encounterList = new JList();

	private JList monsterList = new JList();

	private JScrollPane listScroll = new JScrollPane(encounterList);

	private JScrollPane monsterScroll = new JScrollPane(monsterList);

	private PanelButton addEncounter = new PanelButton("Add");

	private PanelButton remEncounter = new PanelButton("Rem");

	private PanelButton runEncounter = new PanelButton("Run");

	private PanelButton editEncounter = new PanelButton("Edit");

	private TiledGridPanel listPanel = new TiledGridPanel();

	private CampaignNotesPanel owner;

	private MonsterDisplayPanel monsterDisplayPanel = new MonsterDisplayPanel();
	
	

	private Skin skin = null;

	public EncounterTab(CampaignNotesPanel owner) {

		this.owner = owner;

		setLayout(new BorderLayout());
		listPanel.setWeightX(0);
		listPanel.setWeightY(1);
		listPanel.doLayout(listScroll);
		listPanel.incYPos();
		listPanel.doLayout(monsterScroll);
		add(listPanel, BorderLayout.WEST);

		encounterList.addListSelectionListener(this);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addEncounter);
		buttonPanel.add(remEncounter);
		buttonPanel.add(runEncounter);
		buttonPanel.add(editEncounter);

		if (owner != null){
			add(buttonPanel, BorderLayout.SOUTH);
		}
		
		add(monsterDisplayPanel, BorderLayout.CENTER);
		addEncounter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEncounter();
			}
		});

		remEncounter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remEncounter();
			}
		});

		runEncounter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runCurrentEncounter();
			}
		});

		editEncounter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editEncounter();
			}
		});
		System.out.println("ADDING LISTENER");
		monsterList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				System.out.println("UPDATING IN MAIN");
				updateMonsterInfo();
			}
		});

	}

	public void updateMonsterInfo() {
		System.out.println("UPDATING MONSTER");
		
		if (monsterList.getSelectedIndex() >= 0) {
			EncounterElement e = ((EncounterElement) monsterList.getSelectedValue());
			
			
			if (e.getType() == null || e.getType().equalsIgnoreCase("MONSTER")){
				
				Monster m = ((MonsterGroup) monsterList.getSelectedValue()).getM();
				monsterDisplayPanel.setMonster(m);
			
			}else{
				System.out.println("IN BLANK AREA");
				/*
				DCharacter p = ((DCharacter) monsterList.getSelectedValue());
				
					
				
					
					PlayerStatDialog d = new PlayerStatDialog(owner.getOwner(), p, 0, PlayerStatDialog.VIEW_ONLY);
					d.setSkin(skin);
					d.pack();
					d.setLocationRelativeTo(null);
					d.setVisible(true);
					
				*/
			}
			
		}
	}

	public Object getModel() {
		return encounters;
	}

	@SuppressWarnings("unchecked")
	public void setModel(Object o) {
		encounters = (Vector<Encounter>) o;
		encounterList.setListData(encounters);
		if (encounters.size() > 0) {
			encounterList.setSelectedIndex(0);
		}
	}

	public void addEncounter() {
		MonsterDialog md = new MonsterDialog(this);
		md.pack();
		md.setVisible(true);
	}

	public void editEncounter() {
		MonsterDialog md = new MonsterDialog(this);
		md.setEncounter((Encounter) encounterList.getSelectedValue());
		md.pack();
		md.setVisible(true);
	}

	public void remEncounter() {
		int idx = encounterList.getSelectedIndex();
		if (idx >= 0) {
			encounters.remove(idx);
			encounterList.setListData(encounters);
			if (encounters.size() > 0) {
				encounterList.setSelectedIndex(0);
			}
			else{
				monsterList.setListData(new Vector<EncounterElement>());
			}
		}
		
	}

	public int getType() {
		return CampaignNotesPanel.ENCOUNTER_TAB;
	}

	public JFrame getFrame() {
		// TODO Auto-generated method stub
		return null;
	}

	public void runCurrentEncounter() {
		owner.runEncounter((Encounter) encounterList.getSelectedValue(), true);
	}

	public void runEncounter(Encounter e, boolean newEncounter) {
		String inputValue = JOptionPane
				.showInputDialog("What would you like to name this encounter?");
		e.setName(inputValue);
		encounters.add(e);
		encounterList.setListData(encounters);
		encounterList.setSelectedIndex(0);
	}

	public void saveEncounter(Encounter e) {
		int idx = encounterList.getSelectedIndex();
		if (idx >= 0) {
			monsterList.setListData(e.getAllElements());
			monsterList.setSelectedIndex(0);
			owner.saveAllTabs();
		}
	}
	
	public void setSelectedMonster(Monster m){
		Encounter e = (Encounter)encounterList.getSelectedValue();
		for (int i = 0; i < e.getMonsterGroups().size(); i++){
			if (e.getMonsterGroups().get(i).getM().getName().equals(m.getName())){
				monsterList.setSelectedIndex(i);
				break;
			}
		}
	}
	

	public void valueChanged(ListSelectionEvent e) {
		System.out.println("EncounterListFire");
		try {
			if (encounterList.getModel().getSize() > 0 
					&& encounterList.getSelectedValue() != null){
				monsterList.setListData(((Encounter) encounterList
					.getSelectedValue()).getAllElements());
			}
			if (monsterList.getModel().getSize() > 0){
				monsterList.setSelectedIndex(0);
			}
		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}

	public void setSkin(Skin s) {
		this.skin = s;
	}

	public Skin getSkin(){
		return skin;
	}
	
}
