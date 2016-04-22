package components;
import java.lang.Math;
public class collision_ball_paddle {
	
	collision_ball_paddle(Paddle paddle,Ball ball){
		checkCollision (paddle,ball);
	}
	
    private void checkCollision(Paddle pad, Ball ball) {
    	
    	if ((ball.getRect()).intersects(pad.getRect())){
    		
    		if(pad.side==1 || pad.side==3){
    			int paddlePos = (int) pad.getRect().getCenterX();
    			int ballPos = (int) ball.getRect().getCenterX();

    			int up_down;
    			if (pad.side==1){up_down = -1;}
    			else{up_down = 1;}

    			if( Math.abs(paddlePos-ballPos) < pad.i_width/5){
					ball.setXDir(0);
					ball.setYDir(up_down);
				}
    			else{
    				if (ballPos < paddlePos){
    					System.out.println(String.format("%d %d %d \n",ballPos,paddlePos,(pad.i_width/10)));
        				ball.setXDir(-1);
        				ball.setYDir(up_down);
        			}    				
    				else{
    					ball.setXDir(1);
    					ball.setYDir(up_down);
    				}
    			}
    		}
    		else{
    			int paddlePos = (int) pad.getRect().getCenterY();
    			int ballPos = (int) ball.getRect().getCenterY();

    			int left_right;
    			if (pad.side==2){left_right = 1;}
    			else{left_right = -1;}

    			if( Math.abs(paddlePos-ballPos) < pad.i_height/5){
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
    			}
    		}
    	}
    }
}
