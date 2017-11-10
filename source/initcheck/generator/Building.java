package initcheck.generator;

import java.io.Serializable;

public class Building implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name = "Default";
	
	private String description = "Default";

	public String toString(){
		return name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
