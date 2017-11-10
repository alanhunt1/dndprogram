package initcheck.character;

import initcheck.InitColor;
import initcheck.graphics.TiledDialog;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

public class LoadPartyProgressPanel extends GridPanel {

	private static final long serialVersionUID = 1L;

	JProgressBar progressBar;

	JLabel progressLabel = new JLabel("Progress", SwingConstants.CENTER);

	TiledDialog parent;

	boolean first = true;

	public LoadPartyProgressPanel(int numPlayers, TiledDialog parent) {
		progressBar = new JProgressBar(1, numPlayers + 1);
		progressBar.setValue(0);
		progressBar.setForeground(InitColor.red);
		JPanel progressPanel = new JPanel(new BorderLayout());
		progressPanel.add(progressLabel, BorderLayout.NORTH);
		progressPanel.add(progressBar, BorderLayout.CENTER);
		doLayout(progressPanel);
		this.parent = parent;
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

	}

}
