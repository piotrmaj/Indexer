/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package indexer;

/**
 *
 * @author Maju
 */
public class Position {
    private int fileNr;
    private int filePos;
    
    public Position()
    {
        this.fileNr=0;
        this.filePos=0;
    }
    
    public Position(int fileNr,int filePos)
    {
        this.fileNr=fileNr;
        this.filePos=filePos;
    }
    
    public int getFileNr()
    {
        return fileNr;
    }
    
    public int getFilePos()
    {
        return filePos;
    }
    
    @Override
    public String toString()
    {
        return String.format("Found in document %d, word %d", fileNr,filePos);
    }
}
