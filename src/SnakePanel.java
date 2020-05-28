import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SnakePanel extends JPanel  {
	//must be odd numbers for grid to appear correctly
	private final int ROWS = 19, COLS = 21;
	private final int SIZE_PANEL = 600;
	private SnakeBoard board = new SnakeBoard(ROWS,COLS);
	private static boolean visible = true;
	private Timer timer = new Timer(10, null), movetimer = new Timer(300, null);


	public static void main(String[] args) {
		Timer t = new Timer(10, null);
		t.start();
		JFrame frame = new JFrame("SNAKE!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SnakePanel sp = new SnakePanel();
		frame.add(sp);
		frame.pack();
		frame.setVisible(true);
		while(t.isRunning()) {
			if(!SnakePanel.visible){
				frame.dispose();
				t.stop();
			}
		}
	}

	public SnakePanel() {
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(this.SIZE_PANEL,SIZE_PANEL));
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent me) {
				board.justClicked(me);
				/**if pause/play is clicked, stops or starts timers based 
				 * on number of clicks 
				 */
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

			@Override
			public void actionPerformed(ActionEvent e) {
					board.tick();
					repaint();
			}
		});
		movetimer.addActionListener(new ActionListener() {
			/**   -- Check if the snake is in game
			 *    -- Call the board's moveTick method
			 *    -- update timer based on level
			 *    -- repaint
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!board.getSnake().isInGame()) {
					timer.stop();
					movetimer.stop();
					repaint();
					int x = JOptionPane.showConfirmDialog(null, "GAME OVER! \n Play again?" , null, JOptionPane.YES_NO_OPTION);
					if(x==0) {
						board.reset();
						timer.restart();
						movetimer.restart();
					}
					else 
						visible = false;
				}
				else {
					board.moveTick();
					movetimer.setDelay(board.getSpeed());
				}
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

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(!this.hasFocus())
			this.requestFocusInWindow();
		board.draw(g);
		// ask your objects to draw themselves	
	}

}

