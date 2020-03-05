public class Segment extends GameObject {
	private int direction, nextDir;
	//DIRECTION: 1 = north, 2 = east, 3 = south, 4 = west
	public Segment(int r, int c) {
		super(r, c);
		setDirection(2);
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getNextDir() {
		return nextDir;
	}
	public void setNextDir(int nextDir) {
		this.nextDir = nextDir;
	}

	public void move() {
		if(direction==1)
			setY(getY()-1);	
		if(direction==2)
			setX(getX()+1);	
		if(direction==3)
			setY(getY()+1);	
		if(direction==4)
			setX(getX()-1);	
	}
	
	public void move(Segment s) {
		if(getDirection()==1) {
			setY(s.getY()+SQUARE_SIZE);	
			setX(s.getX());
		}
		if(getDirection()==2) {
			setX(s.getX()-SQUARE_SIZE);	
			setY(s.getY());
		}
		if(getDirection()==3) {
			setY(s.getY()-SQUARE_SIZE);	
			setX(s.getX());
		}
		if(getDirection()==4) {
			setX(s.getX()+SQUARE_SIZE);
			setY(s.getY());
		}
	}
	public void snapToGrid(int nextDir) {
		int col = (getX()-SnakeBoard.OFFSET_X+SQUARE_SIZE/2)/SQUARE_SIZE; 
		int row = (getY()-SnakeBoard.OFFSET_Y+SQUARE_SIZE/2)/SQUARE_SIZE; 
		setX((col)*SQUARE_SIZE+SnakeBoard.OFFSET_X);
		setY((row)*SQUARE_SIZE+SnakeBoard.OFFSET_Y);
	}

}
