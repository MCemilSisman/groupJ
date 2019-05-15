package GameEngine;

import ObjectPackage.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.*;

public class MainEngine {
	
	static ScoreKeeper InitialTime = new ScoreKeeper();
	static ScoreKeeper CurrentTime = new ScoreKeeper();
	static ScoreKeeper UserScore = new ScoreKeeper();
	
	//////////////////// ROTATE METHODS ////////////////////
	
	public static void Turn90Right(Blocks block) {
		block.Rotate90Right();
	}
	
	public static void Turn90Left(Blocks block) {
		block.Rotate90Left();
	}
	
////////////////////GETTİNG TİME ////////////////////
	
	public static ScoreKeeper getInitialTime()
	{
		return InitialTime;
	}
	
	public static ScoreKeeper getCurrentTime()
	{
		return CurrentTime;
	}
	
public static void InitLevelTimer(ScoreKeeper init) {
		
		init.returnCurrentTime();
	}
	
	
	public static int returnSeconds(ScoreKeeper init, ScoreKeeper current) {
		
		current.returnCurrentTime();
		LocalDateTime currentTime = current.getTime();
		LocalDateTime InitialTime = init.getTime();
		return (int) (Duration.between(InitialTime, currentTime).toMillis() / 1000);
	    
	}

////////////////////GETTİNG TİME ////////////////////
	
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
		
		if(posX < -2 || posY < -2 || posX >= table.getTableSizeX() || posY >= table.getTableSizeY()) {
			return false;
		}
		
		if(posY == -1) {			
			for(int i = 0; i < 3; i++) {
				if(shape[0][i] == BlockType.BLOCK || shape[0][i] == BlockType.FILLED) {
					return false;
				}
				
			}
		}
		
		if(posX == -1) {			
			for(int i = 0; i < 3; i++) {
				if(shape[i][0] == BlockType.BLOCK || shape[i][0] == BlockType.FILLED) {
					return false;
				}	
			}
		}	
		
		if( posY == -2 ) {			
			for(int i = 0; i < 3; i++) {
				if(shape[0][i] == BlockType.BLOCK || shape[0][i] == BlockType.FILLED) {
					return false;
				}
				
				if(shape[1][i] == BlockType.BLOCK || shape[1][i] == BlockType.FILLED) {
					return false;
				}
				
			}
		}
		
		if( posX == -2) {
			for(int i = 0; i < 3; i++) {
				if(shape[i][0] == BlockType.BLOCK || shape[i][0] == BlockType.FILLED) {
					return false;
				}
				if(shape[i][1] == BlockType.BLOCK || shape[i][1] == BlockType.FILLED) {
					return false;
				}
			}
		}	
		
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				
				if(shape[i][j] == BlockType.BLOCK || shape[i][j] == BlockType.FILLED) {
					if(table.getTableSizeY() > i + posY && table.getTableSizeX() > j + posX) {
						if(tableStatus[i + posY][j + posX] == BlockType.BLOCK ||
								tableStatus[i + posY][j + posX] == BlockType.RED ||
								tableStatus[i + posY][j + posX] == BlockType.FILLED) 
						{
							flag = false;
						}
					}
					else {
						flag = false;
					}
					
				}		
			}
		}
		
		
		/*for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				
				if((i + posY) <= table.getTableSizeY() && (j + posX) <= table.getTableSizeX()) {
					if(shape[i][j] == BlockType.BLOCK || shape[i][j] == BlockType.FILLED) {
						if(table.getTableSizeY() > i + posY && table.getTableSizeX() > j + posX) {
							if(tableStatus[i + posY][j + posX] == BlockType.BLOCK ||
									tableStatus[i + posY][j + posX] == BlockType.RED ||
									tableStatus[i + posY][j + posX] == BlockType.FILLED) 
							{
								flag = false;
							}
						}
						else {
							flag = false;
						}
					}
				}
				else {
					flag = false;
				}
						
			}
		}*/
		
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




	
////////////////////isFree Method ////////////////////

public static boolean isFree(int PositionX, int PositionY, Table Table) {
// memory extended

Table table = new Table();
table.setTableStatus(Table.copyTable());
table.setTableSizeX(Table.getTableSizeX());
table.setTableSizeY(Table.getTableSizeY());
boolean flag = true;

if(PositionX + 1 == table.getTableSizeX()) {

return flag;
}
if(PositionX -1 == -1) {

return flag;
}
if(PositionY + 1 == table.getTableSizeY()) {

return flag;
}
if(PositionY - 1 == -1) {

return flag;
}

if(table.returnLocationEnum(PositionX + 1, PositionY) == BlockType.FILLED || table.returnLocationEnum(PositionX + 1, PositionY) == BlockType.EMPTY) {

/*System.out.println("down " + (PositionX + 1) + " " + PositionY);*/
table.setLocationEnum(PositionX, PositionY, BlockType.MARKED);
if(isFree(PositionX + 1, PositionY, table)) {
return flag;
}
}


if(table.returnLocationEnum(PositionX - 1, PositionY) == BlockType.FILLED || table.returnLocationEnum(PositionX - 1, PositionY) == BlockType.EMPTY) {

/*System.out.println("up " + (PositionX - 1) + " " + PositionY);*/
table.setLocationEnum(PositionX, PositionY, BlockType.MARKED);
if(isFree(PositionX - 1, PositionY, table)) {
return flag;
}
}


if(table.returnLocationEnum(PositionX, PositionY + 1) == BlockType.FILLED || table.returnLocationEnum(PositionX, PositionY + 1) == BlockType.EMPTY) {

/*System.out.println("right" + PositionX + " " + (PositionY + 1));*/
table.setLocationEnum(PositionX, PositionY, BlockType.MARKED);
if(isFree(PositionX, PositionY + 1, table)) {
return flag;
}
}


if(table.returnLocationEnum(PositionX, PositionY - 1) == BlockType.FILLED || table.returnLocationEnum(PositionX, PositionY - 1) == BlockType.EMPTY) {

/*System.out.println("left " + PositionX + " " + (PositionY - 1));*/
table.setLocationEnum(PositionX, PositionY, BlockType.MARKED);
if(isFree(PositionX, PositionY - 1, table)) {
return flag;
}

}

if(CheckTableSpaces(table)) {
flag = false;
}
return flag;
}

