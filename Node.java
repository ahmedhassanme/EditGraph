package editGraph;

import java.util.ArrayList;

//class representing the nodes in the tree of paths
public class Node<T>
{
	private T data;
    private Node<T> parent;
    private ArrayList<Node<T>> children;
    private ArrayList<ArrayList<String>> lists;
    
    public Node()
    {
    	children = new ArrayList<Node<T>>();
    }
    
    public Node(T temp)
    {
    	data = temp;
    	children = new ArrayList<Node<T>>();
    }
    
    public void setParent(Node<T> p)
    {
    	parent = p;
    }
    
    public Node<T> getParent()
    {
    	return parent;
    }
    
    public boolean hasParent()
    {
    	boolean check = false;
    	if(parent!=null)
    	{
    		check = true;
    	}
    	return check;
    }
    
    public void setData(T temp)
    {
    	data = temp;
    }
    
    public T getData()
    {
    	return data;
    }
    
    public void addChild(Node<T> child)
    {
    	children.add(child);
    }
    
    public ArrayList<Node<T>> getChildren()
    {
    	return children;
    }
    
    public boolean hasChildren()
    {
    	boolean check = false;
    	
    	if(children.size()>0)
    	{
    		check = true;
    	}
    	
    	return check;
    }
}

