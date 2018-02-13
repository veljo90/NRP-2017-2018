package main;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;

import domain.Student;

public class ScheduleFitnessFunction extends FitnessFunction{

	@SuppressWarnings("unchecked")
	@Override
	protected double evaluate(IChromosome a_solution) {

		int preklapanja = 0;
		int rupe = 0;
		double fitness = 1000000;
		List<Gene> genes = Arrays.asList(a_solution.getGenes());
		Collections.sort(genes);
		
		for(int i = 0; i<genes.size()-1; i++){
			for(int j = i+1; j<genes.size(); j++){

				DayOfWeek danI = (DayOfWeek) ((List<Object>)genes.get(i).getAllele()).get(0);
				DayOfWeek danJ = (DayOfWeek) ((List<Object>)genes.get(j).getAllele()).get(0);

				int profesorI = ((Blok) genes.get(i)).getProfesor().getId();
				int profesorJ = ((Blok) genes.get(j)).getProfesor().getId();

				int vrijeme1 = getStartTimeInMinutes(genes, i);
				int vrijeme2 = getStartTimeInMinutes(genes, j);
				int trajanjePrvogPredavanja = ((Blok) genes.get(i)).getDuration().getMinutes();
				
				//ako su predavanja isti dan i drzi ih isti profesor
				if( danI == danJ){

					int vrijemeDoSljedecegPredavanja = vrijeme2 - vrijeme1;

					int trajanjePreklapanja = trajanjePrvogPredavanja - vrijemeDoSljedecegPredavanja;

					//zelimo da predavanja pocnu u 10:00
					if(i==0){
						rupe +=Math.abs(vrijeme1 - 600);		//10h * 60min = 600min
					}

					if(profesorI==profesorJ){
						if(trajanjePreklapanja>0){
							preklapanja += trajanjePreklapanja;
						}
						else if (trajanjePreklapanja < 0)
							rupe -= trajanjePreklapanja; //oduzimamo jer je trajanje<0
					}

					List<Student> studenti1 = ((Blok) genes.get(i)).getStudenti();
					List<Student> studenti2 = ((Blok) genes.get(j)).getStudenti();

					if(trajanjePreklapanja>0){
						for(Student student : studenti1){
							if(studenti2.contains(student)){

								if(trajanjePreklapanja > 0)
									preklapanja += trajanjePreklapanja;
								else if (trajanjePreklapanja < 0)
									rupe -= trajanjePreklapanja;
							}
						}
					}
				}
				//predavanja nisu isti dan, opet želimo da predavanje poène u 10:00h
				else{
					rupe += Math.abs(vrijeme1 - 600);		//10h * 60min = 600min
				}
			}
		}

		fitness = fitness - preklapanja*100 - rupe;

		if(fitness<0)
			fitness = 0;

		return fitness;
	}

	@SuppressWarnings("unchecked")
	private int getStartTimeInMinutes(List<Gene> genes, int i) {
		return ((LocalTime) ((List<Object>) genes.get(i).getAllele()).get(1)).getHour() * 60 + 
				((LocalTime) ((List<Object>) genes.get(i).getAllele()).get(1)).getMinute();
	}
}
