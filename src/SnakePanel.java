import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakePanel extends JPanel  {
	private static final long serialVersionUID = 1L;
	private final int SIZE_PANEL = 600;
	private Timer timer = new Timer(10, null);
	private int speed;
	

	public static void main(String[] args) {
		JFrame frame = new JFrame("SNAKE!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new SnakePanel());
		frame.pack();
		frame.setVisible(true);

	}
	public SnakePanel() {
		speed = 2;
		this.setPreferredSize(new Dimension(this.SIZE_PANEL,SIZE_PANEL));
		timer.addActionListener(new ActionListener() {

			/**
			 * This method is called every time the timer goes off.  The Timer can be scheduled
			 * to go off at different intervals (shorter intervals makes actions go faster).
			 * I suggest the following actions:
			 *    -- Have all objects move (if they are moveable)
			 *    -- Check for collisions (react accordingly)
			 *    -- update score, snake, other stuff
			 *    -- repaint
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				board.tick();
				if(!board.snakeInbounds()) {
					timer.stop();
					int x = JOptionPane.showConfirmDialog(null, "GAME OVER! \n Play again?" , null, JOptionPane.YES_NO_OPTION);
					if(x==0) {
						board.reset();
						timer.restart();
					}
					else
						setVisible(false);
				}
				repaint();
			}
			
		});
		this.addKeyListener(new KeyAdapter() {
			/**
			 * This method is called every time a key is pressed.  In this game, what
			 * happens when a key is pressed?  That behavior should be echoed in the game.
			 */
			@Override
			public void keyPressed(KeyEvent key) {
				board.keyPressed(key);
				repaint();
			}
		});
		timer.start();
	}
	
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	SnakeBoard board = new SnakeBoard(13,13);
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(!this.hasFocus())
			this.requestFocusInWindow();
		board.draw(g);
		// ask your objects to draw themselves.		
	}

}

