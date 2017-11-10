package initcheck.character.library;

import initcheck.InitImage;
import initcheck.PanelButton;
import initcheck.database.WeaponSizeIncrease;
import initcheck.database.WeaponSizeIncreaseDAO;
import initcheck.graphics.TiledGridPanel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WeaponSizePanel extends TiledGridPanel{

	private static final long serialVersionUID = 1L;

	private CreateCampaignPanel owner;
	
	private JTextField d1m1 = new JTextField("3");
	private JTextField d2m1 = new JTextField("3");
	private JTextField d3m1 = new JTextField("3");
	private JTextField d4m1 = new JTextField("3");
	private JTextField d6m1 = new JTextField("3");
	private JTextField d42m1 = new JTextField("3");
	private JTextField d8m1 = new JTextField("3");
	private JTextField d10m1 = new JTextField("3");
	private JTextField d12m1 = new JTextField("3");
	private JTextField d20m1 = new JTextField("3");
	private JTextField d26m1 = new JTextField("3");
	private JTextField d28m1 = new JTextField("3");
	
	private JTextField d1m2 = new JTextField("3");
	private JTextField d2m2 = new JTextField("3");
	private JTextField d3m2 = new JTextField("3");
	private JTextField d4m2 = new JTextField("3");
	private JTextField d6m2 = new JTextField("3");
	private JTextField d42m2 = new JTextField("3");
	private JTextField d8m2 = new JTextField("3");
	private JTextField d10m2 = new JTextField("3");
	private JTextField d12m2 = new JTextField("3");
	private JTextField d20m2 = new JTextField("3");
	private JTextField d26m2 = new JTextField("3");
	private JTextField d28m2 = new JTextField("3");
	
	private JTextField d1m3 = new JTextField("3");
	private JTextField d2m3 = new JTextField("3");
	private JTextField d3m3 = new JTextField("3");
	private JTextField d4m3 = new JTextField("3");
	private JTextField d6m3 = new JTextField("3");
	private JTextField d42m3 = new JTextField("3");
	private JTextField d8m3 = new JTextField("3");
	private JTextField d10m3 = new JTextField("3");
	private JTextField d12m3 = new JTextField("3");
	private JTextField d20m3 = new JTextField("3");
	private JTextField d26m3 = new JTextField("3");
	private JTextField d28m3 = new JTextField("3");
	
	private JTextField d1m4 = new JTextField("3");
	private JTextField d2m4 = new JTextField("3");
	private JTextField d3m4 = new JTextField("3");
	private JTextField d4m4 = new JTextField("3");
	private JTextField d6m4 = new JTextField("3");
	private JTextField d42m4 = new JTextField("3");
	private JTextField d8m4 = new JTextField("3");
	private JTextField d10m4 = new JTextField("3");
	private JTextField d12m4 = new JTextField("3");
	private JTextField d20m4 = new JTextField("3");
	private JTextField d26m4 = new JTextField("3");
	private JTextField d28m4 = new JTextField("3");
	
	private JTextField d1m5 = new JTextField("3");
	private JTextField d2m5 = new JTextField("3");
	private JTextField d3m5 = new JTextField("3");
	private JTextField d4m5 = new JTextField("3");
	private JTextField d6m5 = new JTextField("3");
	private JTextField d42m5 = new JTextField("3");
	private JTextField d8m5 = new JTextField("3");
	private JTextField d10m5 = new JTextField("3");
	private JTextField d12m5 = new JTextField("3");
	private JTextField d20m5 = new JTextField("3");
	private JTextField d26m5 = new JTextField("3");
	private JTextField d28m5 = new JTextField("3");
	
	private JTextField d1m6 = new JTextField("3");
	private JTextField d2m6 = new JTextField("3");
	private JTextField d3m6 = new JTextField("3");
	private JTextField d4m6 = new JTextField("3");
	private JTextField d6m6 = new JTextField("3");
	private JTextField d42m6 = new JTextField("3");
	private JTextField d8m6 = new JTextField("3");
	private JTextField d10m6 = new JTextField("3");
	private JTextField d12m6 = new JTextField("3");
	private JTextField d20m6 = new JTextField("3");
	private JTextField d26m6 = new JTextField("3");
	private JTextField d28m6 = new JTextField("3");
	
	private JTextField d1p1 = new JTextField("3");
	private JTextField d2p1 = new JTextField("3");
	private JTextField d3p1 = new JTextField("3");
	private JTextField d4p1 = new JTextField("3");
	private JTextField d6p1 = new JTextField("3");
	private JTextField d42p1 = new JTextField("3");
	private JTextField d8p1 = new JTextField("3");
	private JTextField d10p1 = new JTextField("3");
	private JTextField d12p1 = new JTextField("3");
	private JTextField d20p1 = new JTextField("3");
	private JTextField d26p1 = new JTextField("3");
	private JTextField d28p1 = new JTextField("3");
	
	private JTextField d1p2 = new JTextField("3");
	private JTextField d2p2 = new JTextField("3");
	private JTextField d3p2 = new JTextField("3");
	private JTextField d4p2 = new JTextField("3");
	private JTextField d6p2 = new JTextField("3");
	private JTextField d42p2 = new JTextField("3");
	private JTextField d8p2 = new JTextField("3");
	private JTextField d10p2 = new JTextField("3");
	private JTextField d12p2 = new JTextField("3");
	private JTextField d20p2 = new JTextField("3");
	private JTextField d26p2 = new JTextField("3");
	private JTextField d28p2 = new JTextField("3");
	
	private JTextField d1p3 = new JTextField("3");
	private JTextField d2p3 = new JTextField("3");
	private JTextField d3p3 = new JTextField("3");
	private JTextField d4p3 = new JTextField("3");
	private JTextField d6p3 = new JTextField("3");
	private JTextField d42p3 = new JTextField("3");
	private JTextField d8p3 = new JTextField("3");
	private JTextField d10p3 = new JTextField("3");
	private JTextField d12p3 = new JTextField("3");
	private JTextField d20p3 = new JTextField("3");
	private JTextField d26p3 = new JTextField("3");
	private JTextField d28p3 = new JTextField("3");
	
	private JTextField d1p4 = new JTextField("3");
	private JTextField d2p4 = new JTextField("3");
	private JTextField d3p4 = new JTextField("3");
	private JTextField d4p4 = new JTextField("3");
	private JTextField d6p4 = new JTextField("3");
	private JTextField d42p4 = new JTextField("3");
	private JTextField d8p4 = new JTextField("3");
	private JTextField d10p4 = new JTextField("3");
	private JTextField d12p4 = new JTextField("3");
	private JTextField d20p4 = new JTextField("3");
	private JTextField d26p4 = new JTextField("3");
	private JTextField d28p4 = new JTextField("3");
	
	private JTextField d1p5 = new JTextField("3");
	private JTextField d2p5 = new JTextField("3");
	private JTextField d3p5 = new JTextField("3");
	private JTextField d4p5 = new JTextField("3");
	private JTextField d6p5 = new JTextField("3");
	private JTextField d42p5 = new JTextField("3");
	private JTextField d8p5 = new JTextField("3");
	private JTextField d10p5 = new JTextField("3");
	private JTextField d12p5 = new JTextField("3");
	private JTextField d20p5 = new JTextField("3");
	private JTextField d26p5 = new JTextField("3");
	private JTextField d28p5 = new JTextField("3");
	
	private JTextField d1p6 = new JTextField("3");
	private JTextField d2p6 = new JTextField("3");
	private JTextField d3p6 = new JTextField("3");
	private JTextField d4p6 = new JTextField("3");
	private JTextField d6p6 = new JTextField("3");
	private JTextField d42p6 = new JTextField("3");
	private JTextField d8p6 = new JTextField("3");
	private JTextField d10p6 = new JTextField("3");
	private JTextField d12p6 = new JTextField("3");
	private JTextField d20p6 = new JTextField("3");
	private JTextField d26p6 = new JTextField("3");
	private JTextField d28p6 = new JTextField("3");
	
	private boolean defaultVals = false;
	
	Vector<WeaponSizeIncrease> v = new Vector<WeaponSizeIncrease>();
	
	private PanelButton addButton = new PanelButton("Customize", 130);
	
	public boolean isDefaultVals() {
		return defaultVals;
	}

	public void setDefaultVals(boolean defaultVals) {
		this.defaultVals = defaultVals;
	}

	public WeaponSizePanel(CreateCampaignPanel owner){
		super(InitImage.lightRocks);
		this.owner = owner;
		init();
	}
	
	public void init(){
		
		doLayout(new JLabel("-6"), 0, ypos);
		doLayout(new JLabel("-5"), 1, ypos);
		doLayout(new JLabel("-4"), 2, ypos);
		doLayout(new JLabel("-3"), 3, ypos);
		doLayout(new JLabel("-2"), 4, ypos);
		doLayout(new JLabel("-1"), 5, ypos);
		doLayout(new JLabel("Size"), 6, ypos);
		doLayout(new JLabel("+1"), 7, ypos);
		doLayout(new JLabel("+2"), 8, ypos);
		doLayout(new JLabel("+3"), 9, ypos);
		doLayout(new JLabel("+4"), 10, ypos);
		doLayout(new JLabel("+5"), 11, ypos);
		doLayout(new JLabel("+6"), 12, ypos);
		incYPos();
		
		doLayout(d1m6, 0, ypos);
		doLayout(d1m5, 1, ypos);
		doLayout(d1m4, 2, ypos);
		doLayout(d1m3, 3, ypos);
		doLayout(d1m2, 4, ypos);
		doLayout(d1m1, 5, ypos);
		doLayout(new JLabel("d1"), 6, ypos);
		doLayout(d1p1, 7, ypos);
		doLayout(d1p2, 8, ypos);
		doLayout(d1p3, 9, ypos);
		doLayout(d1p4, 10, ypos);
		doLayout(d1p5, 11, ypos);
		doLayout(d1p6, 12, ypos);
		incYPos();
		
		doLayout(d2m6, 0, ypos);
		doLayout(d2m5, 1, ypos);
		doLayout(d2m4, 2, ypos);
		doLayout(d2m3, 3, ypos);
		doLayout(d2m2, 4, ypos);
		doLayout(d2m1, 5, ypos);
		doLayout(new JLabel("d2"), 6, ypos);
		doLayout(d2p1, 7, ypos);
		doLayout(d2p2, 8, ypos);
		doLayout(d2p3, 9, ypos);
		doLayout(d2p4, 10, ypos);
		doLayout(d2p5, 11, ypos);
		doLayout(d2p6, 12, ypos);
		incYPos();
		
		doLayout(d3m6, 0, ypos);
		doLayout(d3m5, 1, ypos);
		doLayout(d3m4, 2, ypos);
		doLayout(d3m3, 3, ypos);
		doLayout(d3m2, 4, ypos);
		doLayout(d3m1, 5, ypos);
		doLayout(new JLabel("d3"), 6, ypos);
		doLayout(d3p1, 7, ypos);
		doLayout(d3p2, 8, ypos);
		doLayout(d3p3, 9, ypos);
		doLayout(d3p4, 10, ypos);
		doLayout(d3p5, 11, ypos);
		doLayout(d3p6, 12, ypos);
		incYPos();
		
		doLayout(d4m6, 0, ypos);
		doLayout(d4m5, 1, ypos);
		doLayout(d4m4, 2, ypos);
		doLayout(d4m3, 3, ypos);
		doLayout(d4m2, 4, ypos);
		doLayout(d4m1, 5, ypos);
		doLayout(new JLabel("d4"), 6, ypos);
		doLayout(d4p1, 7, ypos);
		doLayout(d4p2, 8, ypos);
		doLayout(d4p3, 9, ypos);
		doLayout(d4p4, 10, ypos);
		doLayout(d4p5, 11, ypos);
		doLayout(d4p6, 12, ypos);
		incYPos();
		
		doLayout(d6m6, 0, ypos);
		doLayout(d6m5, 1, ypos);
		doLayout(d6m4, 2, ypos);
		doLayout(d6m3, 3, ypos);
		doLayout(d6m2, 4, ypos);
		doLayout(d6m1, 5, ypos);
		doLayout(new JLabel("d6"), 6, ypos);
		doLayout(d6p1, 7, ypos);
		doLayout(d6p2, 8, ypos);
		doLayout(d6p3, 9, ypos);
		doLayout(d6p4, 10, ypos);
		doLayout(d6p5, 11, ypos);
		doLayout(d6p6, 12, ypos);
		incYPos();
		
		doLayout(d42m6, 0, ypos);
		doLayout(d42m5, 1, ypos);
		doLayout(d42m4, 2, ypos);
		doLayout(d42m3, 3, ypos);
		doLayout(d42m2, 4, ypos);
		doLayout(d42m1, 5, ypos);
		doLayout(new JLabel("2d4"), 6, ypos);
		doLayout(d42p1, 7, ypos);
		doLayout(d42p2, 8, ypos);
		doLayout(d42p3, 9, ypos);
		doLayout(d42p4, 10, ypos);
		doLayout(d42p5, 11, ypos);
		doLayout(d42p6, 12, ypos);
		incYPos();
		
		doLayout(d8m6, 0, ypos);
		doLayout(d8m5, 1, ypos);
		doLayout(d8m4, 2, ypos);
		doLayout(d8m3, 3, ypos);
		doLayout(d8m2, 4, ypos);
		doLayout(d8m1, 5, ypos);
		doLayout(new JLabel("d8"), 6, ypos);
		doLayout(d8p1, 7, ypos);
		doLayout(d8p2, 8, ypos);
		doLayout(d8p3, 9, ypos);
		doLayout(d8p4, 10, ypos);
		doLayout(d8p5, 11, ypos);
		doLayout(d8p6, 12, ypos);
		incYPos();
		
		doLayout(d10m6, 0, ypos);
		doLayout(d10m5, 1, ypos);
		doLayout(d10m4, 2, ypos);
		doLayout(d10m3, 3, ypos);
		doLayout(d10m2, 4, ypos);
		doLayout(d10m1, 5, ypos);
		doLayout(new JLabel("d10"), 6, ypos);
		doLayout(d10p1, 7, ypos);
		doLayout(d10p2, 8, ypos);
		doLayout(d10p3, 9, ypos);
		doLayout(d10p4, 10, ypos);
		doLayout(d10p5, 11, ypos);
		doLayout(d10p6, 12, ypos);
		incYPos();
		
		doLayout(d12m6, 0, ypos);
		doLayout(d12m5, 1, ypos);
		doLayout(d12m4, 2, ypos);
		doLayout(d12m3, 3, ypos);
		doLayout(d12m2, 4, ypos);
		doLayout(d12m1, 5, ypos);
		doLayout(new JLabel("d12"), 6, ypos);
		doLayout(d12p1, 7, ypos);
		doLayout(d12p2, 8, ypos);
		doLayout(d12p3, 9, ypos);
		doLayout(d12p4, 10, ypos);
		doLayout(d12p5, 11, ypos);
		doLayout(d12p6, 12, ypos);
		incYPos();
		
		doLayout(d20m6, 0, ypos);
		doLayout(d20m5, 1, ypos);
		doLayout(d20m4, 2, ypos);
		doLayout(d20m3, 3, ypos);
		doLayout(d20m2, 4, ypos);
		doLayout(d20m1, 5, ypos);
		doLayout(new JLabel("d20"), 6, ypos);
		doLayout(d20p1, 7, ypos);
		doLayout(d20p2, 8, ypos);
		doLayout(d20p3, 9, ypos);
		doLayout(d20p4, 10, ypos);
		doLayout(d20p5, 11, ypos);
		doLayout(d20p6, 12, ypos);
		incYPos();
		
		doLayout(d26m6, 0, ypos);
		doLayout(d26m5, 1, ypos);
		doLayout(d26m4, 2, ypos);
		doLayout(d26m3, 3, ypos);
		doLayout(d26m2, 4, ypos);
		doLayout(d26m1, 5, ypos);
		doLayout(new JLabel("2d6"), 6, ypos);
		doLayout(d26p1, 7, ypos);
		doLayout(d26p2, 8, ypos);
		doLayout(d26p3, 9, ypos);
		doLayout(d26p4, 10, ypos);
		doLayout(d26p5, 11, ypos);
		doLayout(d26p6, 12, ypos);
		incYPos();
		
		doLayout(d28m6, 0, ypos);
		doLayout(d28m5, 1, ypos);
		doLayout(d28m4, 2, ypos);
		doLayout(d28m3, 3, ypos);
		doLayout(d28m2, 4, ypos);
		doLayout(d28m1, 5, ypos);
		doLayout(new JLabel("2d8"), 6, ypos);
		doLayout(d28p1, 7, ypos);
		doLayout(d28p2, 8, ypos);
		doLayout(d28p3, 9, ypos);
		doLayout(d28p4, 10, ypos);
		doLayout(d28p5, 11, ypos);
		doLayout(d28p6, 12, ypos);
		incYPos();
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setOpaque(false);
		buttonPanel.add(addButton);
		doLayout(buttonPanel, 0, ypos, 12, 1);
		
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		
		setCampaign();
	}
	
	public void setCampaign(){
		WeaponSizeIncreaseDAO db = new WeaponSizeIncreaseDAO();
		WeaponSizeIncrease ws = new WeaponSizeIncrease();
		ws.setcampaignid(owner.getCampaign().getId());
		v = db.selectWeaponSizeIncrease(ws);
		if (v.size() <= 0) {
			
			v = db.getDefaultWeaponSizeIncrease();
			defaultVals = true;
			addButton.setText("Customize");
		}else{
			addButton.setText("Save");
		}
		
		
	
		ws = v.get(0);
		d1m6.setText(ws.getminus6());
		d1m5.setText(ws.getminus5());
		d1m4.setText(ws.getminus4());
		d1m3.setText(ws.getminus3());
		d1m2.setText(ws.getminus2());
		d1m1.setText(ws.getminus1());
		d1p1.setText(ws.getplus1());
		d1p2.setText(ws.getplus2());
		d1p3.setText(ws.getplus3());
		d1p4.setText(ws.getplus4());
		d1p5.setText(ws.getplus5());
		d1p6.setText(ws.getplus6());
		
		ws = v.get(1);
		d2m6.setText(ws.getminus6());
		d2m5.setText(ws.getminus5());
		d2m4.setText(ws.getminus4());
		d2m3.setText(ws.getminus3());
		d2m2.setText(ws.getminus2());
		d2m1.setText(ws.getminus1());
		d2p1.setText(ws.getplus1());
		d2p2.setText(ws.getplus2());
		d2p3.setText(ws.getplus3());
		d2p4.setText(ws.getplus4());
		d2p5.setText(ws.getplus5());
		d2p6.setText(ws.getplus6());
		
		ws = v.get(2);
		d3m6.setText(ws.getminus6());
		d3m5.setText(ws.getminus5());
		d3m4.setText(ws.getminus4());
		d3m3.setText(ws.getminus3());
		d3m2.setText(ws.getminus2());
		d3m1.setText(ws.getminus1());
		d3p1.setText(ws.getplus1());
		d3p2.setText(ws.getplus2());
		d3p3.setText(ws.getplus3());
		d3p4.setText(ws.getplus4());
		d3p5.setText(ws.getplus5());
		d3p6.setText(ws.getplus6());
		
		ws = v.get(3);
		d4m6.setText(ws.getminus6());
		d4m5.setText(ws.getminus5());
		d4m4.setText(ws.getminus4());
		d4m3.setText(ws.getminus3());
		d4m2.setText(ws.getminus2());
		d4m1.setText(ws.getminus1());
		d4p1.setText(ws.getplus1());
		d4p2.setText(ws.getplus2());
		d4p3.setText(ws.getplus3());
		d4p4.setText(ws.getplus4());
		d4p5.setText(ws.getplus5());
		d4p6.setText(ws.getplus6());
		
		ws = v.get(4);
		d6m6.setText(ws.getminus6());
		d6m5.setText(ws.getminus5());
		d6m4.setText(ws.getminus4());
		d6m3.setText(ws.getminus3());
		d6m2.setText(ws.getminus2());
		d6m1.setText(ws.getminus1());
		d6p1.setText(ws.getplus1());
		d6p2.setText(ws.getplus2());
		d6p3.setText(ws.getplus3());
		d6p4.setText(ws.getplus4());
		d6p5.setText(ws.getplus5());
		d6p6.setText(ws.getplus6());
		
		ws = v.get(5);
		d42m6.setText(ws.getminus6());
		d42m5.setText(ws.getminus5());
		d42m4.setText(ws.getminus4());
		d42m3.setText(ws.getminus3());
		d42m2.setText(ws.getminus2());
		d42m1.setText(ws.getminus1());
		d42p1.setText(ws.getplus1());
		d42p2.setText(ws.getplus2());
		d42p3.setText(ws.getplus3());
		d42p4.setText(ws.getplus4());
		d42p5.setText(ws.getplus5());
		d42p6.setText(ws.getplus6());
		
		ws = v.get(6);
		d8m6.setText(ws.getminus6());
		d8m5.setText(ws.getminus5());
		d8m4.setText(ws.getminus4());
		d8m3.setText(ws.getminus3());
		d8m2.setText(ws.getminus2());
		d8m1.setText(ws.getminus1());
		d8p1.setText(ws.getplus1());
		d8p2.setText(ws.getplus2());
		d8p3.setText(ws.getplus3());
		d8p4.setText(ws.getplus4());
		d8p5.setText(ws.getplus5());
		d8p6.setText(ws.getplus6());
		
		ws = v.get(7);
		d10m6.setText(ws.getminus6());
		d10m5.setText(ws.getminus5());
		d10m4.setText(ws.getminus4());
		d10m3.setText(ws.getminus3());
		d10m2.setText(ws.getminus2());
		d10m1.setText(ws.getminus1());
		d10p1.setText(ws.getplus1());
		d10p2.setText(ws.getplus2());
		d10p3.setText(ws.getplus3());
		d10p4.setText(ws.getplus4());
		d10p5.setText(ws.getplus5());
		d10p6.setText(ws.getplus6());
		
		ws = v.get(8);
		d12m6.setText(ws.getminus6());
		d12m5.setText(ws.getminus5());
		d12m4.setText(ws.getminus4());
		d12m3.setText(ws.getminus3());
		d12m2.setText(ws.getminus2());
		d12m1.setText(ws.getminus1());
		d12p1.setText(ws.getplus1());
		d12p2.setText(ws.getplus2());
		d12p3.setText(ws.getplus3());
		d12p4.setText(ws.getplus4());
		d12p5.setText(ws.getplus5());
		d12p6.setText(ws.getplus6());
		
		ws = v.get(9);
		d20m6.setText(ws.getminus6());
		d20m5.setText(ws.getminus5());
		d20m4.setText(ws.getminus4());
		d20m3.setText(ws.getminus3());
		d20m2.setText(ws.getminus2());
		d20m1.setText(ws.getminus1());
		d20p1.setText(ws.getplus1());
		d20p2.setText(ws.getplus2());
		d20p3.setText(ws.getplus3());
		d20p4.setText(ws.getplus4());
		d20p5.setText(ws.getplus5());
		d20p6.setText(ws.getplus6());
		
		ws = v.get(10);
		d26m6.setText(ws.getminus6());
		d26m5.setText(ws.getminus5());
		d26m4.setText(ws.getminus4());
		d26m3.setText(ws.getminus3());
		d26m2.setText(ws.getminus2());
		d26m1.setText(ws.getminus1());
		d26p1.setText(ws.getplus1());
		d26p2.setText(ws.getplus2());
		d26p3.setText(ws.getplus3());
		d26p4.setText(ws.getplus4());
		d26p5.setText(ws.getplus5());
		d26p6.setText(ws.getplus6());
		
		ws = v.get(11);
		d28m6.setText(ws.getminus6());
		d28m5.setText(ws.getminus5());
		d28m4.setText(ws.getminus4());
		d28m3.setText(ws.getminus3());
		d28m2.setText(ws.getminus2());
		d28m1.setText(ws.getminus1());
		d28p1.setText(ws.getplus1());
		d28p2.setText(ws.getplus2());
		d28p3.setText(ws.getplus3());
		d28p4.setText(ws.getplus4());
		d28p5.setText(ws.getplus5());
		d28p6.setText(ws.getplus6());
	}
	
	
	public void save(){
		WeaponSizeIncreaseDAO db = new WeaponSizeIncreaseDAO();
		WeaponSizeIncrease ws = new WeaponSizeIncrease();
		String campaignId = owner.getCampaign().getId();
		ws.setcampaignid(campaignId);
		
		if (defaultVals){
			db.addCampaignCustomization(campaignId);
			v = db.selectWeaponSizeIncrease(ws);
			addButton.setText("Save");
		}
		
		
		ws = v.get(0);
		if (defaultVals){ws.setcampaignid(campaignId);}
		ws.setminus6(d1m6.getText());
		ws.setminus5(d1m5.getText());
		ws.setminus4(d1m4.getText());
		ws.setminus3(d1m3.getText());
		ws.setminus2(d1m2.getText());
		ws.setminus1(d1m1.getText());
		ws.setplus1(d1p1.getText());
		ws.setplus2(d1p2.getText());
		ws.setplus3(d1p3.getText());
		ws.setplus4(d1p4.getText());
		ws.setplus5(d1p5.getText());
		ws.setplus6(d1p6.getText());
		db.updateWeaponSizeIncrease(ws);
		
		
		ws = v.get(1);
		if (defaultVals){ws.setcampaignid(campaignId);}
		ws.setminus6(d2m6.getText());
		ws.setminus5(d2m5.getText());
		ws.setminus4(d2m4.getText());
		ws.setminus3(d2m3.getText());
		ws.setminus2(d2m2.getText());
		ws.setminus1(d2m1.getText());
		ws.setplus1(d2p1.getText());
		ws.setplus2(d2p2.getText());
		ws.setplus3(d2p3.getText());
		ws.setplus4(d2p4.getText());
		ws.setplus5(d2p5.getText());
		ws.setplus6(d2p6.getText());
		db.updateWeaponSizeIncrease(ws);
		
		ws = v.get(2);
		if (defaultVals){ws.setcampaignid(campaignId);}
		ws.setminus6(d3m6.getText());
		ws.setminus5(d3m5.getText());
		ws.setminus4(d3m4.getText());
		ws.setminus3(d3m3.getText());
		ws.setminus2(d3m2.getText());
		ws.setminus1(d3m1.getText());
		ws.setplus1(d3p1.getText());
		ws.setplus2(d3p2.getText());
		ws.setplus3(d3p3.getText());
		ws.setplus4(d3p4.getText());
		ws.setplus5(d3p5.getText());
		ws.setplus6(d3p6.getText());
		db.updateWeaponSizeIncrease(ws);
		
		ws = v.get(3);
		if (defaultVals){ws.setcampaignid(campaignId);}
		ws.setminus6(d4m6.getText());
		ws.setminus5(d4m5.getText());
		ws.setminus4(d4m4.getText());
		ws.setminus3(d4m3.getText());
		ws.setminus2(d4m2.getText());
		ws.setminus1(d4m1.getText());
		ws.setplus1(d4p1.getText());
		ws.setplus2(d4p2.getText());
		ws.setplus3(d4p3.getText());
		ws.setplus4(d4p4.getText());
		ws.setplus5(d4p5.getText());
		ws.setplus6(d4p6.getText());
		db.updateWeaponSizeIncrease(ws);
		
		ws = v.get(4);
		if (defaultVals){ws.setcampaignid(campaignId);}
		ws.setminus6(d6m6.getText());
		ws.setminus5(d6m5.getText());
		ws.setminus4(d6m4.getText());
		ws.setminus3(d6m3.getText());
		ws.setminus2(d6m2.getText());
		ws.setminus1(d6m1.getText());
		ws.setplus1(d6p1.getText());
		ws.setplus2(d6p2.getText());
		ws.setplus3(d6p3.getText());
		ws.setplus4(d6p4.getText());
		ws.setplus5(d6p5.getText());
		ws.setplus6(d6p6.getText());
		db.updateWeaponSizeIncrease(ws);
		
		ws = v.get(5);
		if (defaultVals){ws.setcampaignid(campaignId);}
		ws.setminus6(d42m6.getText());
		ws.setminus5(d42m5.getText());
		ws.setminus4(d42m4.getText());
		ws.setminus3(d42m3.getText());
		ws.setminus2(d42m2.getText());
		ws.setminus1(d42m1.getText());
		ws.setplus1(d42p1.getText());
		ws.setplus2(d42p2.getText());
		ws.setplus3(d42p3.getText());
		ws.setplus4(d42p4.getText());
		ws.setplus5(d42p5.getText());
		ws.setplus6(d42p6.getText());
		db.updateWeaponSizeIncrease(ws);
		
		ws = v.get(6);
		if (defaultVals){ws.setcampaignid(campaignId);}
		ws.setminus6(d8m6.getText());
		ws.setminus5(d8m5.getText());
		ws.setminus4(d8m4.getText());
		ws.setminus3(d8m3.getText());
		ws.setminus2(d8m2.getText());
		ws.setminus1(d8m1.getText());
		ws.setplus1(d8p1.getText());
		ws.setplus2(d8p2.getText());
		ws.setplus3(d8p3.getText());
		ws.setplus4(d8p4.getText());
		ws.setplus5(d8p5.getText());
		ws.setplus6(d8p6.getText());
		db.updateWeaponSizeIncrease(ws);
		
		ws = v.get(7);
		if (defaultVals){ws.setcampaignid(campaignId);}
		ws.setminus6(d10m6.getText());
		ws.setminus5(d10m5.getText());
		ws.setminus4(d10m4.getText());
		ws.setminus3(d10m3.getText());
		ws.setminus2(d10m2.getText());
		ws.setminus1(d10m1.getText());
		ws.setplus1(d10p1.getText());
		ws.setplus2(d10p2.getText());
		ws.setplus3(d10p3.getText());
		ws.setplus4(d10p4.getText());
		ws.setplus5(d10p5.getText());
		ws.setplus6(d10p6.getText());
		db.updateWeaponSizeIncrease(ws);
		
		ws = v.get(8);
		if (defaultVals){ws.setcampaignid(campaignId);}
		ws.setminus6(d12m6.getText());
		ws.setminus5(d12m5.getText());
		ws.setminus4(d12m4.getText());
		ws.setminus3(d12m3.getText());
		ws.setminus2(d12m2.getText());
		ws.setminus1(d12m1.getText());
		ws.setplus1(d12p1.getText());
		ws.setplus2(d12p2.getText());
		ws.setplus3(d12p3.getText());
		ws.setplus4(d12p4.getText());
		ws.setplus5(d12p5.getText());
		ws.setplus6(d12p6.getText());
		db.updateWeaponSizeIncrease(ws);
		
		ws = v.get(9);
		if (defaultVals){ws.setcampaignid(campaignId);}
		ws.setminus6(d20m6.getText());
		ws.setminus5(d20m5.getText());
		ws.setminus4(d20m4.getText());
		ws.setminus3(d20m3.getText());
		ws.setminus2(d20m2.getText());
		ws.setminus1(d20m1.getText());
		ws.setplus1(d20p1.getText());
		ws.setplus2(d20p2.getText());
		ws.setplus3(d20p3.getText());
		ws.setplus4(d20p4.getText());
		ws.setplus5(d20p5.getText());
		ws.setplus6(d20p6.getText());
		db.updateWeaponSizeIncrease(ws);
		
		ws = v.get(10);
		if (defaultVals){ws.setcampaignid(campaignId);}
		ws.setminus6(d26m6.getText());
		ws.setminus5(d26m5.getText());
		ws.setminus4(d26m4.getText());
		ws.setminus3(d26m3.getText());
		ws.setminus2(d26m2.getText());
		ws.setminus1(d26m1.getText());
		ws.setplus1(d26p1.getText());
		ws.setplus2(d26p2.getText());
		ws.setplus3(d26p3.getText());
		ws.setplus4(d26p4.getText());
		ws.setplus5(d26p5.getText());
		ws.setplus6(d26p6.getText());
		db.updateWeaponSizeIncrease(ws);
		
		ws = v.get(11);
		if (defaultVals){ws.setcampaignid(campaignId);}
		ws.setminus6(d28m6.getText());
		ws.setminus5(d28m5.getText());
		ws.setminus4(d28m4.getText());
		ws.setminus3(d28m3.getText());
		ws.setminus2(d28m2.getText());
		ws.setminus1(d28m1.getText());
		ws.setplus1(d28p1.getText());
		ws.setplus2(d28p2.getText());
		ws.setplus3(d28p3.getText());
		ws.setplus4(d28p4.getText());
		ws.setplus5(d28p5.getText());
		ws.setplus6(d28p6.getText());
		db.updateWeaponSizeIncrease(ws);
	}
}
