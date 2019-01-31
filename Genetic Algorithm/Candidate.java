// Shell for the Candidate class of the GA assignment.
// Last Modified: 1/15/19

import java.io.*;
import java.util.BitSet;

public class Candidate {
    
    private BitSet truthVals;
    private int numVals;
    
    public Candidate(int length){
       truthVals = new BitSet(length);
       numVals = length;
    }
    
    public void seedCandidate(){
    // Randomly sets bits in the candidate
        String num = "";
        for(int i = 0; i < numVals; i++){
            num += num.valueOf((int)((Math.round(Math.random())%2)));
        }

        for (int i = 0; i < numVals; i++){
            int n = num.charAt(i);
            if ((n - '0') == 1){
                setValue(i,true);
            }
            else{
                setValue(i, false);
            }
        }
    }
    
    public void setValue(int index, boolean val){
    // Sets the bit at the given index to correspond to val.
        truthVals.set(index,val);
    }
    
    public boolean getValue(int index){

        if(truthVals.get(index) == true){
            return true;
        }
        else {
            return false;
        }
      // Returns the value at a given index
    }
    
    public int getLength(){
      // Returns the length of the Candidate
        return numVals;
    }
    
    public int getFitness(Formula formula) {
      // Returns the fitness of a candidate; this is the number of clauses
      // in a formula made true by the candidate.
      // Since we are trying to focus on the GA aspect of the
      // project, we simply associate variable 'a' with index 0
      // in the candidate. Subsequent indices represent variables
      // 'b', 'c', etc.
        int fitness = 0;
        String negation = "-";



        for(int i = 0; i < formula.clauses.size(); i++){
            //For each clause if one var is true then add one fitness to the total fitness level

            for(int k = 0; k <= formula.numUniqueVars; k++){
                if (formula.clauses.get(i).contains(Character.toString((char)(97+k))) == true ||
                        formula.clauses.get(i).contains(negation + Character.toString((char)(97+k))) == true){

                    // if second part of or is true then flip the answer
                    if( formula.clauses.get(i).contains(negation + Character.toString((char)(97+k))) == true){
                        if(truthVals.get(k) != true){
                            fitness+=1;
                            break;
                        }
                    }
                    else {
                        if (truthVals.get(k) == true) {
                            fitness += 1;
                            break;
                        }
                    }
                }
            }

        }

        return fitness;
    }
    
    public void printCandidate(){
      // Prints out the candidate
        String candidate = "";
        for (int i = 0; i < numVals; i++){
            if (truthVals.get(i) == true){
                candidate += "1";
            }
            else {
                candidate += "0";
            }
        }
        System.out.println(candidate);
    }

}