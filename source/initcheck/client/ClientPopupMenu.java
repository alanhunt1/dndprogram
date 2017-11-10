package initcheck.client;

import initcheck.InitClient;
import initcheck.InitLogger;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class ClientPopupMenu extends JPopupMenu implements ActionListener {

	static final long serialVersionUID = 1;

	// the popup menu
	private InitLogger logger = new InitLogger(this);
	
	private JMenuItem stun;

	private JMenuItem unstun;

	private JMenuItem kill;

	private JMenuItem revive;

	

	private InitClient owner;

	public ClientPopupMenu(InitClient owner) {

		super();

		this.owner = owner;

		stun = new JMenuItem("Stun");
		stun.addActionListener(this);
		add(stun);

		unstun = new JMenuItem("Un-Stun");
		unstun.addActionListener(this);
		add(unstun);

		kill = new JMenuItem("Kill");
		kill.addActionListener(this);
		add(kill);

		revive = new JMenuItem("Revive");
		revive.addActionListener(this);
		add(revive);

	
	}

	public void setFont(Font f) {

		
			stun.setFont(f);
			unstun.setFont(f);
			kill.setFont(f);
			revive.setFont(f);

		
			Graphics2D g = (Graphics2D) owner.getGraphics();
			if (g == null) {
				logger.log("error","NULL GRAPHICS");
			}

			Rectangle2D r = f.getStringBounds("Level Up", g
					.getFontRenderContext());
			

			// set the popup to be as tall as the menu entries (the number of
			// entries is
			// the last multiplier at the end of the equation). Add an extra 20
			// pixels
			// of width for a margin.
			setPopupSize(new Double(r.getWidth()).intValue() + 20, new Double(r
					.getHeight()).intValue() * 5);

		

	}

	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());

		if (source == stun) {
			owner.sendServerStun();
		}  else if (source == unstun) {
			owner.sendServerUnStun();
		} else if (source == kill) {
			owner.sendServerKill();
		} else if (source == revive) {
			owner.sendServerRevive();
		}

	}
}
