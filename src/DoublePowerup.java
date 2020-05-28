import java.awt.*;
import java.awt.image.*;

public class DoublePowerup extends GameObject{
	private static final int SMALL_SIZE = SQUARE_SIZE-5;
	private boolean activated;
	private static Image[] powerups = new Image[2];
	public DoublePowerup(int r, int c) {
		super(r,c);
		setActivated(false);
		powerups[0] =  ((BufferedImage)powerup).getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);  
		powerups[1] =  ((BufferedImage)powerup).getScaledInstance(SMALL_SIZE, SMALL_SIZE, BufferedImage.SCALE_SMOOTH);  
	}

	public void draw(Graphics g, int second) {
		if(activated==false) {
			if(second%60<=30)
				g.drawImage(powerups[0], getX(), getY(), null);
			else
				g.drawImage(powerups[1], getX()+(SQUARE_SIZE-SMALL_SIZE)/2, 
						getY()+(SQUARE_SIZE-SMALL_SIZE)/2, null);
		}
	}

	public boolean isActivated() {
		return activated;
	}
	public void setActivated(boolean activated) {
		this.activated = activated;
	}

}
