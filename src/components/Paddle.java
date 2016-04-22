package components;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Paddle extends Sprite implements Commons {

    private int dx,dy;
    int side;

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

        this.i_width = image.getWidth(null);
        this.i_height = image.getHeight(null);

        resetState();
    }

    public void move(Ball b) {
    
    	if (side==1 || side==3){
    		if(b.getX() < this.x){
    			this.dx =-1;
    		}
    		if(b.getX() > this.x){
    			this.dx= 1;
    		}
    		if (this.x <= 0) {
    			this.x = 0;
    		}
    		if (this.x >= WIDTH - i_width) {
    			this.x = WIDTH - i_width;
    		}
    		this.x += this.dx;
    	}
    	else{
    		if(b.getY() < this.y){
    	           this.dy =-1;
    	    	}
    	    	if(b.getY() > this.y){
    	    		this.dy= 1;
    	    	}
    	        if (this.y <= 0) {
    	            this.y = 0;
    	        }
    	        if (this.y >= HEIGHT - i_height-30) {
    	            this.y = HEIGHT - i_height-30;
    	        }
    	    	this.y += this.dy;
    	}
    	
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            this.dx = -1;
        }

        if (key == KeyEvent.VK_RIGHT) {
        	this.dx = 1;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            this.dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
        	this.dx = 0;
        }
    }

    private void resetState() {
    	if(side==1){			//down side
    		this.x = WIDTH/2;
            this.y = HEIGHT-this.getHeight()-30;
    	}
    	if(side==2){			//left side
    		this.x = 30;
            this.y = HEIGHT/2;
    	}
    	if(side==3){			//top side
    		this.x = WIDTH/2;
    		this.y = 10;
    	}
    	if(side==4){			//right side
    		this.x = WIDTH-this.getWidth()-30;
    		this.y = HEIGHT/2;
    	}
    	
    }
}