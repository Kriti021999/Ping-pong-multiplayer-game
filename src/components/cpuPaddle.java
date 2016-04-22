package components;

public class cpuPaddle extends Paddle{

	public cpuPaddle(int side) {
		super(side);
	}
	
	@Override
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
	

}
