/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package huffman;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitOutput {
    private final FileOutputStream output;
    private int BinValue;
    private int count = 0;
    private final int True = 1; // The bits are all zeros except the last one

    public BitOutput(File file) throws IOException {
      output = new FileOutputStream(file);//call constructor of FileOutput Stream
    }
    
    /*
    The writeBit() method get a String as its parameter and 
    passes its contents one by one
    */
    public void writeBit(String bitString) throws IOException {
      for (int i = 0; i < bitString.length(); i++)
        writeBit(bitString.charAt(i));//pass one by one to the function
    }

    public void writeBit(char bit) throws IOException {
      count++;//increament count per bit
      BinValue = BinValue << 1;//left shift the bit 
      
      if (bit == '1') //if bit is one save it in value as 1
        BinValue = BinValue | True;
      
      if (count == 8) {// when 8bits completed write it to the stream
        output.write(BinValue);
        count = 0;
      }
    }
       
}
