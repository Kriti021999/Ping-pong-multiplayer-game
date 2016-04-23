package components;
import java.lang.Math;
public class collision_ball_paddle {
	
    int collision_area;
	collision_ball_paddle(Paddle paddle,Ball ball){
		checkCollision (paddle,ball);
	}
	
    private void checkCollision(Paddle pad, Ball ball) {
    	 
    	
    	if ((ball.getRect()).intersects(pad.getRect())){
    		
    		if(pad.side==1 || pad.side==3){
    			int paddlePos = (int) pad.getRect().getMinX();
    			int ballPos = (int) ball.getRect().getCenterX();

    			int up_down;
    			if (pad.side==1){up_down = -1;}
    			else{up_down = 1;}
    			
    			
    			if( Math.abs(paddlePos-ballPos) < pad.i_width/5){
					ball.setXDir(-1);
					ball.setYDir(up_down);
				}
    				if (Math.abs(paddlePos-ballPos) > pad.i_width/5 && Math.abs(paddlePos-ballPos) < 2*pad.i_width/5){
        				//ball.setXDir(-1);
        				ball.setYDir(-1 * ball.getYDir());
        			}    				
    				if (Math.abs(paddlePos-ballPos) > 2*pad.i_width/5 && Math.abs(paddlePos-ballPos) < 3*pad.i_width/5){
    					ball.setXDir(0.5);
    					ball.setYDir(up_down);
    				}
    				if (Math.abs(paddlePos-ballPos) > 3*pad.i_width/5 && Math.abs(paddlePos-ballPos) < 4*pad.i_width/5){
        				//ball.setXDir(1);
        				ball.setYDir(-1 * ball.getYDir());
        			} 
    				if (Math.abs(paddlePos-ballPos) > 4*pad.i_width/5 && Math.abs(paddlePos-ballPos) < pad.i_width){
        				ball.setXDir(1);
        				ball.setYDir(up_down);
        			} 
   		}
    		else{
    			int paddlePos = (int) pad.getRect().getMinY();
    			int ballPos = (int) ball.getRect().getCenterY();

    			int left_right;
    			if (pad.side==2){left_right = 1;}
    			else{left_right = -1;}
				  			
    			
    			if( Math.abs(paddlePos-ballPos) < pad.i_height/5){
					
    				ball.setYDir(-1);
					ball.setXDir(left_right);
					System.out.println(1); 
				}
    			
    				if (Math.abs(paddlePos-ballPos) > pad.i_height/5 && Math.abs(paddlePos-ballPos) < 2*pad.i_height/5){
    					
//    					ball.setYDir(-1);
        				ball.setXDir(-1 * ball.getXDir());
    					System.out.println(2);
        			}    				
    				if (Math.abs(paddlePos-ballPos) > 2*pad.i_height/5 && Math.abs(paddlePos-ballPos) < 3*pad.i_height/5){

    					ball.setYDir(0.5);
    					ball.setXDir(left_right);
    					System.out.println(3);
    				}
    				if (Math.abs(paddlePos-ballPos) > 3*pad.i_height/5 && Math.abs(paddlePos-ballPos) < 4*pad.i_height/5){
    					
//    					ball.setYDir(1);
    					ball.setXDir(-1 * ball.getXDir());
    					System.out.println(4);
    				}
    				if (Math.abs(paddlePos-ballPos) > 4*pad.i_height/5 && Math.abs(paddlePos-ballPos) < 5*pad.i_height/5){
    					
    					ball.setYDir(-1);
    					ball.setXDir(left_right);
    					System.out.println(5);
    				}

    			/*if( Math.abs(paddlePos-ballPos) < pad.i_height/5){
					ball.setYDir(0);
					ball.setXDir(left_right);
				}
    			else{
    				if (ballPos < paddlePos){
        				ball.setYDir(-1);
        				ball.setXDir(left_right);
        			}    				
    				else{
    					ball.setYDir(1);
    					ball.setXDir(left_right);
    				}
    			}*/
    		}
    	}
    }
}
