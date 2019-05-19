package GameEngine;

import ObjectPackage.*;
import java.io.*;

public class LevelManager
{
	static FileInputStream in;
	
	public static Level loadLevel(String levelCode) throws IOException
	{
		in = new FileInputStream("resources//Levels");
		Level current = new Level();
		boolean foundFlag = false;
		char temp;
		String entity = "";
		int i = 0;
		
		int sizeX = 0;
		int sizeY = 0;
		
		while(in.available() != 0 && foundFlag != true)
		{
			i = in.read();
			temp = (char)i;
			if(temp == '$')
			{
				boolean checkedFlag = false;
				while(in.available() != 0 && checkedFlag != true)
				{
					i = in.read();
					temp = (char)i;
					entity += temp;
					System.out.println(entity);
					if(entity.equals("Code:"))
					{
						entity = "";
						while(in.available() != 0 && temp != ' ')
						{
							i = in.read();
							temp = (char)i;
							if(temp != ' ')
								entity += temp;
							else
								if(entity.equals(levelCode))
									foundFlag = true;
							System.out.println(entity);
						}
						entity = "";
						checkedFlag = true;
					}
				}
			}
			//System.out.println(foundFlag);
			if(foundFlag == true)
			{
				boolean loadedFlag = false;
				while(in.available() != 0 && loadedFlag != true)
				{
					i = in.read();
					temp = (char)i;
					entity += temp;
					System.out.println(entity);
					if(entity.equals("TableSize:"))
					{
						boolean sizeFlag = false;
						entity = "";
						while(in.available() != 0 && sizeFlag != true)
						{
							i = in.read();
							temp = (char)i;
							if(temp != ' ')
							{
								if(temp != 'x')
									entity += temp;
								else
								{
									sizeX = Integer.valueOf(entity);
									entity = "";
								}
							}
							else
							{
								sizeY = Integer.valueOf(entity);
								entity = "";
								sizeFlag = true;
							}
							//System.out.println(sizeFlag);
						}
						System.out.println(sizeX + "x" + sizeY);
						current.createTable(sizeX, sizeY);
						entity = "";
					}
					if(entity.equals("NumberOfBlocks:"))
					{
						boolean numFlag = false;
						entity = "";
						while(in.available() != 0 && numFlag != true)
						{
							i = in.read();
							temp = (char)i;
							if(temp != '#')
								entity += temp;
							else
							{
								current.createBlocks((Integer.valueOf(entity)));
								numFlag = true;
								entity = "";
							}
						}
					}
					if(temp == '[')
					{
						boolean carsFlag = false;
						String type = "";
						int times = 0;
						entity = "";
						while(in.available() != 0 && carsFlag != true)
						{
							i = in.read();
							temp = (char)i;
							if(temp != ' ')
							{
								if(temp != ']')
								{
									if(temp != ':')
									{
										entity += temp;
										System.out.println(entity);
									}
									else
									{
										type = entity;
										entity = "";										
									}
								}
								else
								{
									carsFlag = true;
									entity = "";
								}
							}
							else
							{
								times = Integer.valueOf(entity);
								switch(type)
								{
								case "CarA":
									while(times > 0)
									{
										current.addBlock(new CarA());
										times--;
									}
									break;
								case "CarB":
									while(times > 0)
									{
										current.addBlock(new CarB());
										times--;
									}
									break;
								case "CarC":
									while(times > 0)
									{
										current.addBlock(new CarC());
										times--;
									}
									break;
								case "CarD":
									while(times > 0)
									{
										current.addBlock(new CarD());
										times--;
									}
									break;
								case "CarE":
									while(times > 0)
									{
										current.addBlock(new CarE());
										times--;
									}
									break;
								case "CarF":
									while(times > 0)
									{
										current.addBlock(new CarF());
										times--;
									}
									break;
								case "CarG":
									while(times > 0)
									{
										current.addBlock(new CarG());
										times--;
									}
									break;
								case "CarH":
									while(times > 0)
									{
										current.addBlock(new CarH());
										times--;
									}
									break;
								case "CarI":
									while(times > 0)
									{
										current.addBlock(new CarI());
										times--;
									}
									break;
								}
								entity = "";
							}
						}
					}
					if(temp == '*')
					{
						int x = 0;
						int y = 0;
						boolean tableFlag = false;
						while(in.available() != 0 && tableFlag != true)
						{
							i = in.read();
							temp = (char)i;
							if(temp == '/')
							{
								sizeY--;
								boolean rowFlag = false;
								while(in.available() != 0 && rowFlag != true)
								{
									i = in.read();
									temp = (char)i;
									if(temp != '/')
									{
										if(temp == 'b')
											current.setTable(x, y, BlockType.BLOCK);
										if(temp == 'r')
											current.setTable(x, y, BlockType.RED);
										if(temp == '-')
											current.setTable(x, y, BlockType.EMPTY);
										x++;
									}
									else
										rowFlag = true;
								}
								x = 0;
								y++;
							}
							if(temp == '*')
								tableFlag = true;
						}
						loadedFlag = true;
					}
				}
			}
		}
		in.close();
		return current;
	}
	
