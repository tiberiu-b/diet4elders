package org.licenta.d4elders.algorithm;

import static org.junit.Assert.*;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.licenta.d4elders.dal.helper.RandomSolutionsGenerator;
import org.licenta.d4elders.model.Solution;
import org.licenta.d4elders.model.user_profile.NutritionalRecommandationHelper;
import org.licenta.d4elders.model.user_profile.UserProfileStub;

public class PathRelinkingTest {

	@Before
	public void init(){
		new NutritionalRecommandationHelper(new UserProfileStub());
	}

	@Test
	public void testPathRelinking() {
		SortedSet<Solution> solutions = RandomSolutionsGenerator.generateRandomSolutions(5);
		Solution queen = solutions.last();
		Solution drone = solutions.first();

		SortedSet<Solution> relinkedPath = PathRelinking.pathRelinking(drone, queen);

		for(Solution brood : relinkedPath){
			System.out.println(brood.getFitness());
		}
	}

}
