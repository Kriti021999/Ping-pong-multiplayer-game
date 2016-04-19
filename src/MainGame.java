import java.awt.EventQueue;
import javax.swing.JFrame;

public class MainGame extends JFrame {

    public MainGame() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new Board());
        
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
}