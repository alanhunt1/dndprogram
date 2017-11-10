package initcheck.dungeon;

public class MapEvent {

		String eventType;
		int xPosition;
		int yPosition;
		int zPosition;
		MapSquare currentSquare;

		/**
		 * Get the CurrentSquare value.
		 * @return the CurrentSquare value.
		 */
		public MapSquare getCurrentSquare() {
				return currentSquare;
		}

		/**
		 * Set the CurrentSquare value.
		 * @param newCurrentSquare The new CurrentSquare value.
		 */
		public void setCurrentSquare(MapSquare newCurrentSquare) {
				this.currentSquare = newCurrentSquare;
		}

		public MapEvent(String s){
				eventType = s;
		}
		public MapEvent(String s, MapSquare ms){
				eventType = s;
				currentSquare = ms;
		}
		
		public MapEvent(String s, int x, int y, int z){
				eventType = s;
				xPosition = x;
				yPosition = y;
				zPosition = z;
		}

		/**
		 * Get the ZPosition value.
		 * @return the ZPosition value.
		 */
		public int getZPosition() {
				return zPosition;
		}

		/**
		 * Set the ZPosition value.
		 * @param newZPosition The new ZPosition value.
		 */
		public void setZPosition(int newZPosition) {
				this.zPosition = newZPosition;
		}

		/**
		 * Get the YPosition value.
		 * @return the YPosition value.
		 */
		public int getYPosition() {
				return yPosition;
		}

		/**
		 * Set the YPosition value.
		 * @param newYPosition The new YPosition value.
		 */
		public void setYPosition(int newYPosition) {
				this.yPosition = newYPosition;
		}

		/**
		 * Get the XPosition value.
		 * @return the XPosition value.
		 */
		public int getXPosition() {
				return xPosition;
		}

		/**
		 * Set the XPosition value.
		 * @param newXPosition The new XPosition value.
		 */
		public void setXPosition(int newXPosition) {
				this.xPosition = newXPosition;
		}

		/**
		 * Get the EventType value.
		 * @return the EventType value.
		 */
		public String getEventType() {
				return eventType;
		}
		
		/**
		 * Set the EventType value.
		 * @param newEventType The new EventType value.
		 */
		public void setEventType(String newEventType) {
				this.eventType = newEventType;
		}
		
}
