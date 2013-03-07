package editGraph;

import java.util.ArrayList;
import java.util.Stack;

public class Graph 
{
	//variables storing deletion, insertion, and substitution costs
	final int DEL_COST;
	final int INS_COST;
	final int SUB_COST;
	
	//variables storing sequences to be aligned
	String[] seqA;
	String[] seqB;
	
	//variables storing row and col lengths
	int ROW;
	int COL;
	
	//variables storing the lengths of the two sequences
	int maxSeqLen;
	int minSeqLen;
	
	//variable storing matrix of numbers
	int[][] numbers;
	
	//root of the trace tree; the Levenshtein distance (final bucket in the matrix)
	Node<String> root;
	
	//variable storing the paths of valid alignments
	private ArrayList<ArrayList<String>> lists;
	//variable storing parents of nodes in the tree
	private ArrayList<String> parents;
	//variable storing the valid alignments
	private ArrayList<String[][]> alignments;
	
	//constructor
	public Graph(int del, int ins, int sub)
	{
		DEL_COST = del;
		INS_COST = ins;
		SUB_COST = sub;
	}
	
	//returns the root of the tree
	public Node<String> getRoot()
	{
		return root;
	}
	
	//store the sequences to be aligned and take care of housekeping
	public void setSeqs(String[] a, String[] b)
	{
		if(a.length > b.length)
		{
			seqA = a;
			seqB = b;
		}
		else
		{
			seqB = a;
			seqA = b;
		}
		
		maxSeqLen = Math.max(seqA.length, seqB.length);
		minSeqLen = Math.min(seqA.length, seqB.length);
		
		ROW = seqB.length+1;
		COL = seqA.length+1;
		
		numbers = new int[ROW][COL];
		//set the starting point to value 0
		numbers[0][0] = 0;
	}
	
	//generate the matrix of values
	public void generate()
	{
		//set the values of the first col (deletion)
		for(int x = 1; x<ROW; x=x+1)
		{
			numbers[x][0] = numbers[x-1][0] + DEL_COST;
		}
		

		for(int y = 1; y<COL; y=y+1)
		{
			//sets the values of the first row (insertion)
			numbers[0][y] = numbers [0][y-1] + INS_COST;
			
			/*set the values of bucket to the min of either:
			bucket on top plus deletions cost
			bucket to left plus insertion cost
			bucket to top left plus sub cost of this bucket*/
			for(int z = 1; z<ROW; z=z+1)
			{
				int sub;	//variable that stores sub cost
				if(seqA[y-1].equals(seqB[z-1]))	//if the letters are equal, sub cost is 0
				{
					sub = numbers[z-1][y-1] + 0;
				}
				else	//otherwise, use default sub cost
				{
					sub = numbers[z-1][y-1] + SUB_COST;
				}
				int del = numbers[z-1][y] + DEL_COST;	//variable that stores del cost
				int ins = numbers[z][y-1] + INS_COST;	//variable that stores ins cost
				
				//store the min of all three variables in current bucket
				int temp = Math.min(sub, del);
				numbers[z][y] = Math.min(temp, ins);
			}
		}
	}
	
	//trace the optimal alignments after generating the matrix
	public void traceAlignment()
	{
		
		root = new Node<String>("root");
		System.out.println("row: " + (ROW-1));
		System.out.println("col: " + (COL-1));
		//call actual trace method
		trace(root, ROW-1, COL-1);
	}
	
	public void trace(Node<String> node, int x, int y)
	{
		//if bucket exists
		if((x-1)>-1)
		{
			//if path exists from [x-1][y] to [x][y] exists, set [x-1][y] as a child of [x][y] in the trace tree
			if(numbers[x-1][y] + DEL_COST == numbers[x][y])
			{
				Node<String> child = new Node<String>("DEL: "+numbers[x][y]+"-"+numbers[x-1][y]);
				child.setParent(node);
				node.addChild(child);
				trace(child, x-1, y);
			}
		}
		
		//if bucket exists
		if((y-1)>-1)
		{
			//if path exists from [x][y-1] to [x][y] exists, set [x][y-1] as a child of [x][y] in the trace tree
			if(numbers[x][y-1] + INS_COST == numbers[x][y])
			{
				Node<String> child = new Node<String>("INS:"+numbers[x][y]+"-"+numbers[x][y-1]);
				child.setParent(node);
				node.addChild(child);
				trace(child, x, y-1);
			}
		}
		
		//if bucket exists
		if((x-1)>-1 && (y-1)>-1)
		{
			//if path exists from [x-1][y-1] to [x][y] exists, set [x-1][y-1] as a child of [x][y] in the trace tree
			if(numbers[x-1][y-1] + SUB_COST == numbers[x][y] || (numbers[x-1][y-1] == numbers[x][y] && seqA[y-1].equals(seqB[x-1])))
			{
				Node<String> child = new Node<String>("SUB:"+numbers[x][y]+"-"+numbers[x-1][y-1]);
				child.setParent(node);
				node.addChild(child);
				trace(child, x-1, y-1);
			}	
		}
	}
	
	//find all the parents of the node back to the root
	public void findParent(Node<String> node)
	{	
		if(node.hasParent())
		{
			findParent(node.getParent());
			parents.add(node.getParent().getData());
		}
		else
		{	
			//System.out.println("no parent:"+node.getData());
		}
	}
	
	//print the alignments
	//REDUNDANT; REPLACE WITH DFS()
	public void printSeqs()
	{
		DFS();
	}
		
