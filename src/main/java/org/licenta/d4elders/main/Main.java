package org.licenta.d4elders.main;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by cristiprg on 1/18/2015.
 */
public class Main {

    public static void main(String args[]) {

        long startTime = System.currentTimeMillis();

        // an initial set of solutions (bees) is generated
        SortedSet<Solution> solutions = InitialSolutionsGenerator.generateRandomSolutions();

        // the bee with the highest fitness function is selected, the others become drones
        Solution queen = solutions.last();

        // Do .. while the queen still has energy
        int nrOfIterations = 0;
        while (queen.hasEnergy() && queen.hasSpeed()) {
            nrOfIterations++;
            int nrOfDronesTheQueenMatedWith = 0;

            System.out.println("------\nQueen:" + queen + " Fitness: " + queen.getFitness());
            SortedSet<Solution> broods = new TreeSet<Solution>();
            for (Solution drone : solutions) {

                if (queen.equals(drone)) {
                    // This is the queen actually.
                    continue;
                }
                // if mating probability with drone > threshold => queen mates with drone
                // TODO: adjust the threshold
                double probToMateDrone = queen.probabilityToMateDrone(drone);
                if (probToMateDrone > Solution.probabilityToMateDroneThreshold) {
                    nrOfDronesTheQueenMatedWith++;
                    broods.addAll((queen.createBroods(drone)));
                }
            }

            // If there is no drone with good enough fitness generate new random set of solutions
            // This step is not documented in the algorithm, should be devised if needed or not
            if (broods.isEmpty()) {
                System.out.println("Generating new set of solutions..");
                solutions = InitialSolutionsGenerator.generateRandomSolutions();
                queen.nextIteration();
                continue;
            }

            // The old drones die, thus the new drones will be top 40 broods
            // that have the best fitness (take the last 40 broods)
            solutions = new TreeSet<Solution>();
            int nrOfBadBroods = broods.size() - 40;
            for (Solution br : broods) {
                if (nrOfBadBroods > 0)
                    nrOfBadBroods--;
                else
                    solutions.add(br);
            }
            //printNewSetOfSolutions(solutions);

            // the best brood is selected as the queen
            Solution bestBrood = broods.last();
            // if bestBrood has a greater fitness function then
            if (bestBrood.compareTo(queen) > 0) {
                queen = bestBrood;
                System.out.println("Found better! Queen is replaced with brood");
            }

            System.out.println("Nr of MatingDrones: " + nrOfDronesTheQueenMatedWith + ", Broods Generated:"
                    + broods.size());

            // Reduce speed and energy of queen
            queen.nextIteration();
        }

        // Print results
        System.out.println("___\nFinal Result:");
        System.out.println(queen + " Fitness: " + queen.getFitness());
        System.out.println("Number of iterations: " + nrOfIterations);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Duration of execution(in millis): " + elapsedTime);
    }

    private static void printNewSetOfSolutions(SortedSet<Solution> ss) {
        System.out.print("New Set: ");
        for (Solution s : ss) {
            System.out.print(s + "->F:" + s.getFitness() + ", ");
        }
        System.out.println();
    }

}