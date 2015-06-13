package d4elders.algorithm.helper;

public class AlgorithmConfigurationCuckoo {

	private int nestSize;
	private int maxIterations;
	private double pa;

	public AlgorithmConfigurationCuckoo() {
		// set default values
		setNestSize(5);
		setMaxIterations(500);
		setPa(0.7);
	}

	public int getNestSize() {
		return nestSize;
	}

	public void setNestSize(int nestSize) {
		this.nestSize = nestSize;
	}

	public int getMaxIterations() {
		return maxIterations;
	}

	public void setMaxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
	}

	public double getPa() {
		return pa;
	}

	public void setPa(double pa) {
		this.pa = pa;
	}
}
