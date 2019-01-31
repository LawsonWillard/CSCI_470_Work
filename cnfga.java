// Assignment: Genetic Algorithms
// Last modified: 1/15/19
// Shell of a
// Java program which uses a genetic algorithm to find a solution to
// an equation in conjunctive normal form.

// Program takes as input a  string representing a formula written in 
// conjunctive normal form and prints a string of 1's and 0's meant to be the
// instantiations of the variables as true/false from left to write
// which satisfy the formula.
// 
// Use the Formula class provided to parse a formula read in from the
// command line. 
// Get the number of unique variables in the equation
// Randomly create a population of strings which assign a 0 or 1 to 
// each variable in the list. Example: String 1011
// represents an assignment where a is True, b is False,
// c is True, and d is True. (Since any formula's variables can be 
// mapped into numbers, we won't worry about fancy
// variable names.)
// While not done do
//     Evaluate fitness of each member of the population
//     (We'll use a fitness function which counts the 
//      number of clauses which evaluate to TRUE.)
//     If a member is "fit enough," 
//        done := true.
//     else
//        Select members based on fitness
//        Produce offspring using crossover and mutation.
//        Replace, based on fitness, candidates of the population
//        with the offspring. 

import java.io.*;
import java.util.*;
import java.util.Scanner;

public class cnfga {
   
   // You can experiment with the mutationRate.
   private static final double mutationRate = 0.015;

   public static void main(String [] args) {

       /*
       Scanner keyboard = new Scanner(System.in);

       System.out.println("Please enter the CNF formula: ");
       String n = keyboard.nextLine();
*/

       String form = args[0];
       int populationSize = 10;
       int genNum = 1;
   
      // Set a population size (use an even number)
      // Set the maximum number of generations that
      // you wish to loop through before you give up.


       int maximumGenerations = 500;
   
      // Create an initial population of random candidates.
      // (Use the number of unique variables in a formula to create 
      // candidates of appropriate size.)   

       Formula formula = new Formula(form);
       int uniqueVars;
       uniqueVars = formula.numUniqueVars;
       int fittest = 0;


       while (genNum  <= maximumGenerations && fittest < (formula.clauses.size()) ) {

           Population population = new Population(populationSize);
           population.seedPop(formula.numUniqueVars);

           Candidate parent1;
           Candidate parent2;
           Candidate childCandidate1;
           Candidate childCandidate2;


           for(int i = 0; i < population.size()/2; i++){
               Candidate temp;
               parent1 = selectParent(population,formula);

               parent2 = selectParent(population,formula);

               temp = parent2;

               childCandidate1 = crossover(parent1,parent2);

               childCandidate2 = crossover(temp,parent1);

               //If the solution exists because of the crossover print solution and exit program
               if (childCandidate1.getFitness(formula) == formula.clauses.size() || childCandidate2.getFitness(formula) == formula.clauses.size()){
                   if (childCandidate1.getFitness(formula) == formula.clauses.size()){
                       System.out.println("Fittest Candidate: ");
                       childCandidate1.printCandidate();
                       return;
                   }
                   else {
                       System.out.println("Fittest Candidate: ");
                       childCandidate2.printCandidate();
                       return;
                   }
               }
               //mutation

               if (mutationRate>Math.random()){
                   mutate(childCandidate1);
                   mutate(childCandidate2);
               }

               //Save crossover candidates
               population.saveCandidate(i,childCandidate1);
               population.saveCandidate(i+1,childCandidate2);
           }
            genNum++;
       }

       System.out.println("No Solution");

      //genNum = 1;            
      //while( the fittest candidate is not fit enough and
      //   genNum < maxGens ){
            
      // Create a new population:
      
      // Since we are creating two new candidates from two
      //   randomly-selected candidates, we'll only have to
      //   loop for 
      //   int halfPop = popSize / 2
      //   to replace the entire population
        
      // for(int i = 0; i < halfPop; i++){
          // Select two candidates; remember, the probability of 
          //   a candidate's selection is weighted by it's fitness.
          
          // Use crossover to create two new candidates
          
          // If a randomly generated number is less than
          // the mutation rate, mutate the candidate by
          // flipping a random bit.
          
          // Add the new candidates to the new population.
        }    
            
        // Get the new fittest candidate
        // genNum++;


      // If you found a fit candidate, print out
      // the solution; else, print "Solution not found."


      
      
   private static Candidate selectParent(Population pop, Formula formula) {
        // Returns a random candidate from the population
       Candidate parent;
       boolean chosen = false;

       parent = pop.getCandidate((int)((Math.random() * 100) % pop.size()));
       for (int i = 0; i < pop.size(); i++){
           if (!selectionWheel(parent, formula, formula.clauses.size())){
               return parent;
           }
           else {
               parent = pop.getCandidate((int)((Math.random() * 100) % pop.size()));
           }
       }
       return parent;
   }
   private static Boolean selectionWheel(Candidate candidate, Formula formula,
         int optimalFitNum) {
       // Uses the optimal fitness number, the
       // fitness of the candidate, and percentages set in the
       // "odds" array
       // to decide whether to keep a candidate.
       // Return True if candidate is to be kept; false otherwise.

       optimalFitNum = formula.clauses.size();

       if (optimalFitNum == candidate.getFitness(formula)) {
           return true;
       }
       double odds;
       odds = (double)candidate.getFitness(formula) / optimalFitNum;

       if (odds >= .4){
           odds = .4;
       }
       else if (odds < .4 && odds >= .3){
           odds = .3;
       }
       else if (odds < .3 && odds >= .15){
           odds = .15;
       }
       else if (odds < .15 && odds >= .1){
           odds = .1;
       }
       else if (odds < .1){
           odds = .05;
       }

       double rand = (Math.random()%100);

       if (rand <= (odds)){
           return true;
       }
       else{
           return false;
       }



       //odds[] represents the categories of the chances
       //  of a candidate being chosen. A candidate in the first category
       //  has a 5% chance of being selected, in the second -- 10%, etc.
       // int[] odds = {5, 10, 15, 30, 40};


       // Normally, we expect fitness scores to be larger than
       // the number of categories given in odds.length,
       // so we need a way to map them into these categories;
       // if the optimal fitness is less than the number of categories
       // in odds, we will simply
       // return true and keep the candidate.
       // Otherwise,
       // find groupNum -- the proper index into the odds
       // array based on fitness; the best fit candidates
       // should have the largest odds of success.
   }
    
    
    private static Candidate crossover(Candidate parent1, Candidate parent2){
      // Uses standard midpoint crossover to create a new candidate.
      // If the formula has an odd number of variables,
      // the midpoint can be approximated with truncation.
        Candidate newCandidate = new Candidate(parent1.getLength());

        if (parent1.getLength() %2 != 0){
            for (int i = 0; i<(parent1.getLength()/2); i++){
                newCandidate.setValue(i, parent1.getValue(i));
            }
            for (int i = (parent2.getLength()/2); i < parent2.getLength(); i++){
                newCandidate.setValue(i, parent2.getValue(i));
            }
        }
        else {
            for (int i = 0; i<parent1.getLength()/2; i++){
                newCandidate.setValue(i, parent1.getValue(i));
            }
            for (int i = parent2.getLength()/2; i < parent2.getLength(); i++){
                newCandidate.setValue(i, parent2.getValue(i));
            }
        }
        return newCandidate;
   }
    
    
    private static void mutate(Candidate candidate) {
      // Flip a random bit in the candidate.

        int rand = (int)((Math.random() *100) % candidate.getLength());
        boolean negate;

        negate = candidate.getValue(rand);
        candidate.setValue(rand,!negate);
    }
}

