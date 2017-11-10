package initcheck;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.util.HashMap;

import javax.swing.ImageIcon;

public class InitImage {

	public static final ImageIcon darkRocks = new ImageIcon("images/rock043.jpg");
	public static final ImageIcon lightRocks = new ImageIcon("images/rockLighter.jpg");
	public static final ImageIcon steel = new ImageIcon("images/steel1.jpg");
	public static final ImageIcon rust = new ImageIcon("images/RustyPipe_tn.jpg");
	public static final ImageIcon tabGraphic = new ImageIcon("images/tabGraphic.jpg");
	public static final ImageIcon paperMiddle = new ImageIcon("images/papermiddle.jpg");
	public static final ImageIcon paperUl = new ImageIcon("images/paperul.jpg");
	public static final ImageIcon paperLeft = new ImageIcon("images/paperleft.jpg");
	public static final ImageIcon paperLl = new ImageIcon("images/paperll.jpg");
	public static final ImageIcon paperTop = new ImageIcon("images/papertop.jpg");
	public static final ImageIcon paperBottom = new ImageIcon("images/paperbottom.jpg");
	public static final ImageIcon paperUr = new ImageIcon("images/paperur.jpg");
	public static final ImageIcon paperLr = new ImageIcon("images/paperlr.jpg");
	public static final ImageIcon paperRight = new ImageIcon("images/paperright.jpg");
	public static final ImageIcon dice = new ImageIcon("images/diceicon.gif");
	public static final ImageIcon hfImage = new ImageIcon("images/heavyforest.jpg");
	public static final ImageIcon mfImage = new ImageIcon("images/medforest.jpg");
	public static final ImageIcon lfImage = new ImageIcon("images/lightforest.jpg");
	public static final ImageIcon sfImage = new ImageIcon("images/stonefloor.jpg");
	public static final ImageIcon trapImage = new ImageIcon("images/trapoverlay.gif");
	public static final ImageIcon grImage = new ImageIcon("images/grass.jpg");
	public static final ImageIcon lf2Image = new ImageIcon("images/lightforest2.jpg");
	public static final ImageIcon lf3Image = new ImageIcon("images/lightforest3.jpg");
	public static final ImageIcon voImage = new ImageIcon("images/visoverlay.gif");
	public static final ImageIcon townImage = new ImageIcon("images/town.jpg");
	public static final ImageIcon townIcon = new ImageIcon("images/townsmall.jpg");
	public static final ImageIcon mapIcon = new ImageIcon("images/compass.jpg");
	public static final ImageIcon swordIcon = new ImageIcon("images/swordicon.jpg");
	public static final ImageIcon scrollIcon = new ImageIcon("images/scrollicon.jpg");
	public static final ImageIcon redX = new ImageIcon("images/redx.jpg");
	public static final ImageIcon qmarkIcon = new ImageIcon("images/qmarkicon.gif");
	public static final ImageIcon exclamIcon = new ImageIcon("images/exclamicon.gif");
	public static final ImageIcon lmountainImage = new ImageIcon("images/lightmountainicon.jpg");
	public static final ImageIcon mmountainImage = new ImageIcon("images/medmountainicon.jpg");
	public static final ImageIcon hmountainImage = new ImageIcon("images/heavymountainicon.jpg");
	public static final ImageIcon swampImage = new ImageIcon("images/swampicon.gif");
	public static final ImageIcon desertImage = new ImageIcon("images/deserticon.gif");
	public static final ImageIcon lightdesertImage = new ImageIcon("images/lightdeserticon.gif");
	
	public static final BufferedImage visoverlay = toBufferedImage(voImage.getImage());
	public static final BufferedImage grass = toBufferedImage(grImage.getImage());
	public static final BufferedImage corridor = toBufferedImage(sfImage.getImage());
	public static final BufferedImage trees = toBufferedImage(hfImage.getImage());
	public static final BufferedImage mtrees = toBufferedImage(mfImage.getImage());
	public static final BufferedImage ltrees = toBufferedImage(lfImage.getImage());
	public static final BufferedImage trap = toBufferedImage(trapImage.getImage());
	public static final BufferedImage ltrees2 = toBufferedImage(lf2Image.getImage());
	public static final BufferedImage ltrees3 = toBufferedImage(lf3Image.getImage());
	public static final BufferedImage town = toBufferedImage(townImage.getImage());
	public static final BufferedImage lmountains = toBufferedImage(lmountainImage.getImage());
	public static final BufferedImage mmountains = toBufferedImage(mmountainImage.getImage());
	public static final BufferedImage hmountains = toBufferedImage(hmountainImage.getImage());
	public static final BufferedImage swamp = toBufferedImage(swampImage.getImage());
	public static final BufferedImage desert = toBufferedImage(desertImage.getImage());
	public static final BufferedImage ldesert = toBufferedImage(lightdesertImage.getImage());
	public static HashMap<String, BufferedImage> bufferMap; 
	
	public static void loadImages(){
		bufferMap.put("LightTrees1", ltrees);
		bufferMap.put("LightTrees2", ltrees2);
		bufferMap.put("LightTrees3", ltrees3);
		bufferMap.put("LightMountain", lmountains);
		bufferMap.put("MediumMountain", mmountains);
		bufferMap.put("HeavyMountain", hmountains);
		bufferMap.put("Swamp", swamp);
		bufferMap.put("Desert", desert);
		bufferMap.put("LightDesert", ldesert);
	}
	
	public static BufferedImage getBufferedImage(String s){
		if (bufferMap == null){
			bufferMap = new HashMap<String, BufferedImage>();
			loadImages();
		}
		return bufferMap.get(s);
	}
	
	
	//	 This method returns a buffered image with the contents of an image
    public static BufferedImage toBufferedImage(Image image) {
    	
    
    	
    	// set a default image if it is null due to a missing file
    	if (image == null || image.getHeight(null) < 0){
    		
    		image = qmarkIcon.getImage();	
    	}
    	
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        }
    
        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();
    
        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent Pixels
        boolean hasAlpha = hasAlpha(image);
    
        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
            if (hasAlpha) {
                transparency = Transparency.BITMASK;
            }
    
            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }
    
        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            if (hasAlpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
    
        // Copy image to buffered image
        Graphics g = bimage.createGraphics();
    
        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();
    
        return bimage;
    }
    
    //  This method returns true if the specified image has transparent pixels
    public static boolean hasAlpha(Image image) {
        // If buffered image, the color model is readily available
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage)image;
            return bimage.getColorModel().hasAlpha();
        }
    
        // Use a pixel grabber to retrieve the image's color model;
        // grabbing a single pixel is usually sufficient
         PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
        }
    
        // Get the image's color model
        ColorModel cm = pg.getColorModel();
        return cm.hasAlpha();
    }
}
