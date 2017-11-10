package initcheck.character.printsheets;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public abstract class PrintPage extends JComponent implements Printable{
	
	private static final long serialVersionUID = 6958182918397894750L;
		ImageIcon icon;
		Image img;
		int minx = 20;
		int maxx = 568;
		int miny = 20;
		int maxy = 748;

		public void updateImage(){
				paintImage();
		}

		public void update(Graphics ig){
				repaint();
		}

		public void repaint() {
				paintComponent(this.getGraphics());
		}

		// we need to override the default method to specify the preferred size, 
		// or the scrollbars will not work correctly.
		public Dimension getPreferredSize() {
			
				return new Dimension(minx+maxx+20,miny+maxy+20);
		}

		public final void paintComponent(Graphics ig) {
				if (img == null){
						
						img = createImage(maxx+20, maxy);
				}
				if (img != null && icon == null){
						
						icon = new ImageIcon(img);
						paintImage();
				}
				if (icon != null){
					icon.paintIcon(this, ig, 0, 0);
				}
		}
		
		public final void paintImage(){
				paintImage(null);
		}

		public abstract void paintImage(Graphics ig);

		public int print(Graphics g, PageFormat format, int pageIndex) {
				paintImage(g);
				return Printable.PAGE_EXISTS;
		}

		public void printSheet(){

				/* Get the representation of the current printer and 
				 * the current print job.
				 */
				PrinterJob printerJob = PrinterJob.getPrinterJob();
				/* Build a book containing pairs of page painters (Printables)
				 * and PageFormats. This example has a single page containing
				 * text.
				 */
				Book book = new Book();
				PageFormat pf = new PageFormat();
				Paper paper = new Paper();
				paper.setImageableArea(20, 20, 568, 748); 
				pf.setPaper(paper);
				book.append(this, pf);
				/* Set the object to be printed (the Book) into the PrinterJob.
				 * Doing this before bringing up the print dialog allows the
				 * print dialog to correctly display the page range to be printed
				 * and to dissallow any print settings not appropriate for the
				 * pages to be printed.
				 */
				printerJob.setPageable(book);
				/* Show the print dialog to the user. This is an optional step
				 * and need not be done if the application wants to perform
				 * 'quiet' printing. If the user cancels the print dialog then false
				 * is returned. If true is returned we go ahead and print.
				 */
				boolean doPrint = printerJob.printDialog();
				if (doPrint) {
						try {
								printerJob.print();
						} catch (PrinterException exception) {
								System.err.println("Printing error: " + exception);
						}
				}
				
		}

}
