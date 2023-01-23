import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.awt.HeadlessException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
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
            case 32: // Spacebar
              revealGrid();
              break;
            case 10: // Enter
              robot.revealAround();
              break;
            case 83: // S
              importMap();
              break;
            case 79: // O
              break;
          }
        }
        return true;
      }
    });
  }

  private void generateGrid() {
    grid[gridSize - 1][gridSize - 1] = new Tile(Type.End);

    for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
        if (grid[i][j] == null) {
          if (Math.random() > .2) {
            grid[i][j] = new Tile(Type.UnknownSolid);
          } else {
            grid[i][j] = new Tile(Type.UnknownLiquid);
          }
        }

        background.add(grid[i][j]);
      }
    }

    int x = 0;
    int y = 0;
    int m = -1;
    int n = -1;

    while (!grid[x][y].getType().equals(Type.End)) {
      int k = (int) Math.round(Math.random() * 23);

      if (10 > k && k >= 0) { // Down
        try {
          y = y + 1;

          if (grid[x][y].getType().equals(Type.End)) {
            break;
          }

          if (!(x == m && n == y)) {
            m = x;
            n = y;

            grid[x][y].setType(Type.UnknownLiquid);
          } else {
            y = y - 1;
          }

        } catch (IndexOutOfBoundsException e) {
          y = y - 1;
        }
      } else if (20 > k && k >= 10) { // Right
        try {
          x = x + 1;

          if (grid[x][y].getType().equals(Type.End)) {
            break;
          }

          if (!(x == m && n == y)) {
            m = x;
            n = y;

            grid[x][y].setType(Type.UnknownLiquid);
          } else {
            x = x - 1;
          }

        } catch (IndexOutOfBoundsException e) {
          x = x - 1;
        }
      } else if (22 > k && k >= 20) { // Up
        try {
          y = y - 1;

          if (grid[x][y].getType().equals(Type.End)) {
            break;
          }

          if (!(x == m && n == y)) {
            m = x;
            n = y;

            grid[x][y].setType(Type.UnknownLiquid);
          } else {
            y = y + 1;
          }

        } catch (IndexOutOfBoundsException e) {
          y = y + 1;
        }
      } else if (24 > k && k >= 22) { // Left
        try {
          x = x - 1;

          if (grid[x][y].getType().equals(Type.End)) {
            break;
          }

          if (!(x == m && n == y)) {
            m = x;
            n = y;

            grid[x][y].setType(Type.UnknownLiquid);
          } else {
            x = x + 1;
          }

        } catch (IndexOutOfBoundsException e) {
          x = x + 1;
        }
      }
    }

    grid[0][0].setType(Type.Start);

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

  private void revealGrid() {
    for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
        if (grid[i][j].getType().equals(Type.UnknownSolid)
            || grid[i][j].getType().equals(Type.UnknownLiquid)) {
          grid[i][j].reveal();
        }
      }

      updateGrid();
    }
  }

  private void importMap() {
    JFileChooser fileChooser = new JFileChooser();
    File selectedFile = null;
    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    int result = fileChooser.showOpenDialog(this);
  }
}