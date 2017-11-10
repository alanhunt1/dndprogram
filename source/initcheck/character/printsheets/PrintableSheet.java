package initcheck.character.printsheets;

import initcheck.InitLogger;
import initcheck.database.VersionDAO;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JComponent;

import util.DateUtil;

public class PrintableSheet extends JComponent implements Printable {
	private InitLogger logger = new InitLogger(this);
	private static final long serialVersionUID = 1L;

	int minx = 20;

	int maxx = 568;

	int miny = 20;

	int maxy = 748;

	double scale = 1.0;

	protected boolean landscape = false;

	private static final Font sansItalic8 = new Font("SansSerif", Font.ITALIC,
			8);

	public void setLandscape() {
		maxx = 748;
		maxy = 568;
		landscape = true;
	}

	public int drawSizedString(Graphics2D g, String s, int x, int y, int width,
			int height, Font f) {

		return drawSizedString(g, s, x, y, width, height, f, "center");
	}

	public int drawSizedString(Graphics2D g, String s, Rectangle r, Font f,
			String justify) {

		int width = new Double(r.getWidth()).intValue();
		int height = (new Double(r.getHeight())).intValue();
		int x = new Double(r.getX()).intValue();
		int y = new Double(r.getY()).intValue();

		return drawSizedString(g, s, x, y, width, height, f, justify);

	}

	// this will try and adjust the font and positioning of a string to fit the
	// space it has available in the display box.
	public int drawSizedString(Graphics2D g, String s, int x, int y, int width,
			int height, Font f, String justify) {

		Font font = f;
		Rectangle2D fontRect = f.getStringBounds(s, g.getFontRenderContext());
		LineMetrics lm = font.getLineMetrics(s, g.getFontRenderContext());

		while (fontRect.getWidth() > width || fontRect.getHeight() > height) {
			font = getSmallerFont(font);
			fontRect = font.getStringBounds(s, g.getFontRenderContext());
			lm = font.getLineMetrics(s, g.getFontRenderContext());
		}
		if (lm != null) {

		} else {
			logger.log("error","NULL METRICS in drawSizedString");
		}

		int centerX = (width - new Double(fontRect.getWidth()).intValue()) / 2;
		int centerY = (height - new Double(fontRect.getHeight()).intValue()) / 2;
		int ascent = new Float(lm.getAscent()).intValue();
		g.setFont(font);
		if (justify.equals("center")) {
			// g.setColor(Color.blue);
			// g.draw(new Rectangle(x+centerX, y+centerY, new
			// Double(fontRect.getWidth()).intValue(), new
			// Float(lh).intValue()));
			// g.setColor(Color.red);
			// g.draw(new Rectangle(x+centerX, y+centerY, new
			// Double(fontRect.getWidth()).intValue(), new
			// Float(lm.getAscent()).intValue()));
			g.setColor(Color.black);
			g.drawString(s, x + centerX, y + centerY + ascent);
		} else if (justify.equals("right")) {
			g.drawString(s, x
					+ (width - new Double(fontRect.getWidth()).intValue()), y
					+ centerY + ascent);
		} else if (justify.equals("left")) {
			g.drawString(s, x, y + centerY + ascent);
		}
		return new Double(fontRect.getHeight()).intValue();
	}

	public void drawVersionString(Graphics2D g, int x, int y) {
		g.setColor(Color.blue);
		g.setFont(sansItalic8);
		VersionDAO vdb = new VersionDAO();
		String printDate = DateUtil.getDateString(new java.util.Date(),
				"MM/dd/yy");
		g.drawString("D&D 3rd Edition w/Group Mods (" + vdb.getVersion()
				+ ") Printed " + printDate, x, y + 10);

	}

	public String renderAsNumber(String s) {
		String r = "";
		for (int i = 1; i <= s.length(); i++) {
			r = s.charAt(s.length() - i) + r;
			if (i % 3 == 0) {
				r = "," + r;
			}
		}
		if (r.startsWith(",")) {
			r = r.substring(1, r.length());
		}
		return r;
	}

	public Font getSmallerFont(Font f) {
		return f.deriveFont(f.getSize2D() - 1);
	}

	public void paintImage(Graphics g) {

	}

	public double getScale() {
		return scale;
	}

	public int print(Graphics g, PageFormat format, int pageIndex) {
		paintImage(g);

		return Printable.PAGE_EXISTS;
	}

	public void printSheet() {

		/*
		 * Get the representation of the current printer and the current print
		 * job.
		 */
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		/*
		 * Build a book containing pairs of page painters (Printables) and
		 * PageFormats. This example has a single page containing text.
		 */
		Book book = new Book();
		PageFormat pf = new PageFormat();
		Paper paper = new Paper();

		paper.setImageableArea(20, 20, 568, 748);

		if (landscape) {
			pf.setOrientation(PageFormat.LANDSCAPE);
		}

		pf.setPaper(paper);
		book.append(this, pf);
		/*
		 * Set the object to be printed (the Book) into the PrinterJob. Doing
		 * this before bringing up the print dialog allows the print dialog to
		 * correctly display the page range to be printed and to dissallow any
		 * print settings not appropriate for the pages to be printed.
		 */
		printerJob.setPageable(book);
		/*
		 * Show the print dialog to the user. This is an optional step and need
		 * not be done if the application wants to perform 'quiet' printing. If
		 * the user cancels the print dialog then false is returned. If true is
		 * returned we go ahead and print.
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
