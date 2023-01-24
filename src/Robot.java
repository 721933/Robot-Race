import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class Robot extends JLabel {
	private int posX = 0;
	private int posY = 0;
	private int steps = 0;
	public boolean[][] localMap;
	public boolean[][] walkedMap;
	public Vision vision;

	public Robot(Tile[][] globalMap, int gridSize) {
		localMap = new boolean[gridSize][gridSize];
		walkedMap = new boolean[gridSize][gridSize];

		vision = new Vision(0, 0, globalMap);
		walkedMap[posX][posY] = true;

		try {
			this.setIcon(new javax.swing.ImageIcon(ImageIO.read(new File("images/robot.png"))));
		} catch (IOException e) {

		}
	}

	class Vision implements Serializable {
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

	public int getPosX() {
		return this.posX;
	}

	public int getPosY() {
		return this.posY;
	}

	public void setPosX(int x) {
		this.posX = x;
	}

	public void setPosY(int y) {
		this.posY = y;
	}

	public void move() {
		try {
			if (walkedMap[posX][posY + 1] == false && localMap[posX][posY + 1] == true) {
				moveDown();
				return;
			}
		} catch (IndexOutOfBoundsException e) {

		}

		try {
			if (walkedMap[posX + 1][posY] == false && localMap[posX + 1][posY] == true) {
				moveRight();
				return;
			}
		} catch (IndexOutOfBoundsException e) {

		}

		try {
			if (walkedMap[posX - 1][posY] == false && localMap[posX - 1][posY] == true) {
				moveLeft();
				return;
			}
		} catch (IndexOutOfBoundsException e) {

		}

		try {
			if (walkedMap[posX][posY - 1] == false && localMap[posX][posY - 1] == true) {
				moveUp();
				return;
			}
		} catch (IndexOutOfBoundsException e) {

		}

		while (true) {
			switch ((int) Math.round(Math.random() * 3)) {
				case 0:
					try {
						if (localMap[posX][posY + 1] == true) {
							moveDown();
							return;
						}
					} catch (IndexOutOfBoundsException e) {

					}
					break;
				case 1:
					try {
						if (localMap[posX + 1][posY] == true) {
							moveRight();
							return;
						}
					} catch (IndexOutOfBoundsException e) {

					}
					break;
				case 2:
					try {
						if (localMap[posX - 1][posY] == true) {
							moveLeft();
							return;
						}
					} catch (IndexOutOfBoundsException e) {

					}
					break;
				case 3:
					try {
						if (localMap[posX][posY - 1] == true) {
							moveUp();
							return;
						}
					} catch (IndexOutOfBoundsException e) {

					}
					break;
			}
		}
	}

	public void moveUp() {
		if (localMap[posX][posY - 1] == true) {
			posY--;
			walkedMap[posX][posY] = true;

			steps++;
		}
	}

	public void moveDown() {
		if (localMap[posX][posY + 1] == true) {
			posY++;
			walkedMap[posX][posY] = true;

			steps++;
		}
	}

	public void moveLeft() {
		if (localMap[posX - 1][posY] == true) {
			posX--;
			walkedMap[posX][posY] = true;

			steps++;
		}
	}

	public void moveRight() {
		if (localMap[posX + 1][posY] == true) {
			posX++;
			walkedMap[posX][posY] = true;

			steps++;
		}
	}

	public void revealAround() {
		try {
			this.vision.getUp().reveal();
			if (this.vision.getUp().getType() == Type.Liquid || this.vision.getUp().getType() == Type.End) {
				localMap[posX][posY - 1] = true;
			}
		} catch (NullPointerException e) {

		}

		try {
			this.vision.getLeft().reveal();
			if (this.vision.getLeft().getType() == Type.Liquid || this.vision.getLeft().getType() == Type.End) {
				localMap[posX - 1][posY] = true;
			}
		} catch (NullPointerException e) {

		}

		try {
			this.vision.getRight().reveal();
			if (this.vision.getRight().getType() == Type.Liquid || this.vision.getRight().getType() == Type.End) {
				localMap[posX + 1][posY] = true;
			}
		} catch (NullPointerException e) {

		}

		try {
			this.vision.getDown().reveal();
			if (this.vision.getDown().getType() == Type.Liquid || this.vision.getDown().getType() == Type.End) {
				localMap[posX][posY + 1] = true;
			}
		} catch (NullPointerException e) {

		}
	}

	public int getSteps() {
		return this.steps;
	}
}