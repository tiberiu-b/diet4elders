package org.licenta.d4elders.algorithm;

import static org.junit.Assert.*;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;
import org.licenta.d4elders.main.InitialSolutionsGenerator;
import org.licenta.d4elders.main.Solution;

public class PathRelinkingTest {

	@Test
	public void testPathRelinking() {
		SortedSet<Solution> solutions = InitialSolutionsGenerator.generateRandomSolutions(5);
		Solution queen = solutions.last();
		Solution drone = solutions.first();

		SortedSet<Solution> relinkedPath = PathRelinking.pathRelinking(drone, queen);

		for(Solution brood : relinkedPath){
			System.out.println(brood.getFitness());
		}
	}

}
