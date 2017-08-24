/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import java.util.Comparator;

public class PQ<Key>
{
    private Key[] Heap;
    private int size;
    private Comparator<Key> comparator;  // optional comparator
    private int maxsize;
 
    private static final int FRONT = 1;
    
 
    public PQ(int maxsize)
    {
        this.maxsize = maxsize;
        this.size = 0;
        Heap = (Key[]) new Object[maxsize + 1];
    }
    
    public PQ(int initCapacity, Comparator<Key> comparator) {
        this.comparator = comparator;
        Heap = (Key[]) new Object[initCapacity + 1];
        size = 0;
    }
    
    public PQ(Comparator<Key> comparator) { this(1, comparator); }
    public int size()
    {
        return(size);
    }
 
    private int parent(int pos)
    {
        return pos / 2;
    }
 
    private int leftChild(int pos)
    {
        return (2 * pos);
    }
 
    private int rightChild(int pos)
    {
        return (2 * pos) + 1;
    }
 
    private boolean isLeaf(int pos)
    {
        if (pos >=  (size / 2)  &&  pos <= size)
        {
            return true;
        }
        return false;
    }
 
    private void swap(int fpos,int spos)
    {
        Key tmp;
        tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    }
 
    private void maxHeapify(int pos)
    {
        if (!isLeaf(pos))
        { 
            if( greater(leftChild(pos),pos)  || greater(rightChild(pos),pos))
            {
                if (greater(leftChild(pos),rightChild(pos)))
                {
                    swap(pos, leftChild(pos));
                    maxHeapify(leftChild(pos));
                }else
                {
                    swap(pos, rightChild(pos));
                    maxHeapify(rightChild(pos));
                }
            }
        }
    }
 
    public void insert(Key element)
    {
        Heap[++size] = element;
        int current = size;
 
        while(current>1 && greater(current,parent(current)))
        {
            swap(current,parent(current));
            current = parent(current);
        }	
    }
    private boolean greater(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Key>) Heap[i]).compareTo(Heap[j]) > 0;
        }
        else {
            return comparator.compare(Heap[i], Heap[j]) > 0;
        }
    }
 
    public void print()
    {
        for (int i = 1; i <= size / 2; i++ )
        {
            System.out.print(" PARENT : " + Heap[i] + " LEFT CHILD : " + Heap[2*i]
                  + " RIGHT CHILD :" + Heap[2 * i  + 1]);
            System.out.println();
        }
    }
 
    public void maxHeap()
    {
        for (int pos = (size / 2); pos >= 1; pos--)
        {
            maxHeapify(pos);
        }
    }
 
    public Key remove()
    {
        Key popped = Heap[FRONT];
        Heap[FRONT] = Heap[size--]; 
        maxHeapify(FRONT);
        return popped;
    }
 
    public static void main(String...arg)
    {

    }
}