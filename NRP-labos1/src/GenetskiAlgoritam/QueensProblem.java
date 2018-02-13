package GenetskiAlgoritam;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.AveragingCrossoverOperator;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.GaussianMutationOperator;
import org.jgap.impl.IntegerGene;
import org.jgap.impl.MutationOperator;
import org.jgap.impl.RangedSwappingMutationOperator;

public class QueensProblem {
	
	public static final int BOARD_SIZE = 10;
	private static final int MAX_ALLOWED_EVOLUTIONS = 100000;
	private static final int POPULATION_SIZE = 10;
	
	public static void main(String[] args){
		
		Configuration conf = new DefaultConfiguration();
		FitnessFunction fitnessFunction = new PlaceQueensFitnessFunction(BOARD_SIZE);
		try {
			
			conf.setFitnessFunction(fitnessFunction);
			conf.addGeneticOperator(new CrossoverOperator(conf));
			//conf.addGeneticOperator(new MutationOperator(conf, 1000));
			conf.addGeneticOperator(new RangedSwappingMutationOperator(conf, 2));
			//conf.addGeneticOperator(new GaussianMutationOperator(conf, 0.0005));
			conf.setPreservFittestIndividual(true);
			conf.setKeepPopulationSizeConstant(true);
			
			int chromosomeSize = BOARD_SIZE;
			Gene[] sampleGenes = new Gene[chromosomeSize];
			
			for (int i = 0; i < chromosomeSize; i++) {
				sampleGenes[i] = new IntegerGene(conf, 0, BOARD_SIZE-1);
			}
			
			Chromosome sampleChromosome = new Chromosome(conf, sampleGenes);

			conf.setSampleChromosome(sampleChromosome);

			conf.setPopulationSize(POPULATION_SIZE);

			Genotype population = Genotype.randomInitialGenotype(conf);
			
			long startTime = System.currentTimeMillis();
			
			for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {

				IChromosome bestSolutionSoFar = population.getFittestChromosome();
				int napadajuce = PlaceQueensFitnessFunction.numberOfAttackingQueens(bestSolutionSoFar);
				if(napadajuce == 0){
					System.out.println("\nKonaèno rješenje nakon " + i + ". evolucije (" + napadajuce + " napada):");
					printSolution(bestSolutionSoFar);
					break;
				}
				
				System.out.println("\nNajbolje rješenje nakon " + i + ". evolucije (" + napadajuce + " napada):");
				printSolution(bestSolutionSoFar);
				
				population.evolve();

				System.out.println();
			}
			
			long endTime = System.currentTimeMillis();
			
			System.out.println("Ukupno vrijeme trajanja : " + (((double) endTime - startTime) / 1000) + " sekundi");
			
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	private static void printSolution(IChromosome solution) {
		int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
		
		for (int i = 0; i < solution.size(); i++) {
			board[i][((Integer) solution.getGene(i).getAllele()).intValue()] = 1;
		}
		
		System.out.print("   ");
		for (int i = 0; i < BOARD_SIZE - 1; i++){
			System.out.print("______");
		}
		System.out.print("_____\n");
		//System.out.print("   _______________________\n");
		for (int i = 0; i < BOARD_SIZE; i++){
			System.out.print("  ");
			for (int j = 0; j<BOARD_SIZE; j++){
				System.out.print("|     ");
			}
			System.out.print("|\n");
			//System.out.print("  |     |     |     |     |\n");
			
			for(int j = 0; j < BOARD_SIZE; j++){
				System.out.print("  |  " + board[i][j]);
			}
			System.out.print("  |\n  ");
			for (int z = 0; z < BOARD_SIZE; z++){
				System.out.print("|_____");
			}
			System.out.print("|\n");
			//System.out.print("  |\n  |_____|_____|_____|_____|\n");
		}
		
		System.out.println("\nFitness: " + solution.getFitnessValue());
	}
}
