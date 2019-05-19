package ObjectPackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

//import ObjectPackage.Blocks.BlockType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CarG extends PoliceCar {

	public CarG() throws FileNotFoundException {
		super();
		
		//Getting image and Block configurations
		String Image = "resources//CarG.png";
		setImage(Image);
		
		BlockType e = BlockType.EMPTY;
		BlockType f = BlockType.FILLED;
		BlockType b = BlockType.BLOCK;
		BlockType[][] blockConfiguration = {
				{f, b, f},
				{e, f, e},
				{e, e, e}
		};
		setBlockSize(blockConfiguration);
		setRotationType(0);
	}

}
