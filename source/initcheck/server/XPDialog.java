package initcheck.server;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import initcheck.DCharacter;
import initcheck.InitServer;
import initcheck.PanelButton;
import initcheck.database.InitDBC;
import initcheck.graphics.TiledDialog;
import initcheck.graphics.TiledList;
import initcheck.graphics.TiledListItem;
import initcheck.utils.StrManip;
import util.Rounding;

public class XPDialog extends TiledDialog {

	private static final long serialVersionUID = 1L;

	private InitServer owner;

	private TiledList bonusList = new TiledList();

	private JTextField partyLevel = new JTextField(10);
	
	private JTextField adjustPercent = new JTextField(10);

	private PanelButton calculate = new PanelButton("Calculate");

	private PanelButton apply = new PanelButton("Send to Clients", 100);
	
	private PanelButton applyLocal = new PanelButton("Apply", 100);
	
	private PanelButton reset = new PanelButton("Reset");

	private Hashtable<String, String> bonusHash = new Hashtable<String, String>();

	private JScrollPane scroll = new JScrollPane(bonusList);

	private Double baseXPperPlayer = 0.0;
	
	public XPDialog(final InitServer owner) {

		super(owner.getFrame(), "XP Calculator", false);
		this.owner = owner;

		bonusList.setFont(new Font("Courier", Font.PLAIN, 12));

		JPanel levelPanel = new JPanel();
		levelPanel.add(new JLabel("Party Level"));
		levelPanel.add(partyLevel);
		partyLevel.setText("" + owner.getPartyLevel());
		levelPanel.add(new JLabel("Adjust %"));
		levelPanel.add(adjustPercent);
		
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(calculate);
		buttonPanel.add(apply);
		buttonPanel.add(applyLocal);
		buttonPanel.add(reset);
		
		buttonPanel.setOpaque(false);

		JPanel contents = new JPanel(new BorderLayout());
		contents.add(levelPanel, BorderLayout.NORTH);

		contents.add(scroll, BorderLayout.CENTER);

		calculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doCalc(false);
			}
		});

		
		apply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSend();
			}
		});
		
		applyLocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doUpdate();
			}
		});
		
		
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doReset();
			}
		});

		setMainWindow(contents);
		setButtonBar(buttonPanel);
		setSize(500, 400);

		setVisible(true);
	}

	private void doReset() {
		bonusHash = new Hashtable<String, String>();
		bonusList.setListData(new Vector<TiledListItem>());
		baseXPperPlayer = 0.0;
	}
	
	private void doSend(){
		int selection = JOptionPane.showConfirmDialog(null, "Broadcast to clients?", "Broadcast", JOptionPane.YES_NO_OPTION);
		if (selection == JOptionPane.YES_OPTION){
		Vector<DCharacter> v = owner.getEngine().getDB().getCharacters();
		ArrayList<String> s = new ArrayList<String>();
		for (DCharacter d:v){
			s.add(d.getName());
		}
		owner.broadcastXpList(s, bonusHash, ""+baseXPperPlayer.intValue());
	
		}
	}
	
	private void doUpdate(){
		int selection = JOptionPane.showConfirmDialog(null, "Apply to Database?", "Apply XP", JOptionPane.YES_NO_OPTION);
		if (selection == JOptionPane.YES_OPTION){
		Vector<DCharacter> v = owner.getEngine().getDB().getCharacters();
		
		for (DCharacter d:v){
			String s = bonusHash.get(d.getName());
			Double xp = baseXPperPlayer;
			if (!StrManip.isNullOrEmpty(s)){
				xp += Double.parseDouble(s);
			}
			owner.addPlayerXP(d.getName(), ""+xp.intValue());
			
		}
		
	
		}
		this.setVisible(false); 
	}
	
	
	
	private void doCalc(boolean write) {
		doReset();
		
		double baseXP = 0;
		Vector<Vector<Object>> hsData = owner.getHitSheetData();
		InitDBC db = new InitDBC();
		double pl = Double.parseDouble(partyLevel.getText());
		String pround = Rounding.toString(pl, 0);
		int numPlayers = owner.getEngine().getDB().getCharacters().size();
		double monsterLevel = 0;

		for (int i = 0; i < hsData.size(); i++) {
			// read in a row
			Vector<Object> entry = (Vector<Object>) hsData.get(i);

			// get the cr for the monster
			double cr = Double.parseDouble((String) entry.get(3));
			monsterLevel += cr;
			String cround = Rounding.toString(cr, 0);

			// if the round goes to 0, make it one, which is the
			// lowest listed CR in the table.
			if (cround.equals("0")) {
				cround = "1";
			}

			// and calculate the xp and bonus for the monster
			int xp = db.getXP(pround, cround);

			// if the cr was less than one, adjust the xp
			// accordingly.
			if (cr < 1) {
				xp *= cr;
			}
			Double bonus = xp * 0.1;
			int bonusInt = bonus.intValue();
			baseXP += xp;

			// get the player that killed it
			String playerName = (String) entry.get(4);

			// get the current bonus for the player
			Object playerBonus = bonusHash.get(playerName);
			if (playerBonus == null) {
				bonusHash.put(playerName, "" + bonusInt);
			} else {
				Double currBonus = Double.parseDouble((String) playerBonus);
				currBonus += bonusInt;
				bonusHash.put(playerName, "" + currBonus.intValue());
			}
		}

		Double pct = 0.0;
		if (!StrManip.isNullOrEmpty(adjustPercent.getText())){
			pct = Double.parseDouble(adjustPercent.getText());
			Double adjust = (pct*baseXP)/100;
			System.out.println("ADJUSTMENT IS "+adjust);
			baseXP = baseXP+adjust;
		}
		
		Vector<String> v = new Vector<String>();

	    baseXPperPlayer = baseXP / numPlayers;
		v.add("Average Monster CR : " + monsterLevel / hsData.size());
		v.add("Base XP : " + Rounding.toString(baseXP, 0));
		v.add("Per Player (" + numPlayers + "): "
				+ Rounding.toString(baseXPperPlayer, 0));
		v.add("------------------");
		Enumeration<String> keys = bonusHash.keys();
		Object plyr = null;
		while (keys.hasMoreElements()) {
			plyr = keys.nextElement();
			String bnus = bonusHash.get(plyr);
			double bnum = Double.parseDouble(bnus);
		
			Double adjust = (pct*bnum)/100;
			System.out.println("bnum "+bnum+" pct "+pct+" adj "+adjust);
			bnum = bnum+adjust;
			String s = (String) plyr + " " + Rounding.toString(bnum, 0);
			v.add(s);
			if (write){
				owner.addPlayerXP((String)plyr, Rounding.toString(bnum, 0));
			}
		}
		if (write){
			owner.addPartyXP((String)plyr, ""+baseXPperPlayer);
		}
		bonusList.setStrings(v);
	}
}
