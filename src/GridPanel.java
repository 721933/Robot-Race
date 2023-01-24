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

    gameKeys = new KeyEventDispatcher() {
      @Override
      public boolean dispatchKeyEvent(KeyEvent e) {
        if (KeyEvent.KEY_PRESSED == e.getID()) {
          switch (e.getKeyCode()) {
            case 32: // Spacebar
              revealGrid();
              break;
            case 10: // Enter
              grid[robot.getPosX()][robot.getPosY()].remove(robot);
              robot.move();
              updateRobot(robot);
              robot.vision.updateMap(robot.getPosX(), robot.getPosY(), grid);
              robot.revealAround();
              if (grid[robot.getPosX()][robot.getPosY()].getType() == Type.End) {
                Main.removeGridPanel(outer(), robot.getSteps());
              }
              break;
            case 79: // O (Open)
              try {
                importMap(robot);
              } catch (IOException f) {

              } catch (ClassNotFoundException f) {

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

  public GridPanel outer() {
    return GridPanel.this;
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

  private void resetRobot(Robot robot) {
    robot.setPosX(0);
    robot.setPosY(0);
    updateRobot(robot);
  }

  public void updateRobot(Robot robot) {
    grid[robot.getPosX()][robot.getPosY()].add(robot);
    robot.setBounds(0, 0, 35, 35);
    this.setVisible(false);
    this.setVisible(true);
  }

  private void importMap(Robot robot) throws IOException, ClassNotFoundException {
    KeyboardFocusManager.setCurrentKeyboardFocusManager(new DefaultKeyboardFocusManager());
    Object obj = null;

    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    int retrival = fileChooser.showOpenDialog(this);

    if (retrival == JFileChooser.APPROVE_OPTION) {
      FileInputStream fis = new FileInputStream(fileChooser.getSelectedFile());
      ObjectInputStream ois = new ObjectInputStream(fis);
      obj = ois.readObject();
      ois.close();
    }

    if (obj instanceof Tile[][]) {
      Tile[][] tempGrid = (Tile[][]) obj;

      if (tempGrid.length == this.grid.length) {
        this.grid = tempGrid;
      }

      resetRobot(robot);
    }

    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(gameKeys);
  }

  private void exportMap() {
    KeyboardFocusManager.setCurrentKeyboardFocusManager(new DefaultKeyboardFocusManager());

    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setSelectedFile(new File("map.txt"));
    int retrival = fileChooser.showSaveDialog(this);

    if (retrival == JFileChooser.APPROVE_OPTION) {
      try {
        FileOutputStream fos = new FileOutputStream(fileChooser.getSelectedFile());
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this.grid);

        oos.close();
      } catch (IOException e) {

      }
    }

    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(gameKeys);
  }
}