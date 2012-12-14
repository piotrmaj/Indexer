/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package indexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author Maju
 */
public class IndexerData {
    private Word[] words;
    private int count;
    private int fileNr;
    
    public IndexerData()
    {
        words=new Word[256];
        count=0;
        fileNr=0;
    }
    
    public Word[] getWords()
    {
        return words;
    }
    
    public void IndexFile(String fileName) throws FileNotFoundException 
    {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file,"ISO-8859-2");
        int filePos=0;
        while (scanner.hasNext()) {
                String word=scanner.next().replaceAll("[^a-zA-Z]", "");
                if(word.matches("[a-zA-Z]+"))
                {
                    indexWord(word.toLowerCase(),fileNr,filePos);
                    filePos++;
                }
        }
        scanner.close();
        fileNr++;
    }
    
    private void indexWord(String word,int fileNr,int filePos)
    {
        int bs=binaryWordSearch(word,0,count-1);
        if (bs!=-1)
        {
            words[bs].addPostion(new Position(fileNr,filePos));
        }
        else
        {
            addWord(word,fileNr,filePos);
        }
                    
    }
    
    private void addWord(String word,int fileNr,int filePos)
    {
        if(words.length==count)
        {
            words = Arrays.copyOf(words, words.length * 2);
        }
        if (count==0)
        {
            words[count]=new Word(word);
            words[count].addPostion(new Position(fileNr,filePos));
            count++;
            return;
        }
        int i;
        for(i=0;i<count;i++)
        {
            if(words[i].compareTo(word)>0)
            {
                for(int j=count;j>i;j--)
                {
                  words[j]=words[j-1];
                }
                break;
            }
        }
        words[i]=new Word(word);
        words[i].addPostion(new Position(fileNr,filePos));
        count++;
    }
    
    public void printWords()
    {
        for(int i=0;i<count;i++)
        {
            System.err.print("\n"+i+" \""+words[i].getContent()+ "\" ");
            for (int j=0;j<words[i].getPositionsCount();j++)
            {
                System.err.print(" "+words[i].getPositions()[j].toString()+";");
            }
        }
    }
    
    public Position getNextWordPostion(String word,int fileNr,int afterPos) throws Exception
    {
        int result=binaryWordSearch(word,0,count);
        if(result==-1)
        {
            throw new Exception("word not found");
        }
        for (int i=0;i<words[result].getPositionsCount();i++)
        {
            if (words[result].getPositions()[i].getFileNr()==fileNr && words[result].getPositions()[i].getFilePos()>afterPos)
            {
                return words[result].getPositions()[i];
            }
        }
        throw new Exception("word not found");
    }
    
    public Position getNextWordGlobalPostion(String word,int firstFileNr,int afterPos) throws Exception
    {
        int result=binaryWordSearch(word,0,count);
        if(result==-1)
        {
            throw new Exception("word not found");
        }
        for (int i=0;i<words[result].getPositionsCount();i++)
        {
            if ( (words[result].getPositions()[i].getFileNr()==firstFileNr && words[result].getPositions()[i].getFilePos()>afterPos) || words[result].getPositions()[i].getFileNr()>firstFileNr)
            {
                return words[result].getPositions()[i];
            }
        }
        throw new Exception("word not found");
    }
    
    public Position getPrevWordPostion(String word,int fileNr,int beforePos) throws Exception
    {
        int result=binaryWordSearch(word,0,count);
        if(result==-1)
        {
            throw new Exception("word not found");
        }
        for (int i=words[result].getPositionsCount()-1;i>=0;i--)
        {
            if (words[result].getPositions()[i].getFileNr()==fileNr && words[result].getPositions()[i].getFilePos()<beforePos)
            {
                return words[result].getPositions()[i];
            }
        }
        throw new Exception("word not found");
    }
    
    public Position getPrevWordGlobalPostion(String word,int lastFileNr,int beforePos) throws Exception
    {
        int result=binaryWordSearch(word,0,count);
        if(result==-1)
        {
            throw new Exception("word not found");
        }
        for (int i=words[result].getPositionsCount()-1;i>=0;i--)
        {
            if ( (words[result].getPositions()[i].getFileNr()==lastFileNr && words[result].getPositions()[i].getFilePos()<beforePos) || words[result].getPositions()[i].getFileNr()<lastFileNr)
            {
                return words[result].getPositions()[i];
            }
        }
        throw new Exception("word not found");
    }
    
    private int binaryWordSearch( String content, int left, int right) {
      if (left > right || count==0)
      {
            return -1;
      }
      int middle = (left + right) / 2;
      if (words[middle].getContent().compareTo(content)==0)
      {
            return middle;
      }
      else if (words[middle].getContent().compareTo(content)>0)
      {
            return binaryWordSearch(content, left, middle - 1);
      }
      else
      {
            return binaryWordSearch(content, middle + 1, right); 
      }
    }
}
