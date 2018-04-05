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
	//https://jp.mathworks.com/help/stats/robust-regression-reduce-outlier-effects.html
	//6列目:世帯数、5列目:80over
	public double[][] getRegression() {
		double [][]result = new double[3][2]; //係数Estimate,pValue,決定係数[2][0],自由度調整済み決定係数[2][1], 今回は２変数
		try {
			ml.eval("x = table(data(:,6),data(:,5));");
			//ml.eval("mdl = fitlm(x)");
			//ml.eval("mdl = fitlm(x,'quadratic')"); //２次の項まで(係数は切片,x,x^2)
			ml.eval("mdl = fitlm(x,'quadratic','RobustOpts','on')");
			ml.eval("Coefficients = mdl.Coefficients");
			ml.eval("Estimate = mdl.Coefficients.Estimate");
			ml.eval("pValue = mdl.Coefficients.pValue");
			ml.eval("Ordinary = mdl.Rsquared.Ordinary");
			ml.eval("Adjusted = mdl.Rsquared.Adjusted");
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
			ml.eval("plot(data(:,6), yPred, '.');");
			ml.eval("legend('All Data','Predicted Response');");
			ml.eval("xlabel('Number of House');");
			ml.eval("ylabel('Population of 80 over');");
			ml.eval("title('Population of 80 over for house')");
			ml.eval("pause(5);");
			ml.eval("saveas(gcf,'regression.png')");
			ml.eval("hold off");
			Future<double[]> futureEval_y = ml.getVariableAsync("yPred");
			double [] ypred = futureEval_y.get();
			getEvaluateFit(data[5], ypred, "Regression");
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
			//ml.eval("mdl = fitlm(x)");
			//ml.eval("mdl = fitlm(x,'quadratic')"); //２次の項まで(係数は切片,x,x^2)
			ml.eval("mdl = fitlm(x,'quadratic','RobustOpts','on')");
			ml.eval("Coefficients = mdl.Coefficients");
			ml.eval("Estimate = mdl.Coefficients.Estimate");
			ml.eval("pValue = mdl.Coefficients.pValue");
			ml.eval("Ordinary = mdl.Rsquared.Ordinary");
			ml.eval("Adjusted = mdl.Rsquared.Adjusted");
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
	
	//https://jp.mathworks.com/help/stats/stepwiselm.html
	public double[][] getStepwise() {
		double [][]result = new double[3][5]; //係数Estimate,pValue,決定係数[2][0],自由度調整済み決定係数[2][1], 今回は5変数
		try {
			ml.eval("x = table(data(:,1),data(:,2),data(:,3),data(:,4),data(:,6),data(:,5));");
			ml.eval("mdl = stepwiselm(x)");
			ml.eval("Coefficients = mdl.Coefficients");
			ml.eval("Estimate = mdl.Coefficients.Estimate");
			ml.eval("pValue = mdl.Coefficients.pValue");
			ml.eval("Ordinary = mdl.Rsquared.Ordinary");
			ml.eval("Adjusted = mdl.Rsquared.Adjusted");
			Future<double[]> futureEval_x0 = ml.getVariableAsync("Estimate");
			result[0] = futureEval_x0.get();
			Future<double[]> futureEval_x1 = ml.getVariableAsync("pValue");
			result[1] = futureEval_x1.get();
			Future<Double> futureEval_x2 = ml.getVariableAsync("Ordinary");
			result[2][0] = futureEval_x2.get();
			Future<Double> futureEval_x3 = ml.getVariableAsync("Adjusted");
			result[2][1] = futureEval_x3.get();
			System.out.println("Coefficients = "+Arrays.deepToString(result));
			ml.eval("yPred = predict(mdl,table(data(:,1),data(:,2),data(:,3),data(:,4),data(:,6)));");
			ml.eval("plot(data(:,6),data(:,5),'.');");
			ml.eval("hold on");
			ml.eval("plot(data(:,6), yPred, '.');");
			ml.eval("legend('All Data','Predicted Response');");
			ml.eval("xlabel('Number of House');");
			ml.eval("ylabel('Population of 80 over');");
			ml.eval("title('Population of 80 over for house')");
			ml.eval("pause(5);");
			ml.eval("saveas(gcf,'stepwise.png')");
			ml.eval("hold off");
			Future<double[]> futureEval_y = ml.getVariableAsync("yPred");
			double [] ypred = futureEval_y.get();
			
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
	
	public void getEvaluateFit(double []y, double []ypred, String name) {
		try {
			ml.putVariableAsync("y", y);
			ml.putVariableAsync("ypred", ypred);
			ml.putVariableAsync("name", name);
			
			//Plot of the actual values (in blue) and the predicted values (in red) against the observation number.
			ml.eval("subplot(2,2,1)");
			ml.eval("plot(data(:,5),'.')");
			ml.eval("hold on");
			ml.eval("plot(ypred,'.')");
			ml.eval("hold off");
			ml.eval("title(name)");
			ml.eval("xlabel('Observation number')");
			ml.eval("ylabel('Y value')");
			
			//Plot of the actual value on the x-axis against the predicted value on the y-axis.
			ml.eval("subplot(2,2,2)");
			ml.eval("scatter(data(:,5),ypred,'.')");
			ml.eval("xl = xlim;");
			ml.eval("hold on");
			ml.eval("plot(xl,xl,'k:')");
			ml.eval("hold off");
			ml.eval("title(name)");
			ml.eval("xlabel('Actual value')");
			ml.eval("ylabel('Predicted value')");
			
			//Histogram showing the distribution of the errors between the actual and predicted values.
			ml.eval("subplot(2,2,3)");
			ml.eval("err = data(:,5) - ypred;");
			ml.eval("MSE = mean(err.^2,'omitnan');");
			ml.eval("histogram(err)");
			ml.eval("title(['MSE = ',num2str(MSE,4)])");
			ml.eval("xlabel('Prediction error')");
			
			//Histogram showing the error as a percentage of the actual value.
			ml.eval("subplot(2,2,4)");
			ml.eval("err = 100*err./data(:,5);");
			ml.eval("MAPE = mean(abs(err),'omitnan');");
			ml.eval("histogram(err)");
			ml.eval("title(['MAPE = ',num2str(MAPE,4)])");
			ml.eval("xlabel('Prediction percentage error')");
			
			ml.eval("pause(5);");
			ml.eval("saveas(gcf,'regressioncheck.png')");
			ml.eval("clf");
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
	
	}
}
