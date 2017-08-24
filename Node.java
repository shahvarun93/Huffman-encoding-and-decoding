package huffman;
import java.util.PriorityQueue;

public class Node implements Comparable<Node> {
    Node left;
    Node right;
    Node parent;
    char text;
    int frequency;
    String code="";
    public Node(char textIn, int frequencyIn) {
        text = textIn;
        frequency = frequencyIn;
    }

    public Node(int frequencyIn) {
        text = '\0';
        frequency = frequencyIn;
    }
    
    public Node(char Char,String code)
    {
        this.text=Char;
        this.code=code;
        
    }

    public int compareTo(Node n) {
        if (frequency < n.frequency) {
            return -1;
        }
        else if(frequency > n.frequency) {
            return 1;
        }
        return 0;
    }
    
       public boolean isLeaf() {
            assert (left == null && right == null) || (left != null && right != null);
            return (left == null && right == null);
        }
}