import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class Tile extends JLabel {
  private boolean solid;
  private int xComp;
  private int yComp;

  public Tile(boolean solid) {
    super();
    setSolid(solid);
  }

  public int getxComp() {
    return xComp;
  }

  public void setxComp(int xComp) {
    this.xComp = xComp;
  }

  public int getyComp() {
    return yComp;
  }

  public void setyComp(int yComp) {
    this.yComp = yComp;
  }

	public boolean getSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}
}