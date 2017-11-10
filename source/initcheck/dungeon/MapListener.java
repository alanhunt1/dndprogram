package initcheck.dungeon;

public interface MapListener{

		public void updateTimeCount(int i);
		public void updateTotalTimeCount(int i);
		public void updateMap(int x, int y, int z);
		public void updateSquare(MapSquare m);
		public void setMap(Dungeon map);

}
