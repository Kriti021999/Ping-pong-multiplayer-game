package components;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
	
public class MainGame extends JFrame implements Commons,ActionListener{
	public static String difficulty;
    /**
	 * 
	 */
	JPanel controlPanel;
	public MainGame() {
        
        initUI();
    }
    
    private void initUI() {
        
    	controlPanel = new JPanel();
    	controlPanel.setLayout(new GridBagLayout());
    	
    	JButton single = new JButton("Single");
        JButton multiplayer = new JButton("Multiplayer");
        JButton exit = new JButton("Exit");
        
        final JRadioButton radEasy = new JRadioButton("Easy", true);
        radEasy.setMnemonic(KeyEvent.VK_E);
        radEasy.setActionCommand("easy");
        final JRadioButton radMedium = new JRadioButton("Medium");
        radMedium.setMnemonic(KeyEvent.VK_M);
        radMedium.setActionCommand("medium");
        final JRadioButton radHard = new JRadioButton("Hard");
        radHard.setMnemonic(KeyEvent.VK_H);
        radHard.setActionCommand("hard");
        
        radEasy.addActionListener(this);
        radMedium.addActionListener(this);
        radHard.addActionListener(this);
        
      //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(radEasy);
        group.add(radMedium);
        group.add(radHard);
        controlPanel.add(single);
        controlPanel.add(radEasy);
        controlPanel.add(radMedium);
        controlPanel.add(radHard); 
        controlPanel.add(multiplayer);      
        controlPanel.add(exit);
        getContentPane().add(controlPanel);
        //difficulty = "hard";
        
        single.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SwingUtilities.invokeLater(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						getContentPane().removeAll();
						getContentPane().add(new Board());
						repaint();
						revalidate();
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