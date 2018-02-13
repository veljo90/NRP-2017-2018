package main;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.jgap.BaseGene;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;
import org.jgap.UnsupportedRepresentationException;

import domain.ClassStartTimeLocation;
import domain.Classroom;
import domain.Duration;
import domain.Profesor;
import domain.Student;

public class Blok extends BaseGene implements Gene, Serializable{
	
	private DayOfWeek day;
	private LocalTime startTime;
	private Classroom classroom;
	private Duration duration;
	private Profesor profesor;
	private List<Student> studenti;
	
	public Blok(Configuration conf,  
				Duration duration, 
				Profesor profesor) throws InvalidConfigurationException {
		
		super(conf);
		this.duration = duration;
		this.profesor = profesor;
		
		
		RandomGenerator rand = conf.getRandomGenerator();
		setToRandomValue(rand);
	}
	
	public Blok(Configuration conf,  
			Duration duration, 
			Profesor profesor,
			List<Student> studenti) throws InvalidConfigurationException {
	
	super(conf);
	this.duration = duration;
	this.profesor = profesor;
	this.studenti = studenti;
	
	RandomGenerator rand = conf.getRandomGenerator();
	setToRandomValue(rand);
}

	public Blok(Configuration conf) throws InvalidConfigurationException {
		super(conf);
		
		RandomGenerator rand = conf.getRandomGenerator();
		setToRandomValue(rand);
	}

	@Override
	public int compareTo(Object arg0) {
		
		int comparison = this.day.compareTo(((Blok) arg0).getDay());
		if (comparison != 0)
			return comparison;
		
		comparison = this.startTime.compareTo(((Blok) arg0).getStartTime());
		if (comparison != 0)
			return comparison;
		
		comparison = this.duration.compareTo(((Blok) arg0).getDuration());
		if (comparison != 0)
			return comparison;
		
//		comparison = this.classroom.compareTo(((Blok) arg0).getClassroom());
//		if (comparison != 0)
//			return comparison;
		
		comparison = this.profesor.compareTo(((Blok) arg0).getProfesor());
		if (comparison != 0)
			return comparison;
	
		return 0;
	}

	@Override
	public void applyMutation(int index, double percentage) {

		int newDay;
		int newTime;


		RandomGenerator rand = getConfiguration().getRandomGenerator();
		if(rand.nextFloat() < 0.1)
		{
			newDay = this.day.getValue() + (int) Math.round(5 * percentage);
			if (newDay < 1 || newDay>5){
				newDay = rand.nextInt(5);
				newDay++;
			}
			day = DayOfWeek.of(newDay);
		}else{	
			newTime = this.startTime.toSecondOfDay() + (15 * 60 * (int) Math.round(40 * percentage));
			if(newTime<28800 || newTime > 64800){
				newTime = rand.nextInt(41) * 15 * 60 + 28800;
			}
			startTime = LocalTime.ofSecondOfDay(newTime);
			//FIXME: dodaj mutaciju ucione
		}
	}

	@Override
	public String getPersistentRepresentation() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setAllele(Object arg0) {
		this.day = (DayOfWeek) ((List<Object>) arg0).get(0);
		this.startTime = (LocalTime) ((List<Object>) arg0).get(1);
	//FIXME: dodaj uèione
	}

	@Override
	public void setToRandomValue(RandomGenerator arg0) {
		int day = arg0.nextInt(5);
		day++;
		
		int hour = arg0.nextInt(11);
		hour += 8;
		int minute = 0;
		int tmp = arg0.nextInt(4);
		switch (tmp){
			case 0:
				minute = 0;
				break;
			case 1:
				minute = 15;
				break;
			case 2:
				minute = 30;
				break;
			case 3:
				minute = 45;
				break;
		}
		
		this.day = DayOfWeek.of(day);
		this.startTime = LocalTime.of(hour, minute);
	}

	@Override
	public void setValueFromPersistentRepresentation(String arg0)
			throws UnsupportedOperationException, UnsupportedRepresentationException {
		
		throw new UnsupportedOperationException();
	}

	@Override
	protected Object getInternalValue() {
		
		List<Object> o = new ArrayList<>(2);
		o.add(day);
		o.add(startTime);
	//FIXME: dodaj ucionu
		return o;
	}

	@Override
	protected Gene newGeneInternal() {
		Blok b=null;
		try {
			b = new Blok(getConfiguration(), duration, profesor, studenti);
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return b;
		//return this;
	}
	
	@Override
	public int size() {
	    return 2;
	  }

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public List<Student> getStudenti() {
		return studenti;
	}

	public void setStudenti(List<Student> studenti) {
		this.studenti = studenti;
	}
	
	public DayOfWeek getDay() {
		return day;
	}

	public void setDay(DayOfWeek day) {
		this.day = day;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public Classroom getClassroom() {
		return classroom;
	}

	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}
	
	public int getTimeInMinutes(){
		return startTime.getHour()*60 + startTime.getMinute();
	}
	
	public Boolean postojiPreklapanje(){
		return true;
	}

}
