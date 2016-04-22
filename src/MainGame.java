import java.awt.EventQueue;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainGame extends JFrame implements Commons{

    /**
	 * 
	 */

	public MainGame() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new Board());
       
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Commons.WIDTH, Commons.HEIGTH);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
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
}