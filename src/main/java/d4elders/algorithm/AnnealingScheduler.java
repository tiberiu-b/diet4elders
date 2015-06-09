package d4elders.algorithm;

public class AnnealingScheduler {
	//private final int k, limit;

	//private final double lam;

	private double T0;
	private double alpha;
	private double Tmin;

	private double T;

	/*public AnnealingScheduler(int k, int limit, double lam) {
		this.k = k;
		this.limit = limit;
		this.lam = lam;
	}

	public AnnealingScheduler(){
		this(20, 100, 0.045);
	}*/

	public AnnealingScheduler(double T0, double alpha, double Tmin){
		this.T0 = T0;
		this.alpha = alpha;
		this.Tmin = Tmin;

		T = T0;
	}

	public AnnealingScheduler(){
		this(0.0001, 0.7, 0.00001);
	}

	/**
	 * Computes the temperature of the given time.
	 * @param t the current time step
	 * @return the temperature
	 */
	public double getTemp(int t){
		/*if(t < limit){
			double res = k * Math.exp( (-1) * lam * t);
			return res;
		} else {
			return 0.0;
		}*/
		T *= alpha;

		if (T < Tmin)
			T = 0;

		return T;
	}

	/**
	 * Computes the number of evaluted neighbors for the given time.
	 * This number is higher in the beginning and lower towarads the end.
	 * limit - t/limit * 10
	 * @param t the current time
	 * @return the number of evaluted neighbors.
	 */
	public int getNumberOfEvaluations(int t){
/*		if(t < limit){
			//double res = limit - (double) t / limit * 10;
			double res = limit / t / 10;
			return (int)res;
		}else{
			return 0;
		}*/

		return (int)(T/T0 * 10);

		//return 5;
	}
}
