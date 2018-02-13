package GenetskiAlgoritam;
import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class PlaceQueensFitnessFunction extends FitnessFunction{
	private final int m_boardSize;
	
	public PlaceQueensFitnessFunction(int a_boardSize){
		if(a_boardSize < 4){
			throw new IllegalArgumentException("Ploèa mora biti minimalne velièine 4x4");
	        }
		
		m_boardSize = a_boardSize;
		}
	
	@Override
	public double evaluate(IChromosome a_potentialSolution){
		int attackingQueens = numberOfAttackingQueens(a_potentialSolution);
		
		double fitness = (5 * m_boardSize - attackingQueens) * 1000;
		
		fitness = Math.max(1, fitness);
		
		return fitness;
	}
	
	public static int numberOfAttackingQueens(IChromosome a_potentialSolution){
		
		return numberOfAttackingQueensSameRow(a_potentialSolution) + numberOfAttackingQueensDiagonal(a_potentialSolution);
	}
	
	private static int numberOfAttackingQueensSameRow(IChromosome a_potentialSolution){
		int numberAttackingQueens = 0;
		
		//dodaj one koje su u istom retku
		for (int i = 0; i < a_potentialSolution.size() - 1; i++){
			for (int j = i + 1; j < a_potentialSolution.size(); j++){
				if(((Integer) a_potentialSolution.getGene(i).getAllele()).intValue() == 
				   ((Integer) a_potentialSolution.getGene(j).getAllele()).intValue()){
					numberAttackingQueens++;
				}
			}
		}
		
		return numberAttackingQueens;
	}
	
	private static int numberOfAttackingQueensDiagonal(IChromosome a_potentialSolution){
		int numberAttackingQueens = 0;
		
		for (int i = 0; i < a_potentialSolution.size() - 1; i++){
			for (int j = i + 1; j < a_potentialSolution.size(); j++){
				if( Math.abs(i-j) == Math.abs(((Integer) a_potentialSolution.getGene(i).getAllele()).intValue() - 
											  ((Integer) a_potentialSolution.getGene(j).getAllele()).intValue())){
					numberAttackingQueens++;
				}
			}
		}
		
		return numberAttackingQueens;
	}
}
