package ObjectPackage;

//import java.swing.*;

public class Blocks {
	
	private int CoordinateX;
	private int CoordinateY;
	private String ImageAdress;
	private BlockType[][] BlockSize;
	private int RotationType; // Types are 0, 1, 2, 3 for every possible rotation on the axis
	
	
	// Constructors
	public Blocks()
	{
		CoordinateX = -1;
		CoordinateY = -1;
		RotationType = 0;
	}
	public Blocks(int coordinateX, int coordinateY, String image, BlockType[][] blockSize, int rotationType) {
		super();
		CoordinateX = coordinateX;
		CoordinateY = coordinateY;
		this.ImageAdress = image;
		BlockSize = blockSize;
		RotationType = 0;
	}
	
	public Blocks(BlockType[][] blockSize, int rotationType) {
		super();
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
	public String getImage() {
		return ImageAdress;
	}
	public void setImage(String image) {
		this.ImageAdress = image;
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
		RotationType = ((RotationType - 1) + 4) % 4;
	}
	
	/////////////////////////
	
	// returns an array without reference
	public BlockType[][] returnArray(){
		
		BlockType[][] temp = new BlockType[3][3];
		
		for (int i = 0; i < 3; i++) {// get row length
			for (int j = 0; j < 3; j++) { // get column length
				temp[i][j] = BlockSize[i][j];
			}
		}	
		return temp;
	}
	
	public void Rotate90Right() {
		
		Right90Change(); // change rotation type

		BlockType[][] temp1 = returnArray();
		int columnLength = temp1[0].length;
		int	rowLength = temp1.length;
		
		
		for (int i = 0; i < rowLength; i++) {// get row length
			for (int j = 0; j < columnLength; j++) { // get column length
				BlockSize[j][(rowLength - 1) - i] = temp1[i][j];
			}
		}
		
	}
	
	public void Rotate90Left() {
		
		this.Left90Change(); // change rotation type

		BlockType[][] temp1 = returnArray();
		int columnLength = temp1[0].length;
		int	rowLength = temp1.length;
		
		
		for (int i = 0; i < rowLength; i++) {// get row length
			for (int j = 0; j < columnLength; j++) { // get column length
				this.BlockSize[(columnLength - 1) - j][i] = temp1[i][j];
			}
		}	
	}
	
}
