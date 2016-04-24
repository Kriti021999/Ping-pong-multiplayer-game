package components;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

import components.Board.TAdapter;

@SuppressWarnings("serial")
	
public class MainGame extends JFrame implements Commons,ActionListener{
	public static String difficulty;
	public static String no_ofPlayer="2";
    /**
	 * 
	 */
	JPanel controlPanel;
	
	//constructor
	public MainGame() {      
        initUI();
    }
    
    private void initUI() {
        
    	controlPanel = new JPanel(){
    		 public void paintComponent(Graphics g){
    		        super.paintComponent(g);
    		        g.setColor(Color.WHITE);
    		        g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
    	            g.drawString("Pong!", 250, 50);

    		 }
    	};
    	controlPanel.setLayout(new GridBagLayout());
    	controlPanel.setBackground(new Color(209,102,242,255));
    	JButton single = new JButton("Single");
        JButton multiplayer = new JButton("Multiplayer");
        JButton exit = new JButton("Exit");
        
        final JRadioButton radEasy = new JRadioButton("Easy", true);
        radEasy.setActionCommand("easy");
        final JRadioButton radMedium = new JRadioButton("Medium");
        radMedium.setActionCommand("medium");
        final JRadioButton radHard = new JRadioButton("Hard");
        radHard.setActionCommand("hard");
        
        final JRadioButton rad2 = new JRadioButton("2",true);
        rad2.setActionCommand("2");
        final JRadioButton rad4 = new JRadioButton("4");
        rad4.setActionCommand("4");

        radEasy.addActionListener(this);
        radMedium.addActionListener(this);
        radHard.addActionListener(this);
        rad2.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		// TODO Auto-generated method stub
        		no_ofPlayer = e.getActionCommand();
        	}
        });
        rad4.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		// TODO Auto-generated method stub
        		no_ofPlayer = e.getActionCommand();
        	}
        });
        
      //Group the radio buttons.
        ButtonGroup difficulty = new ButtonGroup();
        difficulty.add(radEasy);
        difficulty.add(radMedium);
        difficulty.add(radHard);
        controlPanel.add(single);
        controlPanel.add(radEasy);
        controlPanel.add(radMedium);
        controlPanel.add(radHard); 
        
        ButtonGroup no_of_player = new ButtonGroup();
        no_of_player.add(rad2);
        no_of_player.add(rad4);
        controlPanel.add(rad2);
        controlPanel.add(rad4);
        
        controlPanel.add(multiplayer);      
        controlPanel.add(exit);
        getContentPane().add(controlPanel);
        
        single.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SwingUtilities.invokeLater(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						new JFrameGame();
						//Board b = new Board();
						//getContentPane().removeAll();
						//getContentPane().add(b);
						//repaint();
						//revalidate();
					}
					
				});
			}
        	
        });
        
        exit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
        	
        });
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Commons.WIDTH, Commons.HEIGHT);
        setTitle("Pong!");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        
    	 SwingUtilities.invokeLater(new Runnable() {
             @Override
             public void run() {
            	 new MainGame();
             }
         });
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		difficulty = e.getActionCommand();
	}
}