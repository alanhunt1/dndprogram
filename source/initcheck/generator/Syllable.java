package initcheck.generator;

public class Syllable {

	private String syllable = "";

	private int position = 0;

	public Syllable(){
		
	}
	
	public Syllable(String syllable, int position) {
		this.syllable = syllable;
		this.position = position;
	}

	public boolean canStart(){
		return position == 2 || position == 3 || position == 6 || position == 7;
	}
	
	public boolean canMiddle(){
		return position == 4 || position == 5 || position == 6 || position == 7;
	}
	
	public boolean canEnd(){
		return position == 1 || position == 3 || position == 5 || position == 7;
	}
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getSyllable() {
		return syllable;
	}

	public void setSyllable(String syllable) {
		this.syllable = syllable;
	}
}
