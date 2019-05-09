package ObjectPackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

//import ObjectPackage.Blocks.BlockType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RedCar extends Blocks {

	public RedCar(int coordinateX, int coordinateY, String image, BlockType[][] blockSize, int rotationType) throws FileNotFoundException {
		super(coordinateX, coordinateY, image, blockSize, rotationType);
		
		//Getting image and Block configurations
				String Image = "redCar.jpg";
				setImage(Image);
				
				BlockType e = BlockType.EMPTY;
				BlockType f = BlockType.FILLED;
				BlockType b = BlockType.BLOCK;
				BlockType r = BlockType.RED;
				BlockType[][] blockConfiguration = {
						{e, e, e},
						{e, r, e},
						{e, e, e}
				};
				setBlockSize(blockConfiguration);
				setRotationType(0);
	}

}
