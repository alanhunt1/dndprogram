package initcheck.utils;

import java.util.Random;

public class RandomRoll {
	
	private static Random rnd = new Random();
	
	// returns a number from 0 to i-1.  Handy for random selection from
	// a vector.
	public static int getRandom(int i) {
		int j = rnd.nextInt(i);
		return j;
	}
	
}
