package initcheck.preferences;

import java.io.Serializable;

public class SoundPref implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean enabled;

	private String name;

	private String file;

	public SoundPref() {

	}

	public SoundPref(String name, String fileName, boolean enabled) {
		this.name = name;
		file = fileName;
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
