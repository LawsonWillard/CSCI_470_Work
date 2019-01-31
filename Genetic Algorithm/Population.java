// Shell for the Population class of the GA assignment.
// Last modified: 1/15/19
public class Population {

    Candidate[] candidates;

    
    // Constructor
    public Population(int populationSize) {
        candidates = new Candidate[populationSize];
    }
    
    public void seedPop(int candidateLength) {
        // Seed a population with candidates of length candidateLength
        for (int i = 0; i < candidates.length; i++){
            Candidate candidate1 = new Candidate(candidateLength);
            candidate1.seedCandidate();
            saveCandidate(i,candidate1);
        }
    }      


    public Candidate getCandidate(int index) {
        // Return the candidate at a given index
        Candidate candidate;
        candidate = candidates[index];
        return candidate;
    }

    public Candidate getFittest(Formula formula) {
      // Returns the fittest candidate of the population wrt a formula
      Candidate fittest = candidates[0];

      for(int i = 1; i < candidates.length; i++) {
          if (Math.max(candidates[i].getFitness(formula), fittest.getFitness(formula)) == candidates[i].getFitness(formula)){
              fittest = candidates[i];
          }
      }
      return fittest;
    }

  
    public int size() {
       // Returns the population size
        return candidates.length;
    }

    public void saveCandidate(int index, Candidate candidate) {
       // Adds a candidate to the population
        candidates[index] = candidate;
    }
}