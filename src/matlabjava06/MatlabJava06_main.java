package matlabjava06;

import java.util.Arrays;

public class MatlabJava06_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//csv取り込み
		MySQL mysql = new MySQL(1); //csv取り込み
		double data[][] = new double[831][6];
		data = mysql.getCSV("csv/population.csv", 831, 6);
		System.out.println("selectData = "+Arrays.deepToString(data));
		//csv取り込みここまで
		
		MatlabJava06_lib mlib = new MatlabJava06_lib(data);
		double result[][] = mlib.getRegression();
		double result2[][] = mlib.getRegression2();
		System.out.println("Regression = "+Arrays.deepToString(result));
		System.out.println("Regression2 = "+Arrays.deepToString(result2));
		double result3[][] = mlib.getStepwise();
		System.out.println("Stepwiselm = "+Arrays.deepToString(result3));
	}

}
