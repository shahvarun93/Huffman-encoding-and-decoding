
package huffman;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class BitInput{
    private FileInputStream input;
    private int currentBit=8;
    private int currentByte;
    
    //constructor
    public BitInput(File file) throws FileNotFoundException{
        input=new FileInputStream(file);
       
    }

    public boolean readBit() throws IOException{
        if(currentBit==8){
            currentBit=0;
            currentByte=input.read();//read byte from file
            
            if(currentByte==-1)//if end of file
                throw new EOFException();
        }
        boolean v=(currentByte &(1<<(7-currentBit)))!=0;
        currentBit++;
        return v;
    }

    
}
