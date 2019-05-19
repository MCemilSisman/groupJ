package ObjectPackage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PoliceCar extends Blocks{

	public PoliceCar()
	{
		super();
	}
	public PoliceCar(int coordinateX, int coordinateY, String image, BlockType[][] blockSize, int rotationType) {
		super(coordinateX, coordinateY, image, blockSize, rotationType);
		
	}

}
