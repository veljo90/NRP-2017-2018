package domain;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

public class ClassStartTimeLocation implements Comparable{
	
	private DayOfWeek day;
	private LocalTime startTime;
	private Classroom classroom;
	
	public ClassStartTimeLocation(DayOfWeek day, LocalTime startTime){
		this.day = day;
		this.startTime = startTime;
	}
	
	public ClassStartTimeLocation(DayOfWeek day, LocalTime startTime, Classroom classroom){
		this.day = day;
		this.startTime = startTime;
		this.classroom = classroom;
	}

	@Override
	public int compareTo(Object timeLocation) {
		if(!(timeLocation instanceof ClassStartTimeLocation)){
			throw new ClassCastException("A ClassStartTimeLocation object expected.");
		}
		if (this.day.getValue() < ((ClassStartTimeLocation) timeLocation).day.getValue())
			return -1;
		else if (this.day.getValue() > ((ClassStartTimeLocation) timeLocation).day.getValue())
			return 1;
		else if (this.startTime.isBefore(((ClassStartTimeLocation) timeLocation).startTime))
			return -1;
		else if (this.startTime.isAfter(((ClassStartTimeLocation) timeLocation).startTime))
			return 1;
		else if (this.classroom != ((ClassStartTimeLocation) timeLocation).classroom)
			return 1;
		
		return 0;
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
}
