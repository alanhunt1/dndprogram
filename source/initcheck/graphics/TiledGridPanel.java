package initcheck.graphics;

import initcheck.InitImage;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class TiledGridPanel extends TiledPanel {

	private static final long serialVersionUID = 1L;

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints cn = new GridBagConstraints();

	public int ypos;

	public TiledGridPanel(boolean useBorder) {
		super(InitImage.steel);
		setLayout(gridbag);
		cn.fill = GridBagConstraints.BOTH;
		cn.ipadx = 10;
		cn.ipady = 10;
		if (useBorder){
			setBorder(BorderFactory.createEtchedBorder());
		}
		ypos = 0;

	}

	public void setInsets(int inset){
		cn.insets = new Insets(inset,inset,inset,inset);

	}
	
	public TiledGridPanel() {
		super(InitImage.steel);
		setLayout(gridbag);
		cn.fill = GridBagConstraints.BOTH;
		cn.ipadx = 10;
		cn.ipady = 10;

		setBorder(BorderFactory.createEtchedBorder());
		ypos = 0;

	}

	public TiledGridPanel(String graphic) {
		super(graphic);
		setLayout(gridbag);
		cn.fill = GridBagConstraints.BOTH;
		cn.ipadx = 10;
		cn.ipady = 10;

		setBorder(BorderFactory.createEtchedBorder());
		ypos = 0;

	}

	public TiledGridPanel(ImageIcon graphic) {
		super(graphic);
		setLayout(gridbag);
		cn.fill = GridBagConstraints.BOTH;
		cn.ipadx = 10;
		cn.ipady = 10;

		setBorder(BorderFactory.createEtchedBorder());
		ypos = 0;

	}

	public void setPadX(int i) {
		cn.ipadx = i;
	}

	public void setPadY(int i) {
		cn.ipady = i;
	}

	public void incYPos() {
		ypos++;
	}

	public void setWeightX(double x) {
		cn.weightx = x;
	}

	public void setWeightY(double y) {
		cn.weighty = y;
	}

	public void doLayout(Component comp) {
		doLayout(comp, 0, ypos);
	}

	public void doLayout(Component comp, int x) {
		doLayout(comp, x, ypos);
	}

	public void doLayout(Component comp, int x, int y) {
		cn.gridx = x;
		cn.gridy = y;
		cn.gridwidth = 1;
		cn.gridheight = 1;
		gridbag.setConstraints(comp, cn);
		add(comp);
	}

	public void doLayout(Component comp, int x, int width, int height) {
		doLayout(comp, x, ypos, width, height);
	}

	public void doLayout(Component comp, int x, int y, int width, int height) {
		cn.gridx = x;
		cn.gridy = y;
		cn.gridwidth = width;
		cn.gridheight = height;

		gridbag.setConstraints(comp, cn);
		add(comp);
	}

}
