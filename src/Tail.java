import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Tail extends Segment{
	private static Image[] tails = new Image[5];
	public Tail(int r, int c) {
		super(r, c);
		createImages();
	}
	private void createImages() {
		tails[1] =  ((BufferedImage)sprites).getSubimage(196,128,64,64).getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);  
		tails[2] =  ((BufferedImage)sprites).getSubimage(256,128,64,64).getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);  
		tails[3] =  ((BufferedImage)sprites).getSubimage(196,191,64,64).getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);  
		tails[4] =  ((BufferedImage)sprites).getSubimage(256,191,64,64).getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);  	
	}
	@Override
	public void draw(Graphics g) {
		g.drawImage(tails[getDirection()], getX(), getY(), null);
	}
}
