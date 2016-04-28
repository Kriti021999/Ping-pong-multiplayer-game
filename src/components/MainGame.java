package components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class MainGame implements Commons,ActionListener{
	public static String difficulty;
	static String username = "Saurabh";
	public static String no_ofPlayer="2";
	boolean isMultiPlayer = false;
	JPanel controlPanel,mPlyrPanel;
	JFrame frame;
	network_methods netMethods;
	public static boolean isHost = true;
	
    /**
	 * 
	 */ 
	public MainGame() {
		this.isMultiPlayer = false;
        initUI();			//Actual game initialization
    }
	
	public MainGame(JFrame mFrame, network_methods netMethods) {
		this.isMultiPlayer = true;
		this.frame = mFrame;
		this.netMethods = netMethods;
        initUI();			//Actual game initialization
    }
	
    private void initUI() {
        
    	if (!isMultiPlayer){
    		frame = new JFrame("Network Pong - P2P - v1.0");
    		addMainMenu();
    	}
    	else{
    		multiplayerBoard mb = new multiplayerBoard(netMethods);
    		mb.setFocusable(true);
    		mb.requestFocusInWindow();
    		frame.getContentPane().removeAll();
			frame.getContentPane().add(mb);
			frame.repaint();
			frame.revalidate();
    	}
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
				network_methods net = new network_methods(username,frame);
				isHost = true;
				try {
					
					net.lookForPlayers();	//Entry Point for the multi-player game when you're host
				} catch (IOException ex) {
					ex.printStackTrace();
				}				
			}
        });
        join.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				isHost = false;
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
			network_methods net = new network_methods(username, frame);
			net.joinGame(username, s);
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
		difficulty = e.getActionCommand();
	}
	
	@SuppressWarnings("serial")
	private void addMainMenu(){
		controlPanel = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.setColor(Color.WHITE);
				g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
				g.drawString("Pong!", 250, 500);

			}
		};
    	controlPanel.setLayout(new BoxLayout(controlPanel,BoxLayout.Y_AXIS));
    	controlPanel.setBackground(new Color(209,102,242,255));
    	JButton single = new JButton("Single");
    	single.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton multiplayer = new JButton("Multiplayer");
        multiplayer.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton exit = new JButton("Exit");
        exit.setAlignmentX(Component.CENTER_ALIGNMENT);
        
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
        									//<<<<<<< HEAD
        radHard.addActionListener(this);
        rad2.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		no_ofPlayer = e.getActionCommand();
        	}
        });
        rad4.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		no_ofPlayer = e.getActionCommand();
        	}
        });
        
      //Group the radio buttons.
        ButtonGroup difficulty = new ButtonGroup();
        difficulty.add(radEasy);
        difficulty.add(radMedium);
        difficulty.add(radHard);
        								//=======
        radHard.addActionListener(this);     
        	//Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(radEasy);
        group.add(radMedium);
        group.add(radHard);
        								//>>>>>>> saurabh
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

        frame.getContentPane().add(controlPanel);
        
        single.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable(){
					@Override
					public void run() {
						new JFrameGame();
					}
				});
			}
        });
        multiplayer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
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
				System.exit(0);
			}	
        });

    }
}