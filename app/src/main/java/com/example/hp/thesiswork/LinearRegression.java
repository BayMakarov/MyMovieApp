package com.example.hp.thesiswork;

import android.util.Log;

/**
 * Created by hp on 9.01.2018.
 */

public class LinearRegression {
    public int N;
    public double alpha, beta;

/**
   The <tt>LinearRegression</tt> class performs a simple linear regression
    on an set of <em>N</em> data points (<em>y<sub>i</sub></em>, <em>x<sub>i</sub></em>).
   That is, it fits a straight line <em>y</em> = &alpha; + &beta; <em>x</em>,
   (where <em>y</em> is the response variable, <em>x</em> is the predictor variable,
   &alpha; is the <em>y-intercept</em>, and &beta; is the <em>slope</em>)
   that minimizes the sum of squared residuals of the linear regression model. */
     // Performs a linear regression on the data points <tt>(y[i], x[i])</tt>.
    public LinearRegression(double[] x, double[] y) {

        N = x.length;
        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        for (int i = 0; i < N; i++) sumx += x[i];
        for (int i = 0; i < N; i++) sumx2 += x[i] * x[i];
        for (int i = 0; i < N; i++) sumy += y[i];
        double xbar = sumx / N;
        double ybar = sumy / N;

        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int i = 0; i < N; i++) {
            xxbar += (x[i] - xbar) * (x[i] - xbar);
            yybar += (y[i] - ybar) * (y[i] - ybar);
            xybar += (x[i] - xbar) * (y[i] - ybar);
        }
        beta = xybar / xxbar;
        alpha = ybar - beta * xbar;


    }

    public double predict(double x) {
        return beta * x + alpha;
    }

     // @return a string representation of the simple linear regression model

    public String toString() {
        String s = "";
        s += String.format("%.2f N + %.2f", beta, alpha);
        return s;
    }


}