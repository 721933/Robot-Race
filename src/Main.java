import java.awt.Container;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Main {
  public static void main(String[] args) {
    JFrame GUI = new JFrame();

    GUI.setTitle("Robot Race");
    GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  	setIcon(GUI);

    Container pane = GUI.getContentPane();
    GridPanel panel = new GridPanel();
    pane.add(panel);
		
    GUI.pack();
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
}