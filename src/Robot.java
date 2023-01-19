public class Robot {
	public LocalMap vision;

	public Robot(Tile[][] globalMap) {
		vision = new LocalMap(0, 0, globalMap);
	}

	class LocalMap {
		private Tile up;
		private Tile left;
		private Tile right;
		private Tile down;
	
		public LocalMap(int positionX, int positionY, Tile[][] globalMap) {
			updateMap(positionX, positionY, globalMap);
		}
	
		public void updateMap(int positionX, int positionY, Tile[][] globalMap) {
			try {
				this.up = globalMap[positionX][positionY - 1];
			} catch (IndexOutOfBoundsException e) {
				this.up = null;
			}
	
			try {
				this.down = globalMap[positionX][positionY + 1];
			} catch (IndexOutOfBoundsException e) {
				this.down = null;
			}
	
			try {
				this.left = globalMap[positionX - 1][positionY];
			} catch (IndexOutOfBoundsException e) {
				this.left = null;
			}
	
			try {
				this.right = globalMap[positionX + 1][positionY];
			} catch (IndexOutOfBoundsException e) {
				this.right = null;
			}
		}
	
		public Tile getUp() {
			return this.up;
		}
	
		public Tile getLeft() {
			return this.left;
		}
	
		public Tile getRight() {
			return this.right;
		}
	
		public Tile getDown() {
			return this.down;
		}
	}
}