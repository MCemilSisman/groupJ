package ObjectPackage;

//import ObjectPackage.Blocks.BlockType;
import javafx.scene.image.Image;

//import java.swing.*;

public class Blocks {
	
	private int CoordinateX;
	private int CoordinateY;
	private Image image;
	private BlockType[][] BlockSize;
	private int RotationType; // Types are 0, 1, 2, 3 for every possible rotation on the axis
	
	
	// Constructors
	public Blocks(int coordinateX, int coordinateY, Image image, BlockType[][] blockSize, int rotationType) {
		super();
		CoordinateX = coordinateX;
		CoordinateY = coordinateY;
		this.image = image;
		BlockSize = blockSize;
		RotationType = rotationType;
	}
	
	// Getter and Setters
	public int getCoordinateX() {
		return CoordinateX;
	}

	public void setCoordinateX(int coordinateX) {
		CoordinateX = coordinateX;
	}
	public int getCoordinateY() {
		return CoordinateY;
	}
	public void setCoordinateY(int coordinateY) {
		CoordinateY = coordinateY;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	
	public BlockType[][] getBlockSize() {
		return BlockSize;
	}

	public void setBlockSize(BlockType[][] blockSize) {
		BlockSize = blockSize;
	}

	public int getRotationType() {
		return RotationType;
	}

	public void setRotationType(int rotationType) {
		RotationType = rotationType;
	}
	
	//Rotation change methods
	
	public void Right90Change() {
		RotationType = (RotationType + 1) % 4;
	}
	
	public void Left90Change() {
		RotationType = (RotationType + 1) % 4;
	}
	
	/////////////////////////
	
}