	public void DFS()
	{
		//initialize the list of paths
		lists = new ArrayList<ArrayList<String>>();
    	
		//find the optimal alignments for all the children of the root
    	for(int x=0; x<root.getChildren().size(); x=x+1)
    	{
    		System.out.println();
    		ArrayList<String> temp = new ArrayList<String>();
    		lists.add(temp);
    		doDFS(root.getChildren().get(x), temp);
    	}
	}
	
	//find all the optimal paths from the root using a depth first search
	public void doDFS(Node<String> child, ArrayList<String> aList)
    {
    	System.out.print(child.getData() + " , ");
    	//store the operation taking place at this node (ADD, DEL, SUB) in the current node's path 
    	aList.add(child.getData().toString().substring(0, 3));
    	
    	//loop through all the current node's children
    	for(int x=0; x<child.getChildren().size(); x=x+1)
    	{	
    		//if this is the second or greater child of the current node, create a new path
    		if(x>0)
    		{
    			System.out.println();
    			ArrayList<String> temp = new ArrayList<String>();
    			parents = new ArrayList<String>();
    			parents.clear();
    			//find the parents of this node back to the root so the path contains complete information
    			findParent(child);
    			System.out.println("size of parents: "+parents.size());
    			if(parents.size()>1)
    			{
    				for(int d=1; d<parents.size(); d=d+1)
    				{
    					temp.add(parents.get(d).substring(0, 3));
    					System.out.print(parents.get(d) + " , ");
    				}
    			}
    			//store the operation taking place at this node (ADD, DEL, SUB) in the current node's path 
        		temp.add(child.getData().toString().substring(0, 3));
        		//add the path to the list of paths
        		lists.add(temp);
    			System.out.print(child.getData() + " , ");
    			doDFS(child.getChildren().get(x), temp);
    		}
    		else
    		{
    			doDFS(child.getChildren().get(x), aList);
    		}
    	}
    }
	
	/*public void doDFS(Node<String> child, ArrayList<String> aList, ArrayList<Node<String>> bList)
    {
    	System.out.print(child.getData() + " , ");
    	aList.add(child.getData().toString().substring(0, 3));
    	bList.add(child);
    	for(int x=0; x<child.getChildren().size(); x=x+1)
    	{	
    		if(x>0)
    		{
    			System.out.println();
    			ArrayList<String> temp = new ArrayList<String>();
    			//
    			int count = 0;
    			//while(bList.get(count)!=child)
    			{
    				//System.out.println("alist in child "+x+":"+bList.get(count));
    				//temp.add(bList.get(count).getData());
    				//count++;
    			}
    			//for(int c = 0; c<x; c=c+1)
    			//{
    				//System.out.println("alist in child "+x+":"+aList.get(c));
    				//temp.add(aList.get(c));
    			//}
    			//
        		lists.add(temp);
        		temp.add(child.getData().toString().substring(0, 3));
    			System.out.print(child.getData() + " , ");
    			doDFS(child.getChildren().get(x), temp, bList);
    		}
    		else
    		{
    			doDFS(child.getChildren().get(x), aList, bList);
    		}
    	}
    }*/
	
	//print out the alignments
	public void printAlignments()
	{
		Stack<String> aStack = new Stack<String>();
		Stack<String> bStack = new Stack<String>();
	
		alignments = new ArrayList<String[][]>();
		
		
		for(int x = 0; x<lists.size(); x=x+1)
		{
			aStack.clear();
			bStack.clear();
			for(int a = 0; a<maxSeqLen; a=a+1)
			{
				aStack.add(seqA[a]);
			}
			System.out.println();
			for(int b = 0; b<minSeqLen; b=b+1)
			{
				bStack.add(seqB[b]);
			}
			
			int track = 0;
			for(int f = 0; f<lists.get(x).size(); f=f+1)
			{
				if(lists.get(x).get(f).equals("DEL"))
				{
					track = track + 1;
				}
			}
			track = maxSeqLen + track-1;
			alignments.add(new String[2][track+1]);
			
			for(int y=0; y<lists.get(x).size(); y=y+1)
			{
				if(lists.get(x).get(y).equals("DEL"))
				{
					System.out.println("DEL");
					System.out.println(bStack.peek());
					alignments.get(x)[0][track] = bStack.pop();
					alignments.get(x)[1][track] = "_";
				}
				else if(lists.get(x).get(y).equals("INS"))
				{
					System.out.println("INS");
					System.out.println(aStack.peek());
					alignments.get(x)[0][track] = "_";
					alignments.get(x)[1][track] = aStack.pop();
				}
				else
				{
					System.out.println("SUB");
					System.out.println(aStack.peek());
					System.out.println(bStack.peek());
					alignments.get(x)[0][track] = bStack.pop();
					alignments.get(x)[1][track] = aStack.pop();
				}
				
				track = track - 1;
			}
			
			System.out.println();
			for(int a = 0; a<alignments.get(x).length; a=a+1)
			{
				System.out.println();
				for(int b = 0; b<alignments.get(x)[a].length; b=b+1)
				{
					System.out.print(alignments.get(x)[a][b] + " , ");
				}
			}
		}
	}
	
	//print out the matrix
	public void print()
	{
		//print out the matrix
		for(int x = 0; x<ROW; x=x+1)
		{
			for(int y=0; y<COL; y=y+1)
			{
				if(numbers[x][y]<10)
				{
					System.out.print("| " + numbers[x][y] + " | ");
				}
				else
				{
					System.out.print("| " + numbers[x][y] + "| ");
				}
			}
			System.out.println();
		}
	}	
	
	public ArrayList<String[][]> getAlignments()
	{
		return alignments;
	}
	
	public int[][] getNumbers()
	{
		return numbers;
	}
}