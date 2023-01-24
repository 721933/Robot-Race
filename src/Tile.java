import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import java.util.HashMap;

public class Tile extends JLabel {
  private Type type;
  private int xComp;
  private int yComp;

  private static HashMap<Type, Image> images = generateHashmap();

  public Tile(Type type) {
    super();

    setType(type);
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

  public Type getType() {
    return this.type;
  }

  public void setType(Type type) {
    this.type = type;

    try {
      this.setIcon(new javax.swing.ImageIcon(images.get(type)));
    } catch (IndexOutOfBoundsException e) {

    }
  }

  public void reveal() {
    if (this.type.equals(Type.UnknownLiquid)) {
      setType(Type.Liquid);
    } else if (this.type.equals(Type.UnknownSolid)) {
      setType(Type.Solid);
    }

    return;
  }

  private static HashMap<Type, Image> generateHashmap() {
    HashMap<Type, Image> map = new HashMap<Type, Image>();

    try {
      Image image = ImageIO.read(new File("images/solid.png"));
      map.put(Type.Solid, image);
    } catch (IOException e) {

    }

    try {
      Image image = ImageIO.read(new File("images/liquid.png"));
      map.put(Type.Liquid, image);
    } catch (IOException e) {

    }

    try {
      Image image = ImageIO.read(new File("images/start.png"));
      map.put(Type.Start, image);
    } catch (IOException e) {

    }

    try {
      Image image = ImageIO.read(new File("images/end.png"));
      map.put(Type.End, image);
    } catch (IOException e) {

    }

    try {
      Image image = ImageIO.read(new File("images/unknown.png"));
      map.put(Type.UnknownSolid, image);
    } catch (IOException e) {

    }

    try {
      Image image = ImageIO.read(new File("images/unknown.png"));
      map.put(Type.UnknownLiquid, image);
    } catch (IOException e) {

    }

    return map;
  }
}