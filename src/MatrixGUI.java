import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MatrixGUI extends JFrame {

	private JTextArea txtMatrixOne = new JTextArea("1,2,3\n4,5,6\n7,8,9");
	private JTextArea txtMatrixTwo = new JTextArea("-2,6,1\n7,2,-7\n1,8,2");
	private String[] ops = { "Add", "Subtract", "Multiply", "Divide", "Scalar", "Determinant", "Transpose" };
	private JComboBox cmbOperate = new JComboBox(ops);
	private JButton cmdCalculate = new JButton("Calculate");
	private JButton cmdRandom = new JButton("Random");
	private JButton cmdFile = new JButton("From File");
	private JTextArea txtAnsMatrix = new JTextArea();
	private Matrix matrix1;
	private Matrix matrix2;

	public MatrixGUI() {
		setPreferredSize(new Dimension(1500, 1000));
		setTitle("Matrix");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		
		Font font = new Font("Consolas", Font.PLAIN, 32);
		txtMatrixOne.setFont(font);
		txtMatrixTwo.setFont(font);

		cmbOperate.setPreferredSize(new Dimension(100, 100));
		cmbOperate.setFont(font);
		cmdCalculate.setFont(font);
		txtAnsMatrix.setFont(font);
		cmdRandom.setFont(font);
		cmdFile.setFont(font);

		JPanel datapanel = new JPanel(new GridLayout(1, 3, 50, 100));

		datapanel.add(txtMatrixOne);
		datapanel.add(txtMatrixTwo);
		datapanel.add(txtAnsMatrix);
		datapanel.setBackground(new Color(51, 153, 255));

		JPanel buttonpanel = new JPanel(new GridLayout(2, 2, 12, 12));

		buttonpanel.add(cmbOperate);
		buttonpanel.add(cmdCalculate);
		buttonpanel.add(cmdRandom);
		buttonpanel.add(cmdFile);
		buttonpanel.setBackground(new Color(51, 153, 255));

		Container container = getContentPane();

		container.add(datapanel, BorderLayout.CENTER);
		container.add(buttonpanel, BorderLayout.SOUTH);

		cmdCalculate.addActionListener(new calcListener());
		cmdRandom.addActionListener(new randListener());
		cmdFile.addActionListener(new fileListener());
	}

	public class calcListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			matrix1 = new Matrix(txtMatrixOne.getText().split("\\n"));
			matrix2 = new Matrix(txtMatrixTwo.getText().split("\\n"));
			Matrix opMatrix = new Matrix();
			Font font = new Font("Consolas", Font.PLAIN, 28);
			double total = 0;

			UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Consolas", Font.BOLD, 32)));
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("Consolas", Font.BOLD, 32)));

			//System.out.println(matrix1.toString() + "\n" + matrix2.toString());


			if (cmbOperate.getSelectedIndex() == 0) {
				opMatrix = matrix1.addMatrix(matrix2);
				if (opMatrix != null) {
					txtAnsMatrix.setText(opMatrix.toString());
				} else {
					JOptionPane.showMessageDialog(null, "Please make sure the # of columns and rows in the first matrix\n are equal to the # of columns and rows in the second matrix.");
				}
			} else if (cmbOperate.getSelectedIndex() == 1) {
				opMatrix = matrix1.subMatrix(matrix2);
				if (opMatrix != null) {
					txtAnsMatrix.setText(opMatrix.toString());
				} else {
					JOptionPane.showMessageDialog(null, "Please make sure the # of columns and rows in the first matrix\n are equal to the # of columns and rows in the second matrix.");
				}
			} else if (cmbOperate.getSelectedIndex() == 2) {
				opMatrix = matrix1.multMatrix(matrix2);
				if (opMatrix != null) {
					txtAnsMatrix.setText(opMatrix.toString());
				} else {
					JOptionPane.showMessageDialog(null, "Please make sure the # of columns in the first matrix\n are equal to the # of rows in the second matrix.");
				}
			} else if (cmbOperate.getSelectedIndex() == 3) {
				opMatrix = matrix1.divMatrix(matrix2);
				if (opMatrix != null) {
					txtAnsMatrix.setText(opMatrix.toString());
				} else {
					JOptionPane.showMessageDialog(null, "Please make sure the # of columns in the first matrix are equal to\n the # of rows in the second matrix and the number of rows\n and columns in the second matrix are equal to two.");
				}
			} else if (cmbOperate.getSelectedIndex() == 4) {
				try{
				opMatrix = matrix1.scalarMatrix(Integer.parseInt(txtMatrixTwo.getText()));
				
					txtAnsMatrix.setText(opMatrix.toString());
				} catch(Exception r){
					JOptionPane.showMessageDialog(null, "Please make sure there is a single number in the second text box.");
				}
			} else if (cmbOperate.getSelectedIndex() == 5) {
				try {
					total = matrix1.deterMatrix();
					txtAnsMatrix.setText("Determinant = " + total);
				} catch (Exception r) {
					JOptionPane.showMessageDialog(null, "Please make sure the number of rows is >= the\n        number of columns in matrix 1");
				}
			} else if (cmbOperate.getSelectedIndex() == 6) {
				opMatrix = matrix1.transMatrix();
				if (opMatrix != null) {
					txtAnsMatrix.setText(opMatrix.toString());
				} else {
					JOptionPane.showMessageDialog(null, "Please make sure there is only one number in the second text box.");
				}
			}

		}
	}

	public class randListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Consolas", Font.BOLD, 32)));
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("Consolas", Font.BOLD, 32)));
			try {
				String[] mat1 = JOptionPane.showInputDialog("Please enter matrix 1 in this format (rows,columns,min,max)").split(",");
				String[] mat2 = JOptionPane.showInputDialog("Please enter matrix 2 in this format (rows,columns,min,max)").split(",");
				matrix1 = new Matrix(Integer.parseInt(mat1[0]), Integer.parseInt(mat1[1]), Integer.parseInt(mat1[2]), Integer.parseInt(mat1[3]));
				matrix2 = new Matrix(Integer.parseInt(mat2[0]), Integer.parseInt(mat2[1]), Integer.parseInt(mat2[2]), Integer.parseInt(mat2[3]));
				txtMatrixOne.setText(matrix1.toString());
				txtMatrixTwo.setText(matrix2.toString());
			} catch (Exception error) {

				JOptionPane.showMessageDialog(null, "Please enter in the correct format (rows,columns,min,max)");
			}
		}

	}

	public class fileListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			matrix1 = new Matrix("Matrix1");
			matrix2 = new Matrix("Matrix2");
			txtMatrixOne.setText(matrix1.toString());
			txtMatrixTwo.setText(matrix2.toString());
		}
	}
}
