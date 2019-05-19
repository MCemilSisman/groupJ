package ObjectPackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

//import ObjectPackage.Blocks.BlockType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CarH extends PoliceCar {

	public CarH() throws FileNotFoundException {
		super();
		
		//Getting image and Block configurations
		String Image = "resources//CarH.png";
		setImage(Image);
		
		BlockType e = BlockType.EMPTY;
		BlockType f = BlockType.FILLED;
		BlockType b = BlockType.BLOCK;
		BlockType[][] blockConfiguration = {
				{f, f, b},
				{e, f, e},
				{e, e, e}
		};
		setBlockSize(blockConfiguration);
		setRotationType(0);
	}

}