//////////////////// isFree Method ////////////////////


public static boolean CheckTableSpaces(Table table) {

BlockType[][] ts = table.getTableStatus();

for (int i = 0; i < ts.length; i++) {
for (int j = 0; j < ts[i].length; j++) { 
if(ts[i][j] == BlockType.EMPTY) {
return false;
}
}
}
return true;
}

public static String saveSortReturnScores(String player, int score, int level) throws IOException {
	  
	//System.out.println(new File("Scores").getAbsoluteFile());
	String levelAdd = "resources//Scores";
	levelAdd += Integer.toString(level);
	try {
		FileInputStream fin = new FileInputStream(levelAdd);
	}
	catch(Exception e)
	{
		FileOutputStream out = new FileOutputStream(levelAdd); 
		out.close();
	}
	FileInputStream fin = new FileInputStream(levelAdd);
	
	String ScoreBoard[][] = new String[10][2];
	
	int ch;
	int i = 0;
	int j = 0;
	String template = "";
    while((ch=fin.read())!=-1) {
    	char temp = (char)ch; 
        //System.out.print(temp);
    	if (temp == '*') {
    		template = "";
    	}
    	else if(temp == '#') {
    		ScoreBoard[i][j] = template;
    		template = "";
    		j++;
    		if(j==2) {
    			j = 0;
    			i++;
    		}
    	}
    	else if(temp == '$') {
    		break;
    	}
    	else {
    		template = template + temp;
    	}
    }
    
    /*for(i=0 ; i< ScoreBoard.length; i++) {
    	for(j=0; j< ScoreBoard[i].length; j++) {
    		System.out.print(ScoreBoard[i][j] + " ");
    	}
    	System.out.println("");
    }*/ 
    
	fin.close();
	
	
	
	// Getting the size of the scoreBoard
	int size = 0;
	while(true) {
		
		if(ScoreBoard[size][0] == null || size == 9) {
			break;
		}
		size++;
	}
	
	
	//System.out.println("addition " + size);
	// adding new Score
	// String player, int score
	if(size == 0) {
		ScoreBoard[size][1] = Integer.toString(score);
		ScoreBoard[size][0] = player;
	}
	
	for(i=0 ; i< size; i++) {	
		
		if(Integer.parseInt(ScoreBoard[i][1]) > score) {
    		ScoreBoard[size][1] = Integer.toString(score);
    		ScoreBoard[size][0] = player;
    		break;
    	}
		if(!(Integer.parseInt(ScoreBoard[i][1]) > score) && size < 10) {
			ScoreBoard[size][1] = Integer.toString(score);
    		ScoreBoard[size][0] = player;
    		break;
		}
    }
	
	
	// Sorting the scoreBoard
	size++;
       for (int a = 1; a < size; ++a) {
            int key = Integer.parseInt(ScoreBoard[a][1]); 
            String name = ScoreBoard[a][0];
            int b = a - 1; 
  
        
            while (b >= 0 && Integer.parseInt(ScoreBoard[b][1]) > key) { 
            	ScoreBoard[b + 1][1] = ScoreBoard[b][1];
            	ScoreBoard[b + 1][0] = ScoreBoard[b][0];
                b = b - 1; 
            }
            ScoreBoard[b + 1][1] = Integer.toString(key);
            ScoreBoard[b + 1][0] = name;
        }
	
       
       
       	// Preparing the text Scores to write to Scores file
        String text = "";
        for(i=0 ; i< ScoreBoard.length; i++) {
        	text = text + '*';
        	for(j=0; j< ScoreBoard[i].length; j++) {
        		if(ScoreBoard[i][j] != null)
        		text = text + ScoreBoard[i][j] + '#';
        	}
        	text = text + '\n';
    	}
        text = text + '$';
        
        // printing to the Scores file
        
        PrintWriter newfile = new PrintWriter(levelAdd);
        
        newfile.println(text);
        
        newfile.close();
        
        
        
     // Preparing the returntext Scores to write to Scores file
        String returnText = "";
        for(i=0 ; i< ScoreBoard.length; i++) {
        	returnText =  returnText + (i + 1) + ". ";
        	for(j=0; j< ScoreBoard[i].length; j++) {
        		if(ScoreBoard[i][j] != null)
        			returnText = returnText + ScoreBoard[i][j] + " ";
        	}
        	returnText = returnText + '\n';
    	}
        
        return returnText;
        
}
}
