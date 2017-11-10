package initcheck.generator;

import initcheck.utils.RandomRoll;

import java.util.Vector;

public class StoreNameGenerator {

	Vector<String> names = new Vector<String>();
	
	public String getRandomName(){
		names.add("Bucket of Stuff");
		names.add("Maul-Mart");
		names.add("Crates of Junk");
		names.add("Box o Goodies");
		return names.get(RandomRoll.getRandom(names.size()));
	}
	
}
