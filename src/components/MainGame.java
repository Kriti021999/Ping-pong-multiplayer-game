package components;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


@SuppressWarnings("serial")
	
public class MainGame implements Commons,ActionListener{
	public static String difficulty;
    /**
	 * 
	 */
	JPanel controlPanel,mPlyrPanel;
	JFrame frame; 
	public MainGame() {
        initUI();			//Actual game initialization
    }
	
    private void initUI() {
        
    	frame = new JFrame("Network Pong - P2P - v1.0");
    	
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
        frame.getContentPane().add(controlPanel);
        difficulty = "hard";
              
        
        single.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SwingUtilities.invokeLater(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						frame.getContentPane().removeAll();
						frame.getContentPane().add(new Board());
						frame.repaint();
						frame.revalidate();
					}
					
				});
			}
        	
        });
        
        multiplayer.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.getContentPane().removeAll();
				addMultiplayerMenu();
				frame.getContentPane().add(mPlyrPanel);
				frame.repaint();
				frame.revalidate();				
			}
        	
        });
        
        exit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
        	
        });
                
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Commons.WIDTH, Commons.HEIGHT);
        //frame.setTitle("Pong!");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    private void addMultiplayerMenu(){
    	mPlyrPanel = new JPanel();
        mPlyrPanel.setLayout(new GridBagLayout());
        JButton host = new JButton("host");
        JButton join = new JButton("join");
        JLabel  iplabel= new JLabel("Enter IP:", JLabel.RIGHT);        
        final JTextField ipText = new JTextField(17);
               
        host.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				multiplayerBoard mb = new multiplayerBoard();
				try {
					mb.lookForPlayers();	//Entry Point for the multi-player game when you're host
				} catch (IOException ex) {
					ex.printStackTrace();
				}				
			}
        	
        });
        
        join.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String ip = ipText.getText();
				System.out.println("IP: "+ip);
				connectToGame(ip);
			}
        	
        });
        mPlyrPanel.add(host);
        mPlyrPanel.add(iplabel);
        mPlyrPanel.add(ipText);
        mPlyrPanel.add(join);
        frame.setVisible(true);
    }
    
    public void connectToGame(String ip){
		Socket s = null;
		try {
			s = new Socket(ip, GAMEPORT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("Unknown Host");
			return;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to establish connection to host");
			return;
		}
		try {
			multiplayerBoard mb = new multiplayerBoard();
			mb.joinGame("Saurabh1", s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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