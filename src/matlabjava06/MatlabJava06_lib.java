package matlabjava06;

import java.util.Arrays;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;
import com.mathworks.engine.MatlabExecutionException;
import com.mathworks.engine.MatlabSyntaxException;

public class MatlabJava06_lib {
	
	Future<MatlabEngine> eng;
	MatlabEngine ml;
	private double data[][];
	
	public MatlabJava06_lib(double[][] data) {
		this.data = data;
		this.eng = MatlabEngine.startMatlabAsync();
		try {
			ml = eng.get();
			ml.putVariableAsync("data", data);
			ml.eval("scatter(data(:,6),data(:,5));");
			ml.eval("xlabel('Number of House');");
			ml.eval("ylabel('Population of 80 over');");
			ml.eval("title('Population of 80 over for house')");
			ml.eval("pause(5);");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//https://jp.mathworks.com/help/stats/fitlm.html
	//https://jp.mathworks.com/help/stats/linearmodel-class.html
	//6列目:世帯数、5列目:80over
	public double[][] getRegression() {
		double [][]result = new double[3][2]; //係数Estimate,pValue,決定係数[2][0],自由度調整済み決定係数[2][1], 今回は２変数
		try {
			ml.eval("x = table(data(:,6),data(:,5));");
			ml.eval("mdl = fitlm(x)");
			ml.eval("Coefficients = mdl.Coefficients");
			ml.eval("Estimate = mdl.Coefficients.Estimate");
			ml.eval("pValue = mdl.Coefficients.pValue");
			ml.eval("Ordinary = mdl.Rsquared.Ordinary");
			ml.eval("Adjusted = mdl.Rsquared.Adjusted");
			//Future<double[]> futureEval_x = ml.getVariableAsync("Coefficients");
			Future<double[]> futureEval_x0 = ml.getVariableAsync("Estimate");
			result[0] = futureEval_x0.get();
			Future<double[]> futureEval_x1 = ml.getVariableAsync("pValue");
			result[1] = futureEval_x1.get();
			Future<Double> futureEval_x2 = ml.getVariableAsync("Ordinary");
			result[2][0] = futureEval_x2.get();
			Future<Double> futureEval_x3 = ml.getVariableAsync("Adjusted");
			result[2][1] = futureEval_x3.get();
			System.out.println("Coefficients = "+Arrays.deepToString(result));
			ml.eval("yPred = predict(mdl,data(:,6));");
			ml.eval("plot(data(:,6),data(:,5),'.');");
			ml.eval("hold on");
			ml.eval("plot(data(:,6), yPred, '.-');");
			ml.eval("legend('All Data','Predicted Response');");
			ml.eval("xlabel('Number of House');");
			ml.eval("ylabel('Population of 80 over');");
			ml.eval("title('Population of 80 over for house')");
			ml.eval("pause(5);");
			ml.eval("saveas(gcf,'regression.png')");
			ml.eval("hold off");
			
		} catch (MatlabExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MatlabSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CancellationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public double[][] getRegression2() {
		double [][]result = new double[3][3]; //係数Estimate,pValue,決定係数[2][0],自由度調整済み決定係数[2][1], 今回は２変数
		try {
			ml.eval("x = table(data(:,6),data(:,1),data(:,5));");
			ml.eval("mdl = fitlm(x)");
			ml.eval("Coefficients = mdl.Coefficients");
			ml.eval("Estimate = mdl.Coefficients.Estimate");
			ml.eval("pValue = mdl.Coefficients.pValue");
			ml.eval("Ordinary = mdl.Rsquared.Ordinary");
			ml.eval("Adjusted = mdl.Rsquared.Adjusted");
			//Future<double[]> futureEval_x = ml.getVariableAsync("Coefficients");
			Future<double[]> futureEval_x0 = ml.getVariableAsync("Estimate");
			result[0] = futureEval_x0.get();
			Future<double[]> futureEval_x1 = ml.getVariableAsync("pValue");
			result[1] = futureEval_x1.get();
			Future<Double> futureEval_x2 = ml.getVariableAsync("Ordinary");
			result[2][0] = futureEval_x2.get();
			Future<Double> futureEval_x3 = ml.getVariableAsync("Adjusted");
			result[2][1] = futureEval_x3.get();
			
			ml.eval("yPred = predict(mdl,table(data(:,6),data(:,1)));");
			ml.eval("scatter3(data(:,6),data(:,1),data(:,5));");
			ml.eval("hold on");
			ml.eval("scatter3(data(:,6),data(:,1), yPred);");
			ml.eval("legend('All Data','Predicted Response');");
			ml.eval("xlabel('Number of House');");
			ml.eval("ylabel('Population of 80 over');");
			ml.eval("title('Population of 80 over for house')");
			ml.eval("pause(5);");
			ml.eval("saveas(gcf,'regression2.png')");
			ml.eval("hold off");
			
		} catch (MatlabExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MatlabSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CancellationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
