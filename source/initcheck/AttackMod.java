package initcheck;

public class AttackMod{

		private int attackMod = 0;
		private int damageMod = 0;
		private int saveMod = 0;

		public AttackMod(){

		}

		public String toString(){
				String as;
				String ds;
				String ss;
				if (attackMod >= 0) {
						as = "+"+attackMod;
				}else{
						as = ""+attackMod;
				}
				if (damageMod >= 0) {
						ds = "+"+damageMod;
				}else{
						ds = ""+damageMod;
				}
					if (saveMod >= 0) {
						ss = "+"+saveMod;
				}else{
						ss = ""+saveMod;
				}
				return ""+as+"/"+ds+"/"+ss;
		}
		public int getAttackMod(){
				return attackMod;
		}
		
		public int getDamageMod(){
				return damageMod;
		}

		public int getSaveMod(){
				return saveMod;
		}
		
		public void setAttackMod(int i){
				attackMod = i;
		}

		public void setDamageMod(int i){
				damageMod = i;
		}

		public void setSaveMod(int i){
				saveMod = i;
		}

}
