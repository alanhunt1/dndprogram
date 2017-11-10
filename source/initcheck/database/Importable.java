package initcheck.database;

import java.util.Vector;

import initcheck.graphics.TiledListItem;

public interface Importable extends TiledListItem {

	public void save(boolean overwrite);
	public void readImport(String s);
	public String getName();
	public void setName(String s);
	public String listValue();
	public String existsAs();
	public Vector<?> getValues();
	public String getId();
	public void setId(String s);
}
