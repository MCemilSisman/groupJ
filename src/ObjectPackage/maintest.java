package ObjectPackage;

public class maintest {

	public static void main(String[] args) { 
		
		Table t= new Table(10, 10);
		/*BlockType[][] abc = new BlockType[10][10]; 
		abc = t.getTableStatus();
		for(int i= 0; i<9; i++) {
			System.out.println(abc[i][i]);
		}*/
		System.out.println(t.returnLocationEnum(3, 3));
		/*
		BlockType e = BlockType.EMPTY;
		BlockType f = BlockType.FILLED;
		BlockType b = BlockType.BLOCK;
		BlockType r = BlockType.RED;
		
		int posX = 4;
		int posY = 4;
		//Red car is on the 5x5 block
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
		};*/
		int posX = 4;
		int posY = 4;
		Table table = new Table();
		System.out.println(table.returnLocationEnum(0, 0));
		System.out.println(isFree(posX, posY, table));
		
	}

	
	
	public static boolean isFree(int PositionX, int PositionY, Table table) {
		// yanlýþ düzelt
		if(PositionX + 1 < table.getTableSizeX() -1) {
		if(table.returnLocationEnum(PositionX + 1, PositionY) != BlockType.BLOCK ) {
			isFree(PositionX + 1, PositionY, table);
			return true;
		}}
		
		if(PositionX - 1 != -1) {
		if(table.returnLocationEnum(PositionX - 1, PositionY) != BlockType.BLOCK) {	
			isFree(PositionX - 1, PositionY, table);
			return true;
		}}
		
		if(PositionY + 1 < table.getTableSizeY() -1) {
		if(table.returnLocationEnum(PositionX, PositionY + 1) != BlockType.BLOCK) {
			isFree(PositionX, PositionY + 1, table);
			return true;
		}}
		
		if(PositionY - 1 != -1) {
		if(table.returnLocationEnum(PositionX, PositionY - 1) != BlockType.BLOCK) {
			isFree(PositionX, PositionY - 1, table);
			return true;
		}}
		
		return false;
	}

}
