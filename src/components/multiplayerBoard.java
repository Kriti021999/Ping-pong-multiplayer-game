package components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class multiplayerBoard extends Board {
	
	Paddle otherPlyr;	
	private boolean playing = true;
	network_methods netMethods;
	
	public multiplayerBoard(network_methods netMethods){
    	super();				//calls Board() to set window settings.
		this.netMethods = netMethods;	//provides the critical network link formed previously in MainGame.
		this.isMultiplayer = true;
		multiplayer();	//Thread to continuously read output stream.
    }
	
	@Override
	protected void gameInit(){
		
		life = new JLabel[2];
		for(int i=0;i<2;i++){
    		//score[i] = new JLabel("sc");
    		life[i] = new JLabel("lf");
    		//add(score[i]);
    		add(life[i]);
    	}
		System.out.println("addkeylistener");
		//new key adapter attached
		addKeyListener(new MultAdapter());
		
		//game components initializing
		ball = new Ball();
		user_paddle = new userPaddle();
		life[0].setText(""+user_paddle.life+" ::");
		otherPlyr = new Paddle(3);
		life[1].setText(""+otherPlyr.life);
		
		timer = new Timer();
        timer.scheduleAtFixedRate(new mScheduleTask(), DELAY, PERIOD);
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(playing){
        	Graphics2D g2d = (Graphics2D) g;

        	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        			RenderingHints.VALUE_ANTIALIAS_ON);

        	g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
        			RenderingHints.VALUE_RENDER_QUALITY);

        	doDrawing(g2d);
        	g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
        	g.drawString(String.valueOf(user_paddle.life), 290, 550);
        	g.drawString(String.valueOf(otherPlyr.life), 290, 50);
        	Toolkit.getDefaultToolkit().sync();
        }
        else{
        	 g.setColor(Color.WHITE);
        	 g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
             g.drawString("Player "+paddlelose+" loses", 165, 250);
        }
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
            
            if(otherPlyr.life==0||user_paddle.life==0){
        		if(otherPlyr.life==0)
        			paddlelose = ""+otherPlyr.side;
        		else if(user_paddle.life==0)
                	paddlelose = ""+user_paddle.side;
        		ball.stop();
        		//display paddle is losing
        		//add(new JLabel(""+paddlelose));
        	}
        	else{
        		ball.move();
        		otherPlyr.move(ball);
        		new collision_ball_paddle(otherPlyr,ball);
        		//score[i+1].setText(""+paddle.get(i).score);
        		life[1].setText(""+otherPlyr.life);
        		user_paddle.move(ball);
        		new collision_ball_paddle(user_paddle,ball);
        		//score[0].setText(""+user_paddle.score);
        		life[0].setText(""+user_paddle.life+" ::");
        	}
            repaint();
        }
    }
	
	//A new key adapter which sends key-press events
	private class MultAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
        	user_paddle.keyReleased(e);
        	//System.out.println("mov rlsd:"+e.getKeyCode());
        	try {
        		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
        			netMethods.sendMessage("mov rlsd:"+e.getKeyCode());
        		}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
        }

        @Override
        public void keyPressed(KeyEvent e) {
        	//System.out.println("mov prsd:"+e.getKeyCode());
        	if(playing){
        		user_paddle.keyPressed(e);
        		try {
        			if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
        				netMethods.sendMessage("mov prsd:"+e.getKeyCode());
        			}
        		} catch (IOException ex) {
        			ex.printStackTrace();
        		}
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
					while((line = netMethods.getMessage(30000)) != null){
						System.out.println("called");
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
