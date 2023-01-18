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
		//generateGrid();

    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
      @Override
      public boolean dispatchKeyEvent(KeyEvent e) {
        if (KeyEvent.KEY_PRESSED == e.getID()) {
					switch (e.getKeyCode()) {
						case 32:	//Spacebar
							
							break;
					}
        }
      	return true;
      }
    });
  }

	private void generateGrid() {
		grid[0][0] = new Tile(Type.Start);
		grid[gridSize][gridSize] = new Tile(Type.End);
		
		for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
				if (grid[i][j] == null) {
					grid[i][j] = new Tile(Type.Unknown);
				}
      }
    }

		updateGrid();
	}

	private void updateGrid() {
  	for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
        grid[i][j].setBounds(13 + (92 * i), 13 + (92 * j), 35, 35);
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