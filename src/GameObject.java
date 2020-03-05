import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameObject {
	private int row, col, x, y;
	public Image sprites;
	public static final int SQUARE_SIZE = 36;                                                                                                 
	public GameObject(int r, int c) {
		setUpImages();
		this.row = r;
		this.col = c;
		this.x = r*SQUARE_SIZE+SnakeBoard.OFFSET_X;
		this.y = c*SQUARE_SIZE+SnakeBoard.OFFSET_Y;
	}
	public void draw(Graphics g) {
		g.fillRect(col*SQUARE_SIZE+SnakeBoard.OFFSET_X, 
				row*SQUARE_SIZE+SnakeBoard.OFFSET_Y, SQUARE_SIZE, SQUARE_SIZE);
	}
	
	private void setUpImages() {                                                                                                              
		if(sprites == null) {                                                                                      
			try {                                                                                                                             
				sprites = ImageIO.read(new File("snake-graphics.png"));                                                                                                                                                                                                   
			}                                                                                                                                 
			catch (IOException e) {	                                                                                                          
				e.printStackTrace();                                                                                                          
			}                                                                                                                                 
		}     
	}
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
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
}
