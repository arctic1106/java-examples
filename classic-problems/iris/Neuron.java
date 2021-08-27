package iris;

import java.util.function.DoubleUnaryOperator;

public class Neuron {
    public final double learningRate;
    public final DoubleUnaryOperator activationFunction;
    public final DoubleUnaryOperator derivativeActivationFunction;
    public double[] weights;
    public double outputCache;
    public double delta;

    public Neuron(double[] weights, double learningRate, DoubleUnaryOperator activationFunction,
                  DoubleUnaryOperator derivativeActivationFunction) {
        this.weights = weights;
        this.learningRate = learningRate;
        outputCache = 0.0;
        delta = 0.0;
        this.activationFunction = activationFunction;
        this.derivativeActivationFunction = derivativeActivationFunction;
    }

    public double output(double[] inputs) {
        outputCache = Util.dotProduct(inputs, weights);
        return activationFunction.applyAsDouble(outputCache);
    }
}