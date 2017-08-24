/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;
import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
//import java.io.File.delete;

public class huffman_1576  {
 
    public static String[] assign_codes(Node root)
    {
        if(root==null)
            return null;
        String c[]=new String[256];
        rintFromPreOrder(root, c);
        return c;
    }
    
    public static void rintFromPreOrder( Node n, String dashes[]) {
        // print with colon if leaf node
        
        if(n==null)
            return;

        // Start recursive on left child then right 
        if (n.left != null ) {
            n.left.code=n.code + "0";
            rintFromPreOrder( n.left, dashes );
            //st+=dashes;
        }
        if(n.right != null)
        {
            n.right.code=n.code + "1";
            rintFromPreOrder( n.right, dashes );
        }
        else
            dashes[(int)n.text]=n.code;
//        if(n.left==null && n.right==null)
//        {
//            //st=st + dashes;
          // System.out.println(n.text+":"+dashes);
//        }
    }
    // Returns root node to pass to printFromPreOrder
    public static Node makeHuffmanTree(int frequencies[]) {
        //PriorityQueue<Node> queue = new PriorityQueue<Node>();
        PQ<Node> pq=new PQ<>(frequencies.length*2);
        for (int i = 0; i < frequencies.length; i++) {
            if(frequencies[i]>0)
            {
                Node n = new Node((char)i, frequencies[i]);
                pq.insert(n);
            }
        }
        Node root = null;
        while (pq.size() > 1)  {
            Node least1 = pq.remove();
            Node least2 = pq.remove();
            Node combined = new Node(least1.frequency + least2.frequency);
            combined.right = least2;
            combined.left = least1;
            least1.parent = combined;
            least2.parent = combined;
            pq.insert(combined);
            // Keep track until we actually find the root
            root = combined;
        }
        return root;
    } 
    
    public static int[] getfrequency(String fileName) throws FileNotFoundException, IOException{
        BufferedInputStream fileInput=new BufferedInputStream(new FileInputStream(new File(fileName)));//open the file
        int [] counts=new int[256];
        int Read,sum=0;
        
        while((Read=fileInput.read())!=-1){ //if it is not the end of file
            //System.out.println(Read);
            counts[Read]++;//if char appears increase value at its coressponding position
            sum++;
        }
        System.out.println(sum);

        fileInput.close();
        return counts;
    }

    
        public static void compress(String args) throws IOException
        {
            String filename=args;
            int freq[]=getfrequency(filename);
            Node root = makeHuffmanTree(freq);
            String assign_code[]=assign_codes(root);
            for(int i=0;i<assign_code.length;i++){//print table
//            if(freq[i]!=0)
//                System.out.printf("%-15d%-15s%-15d%-15s\n",i,(char)i+"",freq[i],assign_code[i]);
        }
            encode(filename,assign_code,freq);

            PrintWriter ouput=new PrintWriter(new File("Table.fr"));//write frequency table to the file for future usage
            for(int i=0;i<freq.length;i++)
            {
                 ouput.write(String.valueOf(freq[i]));
                 ouput.println();
            }  
         ouput.flush();
         ouput.close();
     }
        public static void encode(String file, String code_table[],int freq[]) throws IOException
        {
            BufferedInputStream fileInput = new BufferedInputStream( new FileInputStream(new File(file)));//open the input file
            BitOutput output = new BitOutput(new File(file + ".huff"));         
            int r,sum=0,c=0;
            while ((r = fileInput.read()) != -1 ) //iterate the input file till the end and read byte by byte
            {
                    output.writeBit(code_table[r]);//write codes in file via output stream
                    int l=code_table[r].length();
                    sum+=(l*freq[r]);
                    c++;
            }
            sum=sum/8;
            System.out.println(sum+":"+c);
        }
           
    public static void main(String[] args) throws IOException{
       
       //Node root = Node.makeHuffmanTree(frequencies,text);
        if(args[0].equals("henc"))
        compress(args[1]);
        else if (args[0].equals("hdec"))
        decodeFile(args[1]);
        //decode(code, root);        
    }
    
    
    public static void decodeFile(String compFile) throws FileNotFoundException, IOException{

        System.out.println("[+] Opening Frequency Table: Table.fr");
        BufferedReader br=new BufferedReader(new FileReader("Table.fr"));// open frequency table
        String line;
        String[] fr=new String[256];
        int counter=0;
        while((line=br.readLine())!=null){//read frequency table file till end and save contents in string array
          if(!line.matches("null")){
              fr[counter]=line;
          }
          counter++;
      }
      int [] codes=new int[256];
      for(int j=0;j<256;j++){// convert string array to integer array 
          codes[j]=Integer.parseInt(fr[j]);
      }
      Node ht=makeHuffmanTree(codes);// build huffmanTree
      String[] Codes=assign_codes(ht);
      BitInput bitInput=new BitInput(new File(compFile));
      String test="";
      System.out.println("[*] Decoding");
      try{
      while(true){// call the getLeaves() method till the end of compressed file
          test=test+found_Leaves(ht,bitInput);
      }
      }catch(Exception e){}
      
      File f=new File(compFile);
      System.out.println("----DONE-----\n Decoded");
      compFile=compFile.substring(0,compFile.lastIndexOf("."));
      PrintWriter output=new PrintWriter(new File(compFile));//open new file for restoring
      File f1=new File("Table.fr");
      boolean b=false;
      boolean b1=false;
      try
      {
      b=f.delete();
      b1=f1.delete();    
      }
      catch(Exception e)
      {
          
      }
      output.write(test);// write decoded text to the file
      output.close();
      //System.out.println(test);// print decoded text to the screen
    }
    public static String found_Leaves(Node root,BitInput bitInput) throws IOException{
     String text="";
     if(root==null)//base condition 1
         return null;
     else if(root.left==null && root.right==null)//base condition 2
         text=text+root.text;//save char in string
     else{
         if(bitInput.readBit()==true)//read 1 bit from file if it is 1
             text=text+found_Leaves(root.right,bitInput);//if bit is 1 goto right
         else//if bit is 0
             text=text+found_Leaves(root.left,bitInput);//if bit is 0 goto left
     }
     return text;
  }
    
}

