package com.epam.task4;
/*PROBLEM STATEMENT
 
 	Huffman coding assigns variable length codewords to fixed length input characters based on their frequencies.
More frequent characters are assigned shorter codewords and less frequent characters are assigned longer codewords.
All edges along the path to a character contain a code digit. If they are on the left side of the tree, they will be a 0 (zero). 
If on the right, they'll be a 1 (one). Only the leaves will contain a letter and its frequency count. 
All other nodes will contain a null instead of a character, and the count of the frequency of all of it and its descendant characters.

	For instance, consider the string ABRACADABRA. There are a total of  characters in the string. 
This number should match the count in the ultimately determined root of the tree. Our frequencies are  and . 
The two smallest frequencies are for  and , both equal to , so we'll create a tree with them. The root node will contain 
the sum of the counts of its descendants, in this case . The left node will be the first character encountered, , and the right will contain .
Next we have items with a character count of : the tree we just created, the character  and the character . The tree came first, 
so it will go on the left of our new root node.  will go on the right. Repeat until the tree is complete, then fill in the 's and 's for the edges. 
The finished graph looks like:

Input characters are only present in the leaves. Internal nodes have a character value of w (NULL). We can determine that our values for characters are:

A - 0
B - 111
C - 1100
D - 1101
R - 10
Our Huffman encoded string is:

A B    R  A C     A D     A B    R  A
0 111 10 0 1100 0 1101 0 111 10 0
or
01111001100011010111100
To avoid ambiguity, Huffman encoding is a prefix free encoding technique. No codeword appears as a prefix of any other codeword.

To decode the encoded string, follow the zeros and ones to a leaf and return the character there.

You are given pointer to the root of the Huffman tree and a binary coded string to decode. You need to print the decoded string.

Function Description

Complete the function decode_huff in the editor below. It must return the decoded string.

decode_huff has the following parameters:

root: a reference to the root node of the Huffman tree
s: a Huffman encoded string


* Input Format

	There is one line of input containing the plain string, . Background code creates the Huffman tree then passes the head node and the encoded string to the function.

* Constraints
	1<=|s|<=25

* Output Format

	Output the decoded string on a single line.





* Sample Input									* Sample Output

	s="1001011"										ABACA



 
 */
import java.util.*;

abstract class Node implements Comparable<Node> {
    public  int frequency; // the frequency of this tree
    public  char data;
    public  Node left, right; 
    public Node(int freq) { 
      frequency = freq; 
    }
 
    // compares on the frequency
    public int compareTo(Node tree) {
        return frequency - tree.frequency;
    }
}
 
class HuffmanLeaf extends Node {
    
 
    public HuffmanLeaf(int freq, char val) {
        super(freq);
        data = val;
    }
}
 
class HuffmanNode extends Node {
    
    public HuffmanNode(Node l, Node r) {
        super(l.frequency + r.frequency);
        left = l;
        right = r;
    }

}


class Decoding {

/*  
	class Node
		public  int frequency; // the frequency of this tree
    	public  char data;
    	public  Node left, right;
    
*/ 

	void decode(String s, Node root) {
        char input[]=new char[100];
        input=s.toCharArray();/*converting the given string to character array*/
        Node root_node=root;
        for(int i=0;i<s.length();i++)
        {
            if(input[i]=='0')/*if input is 0 we are moving left side of tree*/
            root_node=root_node.left;
            else if(input[i]=='1')/*if input is 0 we are moving Right side of tree*/
            root_node=root_node.right;
            if(root_node.left==null||root_node.right==null)/*if we reach to leaf node we are printing the data at that node*/
            {
            System.out.print(root_node.data);
            root_node=root;
            }
        }

       
    }



}

 
public class Huffman_Decoding {
  
    // input is an array of frequencies, indexed by character code
    public static Node buildTree(int[] charFreqs) {
      
        PriorityQueue<Node> trees = new PriorityQueue<Node>();
        // initially, we have a forest of leaves
        // one for each non-empty character
        for (int i = 0; i < charFreqs.length; i++)
            if (charFreqs[i] > 0)
                trees.offer(new HuffmanLeaf(charFreqs[i], (char)i));
 
        assert trees.size() > 0;
      
        // loop until there is only one tree left
        while (trees.size() > 1) {
            // two trees with least frequency
            Node a = trees.poll();
            Node b = trees.poll();
 
            // put into new node and re-insert into queue
            trees.offer(new HuffmanNode(a, b));
        }
      
        return trees.poll();
    }
  
    public static Map<Character,String> mapA=new HashMap<Character ,String>();
  
    public static void printCodes(Node tree, StringBuffer prefix) {
      
        assert tree != null;
      
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf)tree;
 
            // print out character, frequency, and code for this leaf (which is just the prefix)
            //System.out.println(leaf.data + "\t" + leaf.frequency + "\t" + prefix);
            mapA.put(leaf.data,prefix.toString());

        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode)tree;
 
            // traverse left
            prefix.append('0');
            printCodes(node.left, prefix);
            prefix.deleteCharAt(prefix.length()-1);
 
            // traverse right
            prefix.append('1');
            printCodes(node.right, prefix);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }
 
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
    
        String test= input.next();
 
        // we will assume that all our characters will have
        // code less than 256, for simplicity
        int[] charFreqs = new int[256];
      
        // read each character and record the frequencies
        for (char c : test.toCharArray())
            charFreqs[c]++;
 
        // build tree
        Node tree = buildTree(charFreqs);
 
        // print out results
        printCodes(tree, new StringBuffer());
        StringBuffer s = new StringBuffer();
      
        for(int i = 0; i < test.length(); i++) {
          	char c = test.charAt(i);
            s.append(mapA.get(c));
            input.close();

        }
      
        //System.out.println(s);
        Decoding d = new Decoding();
        d.decode(s.toString(), tree);
       
    }
}