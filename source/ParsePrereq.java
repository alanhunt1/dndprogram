

import initcheck.database.Feat;
import initcheck.database.FeatDAO;
import initcheck.database.FeatPrereq;
import initcheck.database.FeatPrereqDAO;
import initcheck.database.Skill;
import initcheck.database.SkillDAO;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class ParsePrereq{


		
		@SuppressWarnings("unchecked")
		public void process(){
				Hashtable hash = new Hashtable();
				FeatPrereqDAO fdb = new FeatPrereqDAO();
				FeatDAO fedb = new FeatDAO();
				Vector v = fedb.getFeats();
				for (int i = 0; i < v.size(); i++){
						Feat f = (Feat)v.get(i);
						Vector fp = fdb.getFeatPrereqs(f.getId());

						String desc = f.getDescription();
						int idx = desc.indexOf("Prereq");
						int idx2 = desc.indexOf("Benefit");
						if (idx2 < 0){
								idx2 = desc.length();
						}
						if (idx >= 0 && idx2 >= 0){
								
								String ps = desc.substring(idx, idx2).trim();
								if (ps.charAt(ps.length()-1) == '.'){
										ps = ps.substring(ps.indexOf(":")+1, ps.length()-1);
								}else{
										ps = ps.substring(ps.indexOf(":")+1, ps.length());
								}
								String [] split = ps.split(",");
								for (int j = 0; j < split.length; j++){
										String token = split[j].trim();
										FeatPrereq p = new FeatPrereq();
									
										if (token.indexOf(" or ") > 0 || token.endsWith("or")){
												
										}
										else if (token.indexOf("ranks in") > 0){
												
												int idx3 = token.indexOf("+");
												if (idx3 > 0){
												String ranks = token.substring(0, idx3);
												int idx4 = token.indexOf("ranks in")+8;
												int idx5 = token.indexOf("skill");
												
												if (idx5 > 0){
														String skill = token.substring(idx4, idx5).trim();
														
														Skill sk = matchesSkill(skill);
														if (sk != null){
																p.setPrereqType("SKILL");
																p.setPrereqName(sk.getSkill());
																p.setPrereqVal(ranks);
																p.setCrossRefId(sk.getId());
																p.setFeatId(f.getId());
																if (!hasPrereq(fp, p)){
																		fdb.addOrUpdateFeatPrereq(p);
																}
														}else{
																hash.put(split[j].trim(), "");
														}
												}else{
														hash.put(split[j].trim(), "");
												}
												}else{
														hash.put(split[j].trim(), "");
												}
										}
										else if (token.indexOf("base attack") >= 0 ||
												token.indexOf("Base attack") >= 0){
												int idx3 = token.indexOf("+")+1;
												int idx4 = token.indexOf(" ", idx3);
												if (idx4 < 0){
														idx4 = token.length();
												}
												String bab = token.substring(idx3, idx4);
												p.setPrereqType("ABILITY");
												p.setPrereqName("BAB");
												p.setPrereqVal(bab);
												p.setFeatId(f.getId());
												if (!hasPrereq(fp, p)){
														fdb.addOrUpdateFeatPrereq(p);
												}
										}else if (token.indexOf("Str ") >= 0 ||
															token.indexOf("str ") >= 0){
												int idx3 = token.indexOf(" ");
												int idx4 = token.indexOf("+");
												if (idx4 < 0){
														idx4 = token.length();
												}
												String str = token.substring(idx3, idx4);
												p.setPrereqType("STAT");
												p.setPrereqName("STRENGTH");
												p.setPrereqVal(str);
												p.setFeatId(f.getId());
												if (!hasPrereq(fp, p)){
														fdb.addOrUpdateFeatPrereq(p);
												}
										}else if (token.indexOf("Wis ") >= 0 ||
															token.indexOf("wis ") >= 0){
												int idx3 = token.indexOf(" ");
											
												int idx4 = token.indexOf("+");
												if (idx4 < 0){
														idx4 = token.length();
												}
												String str = token.substring(idx3, idx4);
											
											
												p.setPrereqType("STAT");
												p.setPrereqName("WISDOM");
												p.setPrereqVal(str);
												p.setFeatId(f.getId());
												if (!hasPrereq(fp, p)){
														fdb.addOrUpdateFeatPrereq(p);
												}
										}else if (token.indexOf("Con ") >= 0 ||
															token.indexOf("con ") >= 0){
												int idx3 = token.indexOf(" ");
												int idx4 = token.indexOf("+");
												if (idx4 < 0){
														idx4 = token.length();
												}
												String str = token.substring(idx3, idx4);
												
												p.setPrereqType("STAT");
												p.setPrereqName("CONSTITUTION");
												p.setPrereqVal(str);
												p.setFeatId(f.getId());
												if (!hasPrereq(fp, p)){
														fdb.addOrUpdateFeatPrereq(p);
												}
										}else if (token.indexOf("Int ") >= 0 ||
															token.indexOf("int ") >= 0){
												int idx3 = token.indexOf(" ");
												if (token.indexOf("+") > 0){
														int idx4 = token.indexOf("+");
														if (idx4 < 0){
																idx4 = token.length();
														}
														String str = token.substring(idx3, idx4);
														
														p.setPrereqType("STAT");
														p.setPrereqName("INTELLIGENCE");
														p.setPrereqVal(str);
														p.setFeatId(f.getId());
														if (!hasPrereq(fp, p)){
																fdb.addOrUpdateFeatPrereq(p);
														}
												}
										}else if (token.indexOf("Dex ") >= 0 ||
															token.indexOf("dex ") >= 0){
												int idx3 = token.indexOf(" ");
												int idx4 = token.indexOf("+");
												if (idx4 < 0){
														idx4 = token.length();
												}
												String str = token.substring(idx3, idx4);
												
												p.setPrereqType("STAT");
												p.setPrereqName("DEXTERITY");
												p.setPrereqVal(str);
												p.setFeatId(f.getId());
												if (!hasPrereq(fp, p)){
														fdb.addOrUpdateFeatPrereq(p);
												}
										}else if (token.indexOf("Cha ") >= 0 ||
															token.indexOf("cha ") >= 0){
												int idx3 = token.indexOf(" ");
												int idx4 = token.indexOf("+");
												if (idx4 < 0){
														idx4 = token.length();
												}
												String str = token.substring(idx3, idx4);
											
												p.setPrereqType("STAT");
												p.setPrereqName("CHARISMA");
												p.setPrereqVal(str);
												p.setFeatId(f.getId());
												if (!hasPrereq(fp, p)){
														fdb.addOrUpdateFeatPrereq(p);
												}
										}else if (matchesFeat(v, token) != null){
											
												Feat newfeat = matchesFeat(v, token);
												p.setPrereqType("FEAT");
												p.setPrereqName(newfeat.getFeatName());
												p.setCrossRefId(newfeat.getId());
												
												p.setFeatId(f.getId());
												if (!hasPrereq(fp, p)){
														fdb.addOrUpdateFeatPrereq(p);
												}
										}	
										else{
												
												hash.put(split[j].trim(), "");
										}
								}
						}
				}
				
				
		}
		
		public Skill matchesSkill(String s){
			
				SkillDAO skdb = new SkillDAO();
				
				Vector<Skill> v = skdb.selectSkill(new Skill());
				
				for (int i = 0; i < v.size(); i++){
						Skill f = (Skill)v.get(i);
						if (f.getSkill().equalsIgnoreCase(s)){
							
								return f;
						}
				}
				return null;
		}

		public Feat matchesFeat(Vector<Feat> v, String s){
				
				for (int i = 0; i < v.size(); i++){
						Feat f = (Feat)v.get(i);
						if (f.getFeatName().equalsIgnoreCase(s)){
								return f;
						}
				}
				return null;
		}

		public boolean hasPrereq(Vector<FeatPrereq> v, FeatPrereq fp){
				FeatPrereqDAO fdb = new FeatPrereqDAO();
				for (int i = 0; i < v.size(); i++){
						FeatPrereq f2 = (FeatPrereq)v.get(i);
						if (f2.getPrereqType().equals(fp.getPrereqType()) &&
								f2.getPrereqName().startsWith(fp.getPrereqName())){
								
								if (f2.getPrereqType().equals("FEAT") && f2.getCrossRefId() == null){
										
										String s = getCrossRefId("FEAT", f2.getPrereqName());
										
										if (!s.equals("")){
												f2.setCrossRefId(s);
												
												fdb.updateFeatPrereq(f2);
										}
								}else if (f2.getPrereqType().equals("SKILL") && f2.getCrossRefId() == null){
										String s = getCrossRefId("SKILL", f2.getPrereqName());
										if (!s.equals("")){
												f2.setCrossRefId(s);
												
												fdb.updateFeatPrereq(f2);
										}
								}

								
								return true;
						}
				}
				return false;
		}
		
		public String getCrossRefId(String type, String prereq){
				String id = "";
				if (prereq.indexOf("[") > 0){
						prereq = prereq.substring(0, prereq.indexOf("[")).trim();
				}
				if (type.equals("FEAT")){
						
						FeatDAO fdb = new FeatDAO();
						Vector<Feat> v = fdb.getFeats();
						Feat f = matchesFeat(v, prereq);
						if (f != null){
								return f.getId();
						}
				}else{
						Skill sk = matchesSkill(prereq);
						if (sk != null){
								return sk.getId();
						}
				}
				return id;
		}
	
		/**
		 * Describe <code>main</code> method here.
		 *
		 * @param args a <code>String[]</code> value
		 */
		public static void main(String[] args) {
				final ParsePrereq app = new ParsePrereq();
				app.process();
		}

}
