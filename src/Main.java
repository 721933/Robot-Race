import java.awt.Container;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Main {
  private static JFrame GUI = new JFrame();
  private static Container pane = GUI.getContentPane();
  private static GridPanel gridPanel;

  public static void main(String[] args) {
    GUI.setTitle("Robot Race");
    GUI.setSize(500, 400);
    GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setIcon(GUI);

    IntroPanel introPanel = new IntroPanel();
    pane.add(introPanel);

    GUI.setVisible(true);
  }

  private static void setIcon(JFrame GUI) {
    Image icon = null;

    try {
      icon = ImageIO.read(new File("images/logo.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    GUI.setIconImage(icon);
  }

  public static void removeIntroPanel(IntroPanel introPanel, int gridSize) {
    pane.remove(introPanel);
    gridPanel = new GridPanel(gridSize);

    pane.add(gridPanel);

    GUI.pack();
  }
}