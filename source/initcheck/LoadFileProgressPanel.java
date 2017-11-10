package initcheck;

import initcheck.graphics.TiledDialog;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;


public class LoadFileProgressPanel extends TiledGridPanel {

	static final long serialVersionUID = 1;

	JProgressBar progressBar;

	JLabel progressLabel = new JLabel("Progress", SwingConstants.CENTER);

	TiledDialog parent;

	boolean first = true;

	public LoadFileProgressPanel(TiledDialog parent, int size, String label) {
		super("images/rockLighter.jpg");
		setOpaque(false);
		progressBar = new JProgressBar(1, size);
		progressLabel.setText(label);
		progressBar.setValue(0);
		progressBar.setForeground(InitColor.red);
		setWeightX(1.0);
		setWeightY(0.0);
		TiledPanel progressPanel = new TiledPanel("images/rockLighter.jpg");
		progressPanel.setLayout(new BorderLayout());
		progressPanel.add(progressLabel, BorderLayout.NORTH);
		progressPanel.add(progressBar, BorderLayout.CENTER);
		doLayout(progressPanel);
		this.parent = parent;

	}

	public Dimension getPreferredSize() {
		return new Dimension(400, 100);
	}

	public void setMaxValue(int i) {
		progressBar.setMaximum(i);
	}

	public void incValue() {
		setValue(progressBar.getValue() + 1);
	}

	public void incValue(int i) {
		setValue(progressBar.getValue() + i);
	}

	public void setValue(int i) {
		progressBar.setValue(i);
		paintImmediately(0, 0, new Double(getSize().getWidth()).intValue(),
				new Double(getSize().getHeight()).intValue());
		if (first) {
			parent.forceRepaint();
			first = false;
		}
	}

	public void signal(String s) {
		progressLabel.setText(s);
		paintImmediately(0, 0, new Double(getSize().getWidth()).intValue(),
				new Double(getSize().getHeight()).intValue());
		if (first) {
			parent.forceRepaint();
			first = false;
		}

	}

}
