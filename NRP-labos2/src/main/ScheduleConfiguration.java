package main;

import org.jgap.Configuration;
import org.jgap.DefaultFitnessEvaluator;
import org.jgap.InvalidConfigurationException;
import org.jgap.event.EventManager;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.ChromosomePool;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.GABreeder;
import org.jgap.impl.GaussianMutationOperator;
import org.jgap.impl.MutationOperator;
import org.jgap.impl.StockRandomGenerator;

public class ScheduleConfiguration extends Configuration {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ScheduleConfiguration() {
		try {
			setBreeder(new GABreeder());
			setRandomGenerator(new StockRandomGenerator());
			setEventManager(new EventManager());
			BestChromosomesSelector bestChromsSelector = new BestChromosomesSelector(this, 0.90d);
			bestChromsSelector.setDoubletteChromosomesAllowed(true);
			addNaturalSelector(bestChromsSelector, false);
			setMinimumPopSizePercent(1);
			setSelectFromPrevGen(0.8);
			setKeepPopulationSizeConstant(true);
			setPreservFittestIndividual(true);
			setFitnessEvaluator(new DefaultFitnessEvaluator());
			setChromosomePool(new ChromosomePool());
			addGeneticOperator(new MutationOperator(this));
			addGeneticOperator(new CustomMadeCrossoverOperator());
			//addGeneticOperator(new CrossoverOperator(this));
		} catch (InvalidConfigurationException e) {
			throw new RuntimeException(
					"Fatal error: DefaultConfiguration class could not use its " +
					"own stock configuration values. This should never happen. " +
					"Please report this as a bug to the JGAP team.");
		}
	}
}
