package org.licenta.d4elders.algorithm;

public class AnnealingScheduler {
	private final int k, limit;

	private final double lam;

	public AnnealingScheduler(int k, int limit, double lam) {
		this.k = k;
		this.limit = limit;
		this.lam = lam;
	}

	public AnnealingScheduler(){
		this(20, 100, 0.045);
	}

	/**
	 * Computes the temperature of the given time.
	 * @param t the current time step
	 * @return the temperature
	 */
	public double getTemp(int t){
		if(t < limit){
			double res = k * Math.exp( (-1) * lam * t);
			return res;
		} else {
			return 0.0;
		}
	}

	/**
	 * Computes the number of evaluted neighbors for the given time.
	 * This number is higher in the beginning and lower towarads the end.
	 * limit - t/limit * 10
	 * @param t the current time
	 * @return the number of evaluted neighbors.
	 */
	public int getNumberOfEvaluations(int t){
		if(t < limit){
			double res = limit - (double) t / limit * 10;
			return (int)res;
		}else{
			return 0;
		}
	}
}
