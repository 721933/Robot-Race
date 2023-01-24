import java.awt.Color;
import java.awt.event.*;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class IntroPanel extends JPanel implements ActionListener {
	private int gridSize;

	public IntroPanel() {
		super();
		this.setBackground(Color.BLUE);
		this.setBounds(0, 0, 500, 700);

		JButton btn5x5 = new JButton();
		JButton btn10x10 = new JButton();
		JButton btn15x15 = new JButton();
		JButton controls = new JButton();
		JLabel introText = new JLabel();

		controls.setText("Controls");
		controls.setBounds(0, 0, 400, 300);
		controls.setBackground(Color.WHITE);
		btn5x5.setText("5 x 5");
		btn5x5.setBounds(0, 400, 100, 100);
		btn5x5.setBackground(Color.WHITE);
		btn10x10.setText("10 x 10");
		btn10x10.setBounds(100, 400, 100, 100);
		btn10x10.setBackground(Color.WHITE);
		btn15x15.setText("15 x 15");
		btn15x15.setBounds(200, 400, 100, 100);
		btn15x15.setBackground(Color.WHITE);

		try {
			introText.setIcon(new javax.swing.ImageIcon(ImageIO.read(new File("images/introText.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}

		add(btn5x5);
		add(btn10x10);
		add(btn15x15);
		add(controls);
		add(introText);

		btn5x5.addActionListener(this);
		btn10x10.addActionListener(this);
		btn15x15.addActionListener(this);
		controls.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();

		if (s.equals("Controls")) {

			JOptionPane.showMessageDialog(this, "Message for the dialog box goes here.", "Controls Information",
					JOptionPane.INFORMATION_MESSAGE);

			return;
		}

		if (s.equals("5 x 5")) {
			gridSize = 5;
		} else if (s.equals("10 x 10")) {
			gridSize = 10;
		} else if (s.equals("15 x 15")) {
			gridSize = 15;
		}

		Main.removeIntroPanel(this, this.gridSize);
	}
}