import java.awt.Container;
import java.awt.Font;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;

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

    }

    GUI.setIconImage(icon);
  }

  public static void removeIntroPanel(IntroPanel introPanel, int gridSize) {
    pane.remove(introPanel);
    gridPanel = new GridPanel(gridSize);

    pane.add(gridPanel);

    GUI.pack();
  }

  public static void removeGridPanel(GridPanel GridPanel, int steps) {
    pane.remove(gridPanel);

    JPanel outro = new JPanel();
    JLabel label = new JLabel();
    JLabel stepsLabel = new JLabel();

    pane.add(outro);
    outro.add(label);
    outro.add(stepsLabel);

    GUI.setBounds(0, 0, 500, 500);
    outro.setBackground(Color.ORANGE);
    label.setBounds(0, 0, 500, 300);
    stepsLabel.setBounds(0, 0, 500, 200);

    try {
      label.setIcon(new javax.swing.ImageIcon(ImageIO.read(new File("images/outro.png"))));
    } catch (IOException e) {

    }

    stepsLabel.setText(String.valueOf(steps));
    stepsLabel.setFont(new Font("Serif", Font.PLAIN, 72));
    stepsLabel.setHorizontalAlignment(SwingConstants.CENTER);
    stepsLabel.setVerticalAlignment(SwingConstants.CENTER);

    try {
      File file = new File("results.txt");
      FileWriter fw = new FileWriter(file);

      fw.write("Results: \n" + steps + " steps were taken!");

      fw.close();
    } catch (IOException e) {

    }
  }
}