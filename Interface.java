package editGraph;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Interface extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	
	private Graph graph;
	private String[] seqA;
	private String[] seqB;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface frame = new Interface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Interface() {
		setTitle("Sequence Alignment");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 847, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		final JTextPane textPane = new JTextPane();
		JScrollPane jsp1 = new JScrollPane(textPane);
		
		final JTextPane textPane_1 = new JTextPane();
		JScrollPane jsp = new JScrollPane(textPane_1);
		
		textField = new JTextField("ERDAWCQPGKWY");
		textField.setColumns(10);
		
		textField_1 = new JTextField("EAWACQGKL");
		textField_1.setColumns(10);
		
		JLabel lblSeqa = new JLabel("seqA");
		
		JLabel lblSeqb = new JLabel("seqB");
		
		JButton btnNewButton = new JButton("Generate");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				textPane_1.setText("");
				textPane.setText("");
				
				Graph graph = new Graph(Integer.parseInt(textField_3.getText()),
						Integer.parseInt(textField_2.getText()),
						Integer.parseInt(textField_4.getText()));
				
				String seq1 = textField.getText();
				String seq2 = textField_1.getText();
				
				seqA = new String[seq1.length()];
				seqB = new String[seq2.length()];
				
				for(int x = 0; x<seqA.length; x=x+1)
				{
					seqA[x] = seq1.substring(x, x+1);
				}
				
				for(int y = 0; y<seqB.length; y=y+1)
				{
					seqB[y] = seq2.substring(y, y+1);
				}
				
				graph.setSeqs(seqA, seqB);
				graph.generate();	
				graph.print();
				graph.traceAlignment();
				graph.printSeqs();
				graph.printAlignments();
				
				int[][] numbers = graph.getNumbers();
				ArrayList<String[][]> alignments = graph.getAlignments();
				
				for(int x = 0; x<alignments.size(); x=x+1)
				{
					for(int a = 0; a<alignments.get(x).length; a=a+1)
					{
						for(int b = 0; b<alignments.get(x)[a].length; b=b+1)
						{
							textPane_1.setText(textPane_1.getText() + alignments.get(x)[a][b] + " , ");
						}
						textPane_1.setText(textPane_1.getText() + '\n');
					}
					textPane_1.setText(textPane_1.getText() + '\n');
				}
				
				//print out the matrix
				//textPane.setText(textPane.getText() + "       ");
				//for(int x = 0; x<seqA.length; x=x+1)
				//{
					//textPane.setText(textPane.getText() + "| "+ seqA[x] +" | ");
				//}
				textPane.setText(textPane.getText() + '\n');
				
				for(int x = 0; x<numbers.length; x=x+1)
				{
					for(int y=0; y<numbers[x].length; y=y+1)
					{
						if(numbers[x][y]<10)
						{
							textPane.setText(textPane.getText() + "| " + numbers[x][y] + " | ");
						}
						else
						{
							textPane.setText(textPane.getText() + "|" + numbers[x][y] + "| ");
						}
					}
					textPane.setText(textPane.getText() + '\n');
				}
				
			}
		});
		
		JLabel lblInsCost = new JLabel("Ins Cost");
		
		textField_2 = new JTextField("1");
		textField_2.setColumns(10);
		
		JLabel lblDelCost = new JLabel("Del Cost");
		
		textField_3 = new JTextField("1");
		textField_3.setColumns(10);
		
		JLabel lblSubCost = new JLabel("Sub Cost");
		
		textField_4 = new JTextField("2");
		textField_4.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblInsCost)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblDelCost)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblSubCost)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblSeqb)
								.addComponent(lblSeqa))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(textField_1)
								.addComponent(textField, GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)))
						.addComponent(jsp, GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jsp1, GroupLayout.PREFERRED_SIZE, 454, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(jsp1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblSeqa))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblSeqb))
									.addGap(48))
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblInsCost)
									.addComponent(lblDelCost)
									.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblSubCost)
									.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jsp, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}
