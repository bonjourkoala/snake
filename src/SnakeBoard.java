import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class SnakeBoard {
	public static final int OFFSET_X = 40, OFFSET_Y = 80;
	private GameObject[][] grid;
	private Apple apple;
	private int rows, cols, points, seconds;
	private Snake snake;
	
	public SnakeBoard(int r, int c) {
		grid = new GameObject[r][c];
		rows = r;
		cols = c;
		fill();
		reset();
	}

	public void reset() {
		snake = new Snake();
		seconds = 0;
		points = 0;
		addApple();		
	}

	public void addApple() {
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
		g.drawRect(OFFSET_X, OFFSET_Y, cols*GameObject.SQUARE_SIZE, rows*GameObject.SQUARE_SIZE);
		apple.draw(g, seconds);
		g.setColor(Color.BLUE);
		Font f = new Font("Impact", 10, 20);
		g.setFont(f);
		g.drawString("Points: "+points+"", OFFSET_X, 20);
		snake.draw(g);
	}

	public void tick() {
		seconds++;
		if(appleEaten()) {
			points++;
			apple.setEaten(false);
			addApple();
			snake.addSegment();
		}
		snake.move();
	}
	
	public boolean inbounds(int x, int y) {
		if(snake.getHead().getDirection()==1 && y<OFFSET_Y)
			return false;
		if(snake.getHead().getDirection()==2 && 
				x-1+GameObject.SQUARE_SIZE>GameObject.SQUARE_SIZE*rows+OFFSET_X)
			return false;
		if(snake.getHead().getDirection()==3 && 
				y-1+GameObject.SQUARE_SIZE>GameObject.SQUARE_SIZE*cols+OFFSET_Y)
			return false;
		if(snake.getHead().getDirection()==4 && x<OFFSET_X)
			return false;
		return true;
	}

	public void keyPressed(KeyEvent key) {
		snake.keyPressed(key);		
	}

	public boolean snakeInbounds() {
		if(!inbounds(snake.getHead().getX(), snake.getHead().getY())) {
			snake.setAlive(false);
			return false;
		}		
		return true;
	}
	
	public boolean appleEaten() {
		int ss = GameObject.SQUARE_SIZE;
		int d = snake.getHead().getDirection();
		int appleX = apple.getX();
		int appleY = apple.getY();
		int headX = snake.getHead().getX();
		int headY = snake.getHead().getY();
		if(d==1 && headX==appleX && headY+ss/2==appleY+ss)
			return true;
		if(d==2 && headX+ss-ss/2==appleX && headY==appleY)
			return true;
		if(d==3 && headX == appleX && headY+ss-ss/2==appleY)
			return true;
		if(d==4 && headX==appleX+ss-ss/2 && headY==appleY)
			return true;
		return false;


	}

}
