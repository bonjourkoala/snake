import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Head extends Segment{
	private static Image[] heads = new Image[5];
	public Head(int r, int c) {
		super(r, c);
		createImages();
	}
	private void createImages() {
		heads[1] =  ((BufferedImage)sprites).getSubimage(192,0,64,64).getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);  
		heads[2] =  ((BufferedImage)sprites).getSubimage(256,0,64,64).getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);  
		heads[4] =  ((BufferedImage)sprites).getSubimage(192,64,64,64).getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);  
		heads[3] =  ((BufferedImage)sprites).getSubimage(256,64,64,64).getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);  
	}
	@Override
	public void draw(Graphics g) {
		g.drawImage(heads[getDirection()], getX(), getY(), null);
	}

}
