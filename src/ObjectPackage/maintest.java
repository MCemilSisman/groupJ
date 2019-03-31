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
				// Y 0  1  2  3  4  5  6  7  8  9	 X
					{s, s, s, s, s, s, s, s, s, s},//0
					{s, b, b, b, b, b, b, b, e, s},//1
					{s, b, e, e, e, e, f, b, e, s},//2
					{s, b, e, b, b, b, f, b, e, s},//3
					{s, b, e, b, r, b, e, b, e, s},//4
					{s, b, e, b, e, b, e, b, e, s},//5
					{s, b, e, b, e, e, e, b, e, s},//6
					{s, b, e, b, b, b, b, b, e, s},//7
					{s, s, s, s, s, s, s, s, s, s} //8
		};*/
		int posX = 4;
		int posY = 4;
		Table table = new Table();
		System.out.println(table.returnLocationEnum(0, 0));
		System.out.println(table.getTableSizeX() + "\n" + table.getTableSizeY());
		System.out.println(isFree(posX, posY, table));
		
		BlockType e = BlockType.EMPTY;
		BlockType f = BlockType.FILLED;
		BlockType b = BlockType.BLOCK;
		
		BlockType[][] block = {
				{f, e, f},
				{e, b, e},
				{b, e, e}
		};
		
		Blocks cType = new Blocks(block, 0);
		
		/*cType.Rotate90Right();
		cType.Rotate90Right();
		cType.Rotate90Right();
		cType.Rotate90Right();
		cType.Rotate90Left();
		cType.Rotate90Left();
		cType.Rotate90Left();
		cType.Rotate90Left();
		cType.Rotate90Right();
		cType.Rotate90Left();*/
		
		
		
		BlockType[][] changedBlock = cType.getBlockSize();
		
		System.out.print(cType.getRotationType()+ "\n");
		
		for (int i = 0; i < changedBlock.length; i++) {// get row length
			for (int j = 0; j < changedBlock[i].length; j++) { // get column length
				System.out.print(changedBlock[i][j] + " ");
			}
			System.out.print("\n");
		}
		
		
		
	}
	
	
	

	/*public static void Rotate90Right(Blocks block) {
		
		block.Right90Change(); // change rotation type
		
		BlockType[][] temp1 = block.getBlockSize();
		int columnLength = temp1[0].length;
		int	rowLength = temp1.length;
		BlockType[][] temp2 = new BlockType[columnLength][rowLength];// set new array with column and row count changed
		
		for (int i = 0; i < rowLength; i++) {// get row length
			for (int j = 0; j < columnLength; j++) { // get column length
				temp2[j][rowLength - i - 1] = temp1[i][j];
			}
		}	
	}*/
	
	

	// isFree method
	public static boolean isFree(int PositionX, int PositionY, Table Table) {
		// memory extended
		
		Table table = Table;
		/*System.out.println("current position: " + PositionX + " " + PositionY);
		System.out.println("value: " + table.returnLocationEnum(PositionX, PositionY));*/
		
		if(table.returnLocationEnum(PositionX + 1, PositionY) == BlockType.SPACE) {
			/*for(int i= 0; i<table.getTableSizeY(); i++) {
				for(int j = 0; j < table.getTableSizeX(); j++) {
					System.out.print(table.returnLocationEnum(i, j) + " ");
				}
				System.out.println("\n");
			}*/
			return true;
		}
		if(table.returnLocationEnum(PositionX - 1, PositionY) == BlockType.SPACE) {
			/*for(int i= 0; i<table.getTableSizeY(); i++) {
				for(int j = 0; j < table.getTableSizeX(); j++) {
					System.out.print(table.returnLocationEnum(i, j) + " ");
				}
				System.out.println("\n");
			}*/
			return true;
		}
		if(table.returnLocationEnum(PositionX, PositionY + 1) == BlockType.SPACE) {
			/*for(int i= 0; i<table.getTableSizeY(); i++) {
				for(int j = 0; j < table.getTableSizeX(); j++) {
					System.out.print(table.returnLocationEnum(i, j) + " ");
				}
				System.out.println("\n");
			}
			return true;*/
		}
		if(table.returnLocationEnum(PositionX, PositionY - 1) == BlockType.SPACE) {
			/*for(int i= 0; i<table.getTableSizeY(); i++) {
				for(int j = 0; j < table.getTableSizeX(); j++) {
					System.out.print(table.returnLocationEnum(i, j) + " ");
				}
				System.out.println("\n");
			}*/
			return true;
		}
		
		if(table.returnLocationEnum(PositionX + 1, PositionY) == BlockType.FILLED || table.returnLocationEnum(PositionX + 1, PositionY) == BlockType.EMPTY) {
			
			/*System.out.println("down " + (PositionX + 1) + " " + PositionY);*/
			table.setLocationEnum(PositionX, PositionY, BlockType.MARKED);
			if(isFree(PositionX + 1, PositionY, table)) {
				return true;
			}
		}
		
		
		if(table.returnLocationEnum(PositionX - 1, PositionY) == BlockType.FILLED || table.returnLocationEnum(PositionX - 1, PositionY) == BlockType.EMPTY) {
			
			/*System.out.println("up " + (PositionX - 1) + " " + PositionY);*/
			table.setLocationEnum(PositionX, PositionY, BlockType.MARKED);
			if(isFree(PositionX - 1, PositionY, table)) {
				return true;
			}
		}
		
		
		if(table.returnLocationEnum(PositionX, PositionY + 1) == BlockType.FILLED || table.returnLocationEnum(PositionX, PositionY + 1) == BlockType.EMPTY) {
			
			/*System.out.println("right" + PositionX + " " + (PositionY + 1));*/
			table.setLocationEnum(PositionX, PositionY, BlockType.MARKED);
			if(isFree(PositionX, PositionY + 1, table)) {
				return true;
			}
		}
		
		
		if(table.returnLocationEnum(PositionX, PositionY - 1) == BlockType.FILLED || table.returnLocationEnum(PositionX, PositionY - 1) == BlockType.EMPTY) {
			
			/*System.out.println("left " + PositionX + " " + (PositionY - 1));*/
			table.setLocationEnum(PositionX, PositionY, BlockType.MARKED);
			if(isFree(PositionX, PositionY - 1, table)) {
				return true;
			}
			
		}
		return false;	
	}
}
