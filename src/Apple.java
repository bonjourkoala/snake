import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Apple extends GameObject{
	private static final int SMALL_SIZE = SQUARE_SIZE-5;
	private boolean eaten;
	private static Image[] apples = new Image[2];
	public Apple(int r, int c) {
		super(r,c);
		setEaten(false);
		apples[0] =  ((BufferedImage)sprites).getSubimage(0,192,64,64).getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);  
		apples[1] =  ((BufferedImage)sprites).getSubimage(0,192,64,64).getScaledInstance(SMALL_SIZE, SMALL_SIZE, BufferedImage.SCALE_SMOOTH);  
	}

	public void draw(Graphics g, int second) {
		if(eaten==false) {
			if(second%60<=30)
				g.drawImage(apples[0], getX(), getY(), null);
			else
				g.drawImage(apples[1], getX()+(SQUARE_SIZE-SMALL_SIZE)/2, 
						getY()+(SQUARE_SIZE-SMALL_SIZE)/2, null);
		}
	}
	public boolean isEaten() {
		return eaten;
	}
	public void setEaten(boolean eaten) {
		this.eaten = eaten;
	}

}
