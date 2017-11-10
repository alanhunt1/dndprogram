package initcheck.generator;

import initcheck.utils.RandomRoll;

import java.util.ArrayList;

public class NameGenerator {

 ArrayList<Syllable> vowels = new ArrayList<Syllable>();

	ArrayList<Syllable> consonants = new ArrayList<Syllable>();

	private int minSyllables = 3;

	private int maxSyllables = 5;

	public int getMaxSyllables() {
		return maxSyllables;
	}

	public void setMaxSyllables(int maxSyllables) {
		this.maxSyllables = maxSyllables;
	}

	public int getMinSyllables() {
		return minSyllables;
	}

	public void setMinSyllables(int minSyllables) {
		this.minSyllables = minSyllables;
	}

	public NameGenerator() {
		initialize();
	}

	public void initialize() {

		for (int i = 1; i < 7; i++) {
			vowels.add(new Syllable("a", 7));
			vowels.add(new Syllable("e", 7));
			vowels.add(new Syllable("i", 7));
			vowels.add(new Syllable("o", 7));
			vowels.add(new Syllable("u", 7));
		}

		vowels.add(new Syllable("ae", 7));
		vowels.add(new Syllable("ai", 7));
		vowels.add(new Syllable("ao", 7));
		vowels.add(new Syllable("au", 7));
		vowels.add(new Syllable("aa", 7));
		vowels.add(new Syllable("ea", 7));
		vowels.add(new Syllable("eo", 7));
		vowels.add(new Syllable("eu", 7));
		vowels.add(new Syllable("ee", 7));
		vowels.add(new Syllable("ia", 7));
		vowels.add(new Syllable("io", 7));
		vowels.add(new Syllable("iu", 7));
		vowels.add(new Syllable("ii", 7));
		vowels.add(new Syllable("oa", 7));
		vowels.add(new Syllable("oe", 7));
		vowels.add(new Syllable("oi", 7));
		vowels.add(new Syllable("ou", 7));
		vowels.add(new Syllable("oo", 7));
		vowels.add(new Syllable("eau", 7));
		vowels.add(new Syllable("'", 4));
		vowels.add(new Syllable("y", 7));

		consonants.add(new Syllable("b", 7));
		consonants.add(new Syllable("c", 7));
		consonants.add(new Syllable("d", 7));
		consonants.add(new Syllable("f", 7));
		consonants.add(new Syllable("g", 7));
		consonants.add(new Syllable("h", 7));
		consonants.add(new Syllable("j", 7));
		consonants.add(new Syllable("k", 7));
		consonants.add(new Syllable("l", 7));
		consonants.add(new Syllable("m", 7));
		consonants.add(new Syllable("n", 7));
		consonants.add(new Syllable("p", 7));
		consonants.add(new Syllable("qu", 6));
		consonants.add(new Syllable("r", 7));
		consonants.add(new Syllable("s", 7));
		consonants.add(new Syllable("t", 7));
		consonants.add(new Syllable("v", 7));
		consonants.add(new Syllable("w", 7));
		consonants.add(new Syllable("x", 7));
		consonants.add(new Syllable("y", 7));
		consonants.add(new Syllable("z", 7));
		// Blends, sorted by second character:
		consonants.add(new Syllable("sc", 7));
		consonants.add(new Syllable("ch", 7));
		consonants.add(new Syllable("gh", 7));
		consonants.add(new Syllable("ph", 7));
		consonants.add(new Syllable("sh", 7));
		consonants.add(new Syllable("th", 7));
		consonants.add(new Syllable("wh", 6));
		consonants.add(new Syllable("ck", 5));
		consonants.add(new Syllable("nk", 5));
		consonants.add(new Syllable("rk", 5));
		consonants.add(new Syllable("sk", 7));
		consonants.add(new Syllable("wk", 0));
		consonants.add(new Syllable("cl", 6));
		consonants.add(new Syllable("fl", 6));
		consonants.add(new Syllable("gl", 6));
		consonants.add(new Syllable("kl", 6));
		consonants.add(new Syllable("ll", 6));
		consonants.add(new Syllable("pl", 6));
		consonants.add(new Syllable("sl", 6));
		consonants.add(new Syllable("br", 6));
		consonants.add(new Syllable("cr", 6));
		consonants.add(new Syllable("dr", 6));
		consonants.add(new Syllable("fr", 6));
		consonants.add(new Syllable("gr", 6));
		consonants.add(new Syllable("kr", 6));
		consonants.add(new Syllable("pr", 6));
		consonants.add(new Syllable("sr", 6));
		consonants.add(new Syllable("tr", 6));
		consonants.add(new Syllable("ss", 5));
		consonants.add(new Syllable("st", 7));
		consonants.add(new Syllable("str", 6));
		// Repeat some entries to make them more common.
		consonants.add(new Syllable("b", 7));
		consonants.add(new Syllable("c", 7));
		consonants.add(new Syllable("d", 7));
		consonants.add(new Syllable("f", 7));
		consonants.add(new Syllable("g", 7));
		consonants.add(new Syllable("h", 7));
		consonants.add(new Syllable("j", 7));
		consonants.add(new Syllable("k", 7));
		consonants.add(new Syllable("l", 7));
		consonants.add(new Syllable("m", 7));
		consonants.add(new Syllable("n", 7));
		consonants.add(new Syllable("p", 7));
		consonants.add(new Syllable("r", 7));
		consonants.add(new Syllable("s", 7));
		consonants.add(new Syllable("t", 7));
		consonants.add(new Syllable("v", 7));
		consonants.add(new Syllable("w", 7));
		consonants.add(new Syllable("b", 7));
		consonants.add(new Syllable("c", 7));
		consonants.add(new Syllable("d", 7));
		consonants.add(new Syllable("f", 7));
		consonants.add(new Syllable("g", 7));
		consonants.add(new Syllable("h", 7));
		consonants.add(new Syllable("j", 7));
		consonants.add(new Syllable("k", 7));
		consonants.add(new Syllable("l", 7));
		consonants.add(new Syllable("m", 7));
		consonants.add(new Syllable("n", 7));
		consonants.add(new Syllable("p", 7));
		consonants.add(new Syllable("r", 7));
		consonants.add(new Syllable("s", 7));
		consonants.add(new Syllable("t", 7));
		consonants.add(new Syllable("v", 7));
		consonants.add(new Syllable("w", 7));
		consonants.add(new Syllable("br", 6));
		consonants.add(new Syllable("dr", 6));
		consonants.add(new Syllable("fr", 6));
		consonants.add(new Syllable("gr", 6));
		consonants.add(new Syllable("kr", 6));

	}

	public String getRandomName() {
		String name = "";

		// find number of syllables
		int syllables = RandomRoll.getRandom(maxSyllables + 1);
		while (syllables < minSyllables) {
			syllables = RandomRoll.getRandom(maxSyllables + 1);
		}

		boolean isVowel = RandomRoll.getRandom(2) == 1;

		for (int i = 0; i < syllables; i++) {
			isVowel = !isVowel;
			boolean found = false;
			Syllable s = new Syllable();
			while (!found) {

				if (isVowel) {
					s = vowels.get(RandomRoll.getRandom(vowels.size()));
				} else {
					s = consonants.get(RandomRoll.getRandom(consonants.size()));
				}
				if (i == 1) {
					if (s.canStart()) {
						found = true;
					}
				} else if (i == syllables - 1) {
					if (s.canEnd()) {
						found = true;
					}
				} else {
					if (s.canMiddle()) {
						found = true;
					}
				}
			}
			name += s.getSyllable();

		}

		return name.substring(0,1).toUpperCase()+name.substring(1);
	}
}
