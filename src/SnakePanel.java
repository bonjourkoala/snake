import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SnakePanel extends JPanel  {
	private static final long serialVersionUID = 1L;
	private final int SIZE_PANEL = 600;
	private Timer timer = new Timer(10, null), movetimer = new Timer(300, null);


	public static void main(String[] args) {
		JFrame frame = new JFrame("SNAKE!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SnakePanel sp = new SnakePanel();
		frame.add(sp);
		frame.pack();
		frame.setVisible(true);
	}

	public SnakePanel() {
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(this.SIZE_PANEL,SIZE_PANEL));
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent me) {
				board.justClicked(me);
				if(board.clickedPausePlay(me.getX(), me.getY())) {
					if(board.getClicks()%2==1) {
						timer.stop();
						movetimer.stop();
					}
					else {
						timer.restart();
						movetimer.restart();
					}
				}
				repaint();
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
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
				board.snakeInbounds();
				if(!board.getSnake().isInGame()) {
					timer.stop();
					movetimer.stop();
					int x = JOptionPane.showConfirmDialog(null, "GAME OVER! \n Play again?" , null, JOptionPane.YES_NO_OPTION);
					if(x==0) {
						board.reset();
						timer.restart();
						movetimer.restart();
					}
					else {
						setVisible(false);
					}
				}
				else {
					board.tick();
				}
			}
		});
		movetimer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				board.moveTick();
				repaint();
				movetimer.setDelay(board.getSpeed());
			}
		});
		this.addKeyListener(new KeyAdapter() {
			/**This method is called every time a key is pressed.  In this game, what
			 * happens when a key is pressed?  That behavior should be echoed in the game.
			 */
			@Override
			public void keyPressed(KeyEvent key) {
				board.keyPressed(key);
			}
		});
		timer.start();
		movetimer.start();
	}

	//must be odd numbers (r,c)
	SnakeBoard board = new SnakeBoard(19,21);

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(!this.hasFocus())
			this.requestFocusInWindow();
		board.draw(g);
		// ask your objects to draw themselves	
	}


}

