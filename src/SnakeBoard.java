import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class SnakeBoard {
	public static final int OFFSET_X = 40, OFFSET_Y = 80;
	private final int PAUSE_X = 500, PAUSE_Y = 10, BUTTON_SIZE = 36, 
			RESET_X = 450, RESET_Y = 10, APPLE_X = 40, APPLE_Y = 5, 
			APPLE_SIZE = 20, LEVEL2_THRESHOLD = 2, LEVEL3_THRESHOLD = 3; 
	public Image pause, play, reset, appl;
	private GameObject[][] grid;
	private Apple apple;
	private DoublePowerup powerup;
	private int rows, cols, points, seconds, clicks, level, speed, powerupsecs;
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
			}                                                                                                                                 
			catch (IOException e) {	                                                                                                          
				e.printStackTrace();                                                                                                          
			}                                                                                                                                 
		}     	
		if(play == null) {                                                                                      
			try {                                                                                                                             
				Image play1 = ImageIO.read(new File("play.png"));   
				play = ((BufferedImage)play1).getScaledInstance
						(GameObject.SQUARE_SIZE, GameObject.SQUARE_SIZE, 
								BufferedImage.SCALE_SMOOTH);
			}                                                                                                                                 
			catch (IOException e) {	                                                                                                          
				e.printStackTrace();                                                                                                          
			}                                                                                                                                 
		}  
		if(reset == null) {                                                                                      
			try {                                                                                                                             
				Image reset1 = ImageIO.read(new File("reset.png"));   
				reset = ((BufferedImage)reset1).getScaledInstance
						(GameObject.SQUARE_SIZE, GameObject.SQUARE_SIZE, 
								BufferedImage.SCALE_SMOOTH);
			}                                                                                                                                 
			catch (IOException e) {	                                                                                                          
				e.printStackTrace();                                                                                                          
			}                                                                                                                                 
		}  
		if(appl == null) {                                                                                      
			try {                                                                                                                             
				Image sprites = ImageIO.read(new File("snake-graphics.png"));
				appl = ((BufferedImage)sprites).getSubimage(0,192,64,64).
						getScaledInstance(GameObject.SQUARE_SIZE, 
								GameObject.SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
			}                                                                                                                                 
			catch (IOException e) {	                                                                                                          
				e.printStackTrace();                                                                                                          
			}                                                                                                                                 
		}     	
	}

	public void reset() {
		snake = new Snake();
		speed = 300;
		powerupsecs = 0;
		seconds = 0;
		points = 0;
		level = 1;
		clicks = 0;
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
		//draw the grid
		int count = 0;
		for(int r=0; r<grid.length; r++) {
			for(int c=0; c<grid[r].length; c++) {
				GameObject tile = grid[r][c];
				if(count%2==0)
					g.setColor(new Color(171, 224, 173));
				if(count%2==1)
					g.setColor(new Color(87, 160, 89));
				tile.draw(g);
				count++;
			}
		}
		if(!snake.isInGame()) {
			for(GameObject[] row : grid) {
				for(GameObject go : row) {
					g.setColor(new Color(102,51,0));
					go.draw(g);
				}
			}
		}
		//draw the outline of the grid
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
		//draw the apple
		g.drawImage(appl,APPLE_X ,APPLE_Y, APPLE_SIZE, APPLE_SIZE, null);
		//draw the apple on the grid
		if(snake.isInGame())
			apple.draw(g, seconds);
		//draw the points
		g.setColor(Color.red);
		g.setFont(new Font("Impact", Font.PLAIN, 20));
		g.drawString(points+"", OFFSET_X+APPLE_SIZE+10, 25);
		//draw the level
		drawLevel(g);
		//draw the snake
		snake.draw(g);
		//draw powerup
		if(snake.isInGame() && level>=3) {
			powerup.draw(g,seconds);
			g.setColor(new Color(235, 204, 52));
			if(powerup.isActivated()) {
				if(seconds%60<=30) {
					g.setFont(new Font("Impact", Font.BOLD, 55));
					g.drawString("x2",OFFSET_X+APPLE_SIZE+100,50);
				}
				else {
					g.setFont(new Font("Impact", Font.BOLD, 50));
					g.drawString("x2",OFFSET_X+APPLE_SIZE+100,50);
				}
			}
		}
	}

	private void drawLevel(Graphics g) {
		if(level==1)
			g.setColor(Color.red);
		if(level==2)
			g.setColor(Color.orange);
		if(level==3)
			g.setColor(Color.green);
		g.drawString("Level: "+level, OFFSET_X, 50);
	}

	public void tick() {
		seconds++;
		if(level>=3) {
			powerupsecs++;
			if(powerupsecs == 1000) {
				newDoublePowerup();
				powerupsecs = 0;
			}
		}
	}

	public void moveTick() {
		if(snake.isInGame()) {
			//moves the snake when the move timer goes off
			snake.move();
			snake.hitSelf();
			//activate the powerup if the snake has eaten it
			if(level>=3 && powerupActivated()) {
				powerup.setActivated(true);
				powerupsecs = 0;
			}
			/** -- check if the apple is eaten
			 *  -- add points, check if level increases
			 *  -- add another point if the powerup is activated
			 */
			if(appleEaten()) {
				points++;
				addApple();
				snake.addSegment();
				if(points==LEVEL2_THRESHOLD) {
					speed = 250;
					level=2;
				}
				if(points==LEVEL3_THRESHOLD) {
					speed = 200;
					level=3;
					newDoublePowerup();
				}
				if(level>=3) {
					if(powerup.isActivated()) 
						points++;
				}
			}
		}
		snakeInbounds();
	}

	//checks if the snake has touched the powerup
	private boolean powerupActivated() {
		int powerupX = powerup.getX();
		int powerupY = powerup.getY();
		int headX = snake.getHead().getX();
		int headY = snake.getHead().getY();
		return headX==powerupX && headY==powerupY;
	}

	private void newDoublePowerup() {
		int r = (int) (Math.random()*rows);
		int c = (int) (Math.random()*cols);
		powerup = new DoublePowerup(r,c);
	}

	//calls the snake's keyPressed method if a key is pressed 
	public void keyPressed(KeyEvent key) {
		snake.keyPressed(key);		
	}

	//check if snake is inbounds
	private boolean snakeInbounds() {
		int x = snake.getHead().getX();
		int y = snake.getHead().getY();
		if(snake.getHead().getDirection()==1 && y<OFFSET_Y) {
			snake.setInGame(false);
			return false;
		}
		if(snake.getHead().getDirection()==2 && 
				(x+GameObject.SQUARE_SIZE)>(GameObject.SQUARE_SIZE*cols+OFFSET_X)) {
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
		return headX==appleX && headY==appleY;
	}

	//when user clicks on the panel
	public void justClicked(MouseEvent me) {
		int x = me.getX();
		int y = me.getY();
		//if this is the pause/play button, pause
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
		return x>PAUSE_X && x<PAUSE_X+BUTTON_SIZE && y>PAUSE_Y && 
				y<PAUSE_Y+BUTTON_SIZE;
	}

	public boolean clickedReset(int x, int y) {
		return x>RESET_X && x<RESET_X+BUTTON_SIZE && y>RESET_Y && 
				y<RESET_Y+BUTTON_SIZE;
	}

	public Snake getSnake() {
		return snake;
	}

	public int getClicks() {
		return clicks;
	}

	public int getSpeed() {
		return speed;
	}

}
