package indexer;

import java.io.FileNotFoundException;

/**
 *
 * @author Maju
 */
public class Indexer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IndexerData data=new IndexerData();
        long startTime = System.nanoTime();
        for (int i=0;i<args.length;i++)
        {
            try{
                data.IndexFile(Indexer.class.getProtectionDomain().getCodeSource().getLocation().getPath()+args[i]);
            }
            catch(FileNotFoundException e){
                System.out.println("Some of files are unable to read or not exists.");
                System.exit(1);
            }    
        }
        long estimatedTime = System.nanoTime() - startTime; 
        System.out.println(String.format("Zaindeksowano %d pliki w %f sekund",args.length,(double)estimatedTime/1000000000));
        
        Menu();
    }
    
    public static void Menu()
    {
        System.out.println("(next? previos? stop?)");
        boolean exit=false;
        do
        {
            exit=true;
        }
        while(!exit);
    }
}
