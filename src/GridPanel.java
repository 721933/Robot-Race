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
    private Tile[][] grid = new Tile[4][4];
    private JLabel background;

    public GridPanel() {
      super();
      this.setBackground(Color.black);
      this.setLayout(new GridBagLayout());

      background = background();
      background.setBounds(0, 0, 500, 500);
      add(background);

      KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
      	@Override
      	public boolean dispatchKeyEvent(KeyEvent e) {
          if (KeyEvent.KEY_PRESSED == e.getID()) {
						switch (e.getKeyCode()) {
							case 32:	//Spacebar
								
								break;
							case 80:	//P
								
								break;
						}
          }
          return true;
        }
      });
    }

    private void updateGrid() {
      for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
          if (grid[i][j] != null) {
            grid[i][j].setBounds(13 + (92 * i), 13 + (92 * j), 80, 80);
          }
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