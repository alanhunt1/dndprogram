package initcheck.graphics;

public class TiledListString implements TiledListItem  {
	String internalString;

	public TiledListString(String s){
		internalString = s;
	}
	
	public String getInternalString() {
		return internalString;
	}

	public void setInternalString(String internalString) {
		this.internalString = internalString;
	}
	
}
