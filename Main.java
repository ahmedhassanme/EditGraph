package editGraph;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		//Graph graph = new Graph(1,1,2);
		//Graph graph = new Graph(1,1,1);
		
		//String[] seqA = {"A", "T", "G", "C", "T", "A"};
		//String[] seqB = {"A", "C", "G", "A"};
		
		//String[] seqA = {"E", "R", "D", "A", "W", "C", "Q", "P", "G", "K", "W", "Y"};
		//String[] seqB = {"E", "A", "W", "A", "C", "Q", "G", "K", "L"};
		
		/*graph.setSeqs(seqA, seqB);
		graph.generate();	
		graph.print();
		graph.traceAlignment();
		graph.printSeqs();
		graph.printAlignments();*/
		
		Interface gui = new Interface();
		gui.setVisible(true);
	}
}
