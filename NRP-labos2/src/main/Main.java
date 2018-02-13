package main;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgap.Chromosome;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;

import domain.ClassStartTimeLocation;
import domain.Classroom;
import domain.Duration;
import domain.Profesor;
import domain.Student;


public class Main {
	
	private static final int POPULATION_SIZE = 100;
	private static final int MAX_ALLOWED_EVOLUTIONS = 5000;
	public static void main(String[] args) {
		
		Boolean postojiPreklapanje = true;
		
		try{
		
		ScheduleFitnessFunction fitnessFunction = new ScheduleFitnessFunction();
		ScheduleConfiguration conf = new ScheduleConfiguration();
		conf.setFitnessFunction(fitnessFunction);
		conf.setPopulationSize(POPULATION_SIZE);

		List<Student> studenti1 = new ArrayList<Student>();
		List<Student> studenti2 = new ArrayList<Student>();
		
		for (int i = 1; i<=10; i++){
			studenti1.add(new Student(i));
		}
		
		studenti2.add(new Student(5));
		studenti2.add(new Student(7));
		
		List<Blok> raspored = new ArrayList<Blok>();
		
		try {
			raspored.add(new Blok(conf, new Duration(90), new Profesor(1), studenti1));
			raspored.add(new Blok(conf, new Duration(60), new Profesor(1), studenti1));
			raspored.add(new Blok(conf, new Duration(90), new Profesor(2), studenti2));
			raspored.add(new Blok(conf, new Duration(120), new Profesor(1), studenti1));
			raspored.add(new Blok(conf, new Duration(90), new Profesor(1), studenti1));
			raspored.add(new Blok(conf, new Duration(120), new Profesor(1), studenti1));
			raspored.add(new Blok(conf, new Duration(90), new Profesor(1), studenti1));
			raspored.add(new Blok(conf, new Duration(90), new Profesor(2), studenti2));
			raspored.add(new Blok(conf, new Duration(120), new Profesor(1), studenti1));
			raspored.add(new Blok(conf, new Duration(90), new Profesor(1), studenti1));
			raspored.add(new Blok(conf, new Duration(90), new Profesor(1), studenti1));
			raspored.add(new Blok(conf, new Duration(90), new Profesor(1), studenti1));
			raspored.add(new Blok(conf, new Duration(150), new Profesor(2), studenti2));
			raspored.add(new Blok(conf, new Duration(90), new Profesor(1), studenti1));
			raspored.add(new Blok(conf, new Duration(90), new Profesor(1), studenti1));
			raspored.add(new Blok(conf, new Duration(90), new Profesor(1), studenti1));
			raspored.add(new Blok(conf, new Duration(90), new Profesor(2), studenti2));
			raspored.add(new Blok(conf, new Duration(180), new Profesor(1), studenti1));
			raspored.add(new Blok(conf, new Duration(90), new Profesor(1), studenti1));
			raspored.add(new Blok(conf, new Duration(240), new Profesor(1), studenti1));
			raspored.add(new Blok(conf, new Duration(90), new Profesor(2), studenti2));
			raspored.add(new Blok(conf, new Duration(90), new Profesor(1), studenti1));
			raspored.add(new Blok(conf, new Duration(90), new Profesor(1), studenti1));
			raspored.add(new Blok(conf, new Duration(90), new Profesor(1), studenti1));
			raspored.add(new Blok(conf, new Duration(90), new Profesor(1), studenti1));			
			
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Population population = generateRandomPopulation(conf, raspored);
		conf.setSampleChromosome(population.toChromosomes()[0]);
		
		Genotype populationSchedule = new Genotype(conf, population);
		
		
		
		long startTime = System.currentTimeMillis();

		int i = 0;
		do {

			IChromosome bestSolutionSoFar = populationSchedule.getFittestChromosome();
			
			if((i+1) % 1000 == 0){
				System.out.println("\nNajbolje rješenje nakon " + (i + 1) + ". evolucije:");
				printSolution(bestSolutionSoFar);
			}
			populationSchedule.evolve();
			i++;
		}while(i<MAX_ALLOWED_EVOLUTIONS);
		
		long endTime = System.currentTimeMillis();
		
		System.out.println("Ukupno vrijeme trajanja : " + (((double) endTime - startTime) / 1000) + " sekundi");
		
		
		}catch(InvalidConfigurationException e){
			e.printStackTrace();
		}
		
	}
	
	private static Population generateRandomPopulation(ScheduleConfiguration conf, List<Blok> blokSegments)
			throws InvalidConfigurationException {
		
		IChromosome[] chromosomes = new Chromosome[POPULATION_SIZE];
		for (int i = 0; i < POPULATION_SIZE; i++) {
			Blok[] genes = new Blok[blokSegments.size()];
			Collections.shuffle(blokSegments);
			int n = 0;
			for (Blok segment : blokSegments) {
				genes[n] = segment;
				n++;
			}
			Chromosome newChromosome = new Chromosome(conf, genes);
			chromosomes[i] = newChromosome;
		}
		Population population = new Population(conf, chromosomes);
		return population;
	}
	
	private static void printSolution(IChromosome solution) {
		List<Blok> raspored = new ArrayList<>();
		for(int i=0;i<solution.size();i++){
			raspored.add((Blok) solution.getGene(i));
		}
		
		Collections.sort(raspored);
		
		for (Blok tl : raspored){
			System.out.println("Dan: " + (DayOfWeek) ((List<Object>) tl.getAllele()).get(0) + 
							   ", Vrijeme: " + (LocalTime) ((List<Object>) tl.getAllele()).get(1) + 
							   ", Trajanje: " + tl.getDuration() + 
							   ", Profesor: " + tl.getProfesor().getId());
		}
		
		System.out.println("Fitness: " + solution.getFitnessValue() + "\n\n");
	}
}
