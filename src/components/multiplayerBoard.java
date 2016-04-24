package components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("serial")
public class multiplayerBoard extends Board {
	
	Paddle otherPlyr;	
	network_methods netMethods;
	
	public multiplayerBoard(network_methods netMethods){
    	super();				//calls Board() to set window settings.
		this.netMethods = netMethods;	//provides the critical network link formed previously in MainGame.
		multiplayer();	//Thread to continuously read output stream.
    }
	
	@Override
	protected void gameInit(){
		//new key adapter attached
		addKeyListener(new MultAdapter());

		//game components initializing
		ball = new Ball();
		user_paddle = new userPaddle();
		otherPlyr = new Paddle(3);
		
		timer = new Timer();
        timer.scheduleAtFixedRate(new mScheduleTask(), DELAY, PERIOD);
	}
	
	@Override
	protected void doDrawing(Graphics g){
		Graphics2D g2d = (Graphics2D) g;  
        g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(),ball.getWidth(), ball.getHeight(), this);
        g2d.drawImage(user_paddle.getImage(), user_paddle.getX(), user_paddle.getY(), user_paddle.getWidth(), user_paddle.getHeight(), this);
        g2d.drawImage(otherPlyr.getImage(), otherPlyr.getX(), otherPlyr.getY(), otherPlyr.getWidth(), otherPlyr.getHeight(), this);
	}
	
	//TimerTask which modifies the multiplayer components initialized above.
	private class mScheduleTask extends TimerTask {

        @Override
        public void run() {
            ball.move();
            
            otherPlyr.move(ball);
            new collision_ball_paddle(otherPlyr,ball);
            
            user_paddle.move(ball);
            new collision_ball_paddle(user_paddle,ball);
            repaint();
        }
    }
	
	//A new key adapter which sends key-press events
	private class MultAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
        	user_paddle.keyReleased(e);
        	try {
				netMethods.sendMessage("mov rlsd:"+e.getKeyCode());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
        }

        @Override
        public void keyPressed(KeyEvent e) {
            user_paddle.keyPressed(e);
            try {
				netMethods.sendMessage("mov prsd:"+e.getKeyCode());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
        }
    }
	
	public void multiplayer(){
		
		//netMethods.newConnection();
		multiplayerThread();
	}
	
	private void multiplayerThread(){
		new Thread(){
			public void run(){
				String line;
				try {
					while((line = netMethods.getMessage(60000)) != null){
						int key = Integer.parseInt((line.split(":", 2)[1]));
						if(line.contains("mov prsd")){
							if (key == KeyEvent.VK_LEFT) {
					            otherPlyr.dx = -2;
					        }

					        if (key == KeyEvent.VK_RIGHT) {
					        	otherPlyr.dx = 2;
					        }
						}
						else{
							if(line.contains("mov rlsd")){
								if (key == KeyEvent.VK_LEFT) {
						            otherPlyr.dx = 0;
						        }

						        if (key == KeyEvent.VK_RIGHT) {
						        	otherPlyr.dx = 0;
						        }
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
