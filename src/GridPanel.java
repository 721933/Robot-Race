import java.awt.Color;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.GridBagLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GridPanel extends JPanel {
  private KeyEventDispatcher gameKeys;
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

    Robot robot = new Robot(grid, this.gridSize);
    updateRobot(robot);
    robot.revealAround();

    gameKeys = new KeyEventDispatcher() { // Keyboard Layout for Game
      @Override
      public boolean dispatchKeyEvent(KeyEvent e) {
        if (KeyEvent.KEY_PRESSED == e.getID()) {
          switch (e.getKeyCode()) {
            case 32: // Spacebar
              revealGrid();
              break;
            case 10: // Enter
              grid[robot.getPosX()][robot.getPosY()].remove(robot); // Remove robot from tile
              robot.move(); // Move Robot
              updateRobot(robot); // Update Robot Position
              robot.vision.updateMap(robot.getPosX(), robot.getPosY(), grid); // Update robot vision map
              robot.revealAround(); // Reveal tiles around robot
              if (grid[robot.getPosX()][robot.getPosY()].getType() == Type.End) { // Check if robot reached the end
                Main.removeGridPanel(outer(), robot.getSteps());
              }
              break;
            case 79: // O (Open)
              try {
                importMap(robot);
              } catch (IOException f) {
                f.printStackTrace();
              } catch (ClassNotFoundException f) {
                f.printStackTrace();
              }
              break;
            case 83: // S (Save)
              exportMap();
              break;
          }
        }
        return true;
      }
    };

    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(gameKeys);
  }

  public GridPanel outer() { // Getter for GridPanel instance
    return GridPanel.this;
  }

  private void generateGrid() {
    grid[gridSize - 1][gridSize - 1] = new Tile(Type.End);

    for (int i = 0; i < gridSize; i++) { // Loop through grid
      for (int j = 0; j < gridSize; j++) {
        if (grid[i][j] == null) {
          if (Math.random() > .2) { // Set about 80% of tiles to UnknownSolid
            grid[i][j] = new Tile(Type.UnknownSolid);
          } else { // Set rest to UnknownLiquid
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

    while (!grid[x][y].getType().equals(Type.End)) { // Generate path
      int k = (int) Math.round(Math.random() * 23);

      if (10 > k && k >= 0) { // Down 10/24 Odds
        try {
          y = y + 1;

          if (grid[x][y].getType().equals(Type.End)) {
            break;
          }

          if (!(x == m && n == y)) { // Check if position is the same as previous
            m = x;
            n = y;

            grid[x][y].setType(Type.UnknownLiquid); // Set to liquid
          } else {
            y = y - 1;
          }

        } catch (IndexOutOfBoundsException e) {
          y = y - 1;
        }
      } else if (20 > k && k >= 10) { // Right 10/24 Odds
        try {
          x = x + 1;

          if (grid[x][y].getType().equals(Type.End)) {
            break;
          }

          if (!(x == m && n == y)) { // Check if position is the same as previous
            m = x;
            n = y;

            grid[x][y].setType(Type.UnknownLiquid); // Set to liquid
          } else {
            x = x - 1;
          }

        } catch (IndexOutOfBoundsException e) {
          x = x - 1;
        }
      } else if (22 > k && k >= 20) { // Up 2/24 Odds
        try {
          y = y - 1;

          if (grid[x][y].getType().equals(Type.End)) {
            break;
          }

          if (!(x == m && n == y)) { // Check if position is the same as previous
            m = x;
            n = y;

            grid[x][y].setType(Type.UnknownLiquid); // Set to liquid
          } else {
            y = y + 1;
          }

        } catch (IndexOutOfBoundsException e) {
          y = y + 1;
        }
      } else if (24 > k && k >= 22) { // Left 2/24 Odds
        try {
          x = x - 1;

          if (grid[x][y].getType().equals(Type.End)) {
            break;
          }

          if (!(x == m && n == y)) { // Check if position is the same as previous
            m = x;
            n = y;

            grid[x][y].setType(Type.UnknownLiquid); // Set to liquid
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
      for (int j = 0; j < gridSize; j++) { // Loop through grid and set bounds for each tile
        grid[i][j].setBounds(initialGap + (spacing * i), initialGap + (spacing * j), 35, 35);
      }
    }
  }

  private JLabel background() {
    JLabel background = new JLabel();

    try { // Set background
      background.setIcon(new javax.swing.ImageIcon(ImageIO.read(new File("images/background.png"))));
    } catch (IOException e) {

    }

    return background;
  }

  private void revealGrid() {
    for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) { // Loop through grid
        if (grid[i][j].getType().equals(Type.UnknownSolid) // If Unknown
            || grid[i][j].getType().equals(Type.UnknownLiquid)) {
          grid[i][j].reveal(); // Reveal
        }
      }

      updateGrid();
    }
  }

  private void resetRobot(Robot robot) {
    robot.setPosX(0); // Reset coords to (0,0)
    robot.setPosY(0);
    updateRobot(robot);
  }

  public void updateRobot(Robot robot) {
    grid[robot.getPosX()][robot.getPosY()].add(robot); // Update robot position
    robot.setBounds(0, 0, 35, 35);
    this.setVisible(false); // Refresh Container
    this.setVisible(true);
  }

  private void importMap(Robot robot) throws IOException, ClassNotFoundException {
    KeyboardFocusManager.setCurrentKeyboardFocusManager(new DefaultKeyboardFocusManager());
    Object obj = null;

    JFileChooser fileChooser = new JFileChooser(); // Create JFileChoser
    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home"))); // Set Directory
    int retrival = fileChooser.showOpenDialog(this); // Open Dialog Box

    if (retrival == JFileChooser.APPROVE_OPTION) {
      FileInputStream fis = new FileInputStream(fileChooser.getSelectedFile());
      ObjectInputStream ois = new ObjectInputStream(fis);
      obj = ois.readObject();
      ois.close(); // Deserialize File
    }

    if (obj instanceof Tile[][]) {
      Tile[][] tempGrid = (Tile[][]) obj; // Convert to grid

      if (tempGrid.length == this.grid.length) {
        this.grid = tempGrid;
      }

      resetRobot(robot);
    }

    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(gameKeys);
  }

  private void exportMap() {
    KeyboardFocusManager.setCurrentKeyboardFocusManager(new DefaultKeyboardFocusManager());

    JFileChooser fileChooser = new JFileChooser(); // Create JFileChooser
    fileChooser.setSelectedFile(new File("map.dat")); // Create File
    int retrival = fileChooser.showSaveDialog(this); // Open Dialog Box

    if (retrival == JFileChooser.APPROVE_OPTION) {
      try {
        FileOutputStream fos = new FileOutputStream(fileChooser.getSelectedFile());
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(grid);

        oos.close(); // Serialize Object
      } catch (IOException e) {

      }
    }

    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(gameKeys);
  }
}