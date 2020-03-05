import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Body extends Segment{
	private int imageNum;
	private static Image[] segments = new Image[8];
	public Body(int r, int c) {
		super(r, c);
		imageNum = 0;
		createImages();
	}

	private void createImages() {
		//regular vertical
		segments[0] =  ((BufferedImage)sprites).getSubimage(127,64,64,64).getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);  
		//regular horizontal
		segments[1] =  ((BufferedImage)sprites).getSubimage(64,0,64,64).getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);  
		//E->N
		segments[2] =  ((BufferedImage)sprites).getSubimage(127,128,64,64).getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);  
		//E->S, N->W
		segments[3] =  ((BufferedImage)sprites).getSubimage(127,0,64,64).getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);  
		//W->N
		segments[4] =  ((BufferedImage)sprites).getSubimage(256,64,64,64).getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);  	
		//W->S
		segments[4] =  ((BufferedImage)sprites).getSubimage(256,64,64,64).getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);  	
		System.out.println(imageNum);

	}
	public int getImageNum() {
		return imageNum;
	}
	public void setImageNum(int imageNum) {
		this.imageNum = imageNum;
	}
	@Override
	public void draw(Graphics g) {
		// TODO Draw correct image2
		if(getDirection()==1||getDirection()==3)
			g.drawImage(segments[0],getX(), getY(), null);
		else
			g.drawImage(segments[1],getX(), getY(), null);


	}
	/*@Override
	public void move(Segment s) {
				if((getX()-SnakeBoard.OFFSET_X)%SQUARE_SIZE==0 
						&& (getY()-SnakeBoard.OFFSET_Y)%SQUARE_SIZE==0) {
					if(getDirection()==2 && getNextDir()==1) {
						this.setImageNum(2);
					}
					if(getDirection()==2 && getNextDir()==3) {
						this.setImageNum(3);
					}
					setDirection(getNextDir());
				}
	}*/
	public void snapToGrid() {
		int col = (getX()-SnakeBoard.OFFSET_X)/GameObject.SQUARE_SIZE;
		int row = (getY()-SnakeBoard.OFFSET_Y)/GameObject.SQUARE_SIZE;
		if((getX()-SnakeBoard.OFFSET_X)%SQUARE_SIZE>=SQUARE_SIZE/2)
			setX((col*SQUARE_SIZE)+SnakeBoard.OFFSET_X+SQUARE_SIZE);
		else
			setX((col*SQUARE_SIZE)+SnakeBoard.OFFSET_X);
		if((getY()-SnakeBoard.OFFSET_Y)%SQUARE_SIZE>=SQUARE_SIZE/2)
			setY((row*SQUARE_SIZE)+SnakeBoard.OFFSET_Y+SQUARE_SIZE);
		else
			setY((row*SQUARE_SIZE)+SnakeBoard.OFFSET_Y);
	}

}
