package initcheck.graphics;

import initcheck.InitImage;

public class DefaultSkin extends Skin {

	public DefaultSkin(){
		setTileLists(true);
		setTilePanels(true);
		setTileTextAreas(true);
		setName("Default");
		setUseGraphics(true);
		setBackgroundPanelIcon(InitImage.steel);
		setForegroundPanelIcon(InitImage.lightRocks);
	}
}
