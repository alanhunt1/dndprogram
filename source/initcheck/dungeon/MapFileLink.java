package initcheck.dungeon;

import initcheck.server.CampaignNotesPanel;

import java.io.Serializable;

public class MapFileLink implements Serializable {

	private static final long serialVersionUID = 1L;

	public String fileName;

	public MapFileLink(MapImage mi) {
		this.fileName = mi.getFileName();
	}

	public int getType() {
		return CampaignNotesPanel.MAP_TAB;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
