package components;
import javax.swing.ImageIcon;

public class Ball extends Sprite implements Commons {

    private double x_dir;
    private double y_dir;

    public Ball() {

        x_dir = 1;
        y_dir = -1;

        ImageIcon ii = new ImageIcon("ball.png");
        image = ii.getImage();

        height_i = image.getHeight(null);
        width_i = image.getWidth(null);
        
        resetState();
    }

    public void move() {
    	double speed;
        if(MainGame.difficulty == "hard")
        	speed = 1.65;
        else if(MainGame.difficulty == "medium")
        	speed = 1.35;
        else
        	speed = 1;
        x += (x_dir*speed);
        y += (y_dir*speed);
        
        /*
        * Setting the direction of movement of the ball i.e., x_dir and y_dir
        * based on the collision condition with the wall
        */
        if (x <= 0) {
            setXDir(1);
        }

        if (x >= BREADTH - width_i) {
            setXDir(-1);
        }
        if (y <= 0) {
            setYDir(1);
        }
        if (y >= HEIGHT - height_i-30) {
            setYDir(-1);
        }
    }
    
    public void stop() {
    	this.x = x;
    	this.y = y;
    }

    private void resetState() {
        x = INIT_BALL_X;
        y = INIT_BALL_Y;
    }

    public void setXDir(double x) {
        x_dir = x;
    }

    public void setYDir(double y) {
        y_dir = y;
    }

    public int getYDir() {
    	Double a = new Double(y_dir);
    	int Y = a.intValue();
        return Y;
    }
    
    public int getXDir() {
    	Double a = new Double(x_dir);
    	int X = a.intValue();
        return X;
    }
}