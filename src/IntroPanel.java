import java.awt.Color;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.JButton;

public class IntroPanel extends JPanel implements ActionListener {
	private int gridSize;
	
	public IntroPanel() {
		super();
		this.setBackground(Color.BLUE);
		this.setBounds(0, 0, 300, 100);

		JButton btn5x5 = new JButton();
		JButton btn10x10 = new JButton();
		JButton btn15x15 = new JButton();

		btn5x5.setText("5 x 5");
		btn5x5.setBounds(0, 0, 100, 100);
		btn5x5.setBackground(Color.WHITE);
		btn10x10.setText("10 x 10");
		btn10x10.setBounds(50, 200, 100, 100);
		btn10x10.setBackground(Color.WHITE);
		btn15x15.setText("15 x 15");
		btn15x15.setBounds(0, 200, 100, 100);
		btn15x15.setBackground(Color.WHITE);

		add(btn5x5);
		add(btn10x10);
		add(btn15x15);

		btn5x5.addActionListener(this);
		btn10x10.addActionListener(this);
		btn15x15.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();

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