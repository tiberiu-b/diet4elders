package org.licenta.d4elders.algorithm;

import static org.junit.Assert.*;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.licenta.d4elders.main.InitialSolutionsGenerator;
import org.licenta.d4elders.model.SolutionOld;
import org.licenta.d4elders.model.user_profile.NutritionalRecommandationHelper;
import org.licenta.d4elders.model.user_profile.UserProfileStub;

public class PathRelinkingTest {

	@Before
	public void init(){
		new NutritionalRecommandationHelper(new UserProfileStub());
	}

	@Test
	public void testPathRelinking() {
		SortedSet<SolutionOld> solutions = InitialSolutionsGenerator.generateRandomSolutions(5);
		SolutionOld queen = solutions.last();
		SolutionOld drone = solutions.first();

		SortedSet<SolutionOld> relinkedPath = PathRelinking.pathRelinking(drone, queen);

		for(SolutionOld brood : relinkedPath){
			System.out.println(brood.getFitness());
		}
	}

}
