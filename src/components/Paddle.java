package components;
import javax.swing.ImageIcon;

public class Paddle extends Sprite implements Commons {

    double dx,dy;
    int side;
    int score=0;
    int life=3;

    public Paddle(int side) {
    	this.side = side;
    	
    	ImageIcon ii;
    	if(side==1 || side==3){
    		ii = new ImageIcon("paddle.png");
    	}
    	else{
    		ii = new ImageIcon("paddle_rotated.png");
    	}
        image = ii.getImage();

        this.width_i = image.getWidth(null);
        this.height_i = image.getHeight(null);

        resetState();
    }

    public void move(Ball b) {
    }

    private void resetState() {
    	if(side==4){			//right side
    		this.x = BREADTH-this.getWidth()-30;
    		this.y = HEIGHT/2;
    	}
        if(side==3){            //top side
            this.x = BREADTH/2;
            this.y = 10;
        }
        if(side==2){            //left side
            this.x = 30;
            this.y = HEIGHT/2;
        }
        if(side==1){            //down side
            this.x = BREADTH/2;
            this.y = HEIGHT-this.getHeight()-30;
        }
    	
    }
}