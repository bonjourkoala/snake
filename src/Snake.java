import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Snake{
	private boolean alive, keyPressed;
	private Segment head;
	private ArrayList<Segment> segments = new ArrayList<Segment>();
	public Snake() {
		setAlive(true);
		head = new Head(4,5);
		segments.add(head);
		segments.add(new Body(4,4));
		segments.add(new Tail(4,3));
	}

	public void draw(Graphics g) {
		for(Segment s : segments) {
			s.draw(g);
		}
	}

	public void keyPressed(KeyEvent key) {
		setKeyPressed(true);
		int d = key.getExtendedKeyCode();
		if(alive) {
			if(d==38 && head.getDirection()!=3 && head.getDirection()!=1) {
				head.snapToGrid(1);
				head.setDirection(1);
			}
			if(d==39 && head.getDirection()!=4 && head.getDirection()!=2) {
				head.snapToGrid(2);
				head.setDirection(2);
			}
			if(d==40 && head.getDirection()!=1 && head.getDirection()!=3) {
				head.snapToGrid(3);
				head.setDirection(3);
			}
			if(d==37 && head.getDirection()!=2 && head.getDirection()!=4) {
				head.snapToGrid(4);
				head.setDirection(4);
			}
			update();
			for(Segment s : segments) {
				s.snapToGrid(s.getDirection());
			}
		}
	}

	private void update() {
		for(int i=1; i<segments.size(); i++) {
			Segment s1 = segments.get(i-1);
			Segment s = segments.get(i);
			s.setNextDir(s1.getDirection());
		}
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public void move() {
		head.move();
		for(int i=1; i<segments.size(); i++) {
			Segment s = segments.get(i);
			s.move(segments.get(i-1));
			if(keyPressed)
				s.setDirection(s.getNextDir());;
		}
	}

	public Segment getHead() {
		return head;
	}

	public void setHead(Segment head) {
		this.head = head;
	}

	public boolean isKeyPressed() {
		return keyPressed;
	}

	public void setKeyPressed(boolean keyPressed) {
		this.keyPressed = keyPressed;
	}

	public void addSegment() {
		Segment previous = segments.get(segments.size()-2);
		if(previous.getDirection()==1)
			segments.add(segments.size()-1, new Body(
					previous.getX(),
					previous.getY()+GameObject.SQUARE_SIZE));	
		if(previous.getDirection()==2)
			segments.add(segments.size()-1, new Body(
					previous.getX()-GameObject.SQUARE_SIZE,
					previous.getY()));	
		if(previous.getDirection()==3)
			segments.add(segments.size()-1, new Body(
					previous.getX(),
					previous.getY()-GameObject.SQUARE_SIZE));	
		if(previous.getDirection()==4)
			segments.add(segments.size()-1, new Body(
					previous.getX()+GameObject.SQUARE_SIZE,
					previous.getY()));	
	}




}
