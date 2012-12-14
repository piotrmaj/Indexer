/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package indexer;

import java.util.Arrays;

/**
 *
 * @author Maju
 */
public class Word implements Comparable {
    private String content;
    private Position[] positions;
    private int posCount;
    
    public Word(String content)
    {
        this.content=content;
        positions=new Position[16];
        posCount=0;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public Position[] getPositions()
    {
        return positions;
    }
    
    public void addPostion(Position position)
    {
        if(positions.length==posCount)
        {
            positions = Arrays.copyOf(positions, positions.length * 2);
        }
        positions[posCount]=position;
        posCount++;
    }
    
    public int getPositionsCount()
    {
        return posCount;
    }

    @Override
    public int compareTo(Object o) {
        return content.compareTo((String)o);
    }
}
