package org.licenta.d4elders.algorithm;

import java.util.SortedSet;
import java.util.TreeSet;

import org.licenta.d4elders.model.SolutionOld;
import org.licenta.d4elders.model.SolutionOld.GeneType;

public class PathRelinking {

	public static SortedSet<SolutionOld> pathRelinking(SolutionOld drone, SolutionOld queen){
		SortedSet<SolutionOld> set = new TreeSet<SolutionOld>();

		while(drone.getFitness() < queen.getFitness()){
			drone = queen.combineSingleGenotype(drone, GeneType.getRandom());
			set.add(drone);
		}

		return set;
	}
}
