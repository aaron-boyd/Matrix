import java.util.Random;
import java.util.Scanner;
import java.io.File;

public class Matrix {
	private int myr, myc;
	private double[][] myelements = new double[20][20];
	private Random rand = new Random();

	public Matrix(int row, int column) {
		myr = row;
		myc = column;
		myelements = new double[myr][myc];

		for (int r = 0; r < myr; r++) {
			for (int c = 0; c < myc; c++) {
				myelements[r][c] = rand.nextInt(25) + 1;
			}
		}
	}

	public Matrix() {
		myr = 10;
		myc = 10;
		myelements = new double[10][10];

		for (int i = 0; i < 10; i++) {
			for (int x = 0; x < 10; x++) {
				myelements[i][x] = 0;
			}
		}
	}

	public Matrix(int row, int column, int min, int max) {
		myr = row;
		myc = column;
		myelements = new double[myr][myc];

		for (int r = 0; r < myr; r++) {
			for (int c = 0; c < myc; c++) {
				myelements[r][c] = (double) rand.nextInt((max - min) + 1) + min;
			}
		}
	}

	public Matrix(String[] mat) {
		double[] nums;
		String[] line = new String[10];
		myr = mat.length;

		for (int i = 0; i < mat.length; i++) {
			line = mat[i].split(",");
			nums = new double[line.length];
			for (int x = 0; x < line.length; x++) {
				nums[x] = Double.parseDouble(line[x]);
				myc = line.length;
				myelements[i][x] = nums[x];
				// System.out.println("[" + i + "][" + x + "]" + " " + myelements[i][x]);
			}
		}
	}

	public Matrix(String path) {
		String input = "";
		double[] nums;
		String[] line;
		int count = 0;
		try {
			Scanner reader = new Scanner(new File(path + ".txt"));
			input = reader.nextLine();
			line = new String[input.split(",").length];

			while (reader.hasNext()) {

				line = input.split(",");
				nums = new double[line.length];

				for (int x = 0; x < line.length; x++) {
					nums[x] = Double.parseDouble(line[x]);
					myc = line.length;
					myelements[count][x] = nums[x];
				}
				count++;
				input = reader.nextLine();
			}
			myr = count;

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public void setElement(int r, int c, double e) {
		myelements[r][c] = e;
	}

	public double getElement(int r, int c) {
		return myelements[r][c];
	}

	public void setRow(int r) {
		myr = r;
	}

	public void setColomn(int c) {
		myc = c;
	}

	public int getColumns() {
		return myc;
	}

	public int getRows() {
		return myr;
	}

	public Matrix addMatrix(Matrix mat2) {

		if (myr == mat2.getRows() && myc == mat2.getColumns()) {
			Matrix addMat = new Matrix(myr, myc);
			double num = 0;
			for (int r = 0; r < myr; r++) {
				for (int c = 0; c < myc; c++) {
					num = myelements[r][c] + mat2.getElement(r, c);
					addMat.setElement(r, c, num);
				}
			}
			return addMat;
		} else {
			return null;
		}
	}

	public Matrix subMatrix(Matrix mat2) {

		if (myr == mat2.getRows() && myc == mat2.getColumns()) {
			Matrix subMat = new Matrix(myr, myc);
			double num = 0;
			for (int r = 0; r < myr; r++) {
				for (int c = 0; c < myc; c++) {
					num = myelements[r][c] - mat2.getElement(r, c);
					subMat.setElement(r, c, num);
				}
			}
			return subMat;
		} else {
			return null;
		}
	}

	public Matrix multMatrix(Matrix mat2) {
		int num = 0;
		
		Matrix multMat = new Matrix(myr, mat2.getColumns());

		if (myc == mat2.getRows()) {
		
			try {

				for (int r = 0; r < myr; r++) { // r = row of desired element
					for (int c = 0; c < mat2.getColumns(); c++) { // c = column of desired element
						
						multMat.setElement(r, c, 0);

						for (int x = 0; x < myr; x++) { // x = column
							multMat.setElement(r, c, multMat.getElement(r, c) + myelements[r][x] * mat2.getElement(x, c));
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return multMat;
		} else {
			return null;
		}
	}

	public Matrix divMatrix(Matrix mat) {
		double temp = 0;
		double mult = 1 / ((mat.getElement(0, 0) * mat.getElement(1, 1)) - (mat.getElement(0, 1) * mat.getElement(1, 0)));
	//	System.out.println(mult);
		if (mat.getColumns() == 2 && mat.getRows() == 2) {

			temp = mat.getElement(0, 0);
			mat.setElement(0, 0, mat.getElement(1, 1));
			mat.setElement(1, 1, temp);

			mat.setElement(0, 1, -1 * mat.getElement(0, 1));
			mat.setElement(1, 0, -1 * mat.getElement(1, 0));

			//System.out.println("In div fun\n " + mat.toString());
			for (int r = 0; r < 2; r++) {
				for (int c = 0; c < 2; c++) {
					mat.setElement(r, c, mat.getElement(r, c) * mult);
				}
			}
			//System.out.println("In div fun\n " + mat.toString());
			return multMatrix(mat);
		} else {
			return null;
		}
	}

	public Matrix scalarMatrix(double num) {
		Matrix scalar = new Matrix(myr, myc);
		try {
			for (int r = 0; r < myr; r++) {
				for (int c = 0; c < myc; c++) {
					scalar.setElement(r, c, myelements[r][c] * num);
				}
			}
		} catch (Exception e) {
			return null;
		}
		return scalar;
	}

	public double deterMatrix() {
		int sum = 0;
		int add = 0;
		int sub = 0;
		int num = 1;
		Matrix deter = new Matrix(myr, myc + myc - 1);
		//Create new matrix
		for (int r = 0; r < deter.getRows(); r++) {
			for (int c = 0; c < myc; c++) {
				deter.setElement(r, c, myelements[r][c]);
			}
			for (int c = 0; c < myc - 1; c++) {
				deter.setElement(r, c + myc, myelements[r][c]);
			}
		}
		//Calc add diags
		for (int c = 0; c < myc; c++) {

			for (int r = 0; r < myr; r++) {
				num *= deter.getElement(r, r + c);
			}
			add += num;
			num = 1;
		}
		//Calc sub diags
		for(int c = deter.getColumns()-1; c >= myc-1; c--){
			for (int r = 0; r < myr; r++) {
				num *= deter.getElement(r, c-r);
			}
			sub -= num;
			num = 1;
		}
		sum = add +sub;
		System.out.println(sum);
		return sum;
	}

	public Matrix transMatrix(){
		Matrix trans = new Matrix(myr,myc);
		for(int r = 0; r <myr; r++){
			for(int c = 0; c < myc; c++){
				trans.setElement(c, r, myelements[r][c]);
			}
		}
		return trans;
		
	}
	public String toString() {
		String word = "";
		for (int r = 0; r < myr; r++) {

			for (int c = 0; c < myc; c++) {
				if (c != myc - 1) {
					word += String.format("%-4.2f", myelements[r][c]) + ",";
				} else {
					word += String.format("%-4.2f", myelements[r][c]);
				}
			}
			word += "\n";
		}
		return word;
	}
}
