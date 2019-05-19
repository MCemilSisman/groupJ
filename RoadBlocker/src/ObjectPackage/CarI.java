package ObjectPackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CarI extends PoliceCar{

	public CarI() throws FileNotFoundException {
		super();
		
		//Getting image and Block configurations
		String Image = "resources//CarI.png";
		setImage(Image);
		
		BlockType e = BlockType.EMPTY;
		BlockType f = BlockType.FILLED;
		BlockType b = BlockType.BLOCK;
		BlockType[][] blockConfiguration = {
				{f, f, e},
				{b, e, e},
				{e, e, e}
		};
		setBlockSize(blockConfiguration);
		setRotationType(0);
	}

}

