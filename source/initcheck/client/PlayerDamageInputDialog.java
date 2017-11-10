package initcheck.client;

import initcheck.PanelButton;
import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JTextField;

public class PlayerDamageInputDialog extends JDialog implements ActionListener {
	static final long serialVersionUID = 1;

	PlayerDamagePanel owner = null;

	TiledPanel contents = new TiledPanel();

	TiledPanel numWrapper = new TiledPanel("images/rockLighter.jpg");

	TiledPanel numPad = new TiledPanel("images/rockLighter.jpg");

	PanelButton num1 = new PanelButton("1", false);

	PanelButton num2 = new PanelButton("2", false);

	PanelButton num3 = new PanelButton("3", false);

	PanelButton num4 = new PanelButton("4", false);

	PanelButton num5 = new PanelButton("5", false);

	PanelButton num6 = new PanelButton("6", false);

	PanelButton num7 = new PanelButton("7", false);

	PanelButton num8 = new PanelButton("8", false);

	PanelButton num9 = new PanelButton("9", false);

	PanelButton num0 = new PanelButton("0", false);

	PanelButton clear = new PanelButton("C", false);

	JTextField number = new JTextField(15);

	PanelButton add = new PanelButton("Dmg", false);

	PanelButton sub = new PanelButton("Heal", false);

	PanelButton dex = new PanelButton("Dex", false);
	
	PanelButton con = new PanelButton("Con", false);
	
	PanelButton str = new PanelButton("Str", false);
	
	PanelButton wis = new PanelButton("Wis", false);
	
	PanelButton cha = new PanelButton("Cha", false);
	
	PanelButton intel = new PanelButton("Int", false);
	
	PanelButton neg = new PanelButton("Neg", false);
	
	public PlayerDamageInputDialog(PlayerDamagePanel owner, int xpos,
			int ypos, Font f) {
		super();
		this.owner = owner;
		init();
		number.setFont(f);
		num1.setFont(f);
		num2.setFont(f);
		num3.setFont(f);
		num4.setFont(f);
		num5.setFont(f);
		num6.setFont(f);
		num7.setFont(f);
		num8.setFont(f);
		num9.setFont(f);
		num0.setFont(f);
		clear.setFont(f);
		add.setFont(f);
		sub.setFont(f);
		str.setFont(f);
		dex.setFont(f);
		con.setFont(f);
		cha.setFont(f);
		wis.setFont(f);
		intel.setFont(f);
		neg.setFont(f);
		setLocationRelativeTo(null);
		//setLocation(xpos, ypos);
		pack();

	}

	private void init() {

		numPad.setLayout(new GridLayout(4, 3));
		numPad.add(num1);
		numPad.add(num2);
		numPad.add(num3);
		numPad.add(num4);
		numPad.add(num5);
		numPad.add(num6);
		numPad.add(num7);
		numPad.add(num8);
		numPad.add(num9);
		numPad.add(num0);
		numPad.add(clear);
		numPad.add(neg);
		
		num1.addActionListener(this);
		num2.addActionListener(this);
		num3.addActionListener(this);
		num4.addActionListener(this);
		num5.addActionListener(this);
		num6.addActionListener(this);
		num7.addActionListener(this);
		num8.addActionListener(this);
		num9.addActionListener(this);
		num0.addActionListener(this);
		clear.addActionListener(this);
		add.addActionListener(this);
		sub.addActionListener(this);
		str.addActionListener(this);
		dex.addActionListener(this);
		con.addActionListener(this);
		cha.addActionListener(this);
		wis.addActionListener(this);
		intel.addActionListener(this);
		neg.addActionListener(this);
		
		numWrapper.setLayout(new BorderLayout());
		numWrapper.add(numPad, BorderLayout.CENTER);
		numWrapper.add(number, BorderLayout.NORTH);

		TiledPanel addSub = new TiledPanel("images/rockLighter.jpg");
		addSub.setLayout(new GridLayout(3, 3));
		addSub.add(add);
		addSub.add(sub);
		addSub.add(dex);
		addSub.add(con);
		addSub.add(str);
		addSub.add(intel);
		addSub.add(cha);
		addSub.add(wis);
		
		numWrapper.add(addSub, BorderLayout.SOUTH);

		contents.setLayout(new BorderLayout());
		contents.add(numWrapper, BorderLayout.CENTER);

		setLocationRelativeTo(null);
		getContentPane().add(contents);
	}

	public void registerKey(String s) {
		number.setText(number.getText() + s);
	}

	public void actionPerformed(ActionEvent e) {
		PanelButton source = (PanelButton) (e.getSource());
		if (e.getSource() == clear) {
			number.setText("");
		} else if (e.getSource() == add) {
			owner.addDamage(Integer.parseInt(number.getText()));
			number.setText("");
			setVisible(false);
		} else if (e.getSource() == sub) {
			owner.subDamage(Integer.parseInt(number.getText()));
			number.setText("");
			setVisible(false);
		} else if (e.getSource() == str) {
			owner.modStat("STR", Integer.parseInt(number.getText()));
			number.setText("");
			setVisible(false);
		} else if (e.getSource() == dex) {
			owner.modStat("DEX", Integer.parseInt(number.getText()));
			number.setText("");
			setVisible(false);
		} else if (e.getSource() == con) {
			owner.modStat("CON", Integer.parseInt(number.getText()));
			number.setText("");
			setVisible(false);
		} else if (e.getSource() == cha) {
			owner.modStat("CHA", Integer.parseInt(number.getText()));
			number.setText("");
			setVisible(false);
		} else if (e.getSource() == wis) {
			owner.modStat("WIS", Integer.parseInt(number.getText()));
			number.setText("");
			setVisible(false);
		} else if (e.getSource() == intel) {
			owner.modStat("INT", Integer.parseInt(number.getText()));
			number.setText("");
			setVisible(false);
		}else if (e.getSource() == neg) {
			int num = 0;
			try{
				num = Integer.parseInt(number.getText());
				num = 0-num;
				number.setText(""+num);
			}catch (Exception ex){
				
			}
		}else {
			registerKey(source.getText());
		}
	}

}
