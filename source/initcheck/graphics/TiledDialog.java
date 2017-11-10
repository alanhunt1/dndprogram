package initcheck.graphics;


import initcheck.InitImage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class TiledDialog extends JDialog {

		private static final long serialVersionUID = 1L;
	
		protected ImageIcon bgImage = InitImage.steel;
		private TiledPanel outerPanel;
		private JPanel bufferPanel;
		private boolean opaque = true;
		
		public TiledDialog(){
				super();
				layoutDialog();
		}
		
		public TiledDialog(JDialog d, String title, boolean modal){
				super(d, title, modal);
				layoutDialog();
		}
		
		public TiledDialog(Frame f, String title, boolean modal){
				super(f, title, modal);
				layoutDialog();
		}
		
		public TiledDialog(Frame f, String title, boolean modal, boolean opaque){
				super(f, title, modal);
				this.opaque = opaque;
				layoutDialog();
		}

		public void setBorderSize(int i){
			bufferPanel.setBorder(BorderFactory.createEmptyBorder(
					i, //top
					i, //left
					i, //bottom
					i) //right
			);
		}
		
		private void layoutDialog(){
				// create the border, which contains the tiled graphic
				outerPanel = new TiledPanel(bgImage);
				outerPanel.setLayout(new BorderLayout());

				// create the buffer panel, which makes sure that there is a 
				// spacer around the main contents
				bufferPanel = new JPanel(new BorderLayout());
				bufferPanel.setOpaque(opaque);
				bufferPanel.setBorder(BorderFactory.createEmptyBorder(
																															5, //top
																															5, //left
																															5, //bottom
																															5) //right
															);

			
				
				
				// create the outer panel margins, which controls how much 
				// tiled graphic shows up.
				outerPanel.setBorder(BorderFactory.createEmptyBorder(
																														 30, //top
																														 30, //left
																														 30, //bottom
																														 30) //right
														 );

				// build the dialog
				getContentPane().add(outerPanel, BorderLayout.CENTER);

		}
		public void forceRepaint(){
				outerPanel.forceRepaint();
		}
		public void setMainWindow(Component c){
				bufferPanel.add(c, BorderLayout.CENTER);
				outerPanel.add(bufferPanel, BorderLayout.CENTER);
		}
		
		public void setButtonBar(Component c){
				outerPanel.add(c, BorderLayout.SOUTH);
		}

}
