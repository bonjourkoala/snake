import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Snake{
	private boolean inGame, paused;
	private ArrayList<Body> segments = new ArrayList<Body>();

	public Snake() {
		inGame = true;
		paused = false;
		//create a default snake of length 3 facing right
		segments.add(new Body(4,5,2));
		segments.add(new Body(4,4,2));
		segments.add(new Body(4,3,2));
	}

	//draw the body of the snake
	public void draw(Graphics g) {
		g.setColor(Color.yellow);
		for(int i=1; i<segments.size(); i++) {
			Body s = segments.get(i);
			s.draw(g);
		}
		drawHead(g);
	}

	private void drawHead(Graphics g) {
		segments.get(0).drawHead(g,getHead().getDirection());
	}

	public void keyPressed(KeyEvent key) {
		Body head = segments.get(0);
		int direction = head.getDirection();
		int d = key.getExtendedKeyCode();
		if(inGame && !paused) {
			if(d==38 && direction!=3 && direction!=1) {
				head.setDirection(1);
			}
			if(d==39 && direction!=4 && direction!=2) {
				head.setDirection(2);
			}
			if(d==40 && direction!=1 && direction!=3) {
				head.setDirection(3);
			}
			if(d==37 && direction!=2 && direction!=4) {
				head.setDirection(4);
			}
		}
	}

	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public Body getHead() {
		return segments.get(0);
	}

	public void move() {
		for(int i=segments.size()-1; i>0; i--) {
			segments.get(i).setX(segments.get(i-1).getX());
			segments.get(i).setY(segments.get(i-1).getY());
			segments.get(i).setDirection(segments.get(i-1).getDirection());
		}
		//move based on direction
		Body head = getHead();
		int direction = head.getDirection();
		if (direction==1) 
			head.setY(head.getY()-GameObject.SQUARE_SIZE);
		if (direction==2) 
			head.setX(head.getX()+GameObject.SQUARE_SIZE);
		if (direction==3) 
			head.setY(head.getY()+GameObject.SQUARE_SIZE);
		if (direction==4) 
			head.setX(head.getX()-GameObject.SQUARE_SIZE);
	}
	
	//check if the snake has hit itself
	public boolean hitSelf() {
		int X = getHead().getX();
		int Y = getHead().getY();
		for(int i=1; i<segments.size(); i++) {
			int x = segments.get(i).getX();
			int y = segments.get(i).getY();
			if(x==X && y==Y) {
				inGame = false;
				return true;
			}
		}
		return false;
	}

	/**adds a segment to the snake when an apple is eaten according to the 
	 * direction of the current tail
	 */
	public void addSegment() {
		Body last = segments.get(segments.size()-1);
		int dir = last.getDirection();
		int r = (last.getY()-SnakeBoard.OFFSET_Y)/GameObject.SQUARE_SIZE;
		int c = (last.getX()-SnakeBoard.OFFSET_X)/GameObject.SQUARE_SIZE;
		if (dir==1) 
			segments.add(new Body(r-1,c,dir));
		if (dir==2) 
			segments.add(new Body(r,c+1,dir));
		if (dir==3) 
			segments.add(new Body(r+1,c,dir));
		if (dir==4) 
			segments.add(new Body(r,c-1,dir));
	}
	
	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

}
