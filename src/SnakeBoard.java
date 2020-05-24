import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class SnakeBoard {
	public static final int OFFSET_X = 40, OFFSET_Y = 80, PAUSE_X = 500,
			PAUSE_Y = 10, BUTTON_SIZE = 36, RESET_X = 450, RESET_Y = 10; 
	public Image pause, play, reset;
	private GameObject[][] grid;
	private Apple apple;
	private int rows, cols, points, seconds, clicks;
	private Snake snake;

	public SnakeBoard(int r, int c) {
		setUpImages();
		grid = new GameObject[r][c];
		rows = r;
		cols = c;
		fill();
		reset();
	}

	private void setUpImages() {
		if(pause == null) {                                                                                      
			try {                                                                                                                             
				Image pause1 = ImageIO.read(new File("pause.png"));   
				pause = ((BufferedImage)pause1).getScaledInstance
						(GameObject.SQUARE_SIZE, GameObject.SQUARE_SIZE, 
								BufferedImage.SCALE_SMOOTH);
				Image play1 = ImageIO.read(new File("play.png"));   
				play = ((BufferedImage)play1).getScaledInstance
						(GameObject.SQUARE_SIZE, GameObject.SQUARE_SIZE, 
								BufferedImage.SCALE_SMOOTH);
				Image reset1 = ImageIO.read(new File("reset.png"));   
				reset = ((BufferedImage)reset1).getScaledInstance
						(GameObject.SQUARE_SIZE, GameObject.SQUARE_SIZE, 
								BufferedImage.SCALE_SMOOTH);
			}                                                                                                                                 
			catch (IOException e) {	                                                                                                          
				e.printStackTrace();                                                                                                          
			}                                                                                                                                 
		}     		
	}

	public void reset() {
		snake = new Snake();
		seconds = 0;
		points = 0;
		addApple();		
	}

	private void addApple() {
		int r = (int) (Math.random()*rows);
		int c = (int) (Math.random()*cols);
		apple = new Apple(r,c);
	}

	private void fill() {
		for(int r=0; r<grid.length; r++) {
			for(int c=0; c<grid[r].length; c++) {
				grid[r][c]=new GameObject(r,c);
			}
		}
	}

	public void draw(Graphics g) {
		int count = 0;
		for(int r=0; r<grid.length; r++) {
			for(int c=0; c<grid[r].length; c++) {
				GameObject obj = grid[r][c];
				if(count%2==0)
					g.setColor(new Color(171, 224, 173));
				if(count%2==1)
					g.setColor(new Color(87, 160, 89));
				obj.draw(g);
				count++;
			}
		}
		g.setColor(Color.BLACK);
		g.drawRect(OFFSET_X, OFFSET_Y, cols*GameObject.SQUARE_SIZE, 
				rows*GameObject.SQUARE_SIZE);
		//draw pause/play
		if(clicks%2==0)
			g.drawImage(pause,PAUSE_X ,PAUSE_Y, BUTTON_SIZE, BUTTON_SIZE, null);
		else
			g.drawImage(play,PAUSE_X ,PAUSE_Y, BUTTON_SIZE, BUTTON_SIZE, null);
		//draw reset
		g.drawImage(reset,RESET_X ,RESET_Y, BUTTON_SIZE, BUTTON_SIZE, null);
		apple.draw(g, seconds);
		g.setColor(Color.BLUE);
		Font f = new Font("Impact", 10, 20);
		g.setFont(f);
		g.drawString("Points: "+points+"", OFFSET_X, 40);
		snake.draw(g);
	}

	public void tick() {
		seconds++;
	}

	//moves the snake when the timer goes off
	public void moveTick() {
		snake.move();
		if(appleEaten()) {
			points++;
			apple.setEaten(false);
			addApple();
			snake.addSegment();
		}
	}

	//calls the snake's keyPressed method if a key is pressed 
	public void keyPressed(KeyEvent key) {
		snake.keyPressed(key);		
	}

	//check if snake is inbounds
	public boolean snakeInbounds() {
		int x = snake.getHead().getX();
		int y = snake.getHead().getY();
		if(snake.getHead().getDirection()==1 && y<OFFSET_Y) {
			snake.setInGame(false);
			return false;
		}
		if(snake.getHead().getDirection()==2 && 
				x+GameObject.SQUARE_SIZE>GameObject.SQUARE_SIZE*cols+OFFSET_X) {
			snake.setInGame(false);
			return false;
		}
		if(snake.getHead().getDirection()==3 && 
				y+GameObject.SQUARE_SIZE>(GameObject.SQUARE_SIZE*rows)+OFFSET_Y) {
			snake.setInGame(false);
			return false;
		}
		if(snake.getHead().getDirection()==4 && x<OFFSET_X) {
			snake.setInGame(false);
			return false;			
		}
		return true;
	}

	//check if the snake's head has touched the apple
	public boolean appleEaten() {
		int appleX = apple.getX();
		int appleY = apple.getY();
		int headX = snake.getHead().getX();
		int headY = snake.getHead().getY();
		if(headX==appleX && headY==appleY)
			return true;
		return false;
	}

	public void justClicked(MouseEvent me) {
		int x = me.getX();
		int y = me.getY();
		//if this is the pause button, pause
		System.out.println(clickedPausePlay(x,y));
		if(clickedPausePlay(x,y)) {
			clicks++;
			if(clicks%2==1) {
				snake.setPaused(true);
			}
			else {
				snake.setPaused(false);
			}
		}
		if(clickedReset(x,y))
			reset();
	}

	public boolean clickedPausePlay(int x, int y) {
		if(x>PAUSE_X && x<PAUSE_X+BUTTON_SIZE && y>PAUSE_Y && y<PAUSE_Y+BUTTON_SIZE)
			return true;
		return false;
	}
	
	public boolean clickedReset(int x, int y) {
		if(x>RESET_X && x<RESET_X+BUTTON_SIZE && y>RESET_Y && y<RESET_Y+BUTTON_SIZE)
			return true;
		return false;
	}
	
	public Snake getSnake() {
		return snake;
	}

	public int getClicks() {
		return clicks;
	}

}
