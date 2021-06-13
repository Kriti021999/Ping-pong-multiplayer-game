package components;
import java.awt.Image;
import java.awt.Rectangle;

public class Sprite {

    protected double x;
    protected double y;
    protected int width_i;
    protected int height_i;
    protected Image image;

    public int getX() {
    	Double a = new Double(x);
    	int X = a.intValue();
        return X;
    }

    public void setX(double xx) {
        this.x = xx;
    }


    public int getY() {
    	Double a = new Double(y);
    	int Y = a.intValue();
        return Y;
    }

    public void setY(double yy) {
        this.y = yy;
    }


    public int getHeight() {
        return height_i;
    }

    public int getWidth() {
        return width_i;
    }

    Rectangle getRect() {
        Double a = new Double(x);
        int X = a.intValue();
        Double b = new Double(y);
        int Y = b.intValue();
        return new Rectangle(X, Y,
                image.getWidth(null), image.getHeight(null));
    }

    Image getImage() {
        return image;
    }
  
   
}