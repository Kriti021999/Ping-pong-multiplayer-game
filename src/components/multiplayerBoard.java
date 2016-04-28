package components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

import components.Board.TAdapter;

@SuppressWarnings("serial")
public class multiplayerBoard extends Board {
	
	Paddle otherPlyr;	
	private boolean playing = true;
	private boolean replaced = true;
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
		addKeyListener(new TAdapter());
		//game components initializing
		ball = new Ball();
		if(MainGame.isHost){
			user_paddle = new userPaddle(1);
			otherPlyr = new Paddle(3);
		}
		else{
			user_paddle = new userPaddle(3);
			otherPlyr = new Paddle(1);
		}
		life[1].setText(""+otherPlyr.life);
		life[0].setText(""+user_paddle.life+" ::");
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
        	if(MainGame.isHost){
        	g.drawString(String.valueOf(user_paddle.life), 290, 550);
        	g.drawString(String.valueOf(otherPlyr.life), 290, 50);
        	}
        	else{
        		g.drawString(String.valueOf(otherPlyr.life), 290, 550);
            	g.drawString(String.valueOf(user_paddle.life), 290, 50);
        	}
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
        		if(MainGame.isHost)
        			ball.stop();
        		//display paddle is losing
        		//add(new JLabel(""+paddlelose));
        	}
        	else{
        		if(MainGame.isHost)
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
           
            try {	
    				DataOutputStream output = new DataOutputStream(netMethods.socket.getOutputStream());
    				if(MainGame.isHost)
    					output.writeUTF("ball "+ball.x+" "+ball.y);
        			output.writeUTF("life " + user_paddle.life);
    				output.writeUTF("paddle "+user_paddle.x+" "+user_paddle.y);
    		} catch (IOException ex) {
    			//ex.printStackTrace();
    			System.out.println("disconnected");
    			MainGame.difficulty = "medium";
    			if(replaced){
    				otherPlyr = new cpuPaddle(MainGame.isHost?3:1);
    				replaced = false;
    			}
    			
    		}
            repaint();
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
					DataInputStream input = new DataInputStream(netMethods.socket.getInputStream());
					while((line = input.readUTF()) != null){
						System.out.println("the line is :"+line);
						if(line.contains("paddle")){
							String[] words = line.split(" ");
							System.out.println("called");
							otherPlyr.x = Double.parseDouble(words[1]);
							otherPlyr.y = Double.parseDouble(words[2]);
						}
						else if(line.contains("ball")){
							if(!MainGame.isHost){
								String[] words = line.split(" ");
								ball.x = Double.parseDouble(words[1]);
								ball.y = Double.parseDouble(words[2]);
							}
						}
						else if(line.contains("life")){
							String[] words = line.split(" ");
							otherPlyr.life = Integer.parseInt(words[1]);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	public class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
        	user_paddle.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
        	user_paddle.keyPressed(e);    	
        	int key = e.getKeyCode();
        	 if (key == KeyEvent.VK_R) {
                 new MainGame();
             }
        }
    }
}

