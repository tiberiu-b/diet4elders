package d4elders.algorithm;

import java.util.SortedSet;
import java.util.TreeSet;

import d4elders.model.Solution;
import d4elders.model.Solution.GeneType;

public class PathRelinking {

	public static SortedSet<Solution> pathRelinking(Solution drone, Solution queen){
		SortedSet<Solution> set = new TreeSet<Solution>();

		while(drone.getFitness() < queen.getFitness()){
			drone = queen.combineSingleGenotype(drone, GeneType.getRandom());
			set.add(drone);
		}

		return set;
	}
}
