package components;

import java.util.Random;

public class cpuPaddle extends Paddle{

	public cpuPaddle(int side) {
		super(side);
	}
	
	@Override
	public void move(Ball b) { 
		double sp;
		if(MainGame.difficulty == "hard"){
			Random r = new Random();
    	    sp = 1 + (1.1 - 1) * r.nextDouble();
    	    }
        else if(MainGame.difficulty == "medium"){
        	Random r = new Random();
        	sp = 0.9 + (1 - 0.9) * r.nextDouble();
        	}
        else{
        	Random r = new Random();
            sp = 0.85 + (0.9 - 0.85) * r.nextDouble();
        	}
    	if (side==1 || side==3){
    		if(b.getX() < this.x){
    			this.dx =-1*sp;
    		}
    		if(b.getX() > this.x){
    			this.dx= 1*sp;
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
    	           this.dy =-1*sp;
    	    	}
    	    	if(b.getY() > this.y){
    	    		this.dy = 1*sp;
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
	

}
