import java.awt.Color;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.awt.KeyboardFocusManager;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GridPanel extends JPanel {
	private int gridSize;
  private Tile[][] grid;
  private JLabel background;

  public GridPanel(int gridSize) {
    super();
		this.gridSize = gridSize;
		this.grid = new Tile[gridSize][gridSize];
    this.setBackground(Color.black);
    this.setLayout(new GridBagLayout());

    background = background();
    background.setBounds(0, 0, 1280, 720);
    add(background);
		generateGrid();

    Robot robot = new Robot(grid);

    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
      @Override
      public boolean dispatchKeyEvent(KeyEvent e) {
        if (KeyEvent.KEY_PRESSED == e.getID()) {
					switch (e.getKeyCode()) {
						case 32:	//Spacebar
              for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                  if (grid[i][j].getType().equals(Type.UnknownSolid) || grid[i][j].getType().equals(Type.UnknownLiquid)) {
                    grid[i][j].reveal();
                  }
                }

                updateGrid();
              }
							break;
            case 10:
              robot.vision.getDown().reveal();
              break;
					}
        }
      	return true;
      }
    });
  }

	private void generateGrid() {
		grid[0][0] = new Tile(Type.Start);
		grid[gridSize - 1][gridSize - 1] = new Tile(Type.End);

    for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
				if (grid[i][j] == null) {
          if (Math.random() > .8) {
            grid[i][j] = new Tile(Type.UnknownSolid);
          } else {
            grid[i][j] = new Tile(Type.UnknownLiquid);
          }
				}

        background.add(grid[i][j]);
      }
    }

    int m = 0;
    int n = 0;

    while (!grid[m][n].getType().equals(Type.End)) {
      switch ((int) (Math.round(Math.random()))) {
        case 0: //Down
          try {
            if (grid[m + 1][n].getType().equals(Type.End)) {
              break;
            }

            grid[m + 1][n].setType(Type.UnknownLiquid);
            m = m + 1;
          } catch (IndexOutOfBoundsException e) {

          }

          break;
        case 3: //Up
          if (grid[m - 1][n].getType().equals(Type.End)) {
            break;
          }

          try {
            grid[m - 1][n].setType(Type.UnknownLiquid);
            m = m - 1;
          } catch (IndexOutOfBoundsException e) {

          }

          break;
        case 2: //Left
          if (grid[m][n - 1].getType().equals(Type.End)) {
            break;
          }

          try {
            grid[m][n - 1].setType(Type.UnknownLiquid);
            n = n - 1;
          } catch (IndexOutOfBoundsException e) {

          }

          break;
        case 1: //Right
          if (grid[m][n + 1].getType().equals(Type.End)) {
            break;
          }

          try {
            grid[m][n + 1].setType(Type.UnknownLiquid);
            n = n + 1;
          } catch (IndexOutOfBoundsException e) {

          }

          break;
      }
    }

		updateGrid();
	}

	private void updateGrid() {
    int spacing = 80;
    int initialGap = 130;
    switch (gridSize) {
      case 10:
        spacing = 60;
        initialGap = 10;
        break;
      case 15:
        spacing = 40;
        initialGap = 2;
        break;
    }

  	for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
        grid[i][j].setBounds(initialGap + (spacing * i), initialGap + (spacing * j), 35, 35);
      }
    }
  }

  private static JLabel background() {
    JLabel background = new JLabel();

    try {
      background.setIcon(new javax.swing.ImageIcon(ImageIO.read(new File("images/background.png"))));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return background;
  }
}