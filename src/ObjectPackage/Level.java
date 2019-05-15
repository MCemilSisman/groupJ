package ObjectPackage;
public class Level {
        Blocks[] blocksArray;
        Table table;
        int noOfBlocks;
        public Level()
        {
        	
        }
        public void createTable(int sizeX, int sizeY)
        {
            table = new Table(sizeX, sizeY);
        }
        
        public void createBlocks(int numberOfBlocks)
        {
            blocksArray = new Blocks[numberOfBlocks];
            noOfBlocks = numberOfBlocks;
        }
        
        public void setTable(int x, int y, BlockType type)
        {
            table.setLocationEnum(x,y,type);
        }
        
        public void addBlock(Blocks a)
        {
            boolean found = false;
            for( int i = 0; i < noOfBlocks; i++){
                if(blocksArray[i] == null){
                    if( found == false){
                        blocksArray[i] = a;
                        found = true;
                    }
                    
                }
            }
        }
        
        public void addEditorBlock(Blocks added)
        {
        	if(blocksArray != null)
        	{
	        	Blocks[] temp = new Blocks[noOfBlocks + 1];
	        	for(int i = 0; i < noOfBlocks; i++)
	        		temp[i] = blocksArray[i];
	        	temp[noOfBlocks] = added;
	        	blocksArray = temp;
        	}
        	else
        	{
        		blocksArray = new Blocks[1];
        		blocksArray[0] = added;
        	}
        	noOfBlocks++;
        }
        
        public void removeEditorBlock(Blocks removed)
        {
        	if(noOfBlocks != 1)
        	{
	        	Blocks[] temp = new Blocks[noOfBlocks - 1];
	        	boolean found = false;
	        	for(int i = 0; i < noOfBlocks; i++)
	        	{
	        		if((!(blocksArray[i].equals(removed))) && !found)
	        		{
	        			temp[i] = blocksArray[i];
	        		}
	        		else if(found)
	        		{
	        			temp[i - 1] = blocksArray[i];
	        		}
	        		else
	        			found = true;
	        	}
	        	blocksArray = temp;
        	}
        	else
        	{
        		blocksArray = null;
        	}
        	noOfBlocks--;
        }
        
        public Table getTable(){
            return table;
        }
        public Blocks[] getBlocks(){
            return blocksArray;
        }
        
}
