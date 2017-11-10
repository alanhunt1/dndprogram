package initcheck.generator;

import initcheck.utils.RandomRoll;

import java.util.ArrayList;

public class CharacterNameGenerator {

	ArrayList<String> maleFirst = new ArrayList<String>();

	ArrayList<String> maleMiddle = new ArrayList<String>();

	ArrayList<String> maleLast = new ArrayList<String>();

	ArrayList<String> femaleFirstStart = new ArrayList<String>();

	ArrayList<String> femaleFirstMiddle = new ArrayList<String>();

	ArrayList<String> femaleFirstEnd = new ArrayList<String>();

	public CharacterNameGenerator() {
		initialize();
	}

	public String getRandomName(){
		String name = "";
		name += femaleFirstStart.get(RandomRoll.getRandom(femaleFirstStart.size()));
		name += femaleFirstMiddle.get(RandomRoll.getRandom(femaleFirstMiddle.size()));
		name += femaleFirstEnd.get(RandomRoll.getRandom(femaleFirstEnd.size()));
		
		return name.substring(0,1).toUpperCase()+name.substring(1);
	}
	
	public void initialize() {		
		
		femaleFirstStart.add("e");
		femaleFirstStart.add("el");
		femaleFirstStart.add("a");
		femaleFirstStart.add("ar");
		femaleFirstStart.add("on");
		
		femaleFirstMiddle.add("an");
		femaleFirstMiddle.add("la");
		femaleFirstMiddle.add("");
		femaleFirstMiddle.add("man");
		femaleFirstMiddle.add("meri");
		
		
		femaleFirstEnd.add("a");
		femaleFirstEnd.add("da");
		femaleFirstEnd.add("dare");
		femaleFirstEnd.add("de");
		femaleFirstEnd.add("dha");
		femaleFirstEnd.add("en");
		femaleFirstEnd.add("era");
		femaleFirstEnd.add("in");
		femaleFirstEnd.add("l");
		femaleFirstEnd.add("la");
		femaleFirstEnd.add("laine");
		femaleFirstEnd.add("lan");
		femaleFirstEnd.add("le");
		femaleFirstEnd.add("lea");
		femaleFirstEnd.add("lin");
		femaleFirstEnd.add("lla");
		femaleFirstEnd.add("lle");
		femaleFirstEnd.add("llin");
		femaleFirstEnd.add("m");
		femaleFirstEnd.add("n");
		femaleFirstEnd.add("nde");
		femaleFirstEnd.add("ndha");
		femaleFirstEnd.add("ndhe");
		femaleFirstEnd.add("ndhra");
		femaleFirstEnd.add("ndra");
		femaleFirstEnd.add("ne");
		femaleFirstEnd.add("ni");
		femaleFirstEnd.add("nna");
		femaleFirstEnd.add("r");
		femaleFirstEnd.add("ra");
		femaleFirstEnd.add("rd");
		femaleFirstEnd.add("reen");
		femaleFirstEnd.add("rian");
		femaleFirstEnd.add("rin");
		femaleFirstEnd.add("rlea");
		femaleFirstEnd.add("rra");
		femaleFirstEnd.add("ryn");
		femaleFirstEnd.add("s");
		femaleFirstEnd.add("sain");
		femaleFirstEnd.add("se");
		femaleFirstEnd.add("sise");
		femaleFirstEnd.add("ssin");
		femaleFirstEnd.add("uin");
		femaleFirstEnd.add("va");
	}

}
