package org.licenta.d4elders.main;

import org.licenta.d4elders.algorithm.HoneyBeeMatingOptimiziation;
import org.licenta.d4elders.algorithm.MainAlgorithm.RunInformation;
import org.licenta.d4elders.helper.model.GenerateSQLScripts;


/**
 * Created by cristiprg on 1/18/2015.
 */
public class Main {

/*
    public static void main(String args[]) {

        HoneyBeeMatingOptimiziation HBMO = new HoneyBeeMatingOptimiziation();
        Solution queen = HBMO.performAlgorithm();
        RunInformation info = HBMO.getLastRunInformation();

        // Print results
        System.out.println("___\nFinal Result:");
        System.out.println(queen + " Fitness: " + queen.getFitness());
        System.out.println("Number of iterations: " + info.nrOfItertions);

        System.out.println("Duration of execution(in millis): " + info.duration);
    }
*/
	public static void main(String args[]) {
		Diet4Elders d4e = new Diet4Elders();
		//d4e.run();
		GenerateSQLScripts gen = new GenerateSQLScripts();
		
	}


}