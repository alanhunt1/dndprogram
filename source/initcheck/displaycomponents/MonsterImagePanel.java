package initcheck.displaycomponents;

import initcheck.MonsterDisplayPanel;
import initcheck.database.Monster;
import initcheck.graphics.TiledPanel;
import initcheck.images.ImageSelectionDialog;
import initcheck.images.ImageSelectionOwner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class MonsterImagePanel extends TiledPanel implements
		ImageSelectionOwner {

	private static final long serialVersionUID = 1L;

	private JButton image = new JButton();

	@SuppressWarnings("unused")
	private MonsterDisplayPanel owner;

	private ImageIcon display;

	private String imageStr;

	private JLabel pictureLabel = new JLabel();

	private JButton icon = new JButton();
	
	private String iconStr;
	
	private static final int PICTURE_MODE = 1;
	
	private static final int ICON_MODE = 2;
	
	private int pictureMode = PICTURE_MODE;
	
	public MonsterImagePanel(Monster m, MonsterDisplayPanel owner) {
		super("images/rockLighter.jpg");
		this.owner = owner;

		String picture = "";
		
		
		// get the filename for the picture
		if (m != null) {
			picture = m.getPicture();
			iconStr = m.getIcon();
		}

		setImage(picture);
		pictureMode = ICON_MODE;
		setImage(iconStr);
		
		image.setContentAreaFilled(false);
		image.setBorderPainted(false);
		image.setFocusPainted(false);

		icon.setContentAreaFilled(false);
		icon.setBorderPainted(false);
		icon.setFocusPainted(false);
		
		setLayout(new BorderLayout());
		add(image, BorderLayout.CENTER);
		add(icon, BorderLayout.EAST);
		
		// put the filename under the picture
		pictureLabel.setText(picture);
		add(pictureLabel, BorderLayout.SOUTH);

		// and set up the image to let you pick a different one from the
		// image file when you click on it.
		image.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectImage();
			}
		});
		
		
		icon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectIcon();
			}
		});

		setPreferredSize(new Dimension(260, 260));
	}

	public Dimension getPreferredSize() {
		return new Dimension(260, 260);
	}

	public void setMonster(Monster m) {

			String picture = "";
			
		// get the filename for the picture
		if (m != null) {
			picture = m.getPicture();
			iconStr = m.getIcon();
		}
		pictureMode = PICTURE_MODE;
		setImage(picture);
		pictureMode = ICON_MODE;
		setImage(iconStr);
		
		pictureLabel.setText(picture);
	}

	public void setImage(String s) {
		String picture = s;
		
		if (pictureMode == ICON_MODE){
			if (picture == null || picture.equals("") || picture.equals("NONE")) {
				picture = "images/qmark.jpg";
			}
			icon.setIcon(new ImageIcon(picture));
			iconStr = picture;
		}else{
		
		imageStr = s;
		if (imageStr != null && imageStr.indexOf("/") > -1){
			imageStr = imageStr.substring(imageStr.indexOf("/")+1);
		}
		

		// if there is no picture currently specified for this monster,
		// use the question mark picture.
		if (picture == null || picture.equals("") || picture.equals("NONE")) {
			picture = "images/qmark.jpg";
		} else {
			if (!picture.startsWith("images/")) {
				picture = "images/" + picture;
			}
		}

		// read in the picture from the file
		display = new ImageIcon(picture);
		int height = display.getIconHeight();
		int width = display.getIconWidth();

		// pull out the image
		Image img = display.getImage();

		// and scale the image to fit inside a reasonable bound
		int max = height;

		if (width > max) {
			max = width;
		}

		double ratio = new Double(max).doubleValue() / 250.0;

		if (ratio == 0) {
			ratio = 250 / max;
			display.setImage(img.getScaledInstance(new Double(width * ratio)
					.intValue(), new Double(height * ratio).intValue(),
					Image.SCALE_FAST));
		} else {

			display.setImage(img.getScaledInstance(new Double(width / ratio)
					.intValue(), new Double(height / ratio).intValue(),
					Image.SCALE_FAST));
		}

		image.setIcon(display);
		}
	}

	public String getImage() {
		return imageStr;
	}

	public String getIcon(){
		return iconStr;
	}
	
	public void selectImage() {
		pictureMode = PICTURE_MODE;
		@SuppressWarnings("unused")
		ImageSelectionDialog isd = new ImageSelectionDialog(null, this,
				"images");
	}
	
	public void selectIcon() {
		pictureMode = ICON_MODE;
		@SuppressWarnings("unused")
		ImageSelectionDialog isd = new ImageSelectionDialog(null, this,
				"images");
	}
}
