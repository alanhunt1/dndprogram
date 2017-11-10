package initcheck;

import initcheck.dungeon.MapLegend;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class InitTest {

		static JFrame frame;
		JPanel panel = new JPanel();

		public JComponent createComponents(){
				MapLegend ml = new MapLegend();
				panel.add(ml);
				return panel;
		}
		
		public static void main(String[] args) throws IOException {
				
				InitTest app = new InitTest();
				
				frame = new JFrame("InitTest");
				JComponent contents = app.createComponents();
				frame.getContentPane().add(contents, BorderLayout.CENTER);
				
				//Finish setting up the frame, and show it.
				frame.addWindowListener(new WindowAdapter() {
								public void windowClosing(WindowEvent e) {
										System.exit(0);
								}
						});
				frame.pack();
				frame.setVisible(true);
				
		}
}