	public static int getLevelNumber() throws IOException
	{
		in = new FileInputStream("resources//Levels");
		int numberOfLevel = 0;
		int i;
		char temp;
		
		while(in.available() != 0)
		{
			i = in.read();
			temp = (char)i;
			if(temp == '$')
				numberOfLevel++;
		}
		
		
		in.close();
		
		return numberOfLevel;
	}
	
	public static void saveLevel(Level level) throws IOException {
		
		String levelText = "\n$Code:A";
		
		int levelCount = 0;
		int ch;
		FileInputStream fin = new FileInputStream("resources//Levels");
		
		while((ch=fin.read())!=-1) {
        	char temp = (char)ch; 
        	if (temp == '$') {
        		levelCount++;
        	}		
		}
		
		if (levelCount > 9) {
			levelText = levelText + levelCount;
		}
		else {
			levelText = levelText + "0" + levelCount + " ";
		}
		
		int posX = level.getTable().getTableSizeX();
		int posY = level.getTable().getTableSizeY();
		
		levelText = levelText + "TableSize:" + posX + "x" + posY + " ";
		
		Blocks[] blockArr = level.getBlocks();
		int noBlocks = blockArr.length;
		levelText = levelText + "NumberOfBlocks:" + noBlocks +"#\n";
		
		levelText = levelText + "[";
		
		Blocks prev;
		int times;
		for(int i = 0; i < blockArr.length; i++)
		{
			if(blockArr[i] != null)
			{
				if(blockArr[i].getCoordinateX() != -1)
				{
					prev = blockArr[i];
					times = 1;
					for(int k = i + 1; k < blockArr.length; k++)
					{
						System.out.println(blockArr[k].getClass() + " ve " + prev.getClass());
						if(blockArr[k].getClass().equals(prev.getClass()) && blockArr[k] != null)
						{
							if(blockArr[k].getCoordinateX() != -1)
							{
								times++;
								blockArr[k] = null;
							}
						}
					}
					levelText = levelText + (prev.getImage().replace("resources//", "")).replace(".png", "") + ":" + times + " ";
					blockArr[i] = null;
					for(int j = 0; j < blockArr.length; j++)
						System.out.println(blockArr[j]);
				}
			}
		}
		levelText = levelText + "]\n*\n";
		for(int i = 0; i < level.getTable().getTableSizeX(); i++)
		{
			levelText = levelText + "/";
			for(int j = 0; j < level.getTable().getTableSizeY(); j++)
			{
				if(level.getTable().returnLocationEnum(j, i).equals(BlockType.EMPTY))
				{
					levelText = levelText + "-";
				}
				else if(level.getTable().returnLocationEnum(j, i).equals(BlockType.BLOCK))
				{
					levelText = levelText + "b";
				}
				else if(level.getTable().returnLocationEnum(j, i).equals(BlockType.RED))
				{
					levelText = levelText + "r";
				}
			}
			levelText = levelText + "/\n";
		}
		levelText = levelText + "*";
		
		FileWriter appender = new FileWriter("resources//Levels", true);
		
		PrintWriter newfile = new PrintWriter(appender);
        
        newfile.print(levelText);
        
        newfile.close();
		
		fin.close();
	}
	
	/*
	public static void main(String args[]) throws IOException
	{
		LevelManager a = new LevelManager();
		
		Level b = a.loadLevel("A00");
		
		Table c = b.getTable();
		
		BlockType[][] d = c.getTableStatus();
		
		for(int k = 0; k < 6; k++)
		{
			for(int l = 0; l < 6; l++)
			{
				System.out.print(d[k][l] + "\t");
			}
			System.out.print("\n");
		}
		
		Blocks[] e;
		
		e = b.getBlocks();
		
		int f = e.length;
		
		BlockType[][] g;
		
		System.out.println("");
		
		while(f > 0)
		{
			g = e[e.length - f].getBlockSize();
			for(int k = 0; k < 3; k++)
			{
				for(int l = 0; l < 3; l++)
					System.out.print(g[k][l] + "\t");
				System.out.println("");
			}
			System.out.println("");
			f--;
		}
	}
	*/
}
