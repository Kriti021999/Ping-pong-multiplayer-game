package components;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import components.collision_ball_paddle;


@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private ArrayList<Paddle> paddle = new ArrayList<Paddle>();
    private Ball ball;
    public static final int DELAY = 1000;
    public static final int PERIOD = 10;
    
    public Board() {

        initBoard();
    }
    
    private void initBoard() {
        
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);

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
    	for(int i=1;i<5;i++){
    		Paddle pad = new Paddle(i);
    		paddle.add(pad);
    	}
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
        for(int i=0;i<4;i++){
        	g2d.drawImage(paddle.get(i).getImage(), paddle.get(i).getX(), paddle.get(i).getY(), paddle.get(i).getWidth(), paddle.get(i).getHeight(), this);
        }
    }
    
    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {

            ball.move();
            for(int i=0;i<4;i++){
            	paddle.get(i).move(ball);
            	new collision_ball_paddle(paddle.get(i),ball);
            }
            repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        //paddle.move(ball);
        repaint();  
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            //paddle.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            //paddle.keyPressed(e);
        }
    }
}