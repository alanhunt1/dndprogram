package initcheck.server;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.StringTokenizer;


/**
 * The PrintText application expands on the
 * PrintExample application in that it images
 * text on to the single page printed.
 */
public class InitPrinter implements Printable {
		
		private String sArray[];
		private int numElements = 0;

		public InitPrinter(String s){
			
				StringTokenizer split = new StringTokenizer(s, "\n");
				numElements = split.countTokens();
				sArray = new String[numElements];
				int count = 0;
				while (split.hasMoreTokens()){
						sArray[count] = split.nextToken();
						count++;
				}
				
		}
		
		/**
		 * Print a page of text.
		 */
		public int print(Graphics g, PageFormat format, int pageIndex) {
				/* We'll assume that Jav2D is available.
				 */
				Graphics2D g2d = (Graphics2D) g;

				g2d.setFont(new Font("Courier", Font.PLAIN, 12));			

				/* Move the origin from the corner of the Paper to the corner
				 * of the imageable area.
				 */
				g2d.translate(format.getImageableX(), format.getImageableY());
				/* Set the text color.
				 */
				g2d.setPaint(Color.black);
				
				// set the initial coords - set y to 20 or it gets clipped.
				int x = 0; int y = 20;

				// print the header
				String header = "Character                               Init Mod";
				String header2= "---------                               --------";
				g2d.drawString(header, x, y);
				y+=15;
				g2d.drawString(header2, x, y);
				y+=15;

				// print the text
				for(int i = 0; i < numElements; i++){
						g2d.drawString(sArray[i], x, y);
						y += 15;
				}
				return Printable.PAGE_EXISTS;
		}
}
