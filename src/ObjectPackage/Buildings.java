package ObjectPackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

//import ObjectPackage.Blocks.BlockType;
import javafx.scene.image.Image;

public class Buildings extends Blocks{

	public Buildings(int coordinateX, int coordinateY, Image image, BlockType[][] blockSize, int rotationType) {
		super(coordinateX, coordinateY, image, blockSize, rotationType);
		
		/*
		//Getting image and Block configurations
		Image Image = new Image(new FileInputStream("6-red-block.jpg"));
		setImage(Image);
		
		BlockType e = BlockType.EMPTY;
		BlockType f = BlockType.FILLED;
		BlockType b = BlockType.BLOCK;
		BlockType[][] blockConfiguration = {
				{e, e, e},
				{e, e, e},
				{e, e, e}
		};
		setBlockSize(blockConfiguration);
		setRotationType(0);*/
	}

	public Buildings(BlockType[][] blockSize, int rotationType) {
		super(blockSize, rotationType);
		// TODO Auto-generated constructor stub
	}
	
	
	
}
