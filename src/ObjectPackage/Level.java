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
        
        public Table getTable(){
            return table;
        }
        public Blocks[] getBlocks(){
            return blocksArray;
        }
        
}
