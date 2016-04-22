import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

public class MainGame extends JFrame implements ActionListener {

	JPanel controlPanel;
    public MainGame() {
        
        initUI();
    }
    
    private void initUI() {
    	controlPanel = new JPanel();
    	
    	
    	JButton single = new JButton("Single");
        JButton multiplayer = new JButton("Multiplayer");
        JButton exit = new JButton("Exit");
        
        final JRadioButton radEasy = new JRadioButton("Easy", true);
        final JRadioButton radMedium = new JRadioButton("Medium");
        final JRadioButton radHard = new JRadioButton("Hard");
        
      //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(radEasy);
        group.add(radMedium);
        group.add(radHard);
        controlPanel.add(single);
        controlPanel.add(multiplayer);
        controlPanel.add(radEasy);
        controlPanel.add(radMedium);
        controlPanel.add(radHard);       
      
        single.addActionListener(this);
      
        controlPanel.add(exit);
        add(controlPanel);
        
        setSize(800, 800);
        setResizable(false);
        
        setTitle("Pong!");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                MainGame ex = new MainGame();
                ex.setVisible(true);
            }
        });
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				add(new Board());
				revalidate();
			
				repaint();
			}
			
		});
		
		
	}
}