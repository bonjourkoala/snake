import java.awt.*;

public class Body extends GameObject{
	private int direction;

	public Body(int r, int c, int dir) {
		super(r, c);
		direction = dir;
	}
	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int i) {
		direction=i;
	}

	@Override
	public void draw(Graphics g) {
		g.fillOval(getX(), getY(), SQUARE_SIZE, SQUARE_SIZE);
	}

	public void drawHead(Graphics g, int dir) {
		if(dir==1) {
			int[] x = {getX(),getX()+GameObject.SQUARE_SIZE/2,
					getX()+GameObject.SQUARE_SIZE};
			int[] y = {getY()+GameObject.SQUARE_SIZE,getY(),
					getY()+GameObject.SQUARE_SIZE};
			g.fillPolygon(x,y,3);
		}
		if(dir==2) {
			int[] x = {getX(),getX()+GameObject.SQUARE_SIZE,getX()};
			int[] y = {getY(),getY()+GameObject.SQUARE_SIZE/2,
					getY()+GameObject.SQUARE_SIZE};
			g.fillPolygon(x,y,3);
		}
		if(dir==3) {
			int[] x = {getX(),getX()+GameObject.SQUARE_SIZE/2,
					getX()+GameObject.SQUARE_SIZE};
			int[] y = {getY(),getY()+GameObject.SQUARE_SIZE,getY()};
			g.fillPolygon(x,y,3);
		}
		if(dir==4) {
			int[] x = {getX()+GameObject.SQUARE_SIZE,getX(),
					getX()+GameObject.SQUARE_SIZE};
			int[] y = {getY(),getY()+GameObject.SQUARE_SIZE/2,
					getY()+GameObject.SQUARE_SIZE};
			g.fillPolygon(x,y,3);
		}
	}
}