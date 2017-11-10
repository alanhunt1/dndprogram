package initcheck.server;

import initcheck.InitColor;
import initcheck.InitServer;
import initcheck.PanelButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class RoundCounterWidget extends JPanel {

	private static final long serialVersionUID = 1L;

	private PanelButton resetRound = new PanelButton("Reset Round", 70);

	private JLabel roundCounter;

	private int roundCount = 1;

	public RoundCounterWidget(final InitServer owner) {

		resetRound.setFont(new Font("Arial", Font.PLAIN, 10));
		resetRound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				owner.resetRound();
			}
		});

		roundCounter = new JLabel("" + roundCount, SwingConstants.CENTER);
		roundCounter.setFont(new Font("Arial", Font.BOLD, 40));
		roundCounter.setForeground(InitColor.red);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel roundNumberPanel = new JPanel(new BorderLayout());
		roundNumberPanel.add(roundCounter, BorderLayout.CENTER);
		roundNumberPanel.setBorder(BorderFactory
				.createBevelBorder(BevelBorder.LOWERED));

		roundNumberPanel.setOpaque(true);
		roundNumberPanel.setPreferredSize(new Dimension(70, 40));
		add(roundNumberPanel);

		JPanel resetButtonPanel = new JPanel();
		resetButtonPanel.setBorder(BorderFactory.createEmptyBorder());

		resetButtonPanel.add(resetRound);
		resetButtonPanel.setOpaque(false);
		add(resetButtonPanel);
		setOpaque(false);
		setBackground(Color.gray);
		setPreferredSize(new Dimension(70, 70));
	}

	public void incrementRound() {
		roundCount++;
		roundCounter.setText("" + roundCount);
	}

	public void resetRound() {
		roundCount = 1;
		roundCounter.setText("" + roundCount);
	}

	public int getRound() {
		return roundCount;
	}

	public void setRound(int i) {
		roundCount = i;
		roundCounter.setText("" + roundCount);
	}
}
