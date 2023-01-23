import javax.swing.JPanel;

public class Robot extends JPanel {
	private static int steps = 0;
	public Vision vision;

	public Robot(Tile[][] globalMap) {
		vision = new Vision(0, 0, globalMap);
	}

	class Vision {
		private Tile up;
		private Tile left;
		private Tile right;
		private Tile down;

		public Vision(int positionX, int positionY, Tile[][] globalMap) {
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

	public void move() {

	}

	public void revealAround() {
		try {
			this.vision.getUp().reveal();
		} catch (NullPointerException e) {

		}

		try {
			this.vision.getLeft().reveal();
		} catch (NullPointerException e) {

		}

		try {
			this.vision.getRight().reveal();
		} catch (NullPointerException e) {

		}

		try {
			this.vision.getDown().reveal();
		} catch (NullPointerException e) {

		}
	}
}