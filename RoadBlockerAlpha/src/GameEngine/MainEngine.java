package GameEngine;

import ObjectPackage.*;

public class MainEngine {
	
	//////////////////// ROTATE METHODS ////////////////////
	
	public static void Turn90Right(Blocks block) {
		block.Rotate90Right();
	}
	
	public static void Turn90Left(Blocks block) {
		block.Rotate90Left();
	}
	
	//////////////////// TAKE AND PLACE BLOCK METHODS ////////////////////
	
	public static void TakeBlock(Table table, Blocks block) {
		
		
		BlockType[][] shape = block.returnArray();
		//BlockType[][] tableStatus = table.getTableStatus();
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				
				if(((i + block.getCoordinateY()) <= table.getTableSizeY() && (j + block.getCoordinateX()) <= table.getTableSizeX()) &&
						(shape[i][j] == BlockType.BLOCK || shape[i][j] == BlockType.FILLED)) {
					
					table.setLocationEnum(j + block.getCoordinateX(), i + block.getCoordinateY(), BlockType.EMPTY);
				}
				
			}
				
		}
		
	}
	
public static boolean PlaceBlock(int posX, int posY, Table table, Blocks block) {
		
		BlockType[][] shape = block.returnArray();
		BlockType[][] tableStatus = table.getTableStatus();
		
		boolean flag = true;
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				
				
				if((i + posY) <= table.getTableSizeY() && (j + posX) <= table.getTableSizeX()) {
					if(shape[i][j] == BlockType.BLOCK || shape[i][j] == BlockType.FILLED) {
						if(table.getTableSizeY() > i + posY && table.getTableSizeX() > j + posX) {
						if(tableStatus[i + posY][j + posX] == BlockType.BLOCK ||
								tableStatus[i + posY][j + posX] == BlockType.RED ||
								tableStatus[i + posY][j + posX] == BlockType.FILLED) 
						{
							flag = false;
						}}
						else {
							flag = false;
						}
					}
				}
				else {
					flag = false;
				}
						
			}
		}
		
		if(flag == true) {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if((i + posY) <= table.getTableSizeY() && (j + posX) <= table.getTableSizeX()) {
						if((shape[i][j] == BlockType.BLOCK || shape[i][j] == BlockType.FILLED)) {
							table.setLocationEnum(posX + j, posY + i, shape[i][j]);
						}
					}
				}	
			}
			
			block.setCoordinateX(posX);
			block.setCoordinateY(posY);
		}
		
		return flag;
		
	}
	
	//////////////////// isFree Method ////////////////////
	
	public static boolean isFree(int PositionX, int PositionY, Table Table) {
		// memory extended
		
		Table table = new Table();
		table.setTableStatus(Table.copyTable());
		table.setTableSizeX(Table.getTableSizeX());
		table.setTableSizeY(Table.getTableSizeY());
		
		
		if(PositionX + 1 == table.getTableSizeX()) {
			
			return true;
		}
		if(PositionX - 1 < 0) {
			
			return true;
		}
		if(PositionY + 1 == table.getTableSizeY()) {
			
			return true;
		}
		if(PositionY - 1 < 0) {

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
