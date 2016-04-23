package components;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;

import components.collision_ball_paddle;


@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {
	int noCPU;
	private Timer timer;
    private ArrayList<Paddle> paddle = new ArrayList<Paddle>();
    private userPaddle user_paddle;
    private Ball ball;
    public static final int DELAY = 1000;
    public static final int PERIOD = 10;
    public JLabel score[];
    public JLabel life[];
    public Board() {
    	if(MainGame.no_ofPlayer=="2")
        	noCPU = 1;
        else
        	noCPU = 3;
    	System.out.println(MainGame.no_ofPlayer);
    	initBoard();
    }
    private void initBoard() {
    	score = new JLabel[noCPU+1];
    	life = new JLabel[noCPU+1];
    	for(int i=0;i<noCPU+1;i++){
    		score[i] = new JLabel("sc");
    		life[i] = new JLabel("lf");
    		add(score[i]);
    		add(life[i]);
    	}
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.cyan);

        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), DELAY, PERIOD);
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        gameInit();
    }
    
    private void gameInit(){
    	ball = new Ball();
    	for(int i=1;i<noCPU+1;i++){
    		cpuPaddle pad;
    		if(noCPU == 1)
    			pad = new cpuPaddle(i+2);
    		else
    			pad = new cpuPaddle(i+1);
    		paddle.add(pad);
    		score[i].setText((""+pad.score));
    		life[i].setText(""+pad.life+" :");
    	}
    	user_paddle = new userPaddle();
    	score[0].setText(""+user_paddle.score);
    	life[0].setText(""+user_paddle.life+" :");
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

         doDrawing(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;  
        g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(),ball.getWidth(), ball.getHeight(), this);
        for(int i=0;i<noCPU;i++){
        	g2d.drawImage(paddle.get(i).getImage(), paddle.get(i).getX(), paddle.get(i).getY(), paddle.get(i).getWidth(), paddle.get(i).getHeight(), this);
        }
        g2d.drawImage(user_paddle.getImage(), user_paddle.getX(), user_paddle.getY(), user_paddle.getWidth(), user_paddle.getHeight(), this);
    }
    
    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {
        	String paddlelose="";
        	if(MainGame.no_ofPlayer=="4"){
            	if(paddle.get(0).life==0||paddle.get(1).life==0||paddle.get(2).life==0||user_paddle.life==0){
            		if(paddle.get(0).life==0)
            			paddlelose = ""+paddle.get(0).side;
            		else if(paddle.get(1).life==0)
            			paddlelose = ""+paddle.get(1).side;
            		else if(paddle.get(2).life==0)
            			paddlelose = ""+paddle.get(2).side;
            		else if(user_paddle.life==0)
                    	paddlelose = ""+user_paddle.side;
            		ball.stop();
            		//display paddle is losing
            		//add(new JLabel(""+paddlelose));
            	}
            	else{
            		ball.move();
                    for(int i=0;i<noCPU;i++){
                    	paddle.get(i).move(ball);
                    	new collision_ball_paddle(paddle.get(i),ball);
                    	score[i+1].setText(""+paddle.get(i).score);
                    	life[i+1].setText(""+paddle.get(i).life+" :");
                    }
                    user_paddle.move(ball);
                    new collision_ball_paddle(user_paddle,ball);
                    score[0].setText(""+user_paddle.score);
                    life[0].setText(""+user_paddle.life+" :");
            	}
        	}
        	else{
        		if(paddle.get(0).life==0||user_paddle.life==0){
            		if(paddle.get(0).life==0)
            			paddlelose = ""+paddle.get(0).side;
            		else if(user_paddle.life==0)
                    	paddlelose = ""+user_paddle.side;
            		ball.stop();
            		//display paddle is losing
            		//add(new JLabel(""+paddlelose));
            	}
            	else{
            		ball.move();
                    for(int i=0;i<noCPU;i++){
                    	paddle.get(i).move(ball);
                    	new collision_ball_paddle(paddle.get(i),ball);
                    	score[i+1].setText(""+paddle.get(i).score);
                    	life[i+1].setText(""+paddle.get(i).life+" :");
                    }
                    user_paddle.move(ball);
                    new collision_ball_paddle(user_paddle,ball);
                    score[0].setText(""+user_paddle.score);
                    life[0].setText(""+user_paddle.life+" :");
            	}
        	}
                repaint(); 
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        //paddle.move(ball);
        repaint();  
    }

    public class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
        	user_paddle.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            user_paddle.keyPressed(e);
        }
    }
}