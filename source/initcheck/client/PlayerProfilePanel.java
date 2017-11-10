package initcheck.client;

import initcheck.InitColor;
import initcheck.InitFont;
import initcheck.InitImage;
import initcheck.Participant;
import initcheck.graphics.TiledGridPanel;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class PlayerProfilePanel extends TiledGridPanel {

	private static final long serialVersionUID = 1L;

	private JLabel iconLabel = new JLabel();
	
	private JLabel hpLabel = new JLabel();
	
	private JProgressBar hpBar = new JProgressBar();
	
	
	public PlayerProfilePanel(){
		super(InitImage.lightRocks);
		hpLabel.setFont(InitFont.courierBig);
		hpBar.setForeground(InitColor.green);
		hpBar.setBackground(InitColor.red);
		
		doLayout(iconLabel);
		incYPos();
		doLayout(hpLabel);
		incYPos();
		doLayout(hpBar);
	}
	
	public void setParticipant(Participant p){
		setImage(p.getPicture());
		
		hpBar.setMaximum(p.getHP());
		hpBar.setValue(p.getCurrentHitPoints());
		
		if(p.getPType().equals("PLAYER")){
			hpLabel.setText(""+p.getCurrentHitPoints());
		}
		else{
			if (p.getCurrentHitPoints() == p.getHP()){
				hpLabel.setText("Undamaged");
			}else if (p.getCurrentHitPoints() > p.getHP()*(0.75)){
				hpLabel.setText("Hit");
			}else if (p.getCurrentHitPoints() > p.getHP()*(0.50)){
				hpLabel.setText("Hurt");
			}else if (p.getCurrentHitPoints() > p.getHP()*(0.25)){
				hpLabel.setText("Bleeding");
			}else{
				hpLabel.setText("Kill Steal!");
			}
		}
	}
	
	public void setImage(String s) {
		if (!s.startsWith("images")){
			s = "images/"+s;
		}
		
		ImageIcon display = new ImageIcon(s);

		// display = new ImageIcon(picture);
		int gheight = display.getIconHeight();
		int gwidth = display.getIconWidth();

		// pull out the image
		Image img = display.getImage();

		// and scale the image to fit inside a reasonable bound
		int max = gheight;

		if (gwidth > max) {
			max = gwidth;
		}

		double ratio = new Double(max).doubleValue() / 250.0;

		if (ratio <= 0) {
			// ratio = 300 / max;
			// display.setImage(img.getScaledInstance(new Double(gwidth * ratio)
			// .intValue(), new Double(gheight * ratio).intValue(),
			// Image.SCALE_FAST));
		} else {

			display.setImage(img.getScaledInstance(new Double(gwidth / ratio)
					.intValue(), new Double(gheight / ratio).intValue(),
					Image.SCALE_FAST));
		}
		iconLabel.setIcon(display);
	}
}
