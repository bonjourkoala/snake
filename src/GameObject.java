import java.awt.*;
import java.io.*;
import javax.imageio.*;

public class GameObject {
	private int x, y;
	public static Image sprites, powerup;
	public static final int SQUARE_SIZE = 25;                                                                                                 
	
	public GameObject(int r, int c) {
		setUpImages();
		this.x = c*SQUARE_SIZE+SnakeBoard.OFFSET_X;
		this.y = r*SQUARE_SIZE+SnakeBoard.OFFSET_Y;
	}
	
	public void draw(Graphics g) {
		g.fillRect(x,y, SQUARE_SIZE, SQUARE_SIZE);
	}
	
	private static void setUpImages() {                                                                                                              
		if(sprites == null) {                                                                                      
			try {                                                                                                                             
				sprites = ImageIO.read(new File("snake-graphics.png"));                                                                                                                                                                                                   
			}                                                                                                                                 
			catch (IOException e) {	                                                                                                          
				e.printStackTrace();                                                                                                          
			}                                                                                                                                 
		}   
		if(powerup == null) {                                                                                      
			try {                                                                                                                             
				powerup = ImageIO.read(new File("powerup-double.png"));                                                                                                                                                                                                   
			}                                                                                                                                 
			catch (IOException e) {	                                                                                                          
				e.printStackTrace();                                                                                                          
			}                                                                                                                                 
		}   
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void incrementX() {
		x+=1;
	}
	
	public void incrementY() {
		y+=1;
	}
}
