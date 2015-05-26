package org.licenta.d4elders.algorithm;

import static org.junit.Assert.*;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import d4elders.algorithm.PathRelinking;
import d4elders.dal.helper.RandomSolutionsGenerator;
import d4elders.model.Solution;
import d4elders.model.user_profile.NutritionalRecommandationHelper;
import d4elders.model.user_profile.UserProfileStub;

public class PathRelinkingTest {

	@Before
	public void init() {
		new NutritionalRecommandationHelper(new UserProfileStub());
	}

	@Test
	public void testPathRelinking() {
		RandomSolutionsGenerator gen = new RandomSolutionsGenerator();
		SortedSet<Solution> solutions = gen.generateRandomSolutions(5);
		Solution queen = solutions.last();
		Solution drone = solutions.first();

		SortedSet<Solution> relinkedPath = PathRelinking.pathRelinking(drone,
				queen);

		for (Solution brood : relinkedPath) {
			System.out.println(brood.getFitness());
		}
	}

}
