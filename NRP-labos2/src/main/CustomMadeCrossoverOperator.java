package main;

import java.util.List;
import java.util.Random;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.GeneticOperator;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.impl.CrossoverOperator;

public class CustomMadeCrossoverOperator implements GeneticOperator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1526923970907794826L;
	
	
	@Override
	public void operate(Population a_population, List a_candidateChromosomes) {
		
		int x,y;
		int size = a_population.size();
		if(size == 1)
			return;
		
		Random rand = new Random();
		x = rand.nextInt(size);
		
		do{
			y = rand.nextInt(size);
		}while(x==y);
		
		IChromosome chromX = a_population.getChromosome(x);
		IChromosome chromY = a_population.getChromosome(y);
		IChromosome copyX = (IChromosome) chromX.clone();
		IChromosome copyY = (IChromosome) chromY.clone();
		
		Gene[] geneX = copyX.getGenes();
		Gene[] geneY = copyY.getGenes();
		
		int geneSize = geneX.length;
		
		int randomGene = rand.nextInt(geneSize);
		
		Gene[] newGenesX = new Gene[geneSize];
		Gene[] newGenesY = new Gene[geneSize];
		
		Gene gene1, gene2;
		Object allele;
		for (int i=randomGene; i<geneSize;i++){
			
			gene1 = geneX[i];
			gene2 = geneY[i];
			
			allele = gene1.getAllele();
			gene1.setAllele(gene2.getAllele());
			gene2.setAllele(allele);
//			if(i<randomGene){
//				newGenesX[i] = geneX[i];
//				newGenesY[i] = geneY[i];
//			}
//			else{
//				newGenesX[i] = geneY[i];
//				newGenesY[i] = geneX[i];
//			}
		}
		
		
//		try {
//			copyX.setGenes(newGenesX);
//			copyY.setGenes(newGenesY);
//		} catch (InvalidConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		a_candidateChromosomes.add(copyX);
		a_candidateChromosomes.add(copyY);

	}

}
