package ObjectPackage;

public class Table {
	
	private int TableSizeX;
	private int TableSizeY;
	private BlockType[][] TableStatus;
	
	// Enum types
	BlockType e = BlockType.EMPTY;
	BlockType f = BlockType.FILLED;
	BlockType b = BlockType.BLOCK;
	BlockType r = BlockType.RED;
	
	
	public Table(int tableSizeX, int tableSizeY) {
		super();
		TableSizeX = tableSizeX;
		TableSizeY = tableSizeY;
		TableStatus = defaultTable(tableSizeX, tableSizeY);
		
	}
	// Trial table constructor
	
	public Table() {
		super();
		BlockType[][] table = {
				//	 0  1  2  3  4  5  6  7  8  9
					{b, b, b, b, b, b, b, b, b, b},
					{e, e, e, e, e, e, f, f, e, b},
					{b, e, e, e, e, e, f, b, e, b},
					{b, e, e, b, b, b, f, b, e, b},
					{b, e, e, b, r, b, e, b, e, b},
					{b, e, e, b, e, b, e, b, e, b},
					{b, e, e, b, e, e, e, b, e, b},
					{b, e, e, b, b, b, b, b, e, b},
					{b, b, b, b, b, b, b, b, b, b}
			};
		TableSizeX = 10;
		TableSizeY = 9;
		this.TableStatus = table;
	}
	
	
	// Default table creator
	public BlockType[][] defaultTable(int tableSizeX, int tableSizeY){
		BlockType[][] temp = new BlockType[tableSizeX][tableSizeY];
		for(int i = 0; i < tableSizeX; i++) {
			for(int j = 0; j < tableSizeY; j++) {
				temp[i][j]= e;
			}
		}
		return temp;
	}
	
	// Returns wanted locations enumeration value
	public BlockType returnLocationEnum(int positionX, int positionY) {
		return this.TableStatus[positionX][positionY];
	}
	
	
	
	

	//Getters and Setters
	public int getTableSizeX() {
		return TableSizeX;
	}

	public void setTableSizeX(int tableSizeX) {
		TableSizeX = tableSizeX;
	}

	public int getTableSizeY() {
		return TableSizeY;
	}

	public void setTableSizeY(int tableSizeY) {
		TableSizeY = tableSizeY;
	}

	public BlockType[][] getTableStatus() {
		return TableStatus;
	}

	public void setTableStatus(BlockType[][] tableStatus) {
		TableStatus = tableStatus;
	}
	
	
}
