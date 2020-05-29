import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class SnakeBoard {
	public static final int OFFSET_X = 40, OFFSET_Y = 80;
	private static final int DIRECTIONS_X = 600, PAUSE_X = 500, PAUSE_Y = 10, 
			BUTTON_SIZE = 36, RESET_X = 450, RESET_Y = 10, APPLE_X = 40, APPLE_Y = 5, 
			APPLE_SIZE = 20, GOLDAPPLE_X = 100, LEVEL2_THRESHOLD = 15, 
			LEVEL3_THRESHOLD = 30; 
	public Image pause, play, reset, appl, goldapple;
	private GameObject[][] grid;
	private Apple apple;
	private DoublePowerup powerup;
	private int rows, cols, highScore, points, seconds, clicks, level, speed, powerupsecs;
	private Snake snake;
	private Color snakeColor = Color.black;

	public SnakeBoard(int r, int c) {
		highScore = 0;
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
		if(goldapple == null) {                                                                                      
			try {                                                                                                                             
				Image sprites = ImageIO.read(new File("golden-apple.jpg"));
				goldapple = ((BufferedImage)sprites).getScaledInstance(GameObject.SQUARE_SIZE, 
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

	//adds a new apple at a random location
	private void addApple() {
		int r = (int) (Math.random()*rows);
		int c = (int) (Math.random()*cols);
		apple = new Apple(r,c);
	}

	//creates the grid
	private void fill() {
		for(int r=0; r<grid.length; r++) {
			for(int c=0; c<grid[r].length; c++) {
				grid[r][c]=new GameObject(r,c);
			}
		}
	}

	public void draw(Graphics g) {
		drawGrid(g);
		drawPausePlay(g);
		g.drawImage(reset,RESET_X ,RESET_Y, BUTTON_SIZE, BUTTON_SIZE, null);
		if(snake.isInGame())
			apple.draw(g, seconds);
		drawPoints(g);
		drawHighScore(g);
		drawLevel(g);
		if(snake.isInGame() && level>=3)
			drawPowerup(g);
		snake.draw(g, snakeColor);
		drawDirections(g);
	}

	private void drawPausePlay(Graphics g) {
		if(clicks%2==0)
			g.drawImage(pause,PAUSE_X ,PAUSE_Y, BUTTON_SIZE, BUTTON_SIZE, null);
		else
			g.drawImage(play,PAUSE_X ,PAUSE_Y, BUTTON_SIZE, BUTTON_SIZE, null);		
	}

	private void drawGrid(Graphics g) {
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
	}

	private void drawPoints(Graphics g) {
		g.drawImage(appl,APPLE_X ,APPLE_Y, APPLE_SIZE, APPLE_SIZE, null);
		g.setColor(Color.red);
		g.setFont(new Font("Impact", Font.PLAIN, 20));
		g.drawString(points+"", OFFSET_X+APPLE_SIZE+10, 25);		
	}

	private void drawHighScore(Graphics g) {
		g.drawImage(goldapple,GOLDAPPLE_X ,APPLE_Y, APPLE_SIZE, APPLE_SIZE, null);
		g.setColor(new Color(247,190,61));
		g.setFont(new Font("Impact", Font.PLAIN, 20));
		g.drawString(highScore+"", GOLDAPPLE_X+APPLE_SIZE+10, 25);
	}

	private void drawPowerup(Graphics g) {
		powerup.draw(g,seconds);
		g.setColor(new Color(235, 204, 52));
		if(powerup.isActivated()) {
			if(seconds%60<=30) {
				g.setFont(new Font("Impact", Font.BOLD, 55));
				g.drawString("x2",OFFSET_X+APPLE_SIZE+300,50);
			}
			else {
				g.setFont(new Font("Impact", Font.BOLD, 50));
				g.drawString("x2",OFFSET_X+APPLE_SIZE+305,50);
			}
		}		
	}

	private void drawDirections(Graphics g) {
		g.setColor(Color.black);
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		g.drawString("Controls:", DIRECTIONS_X+75, OFFSET_Y);
		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		g.drawString("Move up: W/up arrow key", DIRECTIONS_X, OFFSET_Y+20);
		g.drawString("Move down: S/down arrow key", DIRECTIONS_X, OFFSET_Y+40);
		g.drawString("Move right: D/right arrow key", DIRECTIONS_X, OFFSET_Y+60);
		g.drawString("Move left: A/left arrow key", DIRECTIONS_X, OFFSET_Y+80);
		g.drawString("Play/Pause: P ", DIRECTIONS_X, OFFSET_Y+100);
		g.drawString("Reset: R", DIRECTIONS_X, OFFSET_Y+120);
		g.drawString("Exit: X", DIRECTIONS_X, OFFSET_Y+140);
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


	//controls apple's flashing and length of powerup appearance
	public void tick() {
		seconds++;
		if(level>=3) {
			powerupsecs++;
			if(powerupsecs == 500) {
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
				if(points > highScore)
					highScore = points;
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


	//creates a new double powerup at a random location
	private void newDoublePowerup() {
		int r = (int) (Math.random()*rows);
		int c = (int) (Math.random()*cols);
		powerup = new DoublePowerup(r,c);
	}

	//calls the snake's keyPressed method if a key is pressed 
	public void keyPressed(KeyEvent key) {
		snake.keyPressed(key);		
	}

	//returns true if the snake's head is inbounds, false otherwise
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

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Color getSnakeColor() {
		return snakeColor;
	}

	public void setSnakeColor(Color snakeColor) {
		this.snakeColor = snakeColor;
	}

	public int getHighScore() {
		return highScore;
	}

}
